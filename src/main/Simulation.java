/*
  Simulation.java
  Version 0.1
  Project for Operating Systems Class at Tec de Monterrey 2020
  This should not be used as a submission for a project if it is not owned by you.
 */
package main;

import java.util.ArrayList;

public class Simulation {

    private Page memory[]; //Representation of virtual memory.
    private Page swap[]; //Representation of swap area.
    private int pagesAvailable;
    private final int type; //Either FIFO(0)/LRU(1)
    private ArrayList<Process> activeProcesses;
    private int systemTimestamp = 0;


    /**
     * Initializes the Simulator object, with empty memory. The values are specified in the spec doc.
     */
    public Simulation(int type){
        memory = new Page[Commons.MEMORY_SIZE/Commons.PAGE_SIZE];
        swap = new Page[Commons.SWAP_SIZE/Commons.PAGE_SIZE];
        pagesAvailable = Commons.MEMORY_SIZE/Commons.PAGE_SIZE;
        activeProcesses = new ArrayList<>();
        this.type = type;
    }

    /* ***********************
     *     Public Methods    *
     *************************/

    /**
     * Creates a process, then assigns memory to it. Used only with new processes, not when loading from swap/primary.
     * @param processID the ID of the process
     * @param processSize the size, in bytes, of the process.
     */
    public void createNewProcess(int processID, int processSize){
        if(processSize > Commons.MEMORY_SIZE){
            System.out.println("Program is too large, can't load into memory!");
            return;
        }
        Process process = new Process(processID, processSize);
        assignMemoryToNewProcess(process);
        activeProcesses.add(process);
        System.out.println("Assigned process with PID  " + processSize + " bytes of memory to process " + processID);
    }


    /**
     * Assigns memory to a new process as a whole.
     * @param process New Process that needs memory assignment.
     */
    private void assignMemoryToNewProcess(Process process){
        int pagesRequired = (int)Math.ceil(process.getProcessSize()/Commons.PAGE_SIZE);
        if(pagesRequired > pagesAvailable){
            System.out.println("The process you are trying to insert requires more pages, swapping processes out!");
            for (int i = 0; i < pagesRequired-pagesAvailable; i++){
                swapOutMemory();
            }
        }
        int pagesAssignedToProcess = 0;
        while(pagesRequired > pagesAssignedToProcess){
            Page page = new Page(pagesAssignedToProcess+1, process);
            assignPageToPrimaryMemory(process, page);
            process.addPageToPrimaryMemory(page); //Store information in PCB (associate to process)
            ++pagesAssignedToProcess;
        }
            activeProcesses.add(process);//Store information on active processes
    }

    /**
     * Assigns a page to memory
     * @param process Process Object that owns the page
     * @param pageToAssign Page object that needs to be assigned into memory. Be it new or from Swap.
     */
    private void assignPageToPrimaryMemory(Process process, Page pageToAssign){
        for (int i = 0; i < memory.length; i++){
            if(memory[i] == null){
                memory[i] = pageToAssign; //Assign the page to memory
                --pagesAvailable; //Reduce page availability
                System.out.println("Assigned page #" + i + " to process with PID " + process.getProcessId());
                break;
            }
        }
    }

    /**
     * Saves the Page to the first available Page slot in Swap Memory.
     * @param page page to save in Swap
     */
    private void sendToSwapMemory(Page page){
        for (int i = 0; i < swap.length; i++){
            if(swap[i] == null){
                page.setInserted(false);
                page.setLocationInSwap(i);
                page.setLocationInMemory(Integer.MAX_VALUE);
                page.getProcess().removePageFromPrimaryMemory(page);
                page.getProcess().addToSwapPageIndex(page);
                swap[i] = page;
                break;
            }
        }
    }

    /**
     * Frees all of the memory of a given PID. Not to be used to remove a specific number of pages, since this process
     * is destructive and will erase the whole process from memory.
     * @param processID PID of the process to be removed from memory.
     * @return the number of Page Frames that were deleted.
     */
    public int freeProcessFromMemory(int processID){
        int numberOfFramesDeleted = 0;
        //Removes the process from the control array.
        for(int i = 0; i < activeProcesses.size(); i++ ){
            if(activeProcesses.get(i).getProcessId() == processID){
                activeProcesses.remove(activeProcesses.get(i));
            }
        }

        //Deletes the Pages owned by the process using the PID.
        for (int i = 0; i < memory.length; i++){
            if(memory[i] != null && memory[i].getProcess().getProcessId() == processID){
                memory[i] = null;
                System.out.println("Removing PID " + processID + " from memory frame #" + i);
                ++pagesAvailable;
                ++numberOfFramesDeleted;
            }
        }
        return numberOfFramesDeleted;
    }

    /**
     * Given the Virtual Address and a PID of a process in Physical Memory or Swap, return the physical or swap addr.
     * @param addr Virtual Address of the process
     * @param processID PID of the process
     * @return physical address of the given virtual address
     */
    public int returnPhysicalAddress(int addr, int processID){
        //TODO check if the given address actually exists.
        //TODO case when the process is not loaded in memory.
        //TODO check if requested addr is inside the process (not a bigger or smaller (negative) addr)
        int pageNumbertoLookFor = (int)Math.floor(addr/Commons.PAGE_SIZE);
        System.out.println(pageNumbertoLookFor);
        int page = 0;
        for (int i = 0; i < memory.length; i++){
            if(memory[i].getProcess().getProcessId() == processID && page == pageNumbertoLookFor){
                return i*Commons.PAGE_SIZE+addr;
            }else{
                ++page;
            }
        }
        return page;
    }

    /* ***************** Memory Swapping Methods *******************************/

    /**
     * Serves as a wrapper for both replacement policies, to simplify coding.
     */
    private void swapOutMemory(){
        if(type == 0){
            swapUsingFIFOPolicy();
        }else if(type == 1){
            swapUsingLRUPolicy();
        }
    }

    /**
     * Will return the required page to primary memory.
     * @param requiredPageNumber recieves the page number of the page to load and then loads it into memory.
     */
    private void swapInMemory(int requiredPageNumber){
        for (int i = 0; i < swap.length; i++){
            if(swap[i].getNum() == requiredPageNumber){
                assignPageToPrimaryMemory(swap[i].getProcess(), swap[i]);
            }
        }
    }

    /* ***************** Memory Replacement Methods *******************************/

    /**
     * Removes page from memory and saves it to Swap memory.
     * In accordance to the LRU replacement policy.
     * @return the page in physical memory that was modified.
     */
    private int swapUsingLRUPolicy(){
        Page pageWithLeastRecentUsage = null;
        int lastUsage = 0;
        for (Page page : memory) {
            if (page != null && systemTimestamp - page.getLastAppearance() > lastUsage) {
                lastUsage = systemTimestamp - page.getLastAppearance();
                pageWithLeastRecentUsage = page;
            }
        }

        //Removes the page from memory and sends it over to swap memory.
        memory[pageWithLeastRecentUsage.getLocationInMemory()] = null;
        sendToSwapMemory(pageWithLeastRecentUsage);
        ++pagesAvailable;
        return pageWithLeastRecentUsage.getLocationInMemory();
    }

    /**
     * Removes page from memory and saves it to Swap memory.
     * In accordance to the LRU replacement policy.
     * @return the page in physical memory that was modified.
     */
    private int swapUsingFIFOPolicy(){
        Page pageThatEnteredFirst = null;
        int firstEntryTimestamp = Integer.MAX_VALUE;
        for (Page page : memory) {
            if (page != null && firstEntryTimestamp > page.getTimeInserted()) {
                firstEntryTimestamp = systemTimestamp - page.getTimeInserted();
                pageThatEnteredFirst = page;
            }
        }

        //Removes the page from memory and sends it over to swap memory.
        memory[pageThatEnteredFirst.getLocationInMemory()] = null;
        sendToSwapMemory(pageThatEnteredFirst);
        ++pagesAvailable;
        return pageThatEnteredFirst.getLocationInMemory();
    }



    //not prod
    public void viewSimulation(){
        System.out.println("Pages: " + java.util.Arrays.toString(memory));
        System.out.println("Swap: " + java.util.Arrays.toString(swap));
    }



}

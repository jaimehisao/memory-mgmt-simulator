/*
  Simulation.java
  Version 0.1
  Project for Operating Systems Class at Tec de Monterrey 2020
  This should not be used as a submission for a project if it is not owned by you.
 */
package main;

import java.util.ArrayList;
import java.util.LinkedList;

public class Simulation {

    private final Page[] memory;                        //Representation of virtual memory.
    private final Page[] swap;                          //Representation of swap area.
    private int pagesAvailable;                         //Stores pages available
    private int swapsIn;                                //Number of Swaps In made
    private int swapsOut;                               //Number of Swaps Out made
    private final int type;                             //Either FIFO(0)/LRU(1)
    private final ArrayList<Process> activeProcesses;   //Active process ArrayList
    private int systemTimestamp = 0;                    //Stores the timestamp


    /**
     * Initializes the Simulator object, with empty memory. The values are specified in the spec doc.
     */
    public Simulation(int type){
        memory = new Page[Commons.MEMORY_SIZE/Commons.PAGE_SIZE];
        swap = new Page[Commons.SWAP_SIZE/Commons.PAGE_SIZE];
        pagesAvailable = Commons.MEMORY_SIZE/Commons.PAGE_SIZE;
        activeProcesses = new ArrayList<>();
        swapsIn = 0;
        swapsOut = 0;
        this.type = type;
    }

    /* ***********************
     *     Public Methods    *
     *************************/

    /**
     * Creates a process, then assigns memory to it.Used only with new processes, not when loading from swap/primary.
     * @param processID the ID of the process
     * @param processSize the size, in bytes, of the process.
     * @param PrList the list of process created
     */
    public void createNewProcess(int processID, int processSize, LinkedList<Process> PrList){
        //TODO check if process with same PID doesn't exist.
        if(processSize > Commons.MEMORY_SIZE){
            System.out.println("Program is too large, can't load into memory!");
            return;
        }
        Process process = new Process(processID, processSize);
        assignMemoryToNewProcess(process);
        activeProcesses.add(process);
        System.out.println("Assigned process with size " + processSize + " bytes of memory to process " + processID);
        PrList.add(process);
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
            loadPageIntoMemory(process, page);
            process.addPageToPrimaryMemory(page); //Store information in PCB (associate to process)
            ++pagesAssignedToProcess;
        }
    }

    /**
     * Assigns a page to memory
     * @param process Process Object that owns the page
     * @param pageToAssign Page object that needs to be assigned into memory. Be it new or from Swap.
     */
    private void loadPageIntoMemory(Process process, Page pageToAssign){
        for (int i = 0; i < memory.length; i++){
            if(memory[i] == null){
                ++systemTimestamp;
                pageToAssign.setTimeInserted(systemTimestamp);
                memory[i] = pageToAssign; //Assign the page to memory
                --pagesAvailable; //Reduce page availability
                System.out.println("Assigned page" + " to process with PID " + process.getProcessId());
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
                page.setReferenceBit(false);
                page.setLocationInSwap(i);
                page.setLocationInMemory(Integer.MAX_VALUE);
                page.getProcess().removePageFromPrimaryMemory(page);
                page.getProcess().addToSwapPageIndex(page);
                swap[i] = page;
                swapsOut++;
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
        Process processToDelete = null;
        //Removes the process from the control array.
        for(int i = 0; i < activeProcesses.size(); i++ ){
            if(activeProcesses.get(i).getProcessId() == processID){
                processToDelete = activeProcesses.get(i);
                activeProcesses.remove(activeProcesses.get(i));
            }
        }

        if(processToDelete == null){
            System.out.println("Process does not exist! Try again.");
            return 0;
        }

        for (Page swapPage : processToDelete.pageIndexInSwap){
            for (int i = 0; i < swap.length; i++){
                if(swapPage.equals(swap[i])){
                    swap[i] = null;
                    ++numberOfFramesDeleted;
                    System.out.println("Removing PID " + processID + " from swap frame #" + i);
                }
            }
        }

        for (Page page : processToDelete.pageIndexInPrimaryMemory){
            for (int i = 0; i < memory.length; i++){
                if(page.equals(memory[i])){
                    memory[i] = null;
                    System.out.println("Removing PID " + processID + " from memory frame #" + i);
                    ++pagesAvailable;
                    ++numberOfFramesDeleted;
                }
            }
        }

        return numberOfFramesDeleted;
    }

    /**
     * Given the Virtual Address and a PID of a process in Physical Memory or Swap, return the physical or swap addr.
     * @param requestedAddress Virtual Address of the process
     * @param processID PID of the process
     * @return physical address of the given virtual address
     */
    public int returnPhysicalAddress(int requestedAddress, int processID, boolean modify){
        //TODO check if the given address actually exists.
        //TODO case when the process is not loaded in memory.
        //TODO check if requested addr is inside the process (not a bigger or smaller (negative) addr)

        //Get process that owns the page, if it doesn't exist ownerProcess will equal null
        Process ownerProcess = null;
        for (Process process : activeProcesses) {
            if (process.getProcessId() == processID){
                ownerProcess = process;
                break;
            }
        }

        //Process is non existent in {@code activeProcesses}
        if (ownerProcess == null){
            System.out.println("The referenced process does not exist, please try again!");
            return 0;
        }

        //Check if requested address is inside valid range
        if (ownerProcess.getProcessSize() < requestedAddress){
            System.out.println("The address you tried to access does not exist in the given process, try again!");
            return 0;
        }


        int pageNumberToLookFor = requestedAddress/Commons.PAGE_SIZE;
        //Check if pages is loaded in memory
        boolean loaded = false;
        for (Page value : memory) {
            if (value != null && value.getNum() == pageNumberToLookFor) {
                System.out.println("Requested page found in memory");
                loaded = true;
            }
            if (!loaded) {
                swapInMemory(processID, pageNumberToLookFor);
            }
        }

        int page = 0;
        for (int i = 0; i < memory.length; i++){
            if(memory[i] != null && memory[i].getProcess().getProcessId() == processID && page == pageNumberToLookFor){
                //If user requested modification to the page.
                if(modify){
                    memory[i].setLastAppearance(systemTimestamp);
                    System.out.println("Modified page#" + memory[i].getNum() + " in system-timestamp " + systemTimestamp);
                }
                return i*Commons.PAGE_SIZE+requestedAddress%Commons.PAGE_SIZE;
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
     * Will load the required page into primary memory.
     * @param requiredPageNumber receives the page number of the page to load and then loads it into memory.
     */
    private void swapInMemory(int processID, int requiredPageNumber){
        for (Page page : swap) {
            //Making sure that it is the right page number and the right process. (Multiple processes can have an equal page number
            if (page != null && page.getNum() == requiredPageNumber && page.getProcess().getProcessId() == processID) {
                loadPageIntoMemory(page.getProcess(), page);
                swapsIn++;
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
        //TODO protect from NullPointerExceptions on the lower memory blocks, this is due to the memory array containing
        // null values, easier to surround with if than try and catch.
        Page pageWithLeastRecentUsage = null;
        int lastUsage = Integer.MAX_VALUE;
        for (Page page : memory) {
            if (page != null &&  page.getLastAppearance() < lastUsage && page.getLastAppearance() != 0) {
                lastUsage =  page.getLastAppearance();
                pageWithLeastRecentUsage = page;
            }
        }
        //Case when no page has been modified, so we use FIFO to do the replacement
        if(pageWithLeastRecentUsage == null){
            return swapUsingFIFOPolicy();
        }
        
        //Removes the page from memory and sends it over to swap memory.
        memory[pageWithLeastRecentUsage.getLocationInMemory()] = null; //May throw NullPointerException        
        sendToSwapMemory(pageWithLeastRecentUsage);
        ++pagesAvailable;
        return pageWithLeastRecentUsage.getLocationInMemory(); //May throw NullPointerException
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
                firstEntryTimestamp =  page.getTimeInserted();
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

    public int getSystemTimestamp() {
        return systemTimestamp;
    }

    public int getSwapsIn() {
        return swapsIn;
    }

    public int getSwapsOut() {
        return swapsOut;
    }
    
    
    

}

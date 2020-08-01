package main;

import java.util.ArrayList;

public class Simulation {

    Page memory[]; //Representation of virtual memory.
    Page swap[]; //Representation of swap area.
    int pagesAvailable;
    int type; //Either FIFO(0)/LRU(1)
    ArrayList<Process> activeProcesses;


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

    /**
     * Creates a process, then assigns memory to it.
     * @param processID the ID of the process
     * @param processSize the size, in bytes, of the process.
     */
    public void createProcess(int processID, int processSize){
        if(processSize > Commons.MEMORY_SIZE){
            System.out.println("Program is too large, can't load into memory!");
            return;
        }
        Process process = new Process(processID, processSize);
        assignMemoryToNewProcess(process);
        activeProcesses.add(process);
        System.out.println("Assigned " + processSize + " bytes of memory to process " + processID);
    }


    private void assignMemoryToNewProcess(Process process){
        int pagesRequired = (int)Math.ceil(process.getProcessSize()/Commons.PAGE_SIZE);
        if(pagesRequired > pagesAvailable){
            System.out.println("The process requires more pages, swapping processes out!");
            swapOutMemory(pagesRequired);
        }else{
            int pagesAssignedToProcess = 0;
            while(pagesRequired > pagesAssignedToProcess){
                Page page = new Page(pagesAssignedToProcess+1, process.getProcessId());
                assignPageToMemory(process, page);
                process.addPage(page); //Store information in PCB (associate to process)
                ++pagesAssignedToProcess;
            }
            activeProcesses.add(process);//Store information on active processes
        }
    }

    private void assignPageToMemory(Process process, Page pageToAssign){
        for (int i = 0; i < memory.length; i++){
            if(memory[i] == null){
                memory[i] = pageToAssign; //Assign the page to memory
                --pagesAvailable; //Reduce page availability
                System.out.println("Assigned page " + i + " to process " + process.getProcessId());
                break;
            }
        }
    }

    /**
     * Serves as a wrapper for both replacement policies, to simplify coding.
     */
    private void swapOutMemory(int pagesRequired){
        if(type == 0){
            firstInFirstOut(pagesRequired);
        }else if(type == 1){
            leastRecentlyUsed(pagesRequired);
        }
    }

    /**
     * Frees all of the memory of a given PID.
     * @param processID PID of the process to be removed from memory.
     */
    public int freeProcessFromMemory(int processID){
        int numberOfFramesDeleted = 0;
        for(int i = 0; i < activeProcesses.size(); i++ ){
            if(activeProcesses.get(i).getProcessId() == processID){
                activeProcesses.remove(activeProcesses.get(i));
            }
        }

        for (int i = 0; i < memory.length; i++){
            if(memory[i] != null && memory[i].getPid() == processID){
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
        int pageNumber = addr/Commons.PAGE_SIZE;
        int page = 0;
        for (int i = 0; i < memory.length; i++){
            if(memory[i].getPid() == processID && page == pageNumber){
                return i*Commons.PAGE_SIZE+addr;
            }else{
                ++page;
            }
        }
        return 0;
    }

    /**
     * Will swap out memory according to the LRU replacement policy.
     */
    private void leastRecentlyUsed(int requiredPages){

    }

    /**
     * Will swap out memory according to the FIFO/FCFS replacement policy.
     */
    private void firstInFirstOut(int requiredPages){

    }

    /**
     * Will return the required page to primary memory.
     * @param requiredPageNumber recieves the page number of the page to load and then loads it into memory.
     */
    private void swapInMemory(int requiredPageNumber){
        for (int i = 0; i < swap.length; i++){
            if(swap[i].getNum() == requiredPageNumber){

            }
        }
    }

    private void findFirstProcessThatCameIn(){

    }

    private void findLeastRecentlyUsedProcess(){

    }

    public void viewSimulation(){
        System.out.println("Pages: " + java.util.Arrays.toString(memory));
        System.out.println("Swap: " + java.util.Arrays.toString(swap));
    }

}

package main;

import java.util.ArrayList;
import java.util.Arrays;

public class Simulation {

    int memory[]; //Representation of virtual memory.
    int swap[]; //Representation of swap area.
    int pagesAvailable;
    int type; //Either FIFO(0)/LRU(1)
    ArrayList<Process> activeProcesses;


    /**
     * Initializes the Simulator object, with empty memory. The values are specified in the spec doc.
     */
    public Simulation(int type){
        memory = new int[Commons.MEMORY_SIZE/Commons.PAGE_SIZE];
        swap = new int[Commons.SWAP_SIZE/Commons.PAGE_SIZE];
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
        Process process = new Process(processID, processSize);
        assignMemory(process);
        activeProcesses.add(process);
        System.out.println("Assigned " + processSize + " bytes of memory to process " + processID);
    }

    private void assignMemory(Process process){
        int pagesRequired = (int)Math.ceil(process.getProcessSize()/Commons.PAGE_SIZE);
        if(pagesRequired > pagesAvailable){
            System.out.println("The process requires more pages, swapping processes out!");
            swapOutMemory(pagesRequired);
        }else{
            int pagesAssignedToProcess = 0;
            for (int i = 0; i < memory.length && pagesRequired > pagesAssignedToProcess; i++){
                if(memory[i] == 0){
                    memory[i] = process.getProcessId(); //Assign the page to the process
                    process.addProcessPageInMemory(i, process.getProcessId()); //Store information in PCB
                    --pagesAvailable; //Remove page availability
                    ++pagesAssignedToProcess;
                    System.out.println("Assigned page " + i + " to process " + process.getProcessId());
                }
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
     * Will swap out memory according to the LRU replacement policy.
     */
    private void leastRecentlyUsed(int requiredPages){

    }

    /**
     * Will swap out memory according to the FIFO/FCFS replacement policy.
     */
    private void firstInFirstOut(int requiredPages){

    }

    private void swapInMemory(){

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

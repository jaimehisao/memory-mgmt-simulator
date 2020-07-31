package main;

public class Process {

    int processId;
    int processSize;
    int pageIndex[]; //Stores the page numbers of where a process is located.
    int swapMemoryLocations[];

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getProcessSize() {
        return processSize;
    }

    public void setProcessSize(int processSize) {
        this.processSize = processSize;
    }

    public int[] getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int[] pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int[] getSwapMemoryLocations() {
        return swapMemoryLocations;
    }

    public void setSwapMemoryLocations(int[] swapMemoryLocations) {
        this.swapMemoryLocations = swapMemoryLocations;
    }

    public void addProcessPageInMemory(int index, int processID) {
        this.pageIndex[index] = processID;
    }

    public void removeProcessPageFromMemory(int index, int processID) {
        this.pageIndex[index] = processID;
    }

    public Process(int processId, int processSize){
        this.processId = processId;
        this.processSize = processSize;
        this.pageIndex = new int[Commons.MEMORY_SIZE/Commons.PAGE_SIZE];
        this.swapMemoryLocations = new int[Commons.SWAP_SIZE/Commons.PAGE_SIZE];
    }
}

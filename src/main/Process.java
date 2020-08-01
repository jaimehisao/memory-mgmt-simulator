package main;

import java.util.ArrayList;

public class Process {

    int processId;
    int processSize;
    ArrayList<Page> pageIndexInPrimaryMemory; //Stores the page numbers of where a process is located (Physical).
    ArrayList<Page> pageIndexInSwap; //Stores the page numbers of where a process is located (Swap).
    int swapMemoryLocations[];

    public void addToSwapPageIndex(Page page){ pageIndexInSwap.add(page); }

    public void removeFromSwapPageIndex(Page page){ pageIndexInSwap.remove(page); }

    public ArrayList<Page> getPageIndexInSwap() {
        return pageIndexInSwap;
    }

    public void setPageIndexInSwap(ArrayList<Page> pageIndexInSwap) {
        this.pageIndexInSwap = pageIndexInSwap;
    }

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

    public ArrayList<Page> getPageIndexInPrimaryMemory() {
        return pageIndexInPrimaryMemory;
    }

    public void setPageIndexInPrimaryMemory(ArrayList<Page> pageIndexInPrimaryMemory) {
        this.pageIndexInPrimaryMemory = pageIndexInPrimaryMemory;
    }

    public int[] getSwapMemoryLocations() {
        return swapMemoryLocations;
    }

    public void setSwapMemoryLocations(int[] swapMemoryLocations) {
        this.swapMemoryLocations = swapMemoryLocations;
    }

    public void removeProcessPageFromMemory(int index) {
        this.pageIndexInPrimaryMemory.remove(index);
    }

    public void addPageToPrimaryMemory(Page page){ this.pageIndexInPrimaryMemory.add(page);}

    public void removePageFromPrimaryMemory(Page page){ this.pageIndexInPrimaryMemory.remove(page);}

    public Process(int processId, int processSize){
        this.processId = processId;
        this.processSize = processSize;
        this.pageIndexInPrimaryMemory = new ArrayList<>();
        this.swapMemoryLocations = new int[Commons.SWAP_SIZE/Commons.PAGE_SIZE];
    }
}

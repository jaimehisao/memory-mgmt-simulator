package main;

import java.util.ArrayList;

public class Process {

    int processId;
    int processSize;
    ArrayList<Page> pageIndex; //Stores the page numbers of where a process is located.
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

    public ArrayList<Page> getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(ArrayList<Page> pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int[] getSwapMemoryLocations() {
        return swapMemoryLocations;
    }

    public void setSwapMemoryLocations(int[] swapMemoryLocations) {
        this.swapMemoryLocations = swapMemoryLocations;
    }

    public void removeProcessPageFromMemory(int index) {
        this.pageIndex.remove(index);
    }

    public void addPage(Page page){ this.pageIndex.add(page);}

    public void removePage(Page page){ this.pageIndex.remove(page);}

    public Process(int processId, int processSize){
        this.processId = processId;
        this.processSize = processSize;
        this.pageIndex = new ArrayList<>();
        this.swapMemoryLocations = new int[Commons.SWAP_SIZE/Commons.PAGE_SIZE];
    }
}

package main;

import java.util.ArrayList;

public class Process {

    int processId;                              //Process ID
    int processSize;                            //Size in bytes
    int timecreated;                            //Time created
    Main main;                                  //Main object to access original simulation
    ArrayList<Page> pageIndexInPrimaryMemory;   //Stores the Page objects of where a process is located in Physical Mem
    ArrayList<Page> pageIndexInSwap;            //Stores the Page objects of where a process is located in Swap Memory.

    public Process(int processId, int processSize){
        this.processId = processId;
        this.processSize = processSize;
        this.pageIndexInPrimaryMemory = new ArrayList<>();
        this.pageIndexInSwap = new ArrayList<>();
        this.timecreated = Main.getSimulation().getSystemTimestamp();
    }

    public void addToSwapPageIndex(Page page){ pageIndexInSwap.add(page); }

    public int getProcessId() {
        return processId;
    }

    public void addPageToPrimaryMemory(Page page){ this.pageIndexInPrimaryMemory.add(page);}

    public void removePageFromPrimaryMemory(Page page){ this.pageIndexInPrimaryMemory.remove(page); }

    public int getProcessSize() {
        return processSize;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
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

    public void removeProcessPageFromMemory(int index) {
        this.pageIndexInPrimaryMemory.remove(index);
    }

    public void removeFromSwapPageIndex(Page page){ pageIndexInSwap.remove(page); }

    public ArrayList<Page> getPageIndexInSwap() {
        return pageIndexInSwap;
    }

    public void setPageIndexInSwap(ArrayList<Page> pageIndexInSwap) {
        this.pageIndexInSwap = pageIndexInSwap;
    }

    public int getTimecreated() {
        return timecreated;
    }
}

package main;

import java.util.ArrayList;

public class Process {

    int processId;                              //Process ID
    int processSize;                            //Size in bytes
    int timeCreated;                            //Time created
    ArrayList<Page> pageIndexInPrimaryMemory;   //Stores the Page objects of where a process is located in Physical Mem
    ArrayList<Page> pageIndexInSwap;            //Stores the Page objects of where a process is located in Swap Memory.

    /**
     * Process Constructor
     * @param processId process ID
     * @param processSize process size in bytes
     */
    public Process(int processId, int processSize){
        this.processId = processId;
        this.processSize = processSize;
        this.pageIndexInPrimaryMemory = new ArrayList<>();
        this.pageIndexInSwap = new ArrayList<>();
        this.timeCreated = Main.getSimulation().getSystemTimestamp();
    }

    /**
     * Add page to swap index
     * @param page page to add to {@code pageIndexInSwap}
     */
    public void addToSwapPageIndex(Page page){ 
        pageIndexInSwap.add(page); 
    }

    /**
     * Get Process ID
     * @return processId
     */
    public int getProcessId() {
        return processId;
    }

    /**
     * Add page to memory
     * @param page 
     */
    public void addPageToPrimaryMemory(Page page){ 
        this.pageIndexInPrimaryMemory.add(page);
    }

    /**
     * Remove page from memory
     * @param page page to remove from {@code pageIndexInPrimaryMemory}
     */
    public void removePageFromPrimaryMemory(Page page){ 
        this.pageIndexInPrimaryMemory.remove(page); 
    }

    /**
     * Get Process size
     * @return processSize
     */
    public int getProcessSize() {
        return processSize;
    }

    /**
     * Set process ID
     * @param processId process ID (does not check for conflicts)
     */
    public void setProcessId(int processId) {
        this.processId = processId;
    }

    /**
     * Set process size
     * @param processSize process memory size in bytes
     */
    public void setProcessSize(int processSize) {
        this.processSize = processSize;
    }

    /**
     * Get array of pages
     * @return pageIndexInPrimaryMemory
     */
    public ArrayList<Page> getPageIndexInPrimaryMemory() {
        return pageIndexInPrimaryMemory;
    }

    /**
     * Set PageIndexInPrimaryMemory array
     * @param pageIndexInPrimaryMemory Array containing the indexes of the Pages in Primary Memory
     */
    public void setPageIndexInPrimaryMemory(ArrayList<Page> pageIndexInPrimaryMemory) {
        this.pageIndexInPrimaryMemory = pageIndexInPrimaryMemory;
    }

    /**
     * Remove page from memory array
     * @param index index of the page to be removed from the {@code pageIndexInPrimaryMemory}
     */
    public void removeProcessPageFromMemory(int index) {
        this.pageIndexInPrimaryMemory.remove(index);
    }

    /**
     * Remove page from storage array
     * @param page page to remove from {@code pageIndexInSwap}
     */
    public void removeFromSwapPageIndex(Page page){ 
        pageIndexInSwap.remove(page); 
    }

    /**
     * Get page index in storage 
     * @return pageIndexInSwap
     */
    public ArrayList<Page> getPageIndexInSwap() {
        return pageIndexInSwap;
    }

    /**
     * set page index in storage
     * @param pageIndexInSwap set the {@code pageIndexInSwap} Array.
     */
    public void setPageIndexInSwap(ArrayList<Page> pageIndexInSwap) {
        this.pageIndexInSwap = pageIndexInSwap;
    }

    /**
     * Get time the process was created
     * @return timeCreated
     */
    public int getTimeCreated() {
        return timeCreated;
    }
}

package main;

public class Page {

    int num;                    //Stores page number
    Process process;            //Process the page belongs to
    int locationInMemory;       //Stores location in memory
    int locationInSwap;         //Stores location in virtual memory
    int lastAppearance;         //Stores last Appearance
    int timeInserted;           //Stores time inserted
    boolean referenceBit;       //Stores the reference bit

    //Constructor
     public Page(int num, Process process){
        this.num = num;
        this.locationInMemory = 0;
        this.locationInSwap = 0;
        this.referenceBit = true;
        this.lastAppearance = 0;
        this.process = process;
    }
    
    /**
     * Set the page's location in storage
     * @param locationInSwap location of the Page in the Swap memory bank
     */
    public void setLocationInSwap(int locationInSwap) {
        this.locationInSwap = locationInSwap;
    }
    
    /**
     * Get the page's time inserted
     * @return timeInserted
     */
    public int getTimeInserted() {
        return timeInserted;
    }

    /**
     * Set the page's time inserted
     * @param timeInserted Time when the process was inserted into primary memory.
     */
    public void setTimeInserted(int timeInserted) {
        this.timeInserted = timeInserted;
    }

    /**
     * Get the page's location in memory
     * @return timeInserted
     */
    public int getLocationInMemory() {
        return locationInMemory;
    }

    /**
     * Set the page's location in memory
     * @param locationInMemory location of the process in primary memory
     */
    public void setLocationInMemory(int locationInMemory) {
        this.locationInMemory = locationInMemory;
    }

    /**
     * Get the page's last appearance
     * @return lastAppearance
     */
    public int getLastAppearance() {
        return lastAppearance;
    }

    /**
     * Get the page's location in storage
     * @return locationInSwap
     */
    public int getLocationInSwap() {
        return locationInSwap;
    }

    /**
     * Set the page's reference bit
     * @param referenceBit reference bit of the page
     */
    public void setReferenceBit(boolean referenceBit) {
        this.referenceBit = referenceBit;
    }

    /**
     * Get the page's number
     * @return num
     */
    public int getNum() {
        return num;
    }

    /**
     * Get the page's process
     * @return process
     */
    public Process getProcess(){ 
        return process; 
    }

    /**
     * Set the page's last appearance
     * @param lastAppearance last appearance of the page in primary memory
     */
    public void setLastAppearance(int lastAppearance) {
        this.lastAppearance = lastAppearance;
    }

    /**
     * Get the page's reference bit
     * @return the reference bit of the page
     */
    public boolean isReferenceBit() {
        return referenceBit;
    }

    /**
     * Set the page's number
     * @param num the page number
     */
    public void setNum(int num) {
        this.num = num;
    }
}

package main;

public class Page {

    int num;
    Process process;
    int locationInMemory;
    int locationInSwap;
    int lastAppearance;
    int timeInserted;
    int timesUsed;
    boolean referenceBit;

     public Page(int num, Process process){
        this.num = num;
        this.locationInMemory = 0;
        this.locationInSwap = 0;
        this.referenceBit = true;
        this.lastAppearance = 0;
        this.timesUsed = 0;
        this.process = process;
    }

    public void setLocationInSwap(int locationInSwap) {
        this.locationInSwap = locationInSwap;
    }

    public int getTimeInserted() {
        return timeInserted;
    }

    public void setTimeInserted(int timeInserted) {
        this.timeInserted = timeInserted;
    }

    public int getLocationInMemory() {
        return locationInMemory;
    }

    public void setLocationInMemory(int locationInMemory) {
        this.locationInMemory = locationInMemory;
    }

    public int getLastAppearance() {
        return lastAppearance;
    }

    public int getLocationInSwap() {
        return locationInSwap;
    }

    public void setReferenceBit(boolean referenceBit) {
        this.referenceBit = referenceBit;
    }

    public int getNum() {
        return num;
    }

    public Process getProcess(){ return process; }

    public void setLastAppearance(int lastAppearance) {
        this.lastAppearance = lastAppearance;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public boolean isReferenceBit() {
        return referenceBit;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

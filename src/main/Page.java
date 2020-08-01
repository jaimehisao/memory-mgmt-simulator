package main;

import java.awt.*;

public class Page {

    int num;
    int pid;
    int lastAppearance;
    int timesUsed;
    boolean inserted;

     public Page(int num, int pid){
        this.num = num;
        this.pid = pid;
        this.inserted = true;
        this.lastAppearance = -1;
        this.timesUsed = 0;
    }

    public int getLastAppearance() {
        return lastAppearance;
    }

    public void setLastAppearance(int lastAppearance) {
        this.lastAppearance = lastAppearance;
    }

    public int getTimesUsed() {
        return timesUsed;
    }

    public void setTimesUsed(int timesUsed) {
        this.timesUsed = timesUsed;
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPid(){ return pid; }
}

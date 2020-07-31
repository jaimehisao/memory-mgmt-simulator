package main;

import java.awt.*;

public class Page {

    int num;
    int nextAppearance = -1;
    int lastAppearance = -1;
    int timesUsed = 0;
    boolean inserted;

     public Page(int num){
        this.num = num;
        this.inserted = false;
        this.nextAppearance = -1;
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
}

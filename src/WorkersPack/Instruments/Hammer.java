package WorkersPack.Instruments;

import WorkersPack.HardWorker;

import java.io.Serializable;

/**
 * Created by OrangeUser on 3/29/2015.
 */
public class Hammer implements Serializable {
    private int countKicks = 2000;
    private HardWorker owner;
    private String hammerName;

    public Hammer(String hammerName ,HardWorker worker){
        this.hammerName = hammerName;
        owner = worker;
    }

    public void getWork(){
        if (isHammerCanWork())
            countKicks -= 50;

    }

    public boolean isHammerCanWork(){
        return countKicks > 0;
    }
}

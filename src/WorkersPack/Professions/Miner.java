package WorkersPack.Professions;

import WorkersPack.HardWorker;
import WorkersPack.Instruments.Hammer;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Miner extends HardWorker {
    private boolean haveFlashlight;

    public Miner(String name, int age, int skill, String hammerName, boolean haveFlashlight) {
        super(name, age, skill, hammerName);
        hammer = new Hammer(hammerName, this);
        this.haveFlashlight = haveFlashlight;
        this.hammerName = hammerName;
    }

    public Miner(){

    }

    public boolean getHaveFlashlight(){
        return haveFlashlight;
    }

    public void setHaveFlashlight(boolean haveFlashlight){
        this.haveFlashlight = haveFlashlight;
    }



}

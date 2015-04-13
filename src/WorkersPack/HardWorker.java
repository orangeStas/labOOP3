package WorkersPack;

import WorkersPack.Instruments.Hammer;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public abstract class HardWorker extends Worker {
    public String hammerName;
    public Hammer hammer;
    public HardWorker(String name, int age, int skill, String hammerName) {
        super(name, age, skill);
        this.hammerName = hammerName;
    }

    public HardWorker(){

    }

    public String getHammerName(){
        return hammerName;
    }

    public void setHammerName(String hammerName){
        this.hammerName = hammerName;
    }

}

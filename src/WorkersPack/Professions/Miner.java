package WorkersPack.Professions;

import WorkersPack.HardWorker;
import WorkersPack.Instruments.Hammer;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Miner extends HardWorker {
    private Hammer hammer;
    public boolean haveFlashlight;

    public Miner(String name, int age, int skill, String hammerName, boolean haveFlashlight) {
        super(name, age, skill, hammerName);
        hammer = new Hammer(hammerName, this);
        this.haveFlashlight = haveFlashlight;
    }

    public Miner(){

    }

    @Override
    public int getSkill() {
        return skill;
    }

    @Override
    public void setSkill() {
        skill += 1;
    }

    @Override
    public String getName() {
        return name;
    }

    public void getGold(){
        hammer.getWork();
    }

}

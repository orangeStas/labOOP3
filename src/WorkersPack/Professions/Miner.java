package WorkersPack.Professions;

import WorkersPack.HardWorker;
import WorkersPack.Instruments.Hammer;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Miner extends HardWorker {
    private Hammer hammer;

    public Miner(String name, int age, int skill) {
        super(name, age, skill);
        hammer = new Hammer(this);
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

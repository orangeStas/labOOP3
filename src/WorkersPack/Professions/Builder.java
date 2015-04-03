package WorkersPack.Professions;

import WorkersPack.HardWorker;
import WorkersPack.Instruments.Hammer;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Builder extends HardWorker {
    private Hammer hammer;
    public Builder(String name, int age, int skill, String hammerName) {
        super(name, age, skill, hammerName);
        hammer = new Hammer(hammerName, this);
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

    public void makeBuilt(){
        hammer.getWork();
    }
}

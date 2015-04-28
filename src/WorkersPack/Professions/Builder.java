package WorkersPack.Professions;

import WorkersPack.HardWorker;
import WorkersPack.Instruments.Hammer;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Builder extends HardWorker {
    public Builder(String name, int age, int skill, String hammerName) {
        super(name, age, skill, hammerName);
        hammer = new Hammer(hammerName, this);
    }

    public Builder(){}

    @Override
    public void work() {
        System.out.println(this.getName() + " is building a house");
    }

}

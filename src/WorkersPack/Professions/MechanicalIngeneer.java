package WorkersPack.Professions;

import WorkersPack.IngeneerWorker;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class MechanicalIngeneer extends IngeneerWorker {
    public MechanicalIngeneer(String name, int age, int skill, boolean haveSheme) {
        super(name, age, skill, haveSheme);
    }

    public MechanicalIngeneer(){}

    @Override
    public void work() {
        System.out.println(this.getName() + " is drawing a new scheme");
    }

}

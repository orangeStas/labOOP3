package WorkersPack.Professions;

import WorkersPack.IngeneerWorker;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class MechanicalIngeneer extends IngeneerWorker {
    public MechanicalIngeneer(String name, int age, int skill) {
        super(name, age, skill);
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

    public void work() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Working ingeneer..");
    }
}

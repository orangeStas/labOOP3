package WorkersPack.Professions;

import WorkersPack.OfficeWorker;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Economist extends OfficeWorker {
    public Economist(String name, int age, int skill) {
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

    @Override
    public void getReport() {

    }
}

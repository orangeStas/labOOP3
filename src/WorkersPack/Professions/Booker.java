package WorkersPack.Professions;

import WorkersPack.Instruments.Telephone;
import WorkersPack.OfficeWorker;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Booker extends OfficeWorker {
    private Telephone telephone;
    public int countClients;
    public Booker(String name, int age, int skill, int countClients) {
        super(name, age, skill);
        telephone = new Telephone(this);
        this.countClients =  countClients;
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
        telephone.getWork();
    }
}

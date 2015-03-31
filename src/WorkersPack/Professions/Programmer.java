package WorkersPack.Professions;

import WorkersPack.IngeneerWorker;
import WorkersPack.Instruments.Computer;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Programmer extends IngeneerWorker {
    private Computer computer;
    private String language;
    public Programmer(String name, Integer age, Integer skill, String language) {
        super(name, age, skill);
        computer = new Computer(this);
        this.language = language;
    }

    @Override
    public int getSkill() {
        return skill;
    }

    @Override
    public void setSkill() {
        skill+=1;
    }

    @Override
    public String getName() {
        return name;
    }

    public void writeCode() throws InterruptedException {
        computer.getWork();
    }
}

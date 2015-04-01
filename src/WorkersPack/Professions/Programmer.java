package WorkersPack.Professions;

import WorkersPack.IngeneerWorker;
import WorkersPack.Instruments.Computer;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Programmer extends IngeneerWorker {
//    public String Name;
//    public int Age;
//    public int Skill;
    public String language;
    public Computer computer;

    public Programmer(String name, int age, int skill, String language) {
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

    public int getAge(){
        return age;
    }

    public String getLanguage(){
        return language;
    }

    public Computer getComputer(){
        return computer;
    }

    public void writeCode() throws InterruptedException {
        computer.getWork();
    }
}

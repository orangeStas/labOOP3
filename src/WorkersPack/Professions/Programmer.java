package WorkersPack.Professions;

import WorkersPack.IngeneerWorker;
import WorkersPack.Instruments.Computer;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Programmer extends IngeneerWorker {
    private String language;
    private Computer computer;

    public Programmer(String name, int age, int skill, String language, boolean haveScheme) {
        super(name, age, skill, haveScheme);
        this.language = language;
    }

    public Programmer(){}

    @Override
    public void work() {
        System.out.println(this.getName() + " is writing code");
    }


    public String getLanguage(){
        return language;
    }

    public void  setLanguage(String language){
        this.language = language;
    }

    public Computer getComputer(){
        return computer;
    }

    public void setComputer(String name){
        computer = new Computer(this, name);
    }

}

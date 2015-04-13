package WorkersPack.Instruments;

import WorkersPack.IngeneerWorker;

import java.io.Serializable;

/**
 * Created by OrangeUser on 3/29/2015.
 */
public class Computer implements Serializable {
    private IngeneerWorker owner;
    private String name;

    public Computer(IngeneerWorker worker, String name){
        owner = worker;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


}

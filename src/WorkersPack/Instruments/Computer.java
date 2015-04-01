package WorkersPack.Instruments;

import WorkersPack.IngeneerWorker;

import java.io.Serializable;

/**
 * Created by OrangeUser on 3/29/2015.
 */
public class Computer implements Serializable {
    private IngeneerWorker owner;

    public Computer(IngeneerWorker worker){
        owner = worker;
    }

    public void getWork() throws InterruptedException {
        System.out.println("Working on computer..");
        Thread.sleep(200);
    }

}

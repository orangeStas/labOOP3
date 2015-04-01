package WorkersPack.Instruments;

import WorkersPack.OfficeWorker;

import java.io.Serializable;

/**
 * Created by OrangeUser on 3/29/2015.
 */
public class Telephone implements Serializable {

    private OfficeWorker owner;

    public Telephone(OfficeWorker worker){
        owner = worker;
    }

    public void getWork(){

    }
}

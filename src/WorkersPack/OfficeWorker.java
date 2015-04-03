package WorkersPack;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public abstract class OfficeWorker extends Worker {
    public OfficeWorker(String name, int age, int skill) {
        super(name, age, skill);
    }

    public OfficeWorker(){

    }

    public abstract void getReport();
}

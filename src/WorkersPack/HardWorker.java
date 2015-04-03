package WorkersPack;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public abstract class HardWorker extends Worker {
    public String hammerName;
    public HardWorker(String name, int age, int skill, String hammerName) {
        super(name, age, skill);
        this.hammerName = hammerName;
    }
}

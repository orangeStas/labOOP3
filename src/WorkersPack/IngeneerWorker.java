package WorkersPack;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public abstract class IngeneerWorker extends Worker {
    public boolean haveScheme;

    public IngeneerWorker(){

    }

    public IngeneerWorker(String name, int age, int skill, boolean haveScheme) {
        super(name, age, skill);
        this.haveScheme = haveScheme;
    }


}

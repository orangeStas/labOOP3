package WorkersPack;

/**
 * Created by OrangeUser on 4/15/2015.
 */
public class GoWork implements Command  {

    private Worker worker;

    public GoWork (Worker worker) {
        this.worker = worker;
    }

    @Override
    public void execute() {
        worker.work();
    }
}

package WorkersPack;

/**
 * Created by OrangeUser on 4/15/2015.
 */
public class Order {
    Command command;

    public Order(Command command) {
        this.command = command;
    }

    public void doOrder(){
        command.execute();
    }
}

package WorkersPack.Professions;

import WorkersPack.Instruments.Telephone;
import WorkersPack.OfficeWorker;

/**
 * Created by OrangeUser on 3/28/2015.
 */
public class Booker extends OfficeWorker {
    private Telephone telephone;
    public int countClients;
    public Booker(String name, int age, int skill, int countClients) {
        super(name, age, skill);
        telephone = new Telephone(this);
        this.countClients =  countClients;
    }

    public Booker(){
    }

    @Override
    public void work() {
        System.out.println(this.getName() + " is working with client");
    }

    public int getCountClients(){
        return countClients;
    }

    public void setCountClients(int countClients){
        this.countClients = countClients;
    }

}

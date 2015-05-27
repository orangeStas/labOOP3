import WorkersPack.GoWork;
import WorkersPack.Order;
import WorkersPack.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkersList {


    private static WorkersList instance;

    private List<Worker> workers;

    private WorkersList(){
        workers = new ArrayList<Worker>();
    }

    static{
        instance = new WorkersList();
    }


    public static WorkersList getInstance() {
        //if (instance == null)

        return instance;
    }

    public List<Worker> getWorkers(){
        return workers;
    }

    public String[] getWorkersName(){
        String[] names = new String[workers.size()];
        for (int i = 0; i < workers.size(); i++){
            names[i] = workers.get(i).getName();
        }
        return names;
    }

    public void addWorker(Worker worker){
        workers.add(worker);

        GoWork workCommand = new GoWork(worker);
        Order order = new Order(workCommand);
        order.doOrder();
    }

    public void insertWorker(Worker worker, int index){
        workers.set(index, worker);
    }

    public void removeWorker(int index){
        workers.remove(index);
    }

    public Worker getWorker(int index){
        return workers.get(index);
    }

    public void setWorker(Worker worker, int index){
        workers.set(index, worker);
    }

}

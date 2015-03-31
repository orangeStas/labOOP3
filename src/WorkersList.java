import WorkersPack.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkersList {
    public List<Worker> workers;

    public WorkersList(){
        workers = new ArrayList<Worker>();
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

    public void insertWorker(Worker worker){
        workers.add(worker);
    }

    public Worker getWorker(int index){
        return workers.get(index);
    }

    public void setWorker(Worker worker, int index){
        workers.set(index, worker);
    }

}

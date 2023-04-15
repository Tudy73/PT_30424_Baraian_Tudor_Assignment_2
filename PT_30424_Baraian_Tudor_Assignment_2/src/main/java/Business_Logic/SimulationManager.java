package Business_Logic;

import GUI.SimulationFrame;
import Model.Task;
import Model.TaskComparator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimulationManager implements Runnable{

    public int timeLimit = 100;
    public int maxProcessingTime = 10;
    public int minProcessingTime = 2;
    public int numberOfServers = 3;
    public int numberOfClients = 100;

    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private final Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> tasks;

    public SimulationManager(){
        scheduler = new Scheduler(numberOfServers,5);
        scheduler.changeStrategy(selectionPolicy);
        //TODO initialize frame of display
        generateNRandomTasks(numberOfClients);
    }

    public void generateNRandomTasks(int n){
        tasks=new ArrayList<>();
        for(int i=1;i<=n;++i){
            tasks.add(new Task(i, (int) (Math.random() * timeLimit),
                    (int) (Math.random() * (maxProcessingTime - minProcessingTime + 1)) + minProcessingTime));
        }
        tasks.sort(new TaskComparator());
    }

    @Override
    public void run() {
        int currentTime =-1;
        Iterator<Task> iterator;
        while (++currentTime < timeLimit) {
            iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getArrivalTime() == currentTime) {
                    iterator.remove();
                    scheduler.dispatchTask(task);
                }
            }
            //TODO update UI frame
        }
    }
    public static void main(String[] args){
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }
}

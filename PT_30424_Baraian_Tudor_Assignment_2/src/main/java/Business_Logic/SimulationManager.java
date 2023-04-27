package Business_Logic;

import GUI.SimulationFrame;
import Model.Task;
import Model.TaskComparator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable{

    public static int timeLimit = 50;
    public  static int maxProcessingTime =5;
    public static int minProcessingTime = 2;
    public static int numberOfServers = 15;
    public static int numberOfClients = 60;
    public static int maxNoOfClients =6;

    public static int currentTime;
    public static AtomicInteger totalWaitingTime,totalServiceTime = new AtomicInteger(0);
    public static int peakHour,numberOfClientsPeakHour=0;

    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private final Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> tasks;
    private Thread mainThread;

    public static boolean stopped=false;

    public SimulationManager(SimulationFrame frame,Scheduler scheduler){
        this.frame = frame;
        this.scheduler = scheduler;
        scheduler.changeStrategy(selectionPolicy);
        generateNRandomTasks(numberOfClients);
    }

    public void generateNRandomTasks(int n){
        tasks=new ArrayList<>();
        for(int i=1;i<=n;++i){
            tasks.add(new Task(i, (int) (Math.random() * (timeLimit)),
                    (int) (Math.random() * (maxProcessingTime - minProcessingTime + 1)) + minProcessingTime));
        }
        tasks.sort(new TaskComparator());
    }

    @Override
    public void run() {
        currentTime =-1;
        Iterator<Task> iterator;
        while (++currentTime < timeLimit) {
            iterator = tasks.iterator();
            while (iterator.hasNext()) {
                Task task = iterator.next();
                if (task.getArrivalTime() == currentTime) {
                    iterator.remove();
                    scheduler.dispatchTask(task);
                }
                else break;
            }
            frame.update(currentTime,tasks);
            try {
                mainThread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stopped=true;
        float avg = ((float)totalWaitingTime.get())/numberOfClients;
        float avgS = ((float)totalServiceTime.get())/numberOfClients;
        frame.finish(avg,avgS,peakHour);
    }
    public static void main(String[] args){
        SimulationFrame frame = new SimulationFrame();
    }
    public static void begin(SimulationFrame frame,Scheduler scheduler){
        totalWaitingTime = new AtomicInteger();
        totalWaitingTime.set(0);
        SimulationManager gen = new SimulationManager(frame,scheduler);
        gen.mainThread = new Thread(gen);
        gen.mainThread.start();
    }
}

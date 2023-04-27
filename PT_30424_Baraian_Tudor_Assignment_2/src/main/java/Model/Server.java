package Model;

import Business_Logic.SimulationManager;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    int id,remainingTime=0;
    Thread t;

    public int getNoTasks(){
        return tasks.size();
    }

    public Server(int maxServers, int id){
        this.id = id;
        tasks = new ArrayBlockingQueue<>(maxServers);
        waitingPeriod = new AtomicInteger(0);
        t = new Thread(this);
        t.start();
    }
    public void addTask(Task newTask){
        try {
            tasks.put(newTask);
            SimulationManager.totalWaitingTime.addAndGet(Math.min(SimulationManager.timeLimit - SimulationManager.currentTime,waitingPeriod.get()));
            waitingPeriod.addAndGet(newTask.getServiceTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        remainingTime = 0;
        int init = 0;
        while(!SimulationManager.stopped){
            if(waitingPeriod.get() == 0){
                try {
                    t.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                continue;}
            if(remainingTime == 0 && tasks.size()>0)
            {
                remainingTime = tasks.peek().getServiceTime();
                init =remainingTime;
            }
            try {
                t.sleep(500);
                --remainingTime;
                tasks.peek().decrementServiceTime();
                waitingPeriod.decrementAndGet();
                if(remainingTime==0){
                    Task aux = tasks.poll();
                    if (aux != null) {
                        SimulationManager.totalServiceTime.addAndGet(init);
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Task[] getTasks(){
        return tasks.toArray(new Task[0]);
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }
}

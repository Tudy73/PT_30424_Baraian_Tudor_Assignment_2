package Model;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    int id;
    Thread t;

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
            waitingPeriod.addAndGet(newTask.getServiceTime());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        int remainingTime = 0;
        while(true){
            if(waitingPeriod.get() == 0)continue;
            if(remainingTime == 0)
            {
                remainingTime = tasks.poll().getServiceTime();
                System.out.println("Server:" + id + " task with remaining Time: " + remainingTime);
            }
            try {
                t.sleep(500);
                --remainingTime;
                waitingPeriod.decrementAndGet();
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

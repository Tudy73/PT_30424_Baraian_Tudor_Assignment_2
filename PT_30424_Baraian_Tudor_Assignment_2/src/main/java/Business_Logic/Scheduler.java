package Business_Logic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers = new ArrayList<>();
        for(int i=0;i<maxNoServers;++i){
            servers.add(new Server(maxTasksPerServer));
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ShortestQueueStrategy();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new TimeStrategy();
        }
    }
    public void dispatchTask(Task task){
        strategy.addTask(servers,task);
    }
    public  List<Server> getServers(){
        return servers;
    }
}

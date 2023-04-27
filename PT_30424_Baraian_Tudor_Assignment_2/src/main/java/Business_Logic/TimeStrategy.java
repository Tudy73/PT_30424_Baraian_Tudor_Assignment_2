package Business_Logic;

import Model.Server;
import Model.Task;

import java.util.List;

public class TimeStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server candidateServer=null;
        for(Server server: servers){
            if(server.getNoTasks() == SimulationManager.maxNoOfClients)continue;
            if(candidateServer == null || server.getWaitingPeriod() < candidateServer.getWaitingPeriod()){
                candidateServer = server;
            }
        }
        if(candidateServer!=null)
            candidateServer.addTask(task);
    }
}

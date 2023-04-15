package Business_Logic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server candidateServer=null;
        for(Server server: servers){
            if(candidateServer == null || server.getTasks().length < candidateServer.getTasks().length){
                candidateServer = server;
            }
        }
        if(candidateServer!=null)
            candidateServer.addTask(task);
    }
}

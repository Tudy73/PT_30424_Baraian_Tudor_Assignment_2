package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationFrame {
    private JFrame frame;
    private int serverNo,taskNo,topSpace,serverArea;
    private List<JTextArea> servers;
    private List<List<JTextArea>> tasks;
    public SimulationFrame(int serverNo,int taskNo){
        this.serverNo=serverNo;
        this.taskNo=taskNo;
        instantiateViews();
        createFrame();
        declaringListeners();
    }
    public void addTask(int serverId, int taskId){
        tasks.get(serverId).get(taskId).setVisible(true);
    }
    public void removeTask(int serverId,int taskId){
        tasks.get(serverId).get(taskId).setVisible(false);
    }
    private void createFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Lidl");
        frame.pack();
        frame.setVisible(true);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(25, 24, 24));
        for(JTextArea server:servers){
            frame.add(server);
        }
        for(List<JTextArea> server: tasks){
            for(JTextArea task : server){
                frame.add(task);
                task.setVisible(false);
            }
        }
    }
    private void instantiateViews() {
        frame = new JFrame();
        servers = new ArrayList<>();
        instantiateServers();
        instantiateTasks();
    }
    private void instantiateServers(){
        serverArea = 800/serverNo;
        int spacingSize=serverArea/4;
        int serverSize = serverArea/2;
        topSpace = serverSize + 70;
        for(int i=0;i<serverNo;i++){
            JTextArea newText = new JTextArea();
            newText.setBounds(i*serverArea+spacingSize,70,serverSize,serverSize);
            newText.setBackground(Color.pink);
            newText.setText("Server " + (i+1));
            newText.setFont(newText.getFont().deriveFont(18f));
            newText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
            newText.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
            servers.add(newText);
        }
    }
    private void instantiateTasks(){
        int taskArea = (600-topSpace)/taskNo;
        int spacingSize = taskArea/4;
        int taskSize = taskArea/2;
        tasks = new ArrayList<>();
        for(int i=0;i<serverNo;i++){
            List<JTextArea> server = new ArrayList<>();
            for(int j=0;j<taskNo;j++){
                JTextArea newTask = new JTextArea();
                newTask.setBounds(i*serverArea + serverArea/4 + serverArea/6,j*taskArea + topSpace,taskSize,taskSize);
                newTask.setBackground(Color.white);
                newTask.setText("Task");
                newTask.setFont(newTask.getFont().deriveFont(12f));
                newTask.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
                newTask.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
                server.add(newTask);
            }
            tasks.add(server);
        }
    }
    private void declaringListeners() {
    }
}

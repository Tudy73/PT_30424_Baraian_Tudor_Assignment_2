package GUI;

import Business_Logic.Scheduler;
import Business_Logic.SimulationManager;
import Model.Server;
import Model.Task;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationFrame {
    private JFrame frame, initFrame;

    private JPanel mainPanel;
    private JLabel timeLimitLabel, maxProcessingTimeLabel, minProcessingTimeLabel,
            numberOfServersLabel, numberOfClientsLabel, maxNoOfClientsLabel;
    private JTextField timeLimitText, maxProcessingTimeText, minProcessingTimeText,
            numberOfServersText, numberOfClientsText, maxNoOfClientsText;

    private int serverNo, taskNo, topSpace, serverArea;
    FileWriter writer;
    public List<JTextArea> servers;
    private List<List<JTextArea>> tasks;
    private final int WIDTH = 1200;
    private final int HEIGHT = 900;
    private JTextArea counterText;
    public Scheduler scheduler;
    private File file;
    private JLabel minArrivalTimeLabel;
    private JTextField minArrivalTimeText;
    private JLabel maxArrivalTimeLabel;
    private JTextField maxArrivalTimeText;

    public SimulationFrame() {
        file = new File("text.txt");
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        instantiateViews();
        createFirstFrame();
    }

    private void createFirstFrame() {
        initFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initFrame.setTitle("init");
        initFrame.pack();
        initFrame.setVisible(true);
        initFrame.setSize(450, 450);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add the time limit label and input area
        timeLimitLabel = new JLabel("Time Limit:");
        mainPanel.add(timeLimitLabel, gbc);

        gbc.gridx++;
        timeLimitText = new JTextField(10);
        timeLimitText.setText("50");
        mainPanel.add(timeLimitText, gbc);

        // Add the max processing time label and input area
        gbc.gridx = 0;
        gbc.gridy++;
        maxProcessingTimeLabel = new JLabel("Max Processing Time:");
        mainPanel.add(maxProcessingTimeLabel, gbc);

        gbc.gridx++;
        maxProcessingTimeText = new JTextField(10);
        maxProcessingTimeText.setText("5");
        mainPanel.add(maxProcessingTimeText, gbc);

        // Add the min processing time label and input area
        gbc.gridx = 0;
        gbc.gridy++;
        minProcessingTimeLabel = new JLabel("Min Processing Time:");
        mainPanel.add(minProcessingTimeLabel, gbc);

        gbc.gridx++;
        minProcessingTimeText = new JTextField(10);
        minProcessingTimeText.setText("2");
        mainPanel.add(minProcessingTimeText, gbc);

        // Add the number of servers label and input area
        gbc.gridx = 0;
        gbc.gridy++;
        numberOfServersLabel = new JLabel("Number of Servers:");
        mainPanel.add(numberOfServersLabel, gbc);

        gbc.gridx++;
        numberOfServersText = new JTextField(10);
        numberOfServersText.setText("8");
        mainPanel.add(numberOfServersText, gbc);

        // Add the number of clients label and input area
        gbc.gridx = 0;
        gbc.gridy++;
        numberOfClientsLabel = new JLabel("Number of Clients:");
        mainPanel.add(numberOfClientsLabel, gbc);

        gbc.gridx++;
        numberOfClientsText = new JTextField(10);
        numberOfClientsText.setText("60");
        mainPanel.add(numberOfClientsText, gbc);

        // Add the max number of clients label and input area
        gbc.gridx = 0;
        gbc.gridy++;
        maxNoOfClientsLabel = new JLabel("Max Number of Clients:");
        mainPanel.add(maxNoOfClientsLabel, gbc);

        gbc.gridx++;
        maxNoOfClientsText = new JTextField(10);
        maxNoOfClientsText.setText("6");
        mainPanel.add(maxNoOfClientsText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        minArrivalTimeLabel = new JLabel("Min Arrival Time:");
        mainPanel.add(minArrivalTimeLabel, gbc);

        gbc.gridx++;
        minArrivalTimeText = new JTextField(10);
        minArrivalTimeText.setText("1");
        mainPanel.add(minArrivalTimeText, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        maxArrivalTimeLabel = new JLabel("Max Arrival Time:");
        mainPanel.add(maxArrivalTimeLabel, gbc);

        gbc.gridx++;
        maxArrivalTimeText = new JTextField(10);
        maxArrivalTimeText.setText("49");
        mainPanel.add(maxArrivalTimeText, gbc);

        gbc.gridx = 0;
        gbc.gridy += 2;
        JButton start = new JButton("Start");
        mainPanel.add(start, gbc);
        start.addActionListener(e -> {
            SimulationManager.timeLimit = Integer.parseInt(timeLimitText.getText());
            SimulationManager.maxProcessingTime = Integer.parseInt(maxProcessingTimeText.getText());
            SimulationManager.minProcessingTime = Integer.parseInt(minProcessingTimeText.getText());
            SimulationManager.numberOfServers = Integer.parseInt(numberOfServersText.getText());
            SimulationManager.numberOfClients = Integer.parseInt(numberOfClientsText.getText());
            SimulationManager.maxNoOfClients = Integer.parseInt(maxNoOfClientsText.getText());
            SimulationManager.minArrivalTime = Integer.parseInt(minArrivalTimeText.getText());
            SimulationManager.maxArrivalTime = Integer.parseInt(maxArrivalTimeText.getText());
            scheduler = new Scheduler(SimulationManager.numberOfServers, SimulationManager.numberOfClients);
            serverNo = SimulationManager.numberOfServers;
            taskNo = SimulationManager.maxNoOfClients;
            createFrame();
            SimulationManager.begin(this, scheduler);
        });
        initFrame.add(mainPanel);
    }

    private void createFrame() {
        instantiateServers();
        instantiateTasks();
        counterText = new JTextArea();
        counterText.setBounds(WIDTH / 2, 10, 100, 30);
        counterText.setText("Time: " + 0);
        frame.setTitle("Lidl");
        frame.pack();
        frame.setVisible(true);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(25, 24, 24));
        frame.add(counterText, BorderLayout.NORTH);
        counterText.setBackground(Color.WHITE);
        for (JTextArea server : servers) {
            frame.add(server);
        }
        for (List<JTextArea> server : tasks) {
            for (JTextArea task : server) {
                frame.add(task);
                task.setVisible(false);
            }
        }
    }

    private void instantiateViews() {
        frame = new JFrame();
        initFrame = new JFrame();
        mainPanel = new JPanel(new GridBagLayout());
        servers = new ArrayList<>();

    }

    private void instantiateServers() {
        serverArea = WIDTH / serverNo;
        int spacingSize = serverArea / 6;
        int serverSize = (int) (serverArea / 1.5);
        topSpace = serverSize + 70;
        for (int i = 0; i < serverNo; i++) {
            JTextArea newText = new JTextArea();
            newText.setBounds(i * serverArea + spacingSize, 70, serverSize, serverSize);
            newText.setBackground(Color.pink);
            newText.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
            newText.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
            servers.add(newText);
        }
    }

    private void instantiateTasks() {
        int taskArea = Math.min((HEIGHT - topSpace) / taskNo, serverArea / 2);
        int taskSize = taskArea*2/3;
        tasks = new ArrayList<>();
        for (int i = 0; i < serverNo; i++) {
            List<JTextArea> server = new ArrayList<>();
            for (int j = 0; j < taskNo; j++) {
                JTextArea newTask = new JTextArea();
                newTask.setBounds(i * serverArea + serverArea / 4 + (serverArea/2-taskSize)/2, j * taskArea + topSpace, taskSize, taskSize);
                newTask.setBackground(Color.white);
                newTask.setFont(newTask.getFont().deriveFont(12f));
                newTask.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
                newTask.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
                server.add(newTask);
            }
            tasks.add(server);
        }
    }

    public void write(String text) {
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int time, List<Task> tasks1) {
        List<Server> servers1 = scheduler.getServers();
        counterText.setText("Time: " + time);
        write("Time: " + time + "\nWaiting clients: ");

        for (Task task : tasks1) {
            write(task.toString() + ", ");
        }
        write("\n");
        int count=0;
        for (int j = 0; j < serverNo; j++) {
            write("\nQueue " + (j+1) + ": ");
            Server server = servers1.get(j);
            servers.get(j).setText("" + server.getWaitingPeriod());
            int no = server.getNoTasks();
            if(no==0){
                write("closed");
                tasks.get(j).get(0).setVisible(false);
                continue;
            }
            List<JTextArea> pool = tasks.get(j);
            Task[] taskList = server.getTasks();
            for (int i = 0; i < no; i++) {
                ++count;
                pool.get(i).setVisible(true);
                pool.get(i).setText(taskList[i].toString());
                write(taskList[i].toString());
            }
            for (int i = no; i < taskNo; i++) {
                pool.get(i).setVisible(false);
            }
        }
        if(SimulationManager.numberOfClientsPeakHour < count){
            SimulationManager.peakHour = time;
            SimulationManager.numberOfClientsPeakHour = count;
        }
        write("\n");
    }
    public void finish(float avg,float avgS,int peakHour) {
        counterText.setBounds(WIDTH / 2, 10, 200, 30);
        counterText.setText("average waiting time:" + avg);
        write("average waiting time: " + avg +
                "\naverage service time: " + avgS +
                "\npeak hour: " + peakHour);
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

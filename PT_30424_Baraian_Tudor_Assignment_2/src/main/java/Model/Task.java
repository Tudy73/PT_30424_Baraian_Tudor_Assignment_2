package Model;

public class Task {
    private int id;
    private int arrivalTime;
    private int serviceTime;

    public Task(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }
    public int getArrivalTime(){
        return arrivalTime;
    }
    public int getServiceTime(){
        return serviceTime;
    }

    public void decrementServiceTime(){serviceTime--;}

    public int getId(){return id;}

    @Override
    public String toString() {
        return "{" + id +
                "," + arrivalTime +
                "," + serviceTime +
                '}';
    }
}
package mad.project.gogoapp;

public class Todo {

    private String id;
    private String title;
    private String time;
    private String date;
    private String location;
    private double payment;

    public Todo(){

    }

    public Todo(String title, String time, String date, String location, Double payment) {
        this.title=title;
        this.time=time;
        this.date=date;
        this.location=location;
        this.payment=payment;
    }

    public Todo(String id, String title, String time, String date, String location, Double payment) {
        this.id=id;
        this.title=title;
        this.time=time;
        this.date=date;
        this.location=location;
        this.payment=payment;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
}

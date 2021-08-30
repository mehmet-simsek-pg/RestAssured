package tasks;

public class PojoTask {
    private int userId, id;
    private String title;
    private Boolean completed;


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "PojoTask{" +
                "userId=" + userId +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", complete=" + completed +
                '}';
    }
}

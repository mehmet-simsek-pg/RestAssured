package goRest.utilies;

public class Links {
    private String previous, current, next;

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Links{" +
                "previous='" + previous + '\'' +
                ", current='" + current + '\'' +
                ", next='" + next + '\'' +
                '}';
    }
}

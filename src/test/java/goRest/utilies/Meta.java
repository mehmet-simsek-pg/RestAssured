package goRest.utilies;

public class Meta {
    Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }


    @Override
    public String toString() {
        return "Meta{" +
                "pagination=" + pagination +
                '}';
    }
}

package goRest.posts;

import goRest.utilies.Meta;

import java.util.List;

public class PostsBody {

    private Meta meta;
    private List<Posts>data;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Posts> getData() {
        return data;
    }

    public void setData(List<Posts> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PostsBody{" +
                "meta=" + meta +
                ", data=" + data +
                '}';
    }
}

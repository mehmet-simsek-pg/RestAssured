package goRest.comments;

import goRest.utilies.Meta;

import java.util.List;

public class CommentsBody {

    private Meta meta;
    private List<Comments> data;

  public Meta getMeta() {
    return meta;
  }

  public void setMeta(Meta meta) {
    this.meta = meta;
  }

  public List<Comments> getData() {
    return data;
  }

  public void setData(List<Comments> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "Comment{" +
            "meta=" + meta +
            ", data=" + data +
            '}';
  }
}

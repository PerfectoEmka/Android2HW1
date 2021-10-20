package kg.geektech.taskapp35.models;

import java.io.Serializable;

public class News implements Serializable, Comparable<News> {

    private String title;
    private long createdAt;

    private String email;

    public News(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public News(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }


    @Override
    public int compareTo(News o) {
        Long castLong = o.getCreatedAt();
        int compareQuantity = castLong.intValue();
        Long thisLong = this.createdAt;
        int castInt = thisLong.intValue();
        //ascending order
        return castInt - compareQuantity;
    }

    /*public static Comparator<News> NewsTimeComparator
            = new Comparator<News>() {
        @Override
        public int compare(News o1, News o2) {
            int first = (Integer) o1.getCreatedAt() / 60000;
            int second = (Integer) o2.getCreatedAt();
            return first.compareTo(second);
        }
    };*/
}

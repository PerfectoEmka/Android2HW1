package kg.geektech.taskapp35;

import java.util.Comparator;

import kg.geektech.taskapp35.models.News;

public class NewsComparator implements Comparator<News> {
    @Override
    public int compare(News t1, News t2) {
        return Long.compare(t1.getCreatedAt(), t2.getCreatedAt());
    }
}

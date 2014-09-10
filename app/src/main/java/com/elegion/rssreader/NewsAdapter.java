package com.elegion.rssreader;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Collection;

/**
 * @author artyom on 10/09/14.
 */
public class NewsAdapter extends ArrayAdapter<NewsItem> {
    public NewsAdapter(Context context) {
        super(context, R.layout.li_news_item, R.id.text);
    }

    @Override
    public void addAll(Collection<? extends NewsItem> collection) {
        setNotifyOnChange(false);
        for (NewsItem item : collection) {
            add(item);
        }
        setNotifyOnChange(true);
        notifyDataSetChanged();
    }
}

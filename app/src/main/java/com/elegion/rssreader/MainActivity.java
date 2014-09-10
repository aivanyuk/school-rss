package com.elegion.rssreader;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.simpleframework.xml.core.Persister;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView mListView;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        mListView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsAdapter(this);
        mListView.setAdapter(mAdapter);
        runAsync();
    }

    private void runAsync() {
        new AsyncTask<Void, Void, List<NewsItem>>() {
            @Override
            protected List<NewsItem> doInBackground(Void... params) {
                String data = loadData();
                Persister persister = new Persister();
                List<NewsItem> result = new ArrayList<NewsItem>();
                try {
                    NewsWrapper wrapper = persister.read(NewsWrapper.class, data, false);
                    result.addAll(wrapper.getNews());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            protected void onPostExecute(List<NewsItem> newsItems) {
                super.onPostExecute(newsItems);
                mAdapter.addAll(newsItems);
            }
        }.execute();
    }

    private String loadData() {
        String result = "";
        URL url;
        try {
            url = new URL("http://news.yandex.ru/auto_racing.rss");
            URLConnection urlConnection = url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            try {
                result = convertStream(in);
            } finally {
                in.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static final int EOF = -1;

    public static String convertStream(@NonNull InputStream is) {
        final StringBuilder result = new StringBuilder();
        final Reader reader = new InputStreamReader(is, Charset.defaultCharset());
        final char[] buffer = new char[4096];
        try {
            int bytes;
            while ((bytes = reader.read(buffer)) != EOF) {
                result.append(buffer, 0, bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}

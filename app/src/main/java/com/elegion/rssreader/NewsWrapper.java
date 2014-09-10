package com.elegion.rssreader;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * @author artyom on 10/09/14.
 */
@Root(name = "rss")
public class NewsWrapper {
    @Attribute(name = "version")
    private String mVersion;

    @Element(name = "channel")
    private Channel mChannel;

    public List<NewsItem> getNews() {
        return mChannel.getNews();
    }

    @Root(name = "channel")
    public static class Channel {
        @ElementList(name = "item", inline = true)
        private List<NewsItem> mNews;

        public List<NewsItem> getNews() {
            return mNews;
        }
    }
}
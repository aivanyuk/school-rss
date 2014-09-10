package com.elegion.rssreader;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author artyom on 10/09/14.
 */
@Root(name = "item")
public class NewsItem {
    @Element(name = "title")
    String mTitle;
    @Element(name = "description")
    String mDescription;
    @Element(name = "link")
    String mLink;
}

package com.example.simplerss;

import java.util.Date;

public class Feed {	
	    private String title;
	    private String content;
	    private String url;
	    private String description;
	    private Date date;
	    private String urlThumb;

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public String getUrl() {
	        return url;
	    }

	    public void setUrl(String url) {
	        this.url = url;
	    }

	    public Date getDate() {
	        return date;
	    }

	    public void setDate(Date date) {
	        this.date = date;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public String getUrlThumb() {
	        return urlThumb;
	    }

	    public void setUrlThumb(String urlThumb) {
	        this.urlThumb = urlThumb;
	    }
}

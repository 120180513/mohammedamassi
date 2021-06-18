package com.iug.jerusalem.pogo;

public class Article {

    private String id;

    private String Details;

    private int containsVideo = 0;

    private String image, video;

    public Article() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        this.Details = details;
    }

    public int getContainsVideo() {
        return containsVideo;
    }

    public void setContainsVideo(int containsVideo) {
        this.containsVideo = containsVideo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}

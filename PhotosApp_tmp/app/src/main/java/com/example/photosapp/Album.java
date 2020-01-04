package com.example.photosapp;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    ArrayList<Photo> photos;
    int photoNum;
    public Album(String name){

        this.name=name;
    }

    /**
     *
     * @return the album name
     */
    public String getName() {
        return name;
    }

    /**
     * setter for renaming an album
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the album's photos
     * @return the list of photos belonging to the album
     */
    public ArrayList<Photo> getPhotos() {
        if(photos==null){
            photos=new ArrayList<Photo>();
        }

        return photos;
    }

    /**
     * sets album photos
     * @param photos
     */
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    /**
     * return number of photos in album
     * @return numPhotos
     */
    public int getNumPhotos() {
        if(photos!=null)
        { return photos.size();}
        else
            return 0;
    }


}



package com.example.photosapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;


    String imageUri;

    public Map<String, ArrayList<String>> tagTable = new HashMap<>();
    String str;


    public Photo(String imageUri){

        this.imageUri=imageUri;

        ArrayList <String> arrList=new ArrayList <String>();
       /* arrList.add("default");
        tagTable.put("Person", arrList);
        tagTable.put("Location", arrList);*///default Tag to test Search For photos


    }
    public String getImageUri(){

        return this.imageUri;
    }


    public void addTag(String key, String value) {//Add value for the existing tags

        if(tagTable.get(key).contains(value)) {
            return;
        }
        tagTable.get(key).add(value);

    }


    public void removeTag(String key, String value) {

        tagTable.get(key).remove(value);
    }


    public ArrayList<String> getTags(){

        Set <String> keys=tagTable.keySet();
        ArrayList<String> arrList= new ArrayList<String>();
        for(String key: keys) {

            arrList.add(key);
        }
        return arrList;
    }

    public ArrayList<String> getTagsString() {

        ArrayList<String> arrList= new ArrayList<String>();

        try {

            // add 100 in each value using forEach()
            tagTable.forEach((k, v) -> {
                for(int i=0;i<v.size();i++)
                {	str= k+"="+v.get(i);
                str.toLowerCase();
                    arrList.add(str);}

            });
        }
        catch (Exception e) {

            System.out.println("Exception: " + e);
        }
        return arrList;
    }

    public void addTag(String key) {//add new tag{

        ArrayList <String> arrList=new ArrayList <String>();
        tagTable.put(key, arrList);
    }

}
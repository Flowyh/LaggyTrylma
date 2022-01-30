package com.laggytrylma.utils.communication;

public class ArchiveGameDescriptor {
    private String id;
    private String date;
    public ArchiveGameDescriptor(String id, String date){
        this.id = id;
        this.date = date;
    }

    public String getId(){
        return id;
    }

    public String getDate(){
        return date;
    }
}

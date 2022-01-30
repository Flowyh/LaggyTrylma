package com.laggytrylma.utils.communication;

public class ArchiveGameDescriptor {
    private int id;
    private String date;
    public ArchiveGameDescriptor(int id, String date){
        this.id = id;
        this.date = date;
    }

    public int getId(){
        return id;
    }

    public String getDate(){
        return date;
    }


}

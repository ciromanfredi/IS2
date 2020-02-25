package com.example.is2.javaclass;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class SportEvent {

    private String eventdate;
    private String eventhour;
    private String eventname;
    private String eventowner;
    private String eventplace;
    private String eventplayersnumber;
    private String eventprice;
    private String eventsport;
    private ArrayList<String> eventnumberofplayers;
    private String key;

    public SportEvent(){


    }

    public SportEvent(String eventdate,String eventhour,String eventname,String eventowner,String eventplace,String eventplayersnumber,String eventprice,String eventsport,ArrayList<String> eventnumberofplayers, String key){
        this.eventdate=eventdate;
        this.eventhour=eventhour;
        this.eventname=eventname;
        this.eventowner=eventowner;
        this.eventplace=eventplace;
        this.eventprice=eventprice;
        this.eventsport=eventsport;
        this.eventplayersnumber=eventplayersnumber;
        this.eventnumberofplayers=eventnumberofplayers;
        this.key = key;
    }

    public String getEventdate() {
        return eventdate;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public String getEventhour() {
        return eventhour;
    }

    public void setEventhour(String eventhour) {
        this.eventhour = eventhour;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventowner() {
        return eventowner;
    }

    public void setEventowner(String eventowner) {
        this.eventowner = eventowner;
    }

    public String getEventplace() {
        return eventplace;
    }

    public void setEventplace(String eventplace) {
        this.eventplace = eventplace;
    }

    public String getEventplayersnumber() {
        return eventplayersnumber;
    }

    public void setEventplayersnumber(String eventplayersnumber) {
        this.eventplayersnumber = eventplayersnumber;
    }

    public String getEventprice() {
        return eventprice;
    }

    public void setEventprice(String eventprice) {
        this.eventprice = eventprice;
    }

    public String getEventsport() {
        return eventsport;
    }

    public void setEventsport(String eventsport) {
        this.eventsport = eventsport;
    }

    public void setKey(String key){this.key=key;}

    public String getKey(){return key;}

    public ArrayList<String> getEventnumberofplayers() {
        if(eventnumberofplayers!=null)
            return eventnumberofplayers;
        return new ArrayList<String>();
    }

    public void setEventnumberofplayers(ArrayList<String> eventnumberofplayers) {
        this.eventnumberofplayers = eventnumberofplayers;
    }

    public int getnumberofpartecipanticorrenti(){
        if(!(eventnumberofplayers==null))
            return eventnumberofplayers.size();
        return 0;
    }

}

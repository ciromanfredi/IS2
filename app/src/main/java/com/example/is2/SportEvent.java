package com.example.is2;

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

    public SportEvent(){

    }

    public SportEvent(String eventdate,String eventhour,String eventname,String eventowner,String eventplace,String eventplayersnumber,String eventprice,String eventsport,ArrayList<String> eventnumberofplayers){
        this.eventdate=eventdate;
        this.eventhour=eventhour;
        this.eventname=eventname;
        this.eventowner=eventowner;
        this.eventplace=eventplace;
        this.eventplayersnumber=eventplayersnumber;
        this.eventprice=eventprice;
        this.eventsport=eventsport;
        this.eventnumberofplayers=eventnumberofplayers;
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

    public ArrayList<String> getEventnumberofplayers() {
        return eventnumberofplayers;
    }

    public void setEventnumberofplayers(ArrayList<String> eventnumberofplayers) {
        this.eventnumberofplayers = eventnumberofplayers;
    }

    public int getPartecipanticorrenti(){
        if(!(eventnumberofplayers==null))
            return eventnumberofplayers.size();
        return 0;
    }
}

package com.example.is2.javaclass;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

@IgnoreExtraProperties
public class SportEvent {

    private String key;
    private String eventdate;
    private String eventhour;
    private String eventname;
    private String eventowner;
    private String eventplace;
    private String eventplayersnumber;
    private String eventprice;
    private String eventsport;
    private ArrayList<String> eventnumberofplayers;
    private ArrayList<Double> coordinate;

    public SportEvent(){

    }

    public ArrayList<Double> getCoordinate() {
        if (coordinate != null)
            return coordinate;
        ArrayList<Double> notPresent = new ArrayList<Double>();
        notPresent.add(null);
        notPresent.add(null);
        return notPresent;
    }

    public void setCoordinate(ArrayList<Double> coordinate) {
        this.coordinate = coordinate;
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

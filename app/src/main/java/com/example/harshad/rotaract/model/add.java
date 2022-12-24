package com.example.harshad.rotaract.model;

import android.app.DownloadManager;

/**
 * Created by Harshad on 4/4/2018.
 */

public class add {

    String eventId;
    String eventApprovalRequest;
    String eventRequest;
    String eventName;
    String eventDescription;

    public add() {
    }

    public add(String eventApprovalRequest, String eventRequest){
        this.eventApprovalRequest = eventApprovalRequest;
        this.eventRequest= eventRequest;
    }

    public String getEventApprovalRequest() {
        return eventApprovalRequest;
    }

    public void setEventApprovalRequest(String eventApprovalRequest) {
        this.eventApprovalRequest = eventApprovalRequest;
    }

    public add(String eventId, String eventName, String eventDescription) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
    }

    public String getEventRequest() {
        return eventRequest;
    }

    public void setEventRequest(String eventRequest) {
        this.eventRequest = eventRequest;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}

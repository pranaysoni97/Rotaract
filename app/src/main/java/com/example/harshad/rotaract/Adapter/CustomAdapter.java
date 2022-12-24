package com.example.harshad.rotaract.Adapter;

/**
 * Created by Harshad on 6/26/2017.
 */

public class CustomAdapter {

    private String SponsorsName;
    private String SponsorsLogoImageUri;
    private String EventName;
    private int eventNumber;

    private String RecruitmentName;
    private String RecruitmentImageUri;

    private String EventDescription;
    private String EventLocation;
    private String ImageUri,IvPath;

    public CustomAdapter() {
    }

    public CustomAdapter(String IvPath){
        this.IvPath=IvPath;
    }

    public String getIvPath() {
        return IvPath;
    }

    public void setIvPath(String ivPath) {
        IvPath = ivPath;
    }

    public CustomAdapter(String eventName, String eventDescription, String eventLocation, String imageData) {
        EventName = eventName;
        EventDescription = eventDescription;
        EventLocation = eventLocation;
    }


    public CustomAdapter(String sponsorsName, String sponsorsLogoImageUri) {
        SponsorsName = sponsorsName;
        SponsorsLogoImageUri = sponsorsLogoImageUri;
    }

    public String getRecruitmentName() {
        return RecruitmentName;
    }

    public void setRecruitmentName(String recruitmentName) {
        RecruitmentName = recruitmentName;
    }

    public String getRecruitmentImageUri() {
        return RecruitmentImageUri;
    }

    public void setRecruitmentImageUri(String recruitmentImageUri) {
        RecruitmentImageUri = recruitmentImageUri;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public String getEventLocation() {
        return EventLocation;
    }

    public void setEventLocation(String eventLocation) {
        EventLocation = eventLocation;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getSponsorsName() {
        return SponsorsName;
    }

    public void setSponsorsName(String sponsorsName) {
        SponsorsName = sponsorsName;
    }

    public String getSponsorsLogoImageUri() {
        return SponsorsLogoImageUri;
    }

    public void setSponsorsLogoImageUri(String sponsorsLogoImageUri) {
        SponsorsLogoImageUri = sponsorsLogoImageUri;
    }

    public int getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(int eventNumber) {
        this.eventNumber = eventNumber;
    }
}

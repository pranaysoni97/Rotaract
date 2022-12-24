package com.example.harshad.rotaract.model;

/**
 * Created by Harshad on 1/17/2018.
 */

public class addevent {

    String SponsorName,SponsorImage;

    public addevent() {
    }

    public addevent(String sponsorName, String sponsorImage) {
        this.SponsorName = sponsorName;
        this.SponsorImage = sponsorImage;
    }


    public String getSponsorName() {
        return SponsorName;
    }

    public void setSponsorName(String sponsorName) {
        SponsorName = sponsorName;
    }

    public String getSponsorImage() {
        return SponsorImage;
    }

    public void setSponsorImage(String sponsorImage) {
        SponsorImage = sponsorImage;
    }
}

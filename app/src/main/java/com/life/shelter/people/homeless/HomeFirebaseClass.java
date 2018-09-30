package com.life.shelter.people.homeless;

/**
 * Created by AHMED MAGDY on 9/10/2018.
 */

public class HomeFirebaseClass {
    // private String cId;
    private String cName;
    private String cAddress;
    private String cCity;
    private String cUri;

    private String userUri;
    private String username;
    private String pdate;
    private String userid;



    public  HomeFirebaseClass() {}

    public HomeFirebaseClass(String cName, String cAddress, String cCity, String cUri, String userUri, String username, String pdate,String userid) {
        this.cName = cName;
        this.cAddress = cAddress;
        this.cCity = cCity;
        this.cUri = cUri;
        this.userUri = userUri;
        this.username = username;
        this.pdate = pdate;
        this.userid = userid;

    }

    public String getcName() {
        return cName;
    }

    public String getcAddress() {
        return cAddress;
    }

    public String getcCity() {
        return cCity;
    }

    public String getcUri() {
        return cUri;
    }

    public String getUserUri() {
        return userUri;
    }

    public String getUsername() {
        return username;
    }

    public String getPdate() {
        return pdate;
    }

    public String getuserid() {
        return userid;
    }
}


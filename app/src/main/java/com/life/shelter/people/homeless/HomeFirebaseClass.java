package com.life.shelter.people.homeless;

/**
 * Created by AHMED MAGDY on 9/10/2018.
 */

public class HomeFirebaseClass {



    private String cId;
    private String cName;
    private String cAddress;
    private String cCity;
    private String cUri;

    private String userUri;
    private String username;
    private String pdate;
    private String userid;
    private Boolean checked;



    public  HomeFirebaseClass() {}

    public HomeFirebaseClass(String cId , String cName, String cAddress, String cCity, String cUri, String userUri, String username, String pdate,String userid) {

        this.cId = cId;
        this.cName = cName;
        this.cAddress = cAddress;
        this.cCity = cCity;
        this.cUri = cUri;
        this.userUri = userUri;
        this.username = username;
        this.pdate = pdate;
        this.userid = userid;

    }

    public String getName() {
        return cName;
    }
    public String getId() {
        return cId;
    }

    public String getAddress() {
        return cAddress;
    }

    public String getCity() {
        return cCity;
    }

    public String getUri() {
        return cUri;
    }

    public String getUserUri() {
        return userUri;
    }

    public String getUserName() {
        return username;
    }

    public String getDate() {
        return pdate;
    }

    public String getUserId() {
        return userid;
    }

    public boolean isOwner(String id) {
        return id.equals(getUserId());
    }

    public Boolean getChecked() {
        if (checked == null)
            return false;
        else
            return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}


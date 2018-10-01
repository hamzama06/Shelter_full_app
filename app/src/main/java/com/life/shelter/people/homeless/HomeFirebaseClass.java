package com.life.shelter.people.homeless;

/**
 * Created by AHMED MAGDY on 9/10/2018.
 */

public class HomeFirebaseClass {



   // private String cId;
    private String name;
    private String address;
    private String city;
    private String uri;

    private String userUri;
    private String userName;
    private String date;
    private String userId;
    private Boolean checked;



    public  HomeFirebaseClass() {}

    public HomeFirebaseClass( String name, String address, String city, String uri, String userUri, String userName, String date, String userId) {

      //  this.cId = cId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.uri = uri;
        this.userUri = userUri;
        this.userName = userName;
        this.date = date;
        this.userId = userId;

    }

    public String getName() {
        return name;
    }

//    public String getId() {
//        return cId;
//    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getUri() {
        return uri;
    }

    public String getUserUri() {
        return userUri;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
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


package com.wherecanistream.frontend.domain;

/**
 * Created by djuve on 10.05.2017.
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private String loginID;
    private String password;
    private String country;
    private String providers;
    private String altproviders;

    public User() {}

    public User(String id, String pas){
        this.loginID = id;
        this.password = pas;
    }

    public String getLoginID(){
        return loginID;
    }

    public void setLoginID(String loginID){
        this.loginID = loginID;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProviders() {
        return providers;
    }

    public void setProviders(String providers) {
        this.providers = providers;
    }

    public String getAltproviders() {
        return altproviders;
    }

    public void setAltproviders(String altproviders) {
        this.altproviders = altproviders;
    }
}

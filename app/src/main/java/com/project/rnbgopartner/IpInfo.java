package com.project.rnbgopartner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IpInfo {

    @SerializedName("ip_address")
    @Expose
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
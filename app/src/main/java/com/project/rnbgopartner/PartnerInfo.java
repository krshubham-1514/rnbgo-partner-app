package com.project.rnbgopartner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PartnerInfo {

    @SerializedName("partner_name")
    @Expose
    private String partnerName;

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

}

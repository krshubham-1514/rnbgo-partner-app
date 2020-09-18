package com.project.rnbgopartner;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomInfo {

    @SerializedName("room_id")
    @Expose
    private String roomId;
    @SerializedName("room_owner_name")
    @Expose
    private String roomOwnerName;
    @SerializedName("room_contact_number")
    @Expose
    private String roomContactNumber;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomOwnerName() {
        return roomOwnerName;
    }

    public void setRoomOwnerName(String roomOwnerName) {
        this.roomOwnerName = roomOwnerName;
    }

    public String getRoomContactNumber() {
        return roomContactNumber;
    }

    public void setRoomContactNumber(String roomContactNumber) {
        this.roomContactNumber = roomContactNumber;
    }

}
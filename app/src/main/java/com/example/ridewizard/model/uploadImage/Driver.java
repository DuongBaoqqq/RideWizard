package com.example.ridewizard.model.uploadImage;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private int id;
    public int getId() {
        return id;
    }
    private String phNo;
    public String getPhNo(){
        return email;
    }
    private String vehicle;
    public String getVehicle(){
        return vehicle;
    }
    private String priority_and_decreased;

    public String getPriority_and_decreased() {
        return priority_and_decreased;
    }
    private String client_promo;

    public String getClient_promo() {
        return client_promo;
    }
    private String driver_recruiter;

    public String getDriver_recruiter() {
        return driver_recruiter;
    }
    private String email;
    public String getEmail(){
        return email;
    }
    private int userID;
    public int getUserId() {
        return userID;
    }
    private String createdAt;
    public String getCreatedAt() {
        return createdAt;
    }
    private String updatedAt;
    public String getUpdatedAt(){
        return updatedAt;
    }

    public int getIdByUserId(int userID){
        return id+userID;
    }
    public static Driver findDataByUserId(List<Driver> data,int userID){
        for (Driver driver:data){
            if(driver.getUserId() == userID){
                return driver;
            }
        }
        return null;
    }
    @SerializedName("type_1")
    private String type1;
    @SerializedName("type_1_status")
    private String type1Status;
    @SerializedName("type_2")
    private String type2;
    @SerializedName("type_2_status")
    private String type2Status;
    @SerializedName("type_3")
    private String type3;
    @SerializedName("type_3_status")
    private String type3Status;
    @SerializedName("type_4")
    private String type4;
    @SerializedName("type_4_status")
    private String type4Status;
    @SerializedName("type_5")
    private String type5;
    @SerializedName("type_5_status")
    private String type5Status;
    @SerializedName("type_6")
    private String type6;
    @SerializedName("type_6_status")
    private String type6Status;
    @SerializedName("type_7")
    private String type7;
    @SerializedName("type_7_status")
    private String type7Status;
    @SerializedName("type_8")
    private String type8;
    @SerializedName("type_8_status")
    private String type8Status;
    @SerializedName("type_9")
    private String type9;
    @SerializedName("type_9_status")
    private String type9Status;
    @SerializedName("type_10")
    private String type10;
    @SerializedName("type_10_status")
    private String type10Status;
    @SerializedName("type_11")
    private String type11;
    @SerializedName("type_11_status")
    private String type11Status;
    @SerializedName("type_12")
    private String type12;
    @SerializedName("type_12_status")
    private String type12Status;
    @SerializedName("type_13")
    private String type13;
    @SerializedName("type_13_status")
    private String type13Status;
    @SerializedName("type_14")
    private String type14;
    @SerializedName("type_14_status")
    private String type14Status;
    @SerializedName("type_15")
    private String type15;
    @SerializedName("type_15_status")
    private String type15Status;
    @SerializedName("type_16")
    private String type16;
    @SerializedName("type_16_status")
    private String type16Status;
    @SerializedName("type_17")
    private String type17;
    @SerializedName("type_17_status")
    private String type17Status;
    @SerializedName("type_18")
    private String type18;
    @SerializedName("type_18_status")
    private String type18Status;

    public List<String[]> getType(int index) {
        List<String[]> result = new ArrayList<>();
        switch (index){
            case 1:
                result.add(new String[]{type1,type1Status});
                return result;
            case 2:
                result.add(new String[]{type2,type2Status});
                return result;
            case 3:
                result.add(new String[]{type3,type3Status});
                return result;
            case 4:
                result.add(new String[]{type4,type4Status});
                return result;
            case 5:
                result.add(new String[]{type5,type5Status});
                return result;
            case 6:
                result.add(new String[]{type6,type6Status});
                return result;
            case 7:
                result.add(new String[]{type7,type7Status});
                return result;
            case 8:
                result.add(new String[]{type8,type8Status});
                return result;
            case 9:
                result.add(new String[]{type9,type9Status});
                return result;
            case 10:
                result.add(new String[]{type10,type10Status});
                return result;
            case 11:
                result.add(new String[]{type11,type11Status});
                return result;
            case 12:
                result.add(new String[]{type12,type12Status});
                return result;
            case 13:
                result.add(new String[]{type13,type13Status});
                return result;
            case 14:
                result.add(new String[]{type14,type14Status});
                return result;
            case 15:
                result.add(new String[]{type15,type15Status});
                return result;
            case 16:
                result.add(new String[]{type16,type16Status});
                return result;
            case 17:
                result.add(new String[]{type17,type17Status});
                return result;
            case 18:
                result.add(new String[]{type18,type18Status});
                return result;
        }
        return null;
    }
}

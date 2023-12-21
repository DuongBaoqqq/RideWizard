package com.example.ridewizard.model.OrderDriver;

public class OrderItem {
    private String address_from;
    private String address_to;
    private String time;
    public static int count =0;
//    private int imageButtonResource;
    public OrderItem(String from,String to){
        this.address_from = from;
        this.address_to = to;
    }
    public OrderItem(String from,String to,String time){
        this.address_from = from;
        this.address_to = to;
        this.time = time;
    }
    public String getAddress_from() {
        return address_from;
    }
    public String getAddress_to(){
        return address_to;
    }
    public String getTime(){
        return time;
    }
    public String getText(){
        count+=1;
        return String.valueOf(count);
    }
}

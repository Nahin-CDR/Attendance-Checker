package com.nahin.attendancechecker;

public class SendData {


    private String MyName;
    private String Date;
    private String PhoneNumber;
    private String Time;
    int Mystatus;

    public SendData()
    {
        //empty constructor
    }

    public SendData(String myName, String date, String phoneNumber, String time, int mystatus) {
        MyName = myName;
        Date = date;
        PhoneNumber = phoneNumber;
        Time = time;
        Mystatus = mystatus;
    }


    public String getMyName() {
        return MyName;
    }

    public String getDate() {
        return Date;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getTime() {
        return Time;
    }

    public void setMyName(String myName) {
        MyName = myName;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public void setTime(String time) {
        Time = time;
    }

    public int getMystatus() {
        return Mystatus;
    }

    public void setMystatus(int mystatus) {
        Mystatus = mystatus;
    }
}

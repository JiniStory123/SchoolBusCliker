package com.cafe24.as8794.schoolbuscliker;

public class itemReservationCheck
{
    private String start;
    private String end;
    private String bus;
    private String date;
    private int id;
    private String isBoarding;



    public itemReservationCheck()
    {

    }

    public itemReservationCheck(String start, String end, String bus, String date, int id, String isBoarding)
    {
        this.start = start;
        this.end = end;
        this.bus = bus;
        this.date = date;
        this.id = id;
        this.isBoarding = isBoarding;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getIsBoarding()
    {
        return isBoarding;
    }

    public void setIsBoarding(String isBoarding)
    {
        this.isBoarding = isBoarding;
    }
}

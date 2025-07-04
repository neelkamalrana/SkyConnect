package com.airportapi.model;

public class Airport {
    private String code;
    private String name;
    private double lat;
    private double lon;

    public Airport(String code, String name, double lat, double lon){
        this.code = code;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

    public double getLon(){
        return lon;
    }

    public double getLat() {
        return lat;
    }

    @Override
    public String toString(){
        return this.code + " - " + this.name + " - " + this.lat + " - " + this.lon;
    }

}

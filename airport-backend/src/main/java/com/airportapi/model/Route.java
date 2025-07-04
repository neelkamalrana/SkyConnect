package com.airportapi.model;
public class Route {

    private Airport departure;
    private Airport arrival;
    private String aircraft;
    private String operator;
    private double distance;

    public Airport getDeparture(){
        return departure;
    }

    public Airport getArrival(){
        return arrival;
    }

    public String getAircraft(){
        return aircraft;
    }

    public String getOperator(){
        return operator;
    }

    public double getDistance(){
        return distance;
    }

    public String getSource() {
        return departure.getCode();
    }

    public String getDestination() {
        return arrival.getCode();
    }


    public double calculateDistance(Airport departAirport, Airport arrAirport){

        double lon1 = Math.toRadians(departAirport.getLon());
        double lon2 = Math.toRadians(arrAirport.getLon());
        double lat1 = Math.toRadians(departAirport.getLat());
        double lat2 = Math.toRadians(arrAirport.getLat());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers.
        double r = 6371;

        // calculate the result
        return(c * r);
    }

    public Route(Airport departure, Airport arrival, String aircraft, String operator){
        this.departure = departure;
        this.arrival = arrival;
        this.aircraft = aircraft;
        this.operator = operator;
        this.distance = calculateDistance(departure,arrival);
    }

    public Route(String trim, String trim2, int int1) {
        //TODO Auto-generated constructor stub
    }

    @Override
    public String toString(){
        return this.departure.getCode() + " " + this.arrival.getCode() + " " + this.aircraft + " " + this.operator + " " + this.distance;
    }


}

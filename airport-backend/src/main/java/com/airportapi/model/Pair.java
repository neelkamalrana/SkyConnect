package com.airportapi.model;
import java.util.Objects;

public class Pair {
    private String first;
    private String second;

    public String getFirst(){
        return first;
    }

    public String getSecond(){
        return second;
    }

    public Pair(String first, String second){
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair that = (Pair) o;
        return Objects.equals(first, that.first) && Objects.equals(second, that.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "<"+ this.first + ", " + this.second + ">";
    }

}

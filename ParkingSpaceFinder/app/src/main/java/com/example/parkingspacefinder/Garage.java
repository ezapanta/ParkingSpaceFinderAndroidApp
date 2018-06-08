package com.example.parkingspacefinder;

public class Garage {

    private String name, city, description, hoursOfOperation;
    private int id, spacesAvailable, floors;

    public Garage(int id, String name, String city, int spacesAvailable, int floors, String description, String hoursOfOperation){
        this.id = id;
        this.name = name;
        this.city = city;
        this.spacesAvailable = spacesAvailable;
        this.floors = floors;
        this.description = description;
        this.hoursOfOperation = hoursOfOperation;
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCity(){
        return city;
    }

    public void setCity(String city){
        this.city = city;
    }

    public int getSpacesAvailable(){
        return spacesAvailable;
    }

    public void setSpacesAvailable(int spacesAvailable){
        this.spacesAvailable = spacesAvailable;
    }

    public String getDescription(){
        return description;
    }

    public String getHoursOfOperation(){
        return hoursOfOperation;
    }

    public int getFloors(){
        return floors;
    }
}

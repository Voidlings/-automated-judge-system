package com.voidlings.Submissions.s1;
//ID= 816033710
import java.util.Random;

public class Passenger
{
    // Attributes
    String passportNumber;
    String flightNo;
    String firstName;
    String lastName;
    int numLuggage;
    char cabinClass;

    public Passenger(String passportNumber, String firstName, String lastName, String flightNo){
        this.passportNumber= passportNumber;
        this.flightNo= flightNo;
        this.firstName= firstName;
        this.lastName= lastName;
        assignRandomNumLuggage();
        assignRandomCabinClass();
    
    }
    
    public void assignRandomNumLuggage(){
        Random rand= new Random();
        numLuggage= rand.nextInt((5-0) + 0) + 0; //get random number of luggage between 1-5
    }
    
    public void assignRandomCabinClass(){
        char[] classes= {'F', 'B', 'P', 'E'};
        Random rand= new Random();
        int randomSubscript= rand.nextInt((3 - 0) + 1) + 0; //generate random number between 0-3 for the array subscript
        cabinClass= classes[randomSubscript];
    }
    
    public String toString(){
        return  "PP No: " + getPassportNumber() + " Name: " + getFirstName().charAt(0) + "." + getLastName() + " NumLuggage: " + getNumLuggage() + " Class: " + getCabinClass();
    }
    
    //Accessors for each variable
    public String getPassportNumber(){
        return passportNumber;
    }
    
    public String getFlightNumber(){
        return flightNo;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public int getNumLuggage(){
        return numLuggage;
    }
    
    public char getCabinClass(){
        return cabinClass;
    }
}

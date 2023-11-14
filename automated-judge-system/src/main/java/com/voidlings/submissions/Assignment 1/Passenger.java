package com.voidlings.submissions.Assignment 1;

/**
 816032844
 */
import java.util.Random;

public class Passenger
{
    // instance variables - replace the example below with your own
    private String passportNumber;
    private String flightNo;
    private String firstName;
    private String lastName;
    private int numLuggage; 
    private char cabinClass; 

    /**
     * Constructor for objects of class Passenger
     */
    public Passenger(String passportNumber, String flightNo,String firstName,String lastName)
    {
        this.passportNumber=passportNumber;
        this.flightNo=flightNo;
        this.firstName=firstName;
        this.lastName=lastName;
        assignRandomCabinClass();
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    
    public void assignRandomCabinClass()
    {
        // put your code here
        char[] CabClas={'F' ,'B' ,'P' ,'E'};
        
        Random index = new Random();
        
        int inx= index.nextInt(3);
        
        cabinClass=CabClas[inx];
        
        inx= index.nextInt(3);
        
        numLuggage=inx;
        
        
    }
    
    public String toString()
    {
        // put your code here
        char FN=getFirstName().charAt(0);
        String Output=("PP No. "+ passportNumber + " NAME: "+ FN +"."+ getLastName()+" NUMLUGGAGE:"+ getNumLuggage()+ " Class: "+cabinClass);
        return Output;

    }
    
     public String getFirstName()
    {
        // put your code here
        return firstName;

    }
    
    public String getLastName()
    {
        return lastName;

    }
    
    public String getPassportNumber()
    {
        return passportNumber;

    }
    
    public char getCabinClass()
    {
        return cabinClass;

    }
    
    public String getFlightNo()
    {
        // put your code here
        return flightNo;

    }
    
    public int getNumLuggage()
    {
        return numLuggage;
    }
}

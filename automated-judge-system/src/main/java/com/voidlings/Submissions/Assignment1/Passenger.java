package com.voidlings.Submissions.Assignment1;
// Student ID: 816033642

import java.util.Random;
public class Passenger {
     // Variable Declarations
     private String passportNumber;
     private String flightNo;
     private String firstName;
     private String lastName;
     private int numLuggage;
     private char cabinClass;
     
     public Passenger(String passportNumber, String firstName, String lastName, String flightNo){
        this.passportNumber =  passportNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.flightNo = flightNo;
        
        // Assign a random number for numLuggage
        numLuggage = getRandomNumber(0, 4);
        
        // Set Cabin Class.
        assignRandomCabinClass();
     }
     
     public String getPassportNumber(){
         return passportNumber;        
     }
     
     public String getFlightNo(){
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
     
     
     public void assignRandomCabinClass(){
         String classes = "FBPE";
         Random random = new Random();
         cabinClass = classes.charAt(random.nextInt(classes.length()));
     }
     
     public String toString(){
        String s =  "Passenger Information" +
                    "\nPassport Number: " + getPassportNumber() + 
                    "\nName: " + getFirstName().charAt(0) + "." + getLastName().toUpperCase() +
                    "\nNumLuggage: " + getNumLuggage() +
                    "\nClass: " + getCabinClass();
        return s;
     }
     
     public static int getRandomNumber(int min, int max){
        return(int) ((Math.random() * (max-min)) + min);
     }
}
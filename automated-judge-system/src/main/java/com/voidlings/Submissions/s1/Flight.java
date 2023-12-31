package com.voidlings.Submissions.s1;
// Student ID: 816033642

import java.time.LocalDateTime;
public class Flight{
    // Variable Declarations
    private String flightNo;
    private String destination;
    private String origin;
    private LocalDateTime flightDate;
    private LuggageManifest  manifest = new LuggageManifest();
    
    public Flight(String flightNo, String destination, String origin, LocalDateTime flightDate){
        // Initializes variables and creates new LM object.
        this.flightNo = flightNo;
        this.destination = destination;
        this.origin = origin;
        this.flightDate = flightDate;
    }
    
    public String toString(){
        return "Flight Number: " + flightNo +
                "\nDestination: " + destination +
                "\nOrigin: " + origin +
                "\nDate: " + flightDate;
    }
    
    public String getFlightNumber(){
        return flightNo;
    }
    
    public String getDestination(){
        return destination;
    }
    
    public String getOrigin(){
        return origin;
    }
    
    public LocalDateTime getFlightDate(){
        return flightDate;
    }
    
    public LuggageManifest getLuggageManifest(){
        return manifest;
    }
    
    public String checkInLuggage(Passenger p){
        // Validate if passenger can check in luggage for this particular flight.
        // Previously accidentally did this in main - migrate code.
        // If the flight number in the passenger object == this flight number, then true.
        boolean correctFlight = false;
        
        if (p.getFlightNo().equals(this.getFlightNumber())){
            // If correct, then check in their luggage to the manifest.
            // Use the addLuggage method.
            // Return string from addLugagge method.
            manifest.addLuggage(p, this);
            
            System.out.println("Successfully Checked In.\n");
        }
        
        else if (p.getFlightNo() != this.getFlightNumber()){
            // If passenger flightNo does not match this flight number, error message.
            System.out.println("Invalid flight.\n");
        }
        
        return ("");
    }
    
    public int getAllowedLuggage(char cabinClass){
        int numAllowed = 0;
        
        // Class F
        if (cabinClass == 'F'){
            numAllowed = 3;
        }
        
        // Class B
        if (cabinClass == 'B'){
            numAllowed = 2;
        }
        
        // Class P
        if (cabinClass == 'P'){
            numAllowed = 1;
        }
        
        // Class E
        if (cabinClass == 'E'){
            numAllowed = 0;
        }
        
        return numAllowed;
    }
    
    public String printLuggageManifest(){
        return (manifest.toString());
    }
}
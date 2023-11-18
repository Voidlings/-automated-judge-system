package com.voidlings.Submissions.s2;
// Student ID: 816033642

import java.io.File;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class LuggageManagementSystem{
    public static void main (String[] args){
        // Local Date and Time
        LocalDateTime d = LocalDateTime.of(2023, 1, 27, 01, 27, 00);
        
        // ArrayList for passport numbers, first names and last names.
        ArrayList<String> passports = new ArrayList<String>();
        ArrayList<String> firstNames = new ArrayList<String>();
        ArrayList<String> lastNames = new ArrayList<String>();
        
        // This was used to check flight validity in main, but I realized that error
        // checking is supposed to be in the flight class so this now stores the flight
        // numbers in the passenger file regardless of validity.
        ArrayList<String> possibleFlights = new ArrayList<String>();
                
        try{
            File dataFile = new File ("PassengerList.txt");
            Scanner scanner = new Scanner(dataFile);
            String data = "";
            
            while(scanner.hasNextLine()){
                // Read each line of the file and populate ArrayLists.
                // Format is Passport Number[0], FirstName[1], LastName[2]
                String [] split = scanner.nextLine().split(" ");
                passports.add(split[0]);
                firstNames.add(split[1]);
                lastNames.add(split[2]);
                possibleFlights.add(split[3]);
                
                // System.out.println(passports);
                // System.out.println(firstNames);
                // System.out.println(lastNames);
            }
        }
        catch(Exception e){
            
        }

        // Store Flight Information in ArrayLists.
        ArrayList<String> flightNumbers = new ArrayList<String>();
        ArrayList<String> origins = new ArrayList<String>();
        ArrayList<String> destinations = new ArrayList<String>();
        
        try{
            File dataFile = new File ("FlightList.txt");
            Scanner scanner = new Scanner(dataFile);
            
            while(scanner.hasNextLine()){
                // Read each line of the file and populate ArrayLists.
                // Format is Passport Number[0], FirstName[1], LastName[2]
                String [] split = scanner.nextLine().split(" ");
                flightNumbers.add(split[0]);
                origins.add(split[1]);
                destinations.add(split[2]);
                
                // System.out.println(flightNumbers);
                // System.out.println(origins);
                // System.out.println(destinations);
            }
        }
        catch(Exception e){
            
        }
        
        // Create Flights
        // ArrayList to store flights;
        ArrayList<Flight> flights = new ArrayList();
        
        for (int i = 0; i < flightNumbers.size(); i++){
            // Create Flight Objects
            Flight f = new Flight(flightNumbers.get(i), destinations.get(i), origins.get(i), d);
            // System.out.println(f + "\n");
            
            // Add flights to ArrayList.
            flights.add(f);
        }

        // Create Passengers
        // ArrayList to hold passenger objects.
        ArrayList<Passenger> passengers = new ArrayList();
        
        for (int i = 0; i < passports.size(); i++){        
            // Log all passengers into system regardless of validity.
            // Note that error checking occurs in Flight class.
            Passenger p = new Passenger(passports.get(i), firstNames.get(i), lastNames.get(i), possibleFlights.get(i));
            
            passengers.add(p);
            // System.out.println(p + "\n");
        }
        
        // Confirmation message of logging passengers:
        // System.out.println(passengers.size() + " passengers have been logged into the system.\n");
        
        // Check in luggage.
        // For a particular passenger in the passenger ArrayList, check into a random flight.
        int randomIndex = 0;
        
        // RandomFlight initialized as the first flight but this is subject to change.
        Flight randomFlight = flights.get(0);
        
        // For each passenger in the ArrayList, try logging them into a random flight.
        // NOTE: FOR TESTING, CHANGE PASSENGERS.SIZE() TO ANY NUMBER LESS THAN 50.
        for (int i = 0; i < passengers.size(); i++){
            // Choose Random flight
            for (int j = 0; j < 10; j++){
                randomIndex = getRandomNumber(0, flights.size());
                randomFlight = flights.get(randomIndex);
            }
            
            // Identify a passenger.
            Passenger p = passengers.get(i);
            
            // Attempt check in for this passenger on this random flight.
            System.out.println(randomFlight.checkInLuggage(p));
        }
        System.out.println("\n\n");
            
        // Print manifest
        for (int i = 0; i < flights.size(); i++){
            System.out.println("***LUGGAGE MANIFEST FOR FLIGHT " + flights.get(i).getFlightNumber() + "***\n");
            System.out.println(flights.get(i).printLuggageManifest());
        }
    }
    
    // Number Randomizer
    public static int getRandomNumber(int min, int max){
        return(int) ((Math.random() * (max-min)) + min);
    }
}
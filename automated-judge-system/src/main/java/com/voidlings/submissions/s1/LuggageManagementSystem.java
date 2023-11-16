package com.voidlings.submissions.s1;
//ID= 816033710

import java.time.LocalDateTime;
import java.io.File; 
import java.io.FileNotFoundException;
import java.util.*;

public class LuggageManagementSystem{
   
    public static void main(String args[]){
        boolean flightExist;
        flightExist= false;
        int fIndex=-1;
        String flightNo = "";
        String destination = "";
        String origin = "";
        LocalDateTime date= LocalDateTime.of(1, 1, 1, 1,1,1);
                    
        ArrayList <Passenger> p= new ArrayList<Passenger>();
        ArrayList <LuggageSlip> slips= new ArrayList<LuggageSlip>();
        Flight f= new Flight(flightNo, destination, origin, date);
        LuggageManifest manifest= new LuggageManifest();
        
       // f= readFlight();
        p= readPassenger();
                    
         for(int i=0; i < p.size(); i++){
            
             try{
                File flightInput= new File("FlightList.txt");
                Scanner readInput = new Scanner(flightInput);
                
                 while(readInput.hasNextLine()){
                    flightNo = readInput.next();
                    destination = readInput.next();
                    origin = readInput.next();
                    date= LocalDateTime.of(readInput.nextInt(), readInput.nextInt(), readInput.nextInt(), readInput.nextInt(), readInput.nextInt(), readInput.nextInt());
                    f= new Flight(flightNo, destination, origin, date);
                    
                    if(f.flightNo.equals(p.get(i).flightNo)){
                        break;
                     }
                }
            }
            catch(FileNotFoundException error){
                System.out.println("File not Found");
            }
            System.out.println(f.checkInLuggage(p.get(i)));
            //System.out.println(f.printLuggageManifest());
        }
                
    }
    
        
    public static ArrayList<Passenger> readPassenger(){
        try{
            File passengerInput= new File("PassengerList.txt");
            Scanner readInput = new Scanner(passengerInput);
            
            ArrayList <Passenger> p= new ArrayList<Passenger>();
            
            while(readInput.hasNextLine()){
                String passportNo = readInput.next();
                String firstName = readInput.next();
                String lastName = readInput.next();
                String flightNo = readInput.next();
                p.add(new Passenger(passportNo, firstName, lastName, flightNo));
            }
            return p;
        }
        catch(FileNotFoundException error){
            System.out.println("File not Found");
        }
        
        return null;
    }
    
}
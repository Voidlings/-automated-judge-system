package com.voidlings.submissions.s1;
//ID= 816033710
import java.util.*;
import java.time.*;

public class LuggageManifest
{   ArrayList <LuggageSlip> slips= new ArrayList<LuggageSlip>();
    
    public LuggageManifest(){
        ArrayList <LuggageSlip> slips= new ArrayList<LuggageSlip>();
    }
    
    public String addLuggage(Passenger p, Flight f){
    
        int numAllowedPieces= f.getAllowedLuggage(p.cabinClass);
        double excessLuggageCost= getExcessLuggageCost(p.numLuggage, numAllowedPieces);
        String labelStr, str="";
        
        if(p.numLuggage == 0){
            labelStr= "\nNo Luggage to Add.";
        }
        else{
            labelStr= "\nPieces Added: " + p.numLuggage + " Excess Cost: $" + excessLuggageCost + "\n";
            for(int i=0; i < p.numLuggage; i++){
                slips.add(new LuggageSlip(p,f));
                str+= slips.get(i).toStringOne();
            }
        }
        
        String output = p.toString() + labelStr;
        return output + str;
    }
    
    double getExcessLuggageCost(int numPieces, int numAllowedPieces){
        int numExcessLuggage;
        
        if(numPieces > numAllowedPieces){
            numExcessLuggage= numPieces - numAllowedPieces;}
        else{
            numExcessLuggage= 0;
        }
        
        double excessLuggageCost= numExcessLuggage * 35.00;
        
        return excessLuggageCost;
    }
    
    double getExcessLuggageCostByPassenger(String passportNumber){
        double excessLuggageCost=0;
        LocalDateTime dt=  LocalDateTime.of(1, 1, 1, 1,1,1);
        Flight f= new Flight("","","",dt);
        
        for(int i=0; i < slips.size(); i++){
            if(slips.get(i).owner.passportNumber.equals(passportNumber)){
                excessLuggageCost= getExcessLuggageCost(slips.get(i).owner.numLuggage, f.getAllowedLuggage(slips.get(i).owner.cabinClass));
            }
        }
        return excessLuggageCost;
    }
    
    public String toString(){ //****
        boolean has= false;
        int x=0;
        
        String str= "";
        for(int i=0; i < slips.size(); i= i+ x){
            x= slips.get(i).owner.numLuggage;
            str += (slips.get(i).toString());
        }
        return str;
    }

    
    public ArrayList<LuggageSlip> getSlipsList(){
        return slips;
    }
}

package com.voidlings.submissions.s1;
//ID= 816033710
import java.util.*;
import java.time.*;

public class LuggageSlip{
    Passenger owner;
    static int luggageSlipIDCounter= 1;
    static String luggageSlipID;
    String label;
    
    LuggageSlip(Passenger p, Flight f){
        this.owner= p;
        this.luggageSlipID= f.flightNo + "_" + p.lastName + "_" + luggageSlipIDCounter++;
        this.label= "";
    }
    
    LuggageSlip(Passenger p, Flight f, String label){
        this.owner= p;
        this.luggageSlipID= f.flightNo + "_" + p.lastName + "_" + luggageSlipIDCounter++;
        this.label= label;
    }
    
    boolean hasOwner(String passportNumber){
        if(owner.passportNumber.equals(passportNumber))
            return true;
        else
            return false;
    }
    
     public String toString(){ 
        String s= "";
        
        for(int i=0; i < this.owner.numLuggage; i++){
            s += getLuggageSlipID() + " " + owner.toString() + "\n";
                if(label.isEmpty() == false)
                    s += getLabel() + "\n";
        }
        return s;
        
    }
    
    public String toStringOne(){  //One slip
        String s= "";
            s += getLuggageSlipID() + " " + owner.toString() + "\n";
                if(label.isEmpty() == false)
                    s += getLabel() + "\n";
        return s;
    }
    
    //Accessors
    public Passenger getOwner(){
        return owner;
    }
    
    public int getLuggageSlipIDCounter(){
        return luggageSlipIDCounter;
    }
    
    public String getLuggageSlipID(){
        return luggageSlipID;
    }
    
    public String getLabel(){
        return label;
    }
}
package com.voidlings.Submissions.s1;
// Student ID: 816033642

import java.util.ArrayList;
public class LuggageManifest{
    private ArrayList<LuggageSlip> slips;
    
    public LuggageManifest(){
        // Initialize the collection.
        slips = new ArrayList<LuggageSlip>();
    }
    
    public String addLuggage(Passenger p, Flight f){
        // Passenger and Flight are input here from Flight, and handled here.
        // Add one or more luggageSlip objects to the manifest based on numLuggage.
        // How many pieces are allowed based on class?
        int numAllowed = 0;
        double excessCost = 0;
        
        // Class F
        if (p.getCabinClass() == 'F'){
            numAllowed = 3;
        }
        
        // Class B
        if (p.getCabinClass() == 'B'){
            numAllowed = 2;
        }
        
        // Class P
        if (p.getCabinClass() == 'P'){
            numAllowed = 1;
        }
        
        // Class E
        if (p.getCabinClass() == 'E'){
            numAllowed = 0;
        }
        
        if (p.getNumLuggage() > 0){
            // As long as they have one or more pieces of luggage, do:
            for (int i = 0; i < p.getNumLuggage(); i++){
                // Excess cost.
                excessCost = getExcessLuggageCost(p.getNumLuggage(), numAllowed);
                String label = Double.toString(excessCost);
                
                // Add slips.
                // Label is generated if excess cost calculated.
                if (excessCost == 0){
                    LuggageSlip slip = new LuggageSlip(p, f);
                    slips.add(slip);
                    System.out.println(slip + "\n");
                } 
                
                else {
                    LuggageSlip slip = new LuggageSlip(p, f, label);
                    slips.add(slip);
                    System.out.println(slip + "\n");
                }
            }
        }
        
        if (p.getNumLuggage() == 0){
            System.out.println(p.toString() + "\n" + "No Luggage to add.");
            return("");
        }
        
        // Display total excess cost at the end:
        System.out.println("Pieces added: " + p.getNumLuggage() + "\n" + 
                           getExcessLuggageCostByPassenger(p.getPassportNumber()));
        System.out.println("Luggage Added Successfully.");
        return ("");
    }
    
    public double getExcessLuggageCost(int numPieces, int numAllowedPieces){
        double excessCost = 0;
        
        if (numAllowedPieces > numPieces || numAllowedPieces == numPieces){
            excessCost = 0;
        } 
        
        if (numAllowedPieces < numPieces){
            excessCost = 35 * (numPieces - numAllowedPieces);
        }
        
        return excessCost;
    }
    
    public String getExcessLuggageCostByPassenger(String passportNumber){
        // Access the ArrayList and identify the passenger with that passport number.
        // Check the slips for that passenger.
        
        // Initialized to the first one but it will change.
        Passenger p;
        int numAllowed = 0;
        double excessCost = 0;
        int numLuggage = 0;
        
        for(int i = 0; i < slips.size(); i++){
            if (slips.get(i).getOwner().getPassportNumber().equals(passportNumber)){
                p = slips.get(i).getOwner();
                numLuggage = p.getNumLuggage();
                
                // Class F
                if (p.getCabinClass() == 'F'){
                    numAllowed = 3;
                }
                
                // Class B
                if (p.getCabinClass() == 'B'){
                    numAllowed = 2;
                }
                
                // Class P
                if (p.getCabinClass() == 'P'){
                    numAllowed = 1;
                }
                
                // Class E
                if (p.getCabinClass() == 'E'){
                    numAllowed = 0;
                }
            }
        }
        
        if (numAllowed > numLuggage || numAllowed == numLuggage){
            excessCost = 0;
            return ("No excess cost.");
        } 
        
        if (numAllowed < numLuggage){
            excessCost = 35 * (numLuggage - numAllowed);
        }
        
        return ("Excess Cost: $" + excessCost);
    }
    
    public String toString(){
        for (int i = 0; i < slips.size(); i++){
            // Traverse and print.
            LuggageSlip slip = slips.get(i);
            System.out.println(slip.toString() + "\n");
        }
        
        return ("");
    }
}
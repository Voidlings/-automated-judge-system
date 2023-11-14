/**
816032844
 */

package com.voidlings.submissions.Assignment1;

import java.util.ArrayList;
import java.util.Iterator;


public class LuggageManifest
{
    // instance variables - replace the example below with your own
    private ArrayList<LuggageSlip> slips;

   
    /**
     * Constructor for objects of class LuggageManifest
     */
   
    public LuggageManifest()
    {
        slips = new ArrayList<LuggageSlip>();
       
        // initialise instance variables
       
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public String addLuggage(Passenger p, Flight f)
    {
     double x=getExcessLuggageCost(p.getNumLuggage(), f.getAllowedLuggage(p.getCabinClass()));   
         //PP NO. TA890789 NAME: J.BEAN NUMLUGGAGE: 3 CLASS: E   
        // PP NO. TA890789 NAME: J.BEAN NUMLUGGAGE: 2 CLASS: P Pieces Added: (2). Excess Cost: $35
        String out ="";
        if(p.getNumLuggage()==0)
    {
        out =p.toString() + " No Luggage to add";
        return out; 
       // out= p.toString()+" Pieces added:";
        //out//String Label="No Luggage to add";
        //slips.add(new LuggageSlip (p,f,Label));
    }
    String Label = "";
    if (x!=0){
           Label = String.valueOf(x);
       }
    for(int i=0; i<p.getNumLuggage(); i++)
        {
          //  out= ;  
            slips.add(new LuggageSlip (p,f,Label));
           // return ;
        }
        return p.toString() + " Pieces Added:"+"(" + p.getNumLuggage() +")" +" Excesss Cost:" + x; 
    }
   
    public double getExcessLuggageCost( int numPieces, int numAllowedPieces)
    {
        if(numPieces <numAllowedPieces)
        {
            return 0;
        }
        
        double Excess=(numPieces - numAllowedPieces) * 35 ;
       
        return Excess;
       
    }
   
    public String getExcessLuggageCostByPassenger(String passportNumber)
    { String str="";
        for (LuggageSlip element : slips)
      {
         if (element.getOwner().getPassportNumber().equals(passportNumber) ){
             if(!element.getLabel().equals(""))
             {str= element.getLabel();
                }
         }
        // else
       //  return "";
      }
      return str;
      
    }
    
    public String toString()
    {
        String str="";
        for(LuggageSlip element: slips)
        {
            str= str + "\n"+ element.toString()+"\n" ; 
        }
        return str;    
    }
}

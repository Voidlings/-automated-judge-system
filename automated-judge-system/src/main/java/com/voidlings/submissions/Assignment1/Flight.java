package com.voidlings.submissions.Assignment1;


import java.time.LocalDateTime;

/**
816032844
 */
public class Flight 
{
    private String flightNo;
    private String destination;
    private String origin;
    private LocalDateTime flightDate;
    private LuggageManifest manifest;
    
    
    /**
     * Constructor for objects of class Flight
     */
    public Flight(String flightNo,String destination, String origin, LocalDateTime flightDate)
    {
        this.flightNo=flightNo;
        this.destination=destination;
        this.origin=origin;
        this.flightDate=flightDate;        
        manifest= new LuggageManifest(); 
    }

   public String getFlightNo()
    {
        // put your code here
        return flightNo;

    }
    
   public String getDestination()
    {
        // put your code here
        return destination;

    }
    
   public String getOrigin()
    {
        // put your code here
        return origin;

    }
    
   public LocalDateTime getFlightDate()
    {
        // put your code here
        return flightDate;

    }
    
   public LuggageManifest getManifest()
    {
        // put your code here
        return manifest;

    }
   public String checkInLuggage(Passenger p)
   {
       if(getFlightNo()==p.getFlightNo())
       {
         return manifest.addLuggage(p,this);
           
       }
       
       else
       {
           String output="Invalid Flight";
           return output; 
        }
       
   }
  
   public static int getAllowedLuggage(char cabinClass)
   {
       if(cabinClass=='F')
       {  
           return 3;
       }
       
       if(cabinClass=='B')
       {   
           return 2;
       }
       
       if(cabinClass=='P')
       {
           
           return 1;
       }
       
       else 
       return 0;
       
     //  if(cabinClass=='E')
       //{    int r=0;
         //  return r;
       //}
    }
    
   public String printLuggaeManifest()
   {
      return getManifest().toString();
    }
    
   public String toString()
   {
       String output= getFlightNo()+ " Destination:" +getDestination() + " Origin:"+getOrigin()+" "+getFlightDate();
       return output; 
    }

}

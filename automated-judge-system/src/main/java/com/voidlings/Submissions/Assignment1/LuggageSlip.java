package com.voidlings.Submissions.Assignment1;

/**
816032844
 */
public class LuggageSlip
{
    // instance variables - replace the example below with your own
    private Passenger owner;
    private  static int luggageSlipIDCounter= 1;
    private String luggageSlipID;
    private String label;
    
    
    

    /**
     * Constructor for objects of class LuggageSlip
     * 
     */
    public LuggageSlip(Passenger p, Flight f)
    {
        // initialise instance variables
        owner=p;
        luggageSlipIDCounter=luggageSlipIDCounter+1;
        luggageSlipID=f.getFlightNo() +p.getLastName()+luggageSlipIDCounter;
        label="";
    }
    
        public LuggageSlip(Passenger p, Flight f,String label)
    {
        // initialise instance variables
        owner=p;
        luggageSlipIDCounter=luggageSlipIDCounter+1;
        luggageSlipID=f.getFlightNo() +"_"+p.getLastName()+"_"+luggageSlipIDCounter;
        this.label=label;

    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @param  y  a sample parameter for a method
     * @return    the sum of x and y
     */
    public boolean hasOwner(String passportNumber)
    {
        if(passportNumber.equals(getOwner().getPassportNumber()))
        {
            return true;
        }
        else 
        return false; 
    }
    
     public String toString()
    {
        // put your code here
        char FN=getOwner().getFirstName().charAt(0);
        String Output=(luggageSlipID+" PP No. "+ getOwner().getPassportNumber() + " NAME: "+ FN +"."+ getOwner().getLastName()+" NUMLUGGAGE:"+ getOwner().getNumLuggage()+ " Class: "+getOwner().getCabinClass()+ label );
        return Output;

    }
    
    public Passenger getOwner(){
        
        return owner;
        
    }
    
       public String getLabel()
    {
        return label;

    }
}

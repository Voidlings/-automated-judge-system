package com.voidlings.submissions.Assignment 1;

/**
 816032844
 */
import java.util.Random;
import java.time.LocalDateTime;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;


public class LuggageManagementSystem
{
    // instance variables - replace the example below with your own
    
    /**
     * Constructor for objects of class LuggageManagementSystem
     */
    public static void main (String [] args)
    {
        ArrayList<String> firstName= new ArrayList<String>();
        ArrayList<String> lastName= new ArrayList<String>();
        ArrayList<String> fliNum= new ArrayList<String>(); 
        ArrayList<String> pps= new ArrayList<String>();
        
        ArrayList<String> destination= new ArrayList<String>();
        ArrayList<String> origin= new ArrayList<String>();
        ArrayList<String> flightNo= new ArrayList<String>();
        
        ArrayList<Passenger> passengers= new ArrayList<>();
        ArrayList<Flight> flight= new ArrayList<>();

        
        String pData="";
        String fData="";
        String [] arr;
        String [] fArr;
            
            
        try
        {
            File pText= new File("Passenger.txt");
            Scanner pScan= new Scanner(pText);
            
            while(pScan.hasNextLine())
            {
                pData= pScan.nextLine();
                arr= pData.split(",");
                //x=arr.length;                
                //for(int i=0; i<x;i++)
                //{
                    
                   // System.out.println(pData+"WHATTTTTT");  
                    pps.add(arr[0]);
                    System.out.println("Arr: "+arr[0]);
                    System.out.println("PPs: "+pps.get(0));

                    firstName.add(arr[1]);
                    lastName.add(arr[2]);
                    fliNum.add(arr[3]);
                ///}        
                
            }
            
        }
        catch (Exception e)
        {
            System.out.println("Passenger file not found");
            
        }
        
        try
        {
            File fText= new File("Flight.txt");
            Scanner fScan= new Scanner(fText);
            
            while(fScan.hasNextLine())
            {
                fData= fScan.nextLine();
                fArr= fData.split(",");
                //x=arr.length;                
                //for(int i=0; i<x;i++)
                //{   
                   // System.out.println(pData+"WHATTTTTT")
                flightNo.add(fArr[0]);
                origin.add(fArr[1]);
                destination.add(fArr[2]);
                ///}        
                
            }
            
        }
        catch (Exception e)
        {
            System.out.println("Flight file not found");
            
        }
        
        LocalDateTime d=LocalDateTime.of (202,1,23,10,00,00);
        
        Flight xyz= new Flight("BWIA", "JAM", "ROC",d);
        
        //System.out.println(xyz.getOrigin());
        //System.out.println(xyz.getDestination());
        //System.out.println(xyz.getFlightNo());
        //System.out.println(xyz.getFlightDate());
        //System.out.println(xyz);
        
        
        //Random index = new Random();
        
        //int inx= index.nextInt(3);
       // System.out.println("This is the output"+ inx);
        
        
       // Passenger p;
       // String[] pps={"TA823", "VSD904", "OKS984"};
      //  String[] firstNames={"A'Ja", "Luke", "Fredrick"};
       // String[] lastNames={"Bean","Deer","Hart"};
        
        //System.out.println(pps.size()+"This is the size of the list");
        
        for(int i=0; i<pps.size();i++)
        {
            passengers.add(new Passenger(pps.get(i),fliNum.get(i), firstName.get(i),lastName.get(i)));  
           // p=new Passenger(pps.get(i),"BWIA", firstName.get(i),lastName.get(i));
          //  p.assignRandomCabinClass();
           // System.out.println(p);
            System.out.println(passengers.get(i).toString());
            
            System.out.println(xyz.checkInLuggage(passengers.get(i)));
        }
        
        for(int i=0; i<destination.size();i++)
        {
            
            flight.add(new Flight(fliNum.get(i),destination.get(i), origin.get(i),d));  
           // p=new Passenger(pps.get(i),"BWIA", firstName.get(i),lastName.get(i));
          //  p.assignRandomCabinClass();
           // System.out.println(p);
           
            System.out.println(flight.get(i).printLuggaeManifest());
            System.out.println(flight.get(i).checkInLuggage(passengers.get(i)));

        }
        
        
      //  System.out.println(xyz.printLuggaeManifest());
            
    }
    
}

/*
 * Author:      Jeremy Davis
 * Student ID:  260744431
 *  
 * The following program calculates an optimal solution to the 
 * Smart Home Security (SHS) System implementation problem 
 * with a budget of 100$. The way the code has been designed,
 * it could easily be modified to calculate the optimal 
 * implementation for any budget of n$.
 *
 *
 * Note: In order to work with arrays for the dynamic programming
 *       solution, all monetary values in the system are 
 *       multiplied by 10 so they can be treated as integers. This way, 
 *       the dollar costs can be used as indexes into the arrays. In
 *       The final step, the answers are divided by 10 to properly 
 *       display the answer to the true numbers of the problem.
 */

package ecse321_assign5;

import java.math.*;

public class Main {

  
  public static void main(String[] args){
    
    //The variables below represent the solutions to the subproblems
    //which are to be calculated for each step of the dynamic programming
    //algorithm. (ex: Asub150 = answer to the problem with a budget that
    //is 150$ smaller)

    double Asub150; //camera
    double Asub200; //gateway
    double Asub50;  //motion sensor
    double Asub25;  //thermal sensor
    double Asub100; //data cloud storage
    
    double tempMax; // A temporary variable to hold a max
    
    double maxAvailability[] = new double[1001];
    
    //These parallel arrays hold the amount of security modules chosen for a given cost (index)
    //Which would lead to an optimal availability
    int cChosen[] = new int[1001];
    int gChosen[] = new int[1001];
    int mChosen[] = new int[1001];
    int tChosen[] = new int[1001];
    int dChosen[] = new int[1001];
    
    //Array indexing scheme:
    // [i] = optimal solution using i dollars (0 indexing is being ignored).

    //Initialize the base cases
    cChosen[475] = 1;
    gChosen[475] = 1;
    mChosen[475] = 0;
    tChosen[475] = 1;
    dChosen[475] = 1;
    
    cChosen[450] = 1;
    gChosen[450] = 1;
    mChosen[450] = 0;
    tChosen[450] = 0;
    dChosen[450] = 1;
    
    maxAvailability[475] = calculateAvailability(1,0,1,1,1);

    System.out.println("Minimum cost: $47.5, Availability: " + calculateAvailability(1,1,0,1,1) + "\n");
    
    for(int i = 500; i <= 1000; i = i + 25){
      
     //Find the new availabilities of adding 1 more component
     Asub150 = calculateAvailability(cChosen[i-150]+1, gChosen[i-150], mChosen[i-150], tChosen[i-150], dChosen[i-150]); //c
     Asub200 = calculateAvailability(cChosen[i-200], gChosen[i-200]+1, mChosen[i-200], tChosen[i-200], dChosen[i-200]); //g
     Asub50 = calculateAvailability(cChosen[i-50], gChosen[i-50], mChosen[i-50]+1, tChosen[i-50], dChosen[i-50]);  //m
     Asub25 = calculateAvailability(cChosen[i-25], gChosen[i-25], mChosen[i-25], tChosen[i-25]+1, dChosen[i-25]);  //t
     Asub100 = calculateAvailability(cChosen[i-100], gChosen[i-100], mChosen[i-100], tChosen[i-100], dChosen[i-100]+1); //d 
        
     //Find the max of all 5 availabilities
     tempMax = Math.max(Asub150, Asub200);
     tempMax = Math.max(tempMax, Asub50);
     tempMax = Math.max(tempMax, Asub25);
     tempMax = Math.max(tempMax, Asub100);
     
     /*   Print statements for debugging and testing purposes
     System.out.println(i);
     System.out.println("Asub150: " + Asub150);
     System.out.println("Asub200: " + Asub200);
     System.out.println("Asub50: " + Asub50);
     System.out.println("Asub25: " + Asub25);
     System.out.println("Asub100: " + Asub100 + "\n");
     System.out.println("Best Choice:" + tempMax);   
     */

     if(tempMax != 0){
       if(tempMax == Asub150){
         //We have chosen to add another Camera as optimal solution
         cChosen[i] = cChosen[i-150] + 1;
         gChosen[i] = gChosen[i-150];
         mChosen[i] = mChosen[i-150];
         tChosen[i] = tChosen[i-150];
         dChosen[i] = dChosen[i-150];
       }
       if(tempMax == Asub200){
         //We have chosen to add another Gateway as optimal solution
         cChosen[i] = cChosen[i-200];
         gChosen[i] = gChosen[i-200] + 1;
         mChosen[i] = mChosen[i-200];
         tChosen[i] = tChosen[i-200];
         dChosen[i] = dChosen[i-200];
       }
       if(tempMax == Asub50){
         //We have chosen to add another motion sensor as optimal solution
         cChosen[i] = cChosen[i-50];
         gChosen[i] = gChosen[i-50];
         mChosen[i] = mChosen[i-50] + 1;
         tChosen[i] = tChosen[i-50];
         dChosen[i] = dChosen[i-50];
       }
       if(tempMax == Asub25){
         //We have chosen to add another thermal sensor as optimal solution
         cChosen[i] = cChosen[i-25];
         gChosen[i] = gChosen[i-25];
         mChosen[i] = mChosen[i-25];
         tChosen[i] = tChosen[i-25] + 1;
         dChosen[i] = dChosen[i-25];
       }
       if(tempMax == Asub100){
         //We have chosen to add another data storage to cloud as optimal solution
         cChosen[i] = cChosen[i-100];
         gChosen[i] = gChosen[i-100];
         mChosen[i] = mChosen[i-100];
         tChosen[i] = tChosen[i-100];
         dChosen[i] = dChosen[i-100] + 1;
       }
       //Update Max availability
       maxAvailability[i] = calculateAvailability(cChosen[i], gChosen[i], mChosen[i], tChosen[i], dChosen[i]);
     }    
    }
    
    //Print statements to display solutions
    System.out.println("Optimal Choices leading up to 100$:");
    for(double i = 1; i <= 1000; i++){
      if(maxAvailability[(int)i] != 0){
      System.out.println("$" + i/10 +": " + " c = " + cChosen[(int)i] + " " + " g = " + gChosen[(int)i] + " "  + " m = " + mChosen[(int)i] + " "  + " t = " + tChosen[(int)i] + " "  + " d = " + dChosen[(int)i] );
      }
    }
    System.out.println("\nOptimal Availabilities leading up to 100$:");
    for(double i = 1; i <= 1000; i++){
      if(maxAvailability[(int)i] != 0){
      System.out.println("$" + i/10 +": " + maxAvailability[(int)i] );
      }
    }
    System.out.println();
  }

  private static double calculateAvailability(int c, int g, int m, int t, int d){
    //Calculates the availability of an implementation of the system, give the number of security devices.
    
    double availability = 0;
    
    //Formula from question 1) a)
    availability = (1-Math.pow((0.03),c))*(1-Math.pow(0.01,g))*(1-((Math.pow(0.1, m) * Math.pow(0.15, t))))*(1-Math.pow(0.05,d))   ;
    
    //The below condition makes sure every implementation meets the minimum requirements
    //for the number of each components used.
    if(c == 0 ||g == 0 || (m == 0 && t == 0) || d == 0){
      availability = 0;
    }
      
    return availability;
  }
  
}

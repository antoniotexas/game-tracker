/*
Author: Jose A Ramos
        Department of Computer Scince, Texas A&M 
UIN:    124008802
Poject: Game Leaderboard Tracker
Data:   09/10/2016
*/

import java.util.*;
import java.io.*;
import java.util.regex.Pattern;


public class Main{

	 public static void main(String[] args) {
        
      ArrayList<String> saveCommands = new ArrayList();

      Scanner scan = new Scanner(System.in);

      while(scan.hasNextLine()){
      	String str = scan.nextLine();
      	if(!str.equals(" ")){
      		saveCommands.add(str);
      	}	
      }
      scan.close();

      Report r = new Report(saveCommands);
	r.report();
	}
}







import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

public class StandingPerson {
	
	PApplet p;
	int noOfStandingPersons;
	int[] standingPersonX, //Standing Agents Location Array
	      standingPersonY; //Standing Agents Location Array
	
	PImage imgStandingPerson; //Image for standing agents

	public StandingPerson(PApplet _p, int _noOfStandingPersons){
		p = _p;
		noOfStandingPersons = _noOfStandingPersons;
		standingPersonX = new int[noOfStandingPersons];
		standingPersonY = new int[noOfStandingPersons];	
		imgStandingPerson = p.loadImage("man_standing_green.png");
	}
	
	public void SetupStartingLocation(int w, int h, int gridSize){
		//Set up standing peoples initial location randomly
		Random rn = new Random();
		int xmin = gridSize, xmax = w - gridSize;
		int ymin = gridSize, ymax = h - gridSize;
		int xrange = xmax - xmin;
		int yrange = ymax - ymin;
		
		for(int j = 0; j < noOfStandingPersons; j++){			
		    standingPersonX[j] = rn.nextInt(xrange) + xmin;
		    standingPersonY[j] = rn.nextInt(yrange) + ymin;
		} 
	}
	
	public void drawStandingPeople(){
		for(int j = 0; j < noOfStandingPersons; j++){
			p.image(imgStandingPerson, standingPersonX[j], standingPersonY[j], 15, 30);    
		}
	}

}

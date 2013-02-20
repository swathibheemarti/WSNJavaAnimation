import processing.core.*;

public class WSNAnimation extends PApplet {

	ImportAgentsDataFromExcel objAgentDataImport;
	Sensor sensor;
	StandingPerson objStandingPerson;
	
	int w = 600, h = 600,            //Screen Size  
	    gridSize = 40,               //Grid Size
	    noOfSensors = 40,            //No of Sensors watching agents 
	    t = 1,                       //Time variable
	    noOfAgents = 200,            //No of agents -- this is coming from Matlab
	    noOfAgentSets = 100,         //No of agent Sets (time laps)-- this is coming from Matlab
	    noOfAgentProperties = 10,    //No of agent properties, X, Y, S, D, T, p(x,y), q(s,d), s(p(x,y)), 
	                                 //s(q(s,d)), Suspiciousness
	    noOfStandingAgents = 20,     //No of agents not moving
	    noOfSuspiciousAgents = 0;  
	
	//10 sets of 200 Agents data, data points are 
	//X, Y, S, D, T, p(x,y), q(s,d), s(p(x,y)), s(q(s,d)), Suspiciousness (0 or 1)
	double AgentData[][][] = new double[noOfAgentSets][noOfAgents][noOfAgentProperties]; 
	
	int[] personX = new int[noOfAgents], personY = new int[noOfAgents],   //Agents Locations Array
	      standingPersonX = new int[noOfStandingAgents], //Standing Agents Location Array
	      standingPersonY = new int[noOfStandingAgents]; //Standing Agents Location Array

	
	
	PImage[] imgPeople = new PImage[noOfAgents];  //Image for moving agents
	PImage[] imgPeopleWalkingLeft = new PImage[noOfAgents]; //Image for agents moving opposite direction
	PImage[] suspiciousAgents = new PImage[noOfAgents]; //Suspicious agent Image
	PImage[] suspiciousAgentWalkingLeft = new PImage[noOfAgents]; //Suspicious agent Image
	
	PImage[] imgStandingPeople = new PImage[noOfStandingAgents]; //Image for standing agents
	
	/*
	public static void main(String args[]){
		PApplet.main(new String[] {"--present", "WSNAnimation"});
	}*/
	
	public void setup(){
		
		size(w, h);
		LoadAgents();
		SetupInitialSensorLocations();
		SetupStandingPeople();
	}
	
	public void LoadAgents(){
		objAgentDataImport = new ImportAgentsDataFromExcel(this, noOfAgentSets, noOfAgents, noOfAgentProperties, "AgentsData.xls");
		AgentData = objAgentDataImport.LoadAgentData();
		
		//Print Data
		//objAgentDataImport.PrintAgentData();
	}
	
	public void SetupInitialSensorLocations(){	
		sensor = new Sensor(this, noOfSensors);		
		sensor.setupSensorLocations(w, h, gridSize);		
	}
	
	public void SetupStandingPeople(){
		objStandingPerson = new StandingPerson(this, noOfStandingAgents);
		objStandingPerson.SetupStartingLocation(w, h, gridSize);
	}
	
	public void draw(){
		//Background is set to white
		background(255);
		
		delay(100);
		drawGrid();  
		drawRandomSensors();  	
		drawRandomStandingPeople();
	}
	
	/*
	  Grid lines are drawn horizontally and vertically
	*/
	public void drawGrid(){
		//horizontal lines
		for(int y = gridSize; y <= h; y += gridSize){
			line(0, y, w, y);
		}
	  
		//vertical lines
		for(int x = 0; x <= w; x+=gridSize){
			line(x, 0, x, h);
		} 
	}
	
	public void drawRandomSensors(){
		sensor.drawSensors();
	}
	
	public void drawRandomStandingPeople(){
		objStandingPerson.drawStandingPeople();
	}
	
}

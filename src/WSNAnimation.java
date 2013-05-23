import processing.core.*;

public class WSNAnimation extends PApplet {

	ImportAgentsDataFromExcel objAgentDataImport;
	Sensor objSensor;
	StandingPerson objStandingPerson;
	Agent objAgent;
	
	int w = 600, h = 600,            //Screen Size  
	    gridSize = 40,               //Grid Size
	    noOfSensors = 40,            //No of Sensors watching agents 
	    t = 1,                       //Time variable
	    noOfAgents = 50,             //No of agents -- this is coming from Matlab
	    noOfAgentSets = 50,          //No of agent Sets (time laps)-- this is coming from Matlab
	    noOfAgentProperties = 10,    //No of agent properties, X, Y, S, D, T, p(x,y), q(s,d), s(p(x,y)), 
	                                 //s(q(s,d)), Suspiciousness
	    noOfStandingAgents = 20;     //No of agents not moving
	    
	
	//10 sets of 200 Agents data, data points are 
	//X, Y, S, D, T, p(x,y), q(s,d), s(p(x,y)), s(q(s,d)), Suspiciousness (0 or 1)
	double AgentData[][][] = new double[noOfAgentSets][noOfAgents][noOfAgentProperties]; 
	
	public void setup(){
		
		size(w, h);
		
		LoadAgents();
		SetupInitialSensorLocations();
		SetupStandingPeople();
		
		objAgent = new Agent(this, noOfAgents);
	}
	
	public void LoadAgents(){
		
		objAgentDataImport = new ImportAgentsDataFromExcel(this, noOfAgentSets, noOfAgents, noOfAgentProperties, "AgentsData.xls");
		AgentData = objAgentDataImport.LoadAgentData();
		
	}
	
	public void SetupInitialSensorLocations(){	
		objSensor = new Sensor(this, noOfSensors);		
		objSensor.setupSensorLocations(w, h, gridSize);		
	}
	
	public void SetupStandingPeople(){
		objStandingPerson = new StandingPerson(this, noOfStandingAgents);
		objStandingPerson.SetupStartingLocation(w, h, gridSize);
	}
	
	public void draw(){
		
		//Background is set to white
		background(255);
		
		delay(150);
		
		drawGrid();  
		drawRandomSensors();  	
		drawRandomStandingPeople();
		drawAgents();
		
		t++;
		
		if(t >= noOfAgentSets){
			noLoop();			
		}
		
	}
	
	public void mousePressed(){
		noLoop();
	}
	
	public void mouseReleased(){
		loop();
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
		objSensor.drawSensors();
	}
	
	public void drawRandomStandingPeople(){
		objStandingPerson.drawStandingPeople();
	}
	
	public void drawAgents(){
		objAgent.drawAgents(AgentData, t);
	}	
}

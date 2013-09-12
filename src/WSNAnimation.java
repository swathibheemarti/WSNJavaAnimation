import processing.core.*;
import de.bezier.data.*;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

public class WSNAnimation extends PApplet {

	ImportAgentsDataFromExcel objAgentDataImport;
	Sensor objSensor;
	StandingPerson objStandingPerson;
	Agent objAgent;
	PImage imgPause, imgPlay;
	boolean onPause = false, onPlay = false;	
	boolean paused = false;
	
	int w = 0, h = 0,            //Screen Size  
	    gridSize = 0,               //Grid Size
	    noOfSensors = 40,            //No of Sensors watching agents 
	    t = 1,                       //Time variable
	    noOfAgents = 0,            //No of agents -- this is coming from Matlab
	    noOfAgentSets = 0,         //No of agent Sets (time laps)-- this is coming from Matlab
	    noOfAgentProperties = 10,    //No of agent properties, X, Y, S, D, T, p(x,y), s(p(x,y)), q(s,d),
	                                 //s(q(s,d)), Suspiciousness
	    noOfStandingAgents = 20;     //No of agents not moving
	   
	PFont f;
	
	//10 sets of 200 Agents data, data points are 
	//X, Y, S, D, T, p(x,y), q(s,d), s(p(x,y)), s(q(s,d)), Suspiciousness (0 or 1)
	double AgentData[][][] = new double[noOfAgentSets][noOfAgents][noOfAgentProperties]; 
	
	public void setup(){
				
		Initialize();
		
		imgPause = loadImage("PauseButton.png");
		imgPlay = loadImage("PlayButton.png");
		
		size(w, h + 35);
		
		LoadAgents();
		SetupInitialSensorLocations();
		SetupStandingPeople();
		
		objAgent = new Agent(this, noOfAgents, w, h);
		
		f = createFont("Book Antiqua",10,true);
	}
	
	public void Initialize(){
		
		XlsReader reader1 = new XlsReader(this, "AgentsData.xls");
		
		reader1.firstRow();
		
		//reader.nextRow();
		
		if(reader1.hasMoreRows()){
			noOfAgents = (int) Math.round(reader1.getFloat(reader1.getRowNum(), 1));
			gridSize = (int) Math.round(reader1.getFloat(reader1.getRowNum(), 3));
			noOfAgentSets = (int) Math.round(reader1.getFloat(reader1.getRowNum(), 5));
			w = h = (int) Math.round(reader1.getFloat(reader1.getRowNum(), 7));
		}				
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
		
		update(mouseX, mouseY);
		
		//Background is set to white
		background(255);
		textFont(f,10);
		
		
		delay(150);
		
		drawPause();
		drawPlay();
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
		update(mouseX, mouseY);
		
		if(onPause && !paused){
			noLoop();
			paused = true;
		}else if(onPlay && paused){
			loop();
			paused = false;
		}else if(!paused){
			objAgent.drawAgentDetails(AgentData, t, mouseX, mouseY);
			noLoop();
			paused = true;
		}
	}
	
	/*
	public void mouseReleased(){
		loop();
	}*/
	
	public void drawPause(){		
		fill(255);
		rect((w/2 - 25), h + 5, 25, 25);		
		image(imgPause, (w/2 - 25), h + 5, 25, 25);
	}
	
	public void drawPlay(){		
		fill(255);
		rect((w/2 + 25), h + 5, 25, 25);		
		image(imgPlay, (w/2 + 25), h + 5, 25, 25);
	}
	
	void update(int x, int y) {			
		onPause = overRect((w/2 - 25), h + 5, 25, 25);	
		onPlay = overRect((w/2 + 25), h + 5, 25, 25);
	}
	
	boolean overRect(int x, int y, int width, int height)  {
	  if (mouseX >= x && mouseX <= x+width && 
	      mouseY >= y && mouseY <= y+height) {
	    return true;
	  } else {
	    return false;
	  }
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
		//objAgent.drawAgentsDetails(AgentData, t);
	}	
}

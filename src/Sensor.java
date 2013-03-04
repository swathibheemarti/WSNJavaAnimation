import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

public class Sensor {
	
	PApplet p;
	PImage imgSensor;
	int noOfSensors;
	int[] sensorX, sensorY; //Sensors Location Array
	
	
	public Sensor(PApplet _p, int _noOfSensors){
		p = _p;
		sensorX = new int[_noOfSensors];
		sensorY = new int[_noOfSensors];
		noOfSensors = _noOfSensors;
		imgSensor = p.loadImage("sensor.png"); 
	}
	
	/*
	  xGrid and yGrid are generated randomly, 
	  xGrid, yGrid represent random cell (grid) for placement of sensor
	  x & y co-ordinate is calculated based onthe grid locatin as 
	  x = gridnumber * size of grid + (som value to place the sensor middle of the grid)
	*/

	public void setupSensorLocations(int w, int h, int gridSize){
	  int xGrid = 0, yGrid = 0;
	  
	  Random rn = new Random();
	  
	  for(int s = 0; s < noOfSensors; s++){
	    
	    xGrid = rn.nextInt(w/gridSize);
	    yGrid = rn.nextInt(h/gridSize);
	    
	    sensorX[s] = (xGrid * gridSize) + (gridSize / 4);
	    sensorY[s] = (yGrid * gridSize) + (gridSize / 4); 
	  }
	}
	
	public void drawSensors(){
		for(int s = 0; s < noOfSensors; s++){     
		     p.image(imgSensor, sensorX[s], sensorY[s], 10, 10);
	     }
	}
	
}

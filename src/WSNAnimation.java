import processing.core.*;

public class WSNAnimation extends PApplet {

	ImportAgentsDataFromExcel objAgentDataImport;
	
	int w = 600, h = 600,            //Screen Size  
	    gridSize = 40,               //Grid Size
	    noOfSesnors = 40,            //No of Sensors watching agents 
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
	
	
	/*
	public static void main(String args[]){
		PApplet.main(new String[] {"--present", "WSNAnimation"});
	}*/
	
	public void setup(){
		
		size(w, h);
		objAgentDataImport = new ImportAgentsDataFromExcel(this, noOfAgentSets, noOfAgents, noOfAgentProperties, "AgentsData.xls");
		
		AgentData = objAgentDataImport.LoadAgentData();
		
		//Print DAta
		objAgentDataImport.PrintAgentData();
		
		
	}
	
	public void draw(){
		
	}

}

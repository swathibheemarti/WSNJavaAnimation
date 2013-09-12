import processing.core.PApplet;
import de.bezier.data.*;
import java.util.*;

public class ImportAgentsDataFromExcel {

	PApplet p;
	
	//10 sets of 200 Agents data, data points are 
	//X, Y, S, D, T, p(x,y), q(s,d), s(p(x,y)), s(q(s,d)), Suspiciousness (0 or 1)
	double AgentData[][][];
	
	String XLSPath; 
	XlsReader reader;
	int noOfAgentSets,noOfAgents,noOfAgentProperties;
	
	public ImportAgentsDataFromExcel(PApplet _p, int _noOfAgentSets, int _noOfAgents, int _noOfAgentProperties, String xlsPath){
		
		p = _p;
		
		noOfAgentSets = _noOfAgentSets;
		noOfAgents = _noOfAgents;
		noOfAgentProperties = _noOfAgentProperties;
		
		AgentData = new double[noOfAgentSets][noOfAgents][noOfAgentProperties];
		XLSPath = xlsPath;
		reader = new XlsReader(p, XLSPath);
		
	}
	
	public double[][][] LoadAgentData(){
		
		  reader.firstRow();
		
		  //Ignore Simulation Details row		  
		  reader.nextRow();
  		  //Ignore Header Row
		  reader.nextRow(); 
		  
		  
		  int rowindex = 0;
		  int i = 0, j = 0;
		     
		  while(reader.hasMoreRows()){
		    
		    if(rowindex >= noOfAgentSets * noOfAgents)
		      break;
		    
		    reader.nextRow();
		    rowindex++;
		    
		    for(int k = 1; k <= noOfAgentProperties; k++){
		      
		      AgentData[i][j][k-1] = Math.floor( reader.getFloat(reader.getRowNum(), k) * 100000 ) / 100000.0000;
		            
		    }
		    
		    j++;
		    if(j >= noOfAgents){
		      j = 0;
		      i++;
		    }    
		  }
		  		 
		  return AgentData;      
	}
	
	/*
	 * Current doing this Mock setup to show the moving agents and suspicious agents clearly
	 * Will move this code to Matlab randomness later for more genuine values
	 */
	public double[][][] LoadXY(double[][][] agentData){
		Random rn = new Random();
		
		for (int i = 1; i < noOfAgentSets; i++) {
			
			for (int j = 0; j < noOfAgents/2; j++) {
				agentData[i][j][0] = agentData[i-1][j][0] + rn.nextInt(10);  
				agentData[i][j][1] = agentData[i-1][j][1] + rn.nextInt(2);						
			}
			
			for (int j = noOfAgents/2; j < noOfAgents; j++) {
				agentData[i][j][0] = agentData[i-1][j][0] - rn.nextInt(10);
				agentData[i][j][1] = agentData[i-1][j][1] - rn.nextInt(2);								
			}	
			
		}
		
		return agentData;
	}
	
	public void PrintAgentData(){		  
	    for(int j=0; j<noOfAgents; j++){
	      for(int k=0; k<noOfAgentProperties; k++){
	        p.print(AgentData[0][j][k] + "   ");
	      }
	      p.println();
	    }
	}
	
	public double GetData(int set, int agent, int property){
		return AgentData[set][agent][property];
	}
	
}

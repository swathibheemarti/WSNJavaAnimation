import processing.core.PApplet;
import de.bezier.data.*;

public class ImportAgentsDataFromExcel {

	PApplet p;
	double AgentData[][][];
	String XLSPath; 
	XlsReader reader;
	int noOfAgentSets,noOfAgents,noOfAgentProperties;
	
	public ImportAgentsDataFromExcel(PApplet p, int _noOfAgentSets, int _noOfAgents, int _noOfAgentProperties, String xlsPath){
		
		noOfAgentSets = _noOfAgentSets;
		noOfAgents = _noOfAgents;
		noOfAgentProperties = _noOfAgentProperties;
		
		AgentData = new double[noOfAgentSets][noOfAgents][noOfAgentProperties];
		XLSPath = xlsPath;
		reader = new XlsReader(p, XLSPath);
		
	}
	
	public double[][][] LoadAgentData(){
		  
		  //Ignore Header Row
		  reader.nextRow(); 
		  
		  int rowindex = 0;
		  int i = 0, j = 0;
		     
		  while(reader.hasMoreRows()){
		    
		    if(rowindex >= noOfAgentSets * noOfAgents)
		      break;
		    
		    reader.nextRow();
		    rowindex++;
		    
		    for(int k = 1; k <= noOfAgentProperties-1; k++){
		      
		      AgentData[i][j][k-1] = Math.floor( reader.getFloat(reader.getRowNum(), k) * 100000 ) / 100000.0000;
		            
		    }
		          
		    //Suspicious
		    if(reader.getString(reader.getRowNum(), noOfAgentProperties).toLowerCase().contains("y")){     
		      AgentData[i][j][noOfAgentProperties-1] = 1;
		    }
		    else{
		      AgentData[i][j][noOfAgentProperties-1] = 0;
		    }
		    
		    j++;
		    if(j >= noOfAgents){
		      j = 0;
		      i++;
		    }    
		  }
		  
		  return AgentData;
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

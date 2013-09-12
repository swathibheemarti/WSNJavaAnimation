import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

public class Agent {
	
	PApplet p;
	int noOfAgents;
	int[] agentX;
	int[] agentY;
	int w, h;
	PImage imgAgentGoingRight, imgAgentGoingLeft, 
	       imgSuspiciousAgentGoingLeft, imgSuspiciousAgentGoingRight;
	
	String agentDetails = "";
	
	public Agent(PApplet _p, int _noOfAgents, int _w, int _h){
		p = _p;
		noOfAgents = _noOfAgents;
		agentX = new int[noOfAgents];
		agentY = new int[noOfAgents];
		w = _w;
		h = _h;
		
		imgAgentGoingRight = p.loadImage("man_walking_right.png");
		imgAgentGoingLeft = p.loadImage("man_walking_left.png");	
		imgSuspiciousAgentGoingLeft = p.loadImage("man_walking_left_red.png");
		imgSuspiciousAgentGoingRight = p.loadImage("man_walking_Right_red.png");
	}
	
	public void SetupAgentsInitialLocation(double[][][] AgentData){
		for(int m = 0; m < noOfAgents; m++){    
			//AgentData[0:first set of agent data][m:agent no][0:X location]
			agentX[m] = ((Double)AgentData[0][m][0]).intValue(); 
		    //AgentData[0:first set of agent data][m:agent no][1:Y location]
			agentY[m] = ((Double)AgentData[0][m][1]).intValue(); 
		}
	}
	
	public void drawAgents(double[][][] AgentData, int t) {
		
		Random rn = new Random();
		
		for (int m = 0; m < noOfAgents; m++) {
			// AgentData[0:first set of agent data][m:agent no][0:X location]
			agentX[m] = ((Double) AgentData[t][m][0]).intValue();

			// AgentData[0:first set of agent data][m:agent no][1:Y location]
			// NOTE : We are ignoring Y co-ordinate changes from Matlab for now
			// So Agents only move in Left to Right or Right to Left
			// at whatever Y location they were initially
			agentY[m] = ((Double) AgentData[t][m][1]).intValue();
			
			if(agentX[m] < 0 || agentX[m] > w || agentY[m] < 0 || agentY[m] + 25 > h){
				continue;
			}

			//Introducing random here so we only draw path for 20% of the agents each time
			if(rn.nextInt(100) < 15)
			{
				p.stroke(0,0,0);
				//p.strokeWeight(2);
				for (int i = 1; i < t; i++) {

					int x1 = ((Double) AgentData[i-1][m][0]).intValue(),
						y1 = ((Double) AgentData[i-1][m][1]).intValue(),
						x2 = ((Double) AgentData[i][m][0]).intValue(),
						y2 = ((Double) AgentData[i][m][1]).intValue();
					
					
					p.line(x1, y1, x2, y2);
						
				}		
				p.stroke(0);
				//p.strokeWeight(1);
			}
			
			//Draw path for the suspicious agent
			if(AgentData[t][m][9] == 1){
				
				p.stroke(255,0,0);
				p.strokeWeight(2);
				
				for (int i = 1; i < t; i++) {

					int x1 = ((Double) AgentData[i-1][m][0]).intValue(),
						y1 = ((Double) AgentData[i-1][m][1]).intValue(),
						x2 = ((Double) AgentData[i][m][0]).intValue(),
						y2 = ((Double) AgentData[i][m][1]).intValue();
					
					
					p.line(x1, y1, x2, y2);
						
				}
								
				p.stroke(0);
				p.strokeWeight(1);
			}
						
			
			if (agentX[m] < ((Double) AgentData[t - 1][m][0]).intValue()) {
				if (AgentData[t][m][9] == 1) {
					p.image(imgSuspiciousAgentGoingLeft, agentX[m], agentY[m],10, 25);
					//p.delay(1000);
				} else {
					p.image(imgAgentGoingLeft, agentX[m], agentY[m], 10, 25);
				}
			} else {
				if (AgentData[t][m][9] == 1) {
					p.image(imgSuspiciousAgentGoingRight, agentX[m], agentY[m],10, 25);
					//p.delay(1000);
				} else {
					p.image(imgAgentGoingRight, agentX[m], agentY[m], 10, 25);
				}
			}
		}
	}
	
	public void drawAgentsDetails(double[][][] AgentData, int t) {
		
		Random rn = new Random();
		
		for (int m = 0; m < noOfAgents; m++) {
			// AgentData[0:first set of agent data][m:agent no][0:X location]
			agentX[m] = ((Double) AgentData[t][m][0]).intValue();

			// AgentData[0:first set of agent data][m:agent no][1:Y location]
			// NOTE : We are ignoring Y co-ordinate changes from Matlab for now
			// So Agents only move in Left to Right or Right to Left
			// at what ever Y location they were initially
			agentY[m] = ((Double) AgentData[t][m][1]).intValue();
			
			if(agentX[m] < 0 || agentX[m] > w || agentY[m] < 0 || agentY[m] + 25 > h){
				continue;
			}

			//Introducing random here so we only draw path for 10% of the agents each time
			if(rn.nextInt(100) < 10)
			{
				agentDetails = "X : " + agentX[m] + 
						       "Y : " + agentY[m] + 
						       "S : " + ((Double) AgentData[t][m][2]).intValue() + 
						       "D : " + ((Double) AgentData[t][m][3]).intValue();
				
				p.fill(102,0,0);
				p.text(agentDetails,agentX[m] + 10,agentY[m]);
				p.fill(0);
			}
		}		
		
	}
	
	public void drawAgentDetails(double[][][] AgentData, int t, int x, int y){
		for (int m = 0; m < noOfAgents; m++) {
			
			agentX[m] = ((Double) AgentData[t][m][0]).intValue();
			agentY[m] = ((Double) AgentData[t][m][1]).intValue();
			
			if(agentX[m] >= x - 20 && agentX[m] <= x + 20 && agentY[m] >= y - 20 && agentY[m] <= y + 20){
				
				agentDetails = "X : " + agentX[m] + 
						       "Y : " + agentY[m] + 
						       "S : " + ((Double) AgentData[t][m][2]).intValue() + 
						       "D : " + ((Double) AgentData[t][m][3]).intValue();
			
				p.fill(102,0,0);
				p.text(agentDetails,agentX[m] + 10,agentY[m]);
				p.fill(0);
				
				break;
			}			
		}
	}
	
}

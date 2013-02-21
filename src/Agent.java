import processing.core.PApplet;
import processing.core.PImage;
import java.util.*;

public class Agent {
	
	PApplet p;
	int noOfAgents;
	int[] agentX;
	int[] agentY;
	PImage imgAgentGoingRight, imgAgentGoingLeft, 
	       imgSuspiciousAgentGoingLeft, imgSuspiciousAgentGoingRight;
	
	public Agent(PApplet _p, int _noOfAgents){
		p = _p;
		noOfAgents = _noOfAgents;
		agentX = new int[noOfAgents];
		agentY = new int[noOfAgents];
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
		for (int m = 0; m < noOfAgents; m++) {
			// AgentData[0:first set of agent data][m:agent no][0:X location]
			agentX[m] = ((Double) AgentData[t][m][0]).intValue();

			// AgentData[0:first set of agent data][m:agent no][1:Y location]
			// NOTE : We are ignoring Y co-ordinate changes from Matlab for now
			// So Agents only move in Left to Right or Right to Left
			// at what ever Y location they were initially
			agentY[m] = ((Double) AgentData[0][m][1]).intValue();

			// Drawing a line for Agent[m] from location @t=0 to t=current
			for (int i = 0; i < t; i++) {

				p.line(((Double) AgentData[i][m][0]).intValue(),
						((Double) AgentData[0][m][1]).intValue(),
						((Double) AgentData[i][m][0]).intValue(),
						((Double) AgentData[0][m][1]).intValue());
			}

			
			
			if (agentX[m] < ((Double) AgentData[t - 1][m][0]).intValue()) {
				if (AgentData[0][m][9] == 1) {
					p.image(imgSuspiciousAgentGoingLeft, agentX[m], agentY[m],10, 25);
				} else {
					p.image(imgAgentGoingLeft, agentX[m], agentY[m], 10, 25);
				}
			} else {
				if (AgentData[0][m][9] == 1) {
					p.image(imgSuspiciousAgentGoingRight, agentX[m], agentY[m],10, 25);
				} else {
					p.image(imgAgentGoingRight, agentX[m], agentY[m], 10, 25);
				}
			}
		}
	}
}

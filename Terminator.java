package skynet_ufba;
import robocode.*;
import java.awt.*;


public class Terminator extends AdvancedRobot
{
	public double speed = 5000;
	public double arena;
	public double pontoX;
    public double pontoY;
	public double pos_inimigo; 
    public double distancia;  	
	public double energ_inimigo;


	public void run() {
	
		setRadarColor(Color.black);
		setBodyColor(Color.black);
		setGunColor(Color.black);

		arena = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		speed = arena;
		turnLeft(getHeading() % 90);
		ahead(speed);		
				
		turnGunRight(90);
		turnRight(90);	
		
		while(true){	
			if(speed == 0){
				turnGunLeft(90);
				turnGunRight(90);
			}
			else { 
				ahead(speed);		
				turnRight(90);	
			}			
		}
	}

	
	public void onScannedRobot(ScannedRobotEvent e) {	
		
		distancia = e.getDistance();	

		if(speed == 0 ){
			if(distancia > 200){
				fire(1);
			}
			else{
				fire(3);
			}
		}	
		fire(2);		
	}

	public void onHitByBullet(HitByBulletEvent e) {	
		speed = arena;
	}
	

	public void onHitWall(HitWallEvent e) {		
		pontoX = getX();
     	pontoY = getY();  
		   	
		if((pontoX == 18 && pontoY == 18 ) ||    		
		(pontoX == 18 && pontoY == 782) ||    			
		(pontoX == 782 && pontoY == 18) || 
   		(pontoX == 782 && pontoY == 782)){
   			speed = 0; 			
     	} 
	}	


}

package skynet_ufba;
import robocode.*;
import java.awt.Color;

//getHeading Retorna o ângulo em graus que o meu robô está virado em direção ao norte. Varia de (0 - 360)
//getGunHeading Retorna o ângulo em graus que o canhão está virado. Varia de (0 - 360)
//getBearing Retorna o ângulo em graus em que o robô adversário está virado para nós. Varia de (0 - 360)


public class Exterminio extends AdvancedRobot
{
	
	public double speed = 5000;
	public double arena;
	
	public double pontoX;
    public double pontoY;

	public void run() {
		
		setColors(Color.green, Color.black, Color.white, Color.green, Color.red);
		arena = Math.max(getBattleFieldWidth(), getBattleFieldHeight());

		while(true) {


			if(getOthers() == 1){ //Genesis

				if(nearbyWall()) {
					back(400);
				}
				else {
					
					setAhead(200);
					
					setTurnGunRight(360); 
					setTurnGunLeft(360); 
					
					setTurnRight(360); 
					setTurnLeft(360); 
					
					execute();				
				}
			}//Fim Genesis

			else{ //Terminator
				speed = arena;
				turnLeft(getHeading() % 90);
				ahead(speed);	

				if(speed == 0){
					turnGunLeft(90);
					turnGunRight(90);
				}
				else { 
					ahead(speed);		
					turnRight(90);	
				}				
			}//Fim Terminator
		}
	}


	public void onScannedRobot(ScannedRobotEvent e) {		
		
		double pos_inimigo = e.getBearing();
		double distancia_inimigo = e.getDistance();
		
		double angulo = getHeading() + pos_inimigo - getGunHeading(); 

		if(getOthers() == 1)//Genesis
			if (!(angulo > -180 && angulo <= 180)) { 

				while (angulo <= -180) { 
					angulo += 360;
				}
				while (angulo > 180) { 
					angulo -= 360;
				}
			}
			turnGunRight(angulo); 

			if (distancia_inimigo > 200 || getEnergy() < 15) { 
				setFireBullet(1.5); 
			} else if (distancia_inimigo > 50) { 
				setFireBullet(2); 
			} else {
				setFireBullet(3); 	
    		}

    		if(e.getEnergy() < 12) { 
				hitKill(e.getEnergy()); 
			}	
		}//Fim Genesis	
		else{

			if(speed == 0 ){
				if(distancia > 200){
					fire(1);
				}
				else{
					fire(3);
				}
			}
			else{
				fire(2);
			}	
		}
	}


	
	public void onHitByBullet(HitByBulletEvent e) {
		
		double angulo = getHeading() + e.getBearing() - getGunHeading(); 

		if(getOthers() == 1){//Genesis
			if (!(angulo > -180 && angulo <= 180)) { 

				while (angulo <= -180) { 
					angulo += 360;
				}
				while (angulo > 180) {
					angulo -= 360;
				}
			}
			turnGunRight(angulo); 
		}
		else{//Terminator
			speed = arena;
		}	
	}
	
	
	public void onHitWall(HitWallEvent e) {

		if(getOthers() == 1){//Genesis
			setAhead(300);
			setTurnRight(360);
			setAhead(200);	
		}
		else{//Terminator
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
	
	public void hitKill(double energia_inimigo) { 
		double tiro_energia = (energia_inimigo / 4) + 0.1;
		setFireBullet(tiro_energia);
	}
	
	public boolean nearbyWall() {
		return (getX() < 50 || getX() > getBattleFieldWidth() - 50 || getY() < 50 || getY() > getBattleFieldHeight() - 50);
	}
	
}

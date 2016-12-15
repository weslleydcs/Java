package skynet_ufba;
import robocode.*;
import java.awt.Color;

//getHeading Retorna o ângulo em graus que o meu robô está virado em direção ao norte. Varia de (0 - 360)
//getGunHeading Retorna o ângulo em graus que o canhão está virado. Varia de (0 - 360)
//getBearing Retorna o ângulo em graus em que o robô adversário está virado para nós. Varia de (0 - 360)


public class Genesis extends AdvancedRobot
{
	
	public double arena;

	public void run() {
		
		setColors(Color.yellow, Color.black, Color.white, Color.green, Color.red);
		arena = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		//setAdjustGunForRobotTurn(true);
		
		while(true) {

			if(nearbyWall()) {
				back(400);
			} else {
				setAhead(200);
				setTurnGunRight(360); 
				setTurnGunLeft(360); 
				setTurnRight(360); 
				setTurnLeft(360); 
				execute();
			}
		}
	}


	public void onScannedRobot(ScannedRobotEvent e) {
		
		double pos_inimigo = e.getBearing();
		double distancia_inimigo = e.getDistance();
		
		double angulo = getHeading() + pos_inimigo - getGunHeading(); 

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
	}

	
	public void onHitByBullet(HitByBulletEvent e) {
		
		double angulo = getHeading() + e.getBearing() - getGunHeading(); 

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
	
	
	public void onHitWall(HitWallEvent e) {
		setAhead(300);
		setTurnRight(360);
		setAhead(200);	
	}	
	
	public void hitKill(double energia_inimigo) { 
		double tiro_energia = (energia_inimigo / 4) + 0.1;
		fire(tiro_energia);
	}
	
	public boolean nearbyWall() {
		return (getX() < 50 || getX() > getBattleFieldWidth() - 50 || getY() < 50 || getY() > getBattleFieldHeight() - 50);
	}
	
	public void onWin(WinEvent e) {
		turnRight(36000);
	}

}

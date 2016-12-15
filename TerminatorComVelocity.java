package SKY;
import java.awt.Color;
import robocode.*;

public class Binho extends AdvancedRobot {

  static final int inicio = 1; 
  static final double life = 100;
  static final int velocidade = 5000;
  static final double angle = 90;

  int movimento = inicio;

  int direcao_arma = inicio;

  double energia_suposta = life;
  
  public void run() {

   setScanColor(new Color(50, 50, 255));
   setGunColor(new Color(47, 79, 79));
   setBodyColor(new Color(28, 28, 28));//vermelho 255 0 0
   setBulletColor(new Color(255, 0, 255));
   setRadarColor(new Color(255, 255, 0));
   setTurnGunRight(velocidade);
   
  }
  
  public void onScannedRobot(ScannedRobotEvent e) {

    setTurnRight(movimento_genesis(e)); 

  
    double energia_inimigo = e.getEnergy();
    double distancia_inimigo = e.getDistance();
    double muda_energia = energia_suposta - energia_inimigo;
    System.out.println("Velocidade do inimigo: "+e.getVelocity());
  

    if(e.getVelocity() == 0){
      muda_movimento(e, movimento);
      mira(distancia_inimigo);
    }
    else{
      if (muda_energia<=3 && muda_energia>0) {
          movimento = -movimento;
          muda_movimento(e, movimento);
      }

      direcao_arma = - direcao_arma;
      setTurnGunRight(velocidade * direcao_arma);
      mira(distancia_inimigo);
      energia_suposta = energia_inimigo;
      
    }
  }
  
  public double movimento_genesis(ScannedRobotEvent e){
    double pos_inimigo = e.getBearing() + angle;
    double mov_futuro = 30*movimento;
    return (pos_inimigo - mov_futuro);
  }

  public void muda_movimento(ScannedRobotEvent f, double m){
      double distancia = f.getDistance()/4;
      double dist_final = distancia + 25;
      setAhead(dist_final*m);
  }

  public void onHitRobot(HitRobotEvent e) {
       // setBack(velocidade);
   }
   
  public void onHitWall(HitWallEvent e) {
  //  turnLeft(180);
  }
  
  public void onHitByBullet(HitByBulletEvent e) {
  //ahead(100);
  }

  public void mira(double d){
    if(d > 450 || getEnergy() < 20) { 
       setFireBullet(0.5); 
      } else if (d > 250) { 
        setFireBullet(1); 
      } else {
        setFireBullet(1.5);   
      }
  }


}
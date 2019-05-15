import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
public class Gegner1 implements Gegner{
    private int speedX, speedY, xValue, yValue, length;
    private ArrayList<Beobachter> beobachter;
    private int key;
    private boolean shoot;
    private int xRichtung, yRichtung, shot;
    public Gegner1(int x, int y) {
        speedX=5;
        speedY=5;
        xValue=x;
        yValue=y;
        length=64;
        beobachter=new ArrayList<Beobachter>();
        key=0;
    }

    public void anmelden(Beobachter b){
        beobachter.add(b);
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }

    public int getX(){
        return xValue;
    }

    public int getY(){
        return yValue;
    }

    public int getYPosition(){
        return yValue;
    }
    
    //bestimmt in einem gewissen Rahmen zufällig die Bewegung des Gegners bzw. ob er schießen soll
    //wird von move() siehe unten aufgerufen
    //
    public void setPosition(){
        //bestimmt, ob Gegner nach links oder rechts fliegt
        xRichtung=ThreadLocalRandom.current().nextInt(0, 2);
        yRichtung=ThreadLocalRandom.current().nextInt(0, 2);
        speedX=ThreadLocalRandom.current().nextInt(3, 7);
        speedY=ThreadLocalRandom.current().nextInt(3, 7);
        //Variable shot=int
        shot=ThreadLocalRandom.current().nextInt(0, 8);

        if(xRichtung==0){
            //wenn xRichtung==0, wird xGeschwindigkeit negativ -> Gegner fliegt nach links
            speedX=-speedX;
            if(yRichtung==0){
                //analog bei y
                speedY=-speedY;
            }
        }else if(xRichtung==1){
            if(yRichtung==0){
                speedY=-speedY;
            }
        }
    }

    public void tryShoot(){
        shot=ThreadLocalRandom.current().nextInt(0, 20);
        /*wenn shot==0 wird geschossen -> oben bei der Generierung des Wertes für shot (siehe setPositon() Kommentare)
         wird Wahrscheinlichkeit des Schusses festgelegt ->(hier 1/8)
         */
        if(shot==0){
            shoot=true;
        }
    }
    
    public void setShoot(boolean b){
        shoot=b;
    }

    public int getKey(){
        return key;
    }

    public boolean getShoot(){
        return shoot;
    }

    public void alleInformieren() {
        for(Beobachter b: beobachter){
            b.geändert();
        }
    }
    //move() wird von Haupt-Timer in der Klasse Steuerung aufgerufen und setzt die Bewegung des Gegners neu, wenn er an den Spielfeldrand kommt
    public void move(){
        //der if-Zweig sorgt dafür, dass kein Gegner sich außerhalb des Spielfeldrandes bewegt
        if((xValue<32 && speedX<0)|| (xValue>950 && speedX>0) || (yValue<40 && speedY<0) || (yValue>850 && speedY>0)){
            setPosition();
        }else{
            xValue=xValue+speedX;
            yValue=yValue+speedY;
        }
    }

    public void stopMove(){
        speedX=0;
        speedY=0;
    }
}
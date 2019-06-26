import java.util.*;
import java.awt.*;
public class Gegner3 implements Gegner{
    
    private int speedX, speedY, posX, posY, length, bulletFreq, maxCount, mCounter, bCounter, state;
    private ArrayList<Beobachter> beobachter;
    private int key;
    private boolean shoot;
    private Random rand;
    private int screenHeight;
    public Gegner3(int x, int y, int bf, int mf, int l) {
        rand = new Random();
        shoot = false;
        posX = x;
        posY = y;
        length = l;
        screenHeight= (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        speedX = 0;
        speedY =(int) (screenHeight/204.8);
        if(mf != 0){maxCount = mf;}
        else{maxCount = rand.nextInt(150) + 50;}
        mCounter = maxCount;
        state = 1;
        if(bf != 0){bulletFreq = bf;}
        else{bulletFreq = rand.nextInt(285) + 16;}
        bCounter = bulletFreq;
        key = 2;
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
        return posX;
    }

    public int getY(){
        return posY;
    }
    
    public void setShoot(boolean b){
        shoot=b;
    }
    
    public boolean getShoot(){
        return shoot;
    }

    public void alleInformieren() {
        for(Beobachter b: beobachter){
            b.geändert();
        }
    }
    
    public int getKey(){
        return key;
    }
    
    public int getLength(){
        return length;
    }
    
    public void stopMove(){
        speedY = 0;
        speedX = 0;
    }
    
    public void move(){
        if(bCounter == 0){setShoot(true);
            bCounter = bulletFreq;}
        else{bCounter--;}
        if(mCounter == 0){
            mCounter = maxCount;
            if(state == 0){
                state = 1;
                speedX = 15;
                speedY = 0;
            }
            else if(state == 1){
                state = 2;
                speedX = 0;
                speedY = (int) (screenHeight/204.8);
            }
            else if(state == 2){
                state = 3;
                speedX = -15;
                speedY = 0;
            }
            else{
                state = 0;
                speedX = 0;
                speedY = (int) (screenHeight/204.8);
            }
        }
        else{
            posX = posX + speedX;
            if(posX < 0){posX = 1024 + posX;}
            if(posX > 1024){posX = posX - 1024;}
            posY = posY + speedY;
            if(posY > 1024){posY = -64;}
            mCounter--;
        }
    }
}
import java.util.*;

public class Gegner2 implements Gegner{
    
    private int speedX, speedY, posX, posY, lenght, bulletFreq, maxCount, mCounter, bCounter, state;
    private ArrayList<Beobachter> beobachter;
    private int key;
    private boolean shoot;
    private Random rand;
    
    public Gegner2(int x, int y, int bf, int mf) {
        rand = new Random();
        shoot = false;
        posX = x;
        posY = y;
        lenght = 64;
        speedX = 0;
        speedY = 5;
        if(mf != 0){maxCount = mf;}
        else{maxCount = rand.nextInt(150) + 50;}
        mCounter = maxCount;
        state = 1;
        if(bf != 0){bulletFreq = bf;}
        else{bulletFreq = rand.nextInt(285) + 16;}
        bCounter = bulletFreq;
        key = 1;
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
                speedX = 5;
                speedY = 0;
            }
            else if(state == 1){
                state = 2;
                speedX = 0;
                speedY = 5;
            }
            else if(state == 2){
                state = 3;
                speedX = -5;
                speedY = 0;
            }
            else{
                state = 0;
                speedX = 0;
                speedY = 5;
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
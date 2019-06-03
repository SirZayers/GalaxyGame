
import java.util.*;

public class  Rettungsschiff {
    
    private int speedX, speedY, posX, posY, length, bulletFreq, maxCount, mCounter, bCounter, state;
    private ArrayList<Beobachter> beobachter;
    private int key;
    private boolean shoot;
    private Random rand;
    
    public Rettungsschiff(int x, int y, int bf, int mf, int l) {
        rand = new Random();
        shoot = false;
        posX = x;
        posY = y;
        speedX = 0;
        speedY = 5;
        if(mf != 0){maxCount = mf;}
        else{maxCount = rand.nextInt(250) + 50;}
        mCounter = maxCount;
        state = 1;
        if(bf != 0){bulletFreq = bf;}
        else{bulletFreq = rand.nextInt(285) + 16;}
        bCounter = bulletFreq;
        key = 0;
        length=l;
    }
    
    public int getLength(){
        return length;
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
       
        if(mCounter == 0){
            state = 1;
                speedX = 1;
                speedY =2;
                  mCounter=250;
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
    public void setX(int x)
    {
        posX=x;
       
    }
     public void setY(int y)
    {
        posY=y;
       
    }
}
import java.util.*;
public class Spieler {
    private ArrayList<Beobachter> beobachter;
    private int x;
    private int y;
    private int length;
    private int speed;

    public Spieler(int schnell, int l) {
        beobachter=new ArrayList<Beobachter>();
        y=900;
        x=512;
        length=l;
        speed=schnell;

    }

    public void anmelden(Beobachter b) {
        beobachter.add(b);
    }

    public void goRight()
    {if(x<955){x=x+speed;}}

    public void goLeft()
    {if(x>5)
        {x=x-speed;}
    }

    public void alleInformieren() {
        for(Beobachter b: beobachter){
            b.geändert();
        }
    }

    public int getY()
    {
        return y;
    }

    public int getX()
    { return x;
    }

    public int getLength()
    {return length;}

    public void setX(int h)
    {x=h;}

    public int getSpeed()
    { return speed;}

    public void setSpeed(int s)
    {speed=s;}
    
    public void stopMove(){
        speed=0;
    }

}
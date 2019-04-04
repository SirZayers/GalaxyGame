import java.util.*;
public class Spieler {
	private ArrayList<Beobachter> beobachter;
	private int x;
	private int y;
	private int longe;
	private int speed;
	
	public Spieler(int schnell) {
		beobachter=new ArrayList<Beobachter>();
		y=50;
		x=500;
		longe=32;
		speed=schnell;
		
	}
	
	public void anmelden(Beobachter b) {
		beobachter.add(b);
	}
	public void goRight()
	{if(x<987){x=x+5;}}
	
	public void goLeft()
	{if(x<5)
		{x=x-5;}
	}
	
	public void alleInformieren() {
		for(Beobachter b: beobachter){
            b.geändert();
        }
	}
	public void yGive()
	{
		return y;
	}
	public void xGive()
	{ return x;
	}
	public void longeGive()
	{return longe;}
	public void setX(int h)
	{x=h;}
	public void speedGive()
	{ return speed;}
	public void setSpeed(int s)
	{speed=s;}
	
	
}

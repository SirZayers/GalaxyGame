import java.util.*;
public class Spieler {
	private ArrayList<Beobachter> beobachter;
	private int x;
	private int y;
	private int long;
	
	public Spieler() {
		beobachter=new ArrayList<Beobachter>();
		y=50;
		long=32;
		
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
	public void longGive()
	{return long;}
}

import java.util.*;
public class Spieler {
	private ArrayList<Beobachter> beobachter;
	private int x;
	private int y;
	
	public Spieler() {
		beobachter=new ArrayList<Beobachter>();
		y=50;
		
	}
	
	public void anmelden(Beobachter b) {
		beobachter.add(b);
	}
	
	public void alleInformieren() {
		for(Beobachter b: beobachter){
            b.geändert();
        }
	}
}

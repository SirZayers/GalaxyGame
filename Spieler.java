import java.util.*;
public class Spieler {
	private ArrayList<Beobachter> beobachter;
	
	public Spieler() {
		beobachter=new ArrayList<Beobachter>();
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

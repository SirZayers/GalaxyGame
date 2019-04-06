
public class Ansicht implements Beobachter {
	private Spieler spieler;
	private Gegner gegner;
	private Geschoss geschoss;
	
	public Ansicht(Spieler s, Gegner g, Geschoss ge) {
		spieler=s;
		gegner=g;
		geschoss=ge;
	}
	
	public void geändert() {
		
	}

}

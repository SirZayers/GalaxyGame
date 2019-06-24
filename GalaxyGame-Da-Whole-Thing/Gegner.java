public interface Gegner {
  //gibt Geschwindigkeit des Gegners zurück
  int getSpeedX();
  int getSpeedY();
  //gibt die xPosition des Gegners zurück
  int getX();
  //gibt die yPosition des Gegners zurück
  int getY();
  //meldet den Beobachter an
  void anmelden(Beobachter b);
  //legt die Position des Gegners fest
 
  //gibt den Identifizierungswert zurück (welche Art des Gegners dieser ist)
  int getKey();
  //gibt den boolean zurück, der bestimmt, ob der Gegner schießen soll
  boolean getShoot();
  //stoppt jegliche Bewegung des Gegners
  
  void setShoot(boolean b);
  
  void move();
  
  void stopMove();
  int getLength();
}
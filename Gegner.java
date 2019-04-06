
public interface Gegner {
  //legt die Geschwindigkeit des Gegners fest
  void setSpeed(int speed);
  //gibt Geschwindigkeit des Gegners zurück
  int getSpeed();
  //legt die Geschossrate des Gegners fest
  void setBulletFrequency(int frequency);
  //gibt Geschossrate des Gegners zurück
  int getBulletFrequency();
  //legt die Position des Gegners fest
  void setPosition(int x, int y);
  //gibt die xPosition des Gegners zurück
  int getXPosition();
  //gibt die yPosition des Gegners zurück
  void getYPosition();
}

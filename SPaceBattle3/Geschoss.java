public interface Geschoss{
    int getX();
    int getY();
    //gibt den Identifizierungsschlüssel zurück
    int getKey();
    //informiert die Beobachter
    void alleInformieren();
    //meldet den Beobachter an
    void anmelden(Beobachter b);
    //bewegt das Geschoss
    void move();
    void setX(int x);
    void setY(int y);
}
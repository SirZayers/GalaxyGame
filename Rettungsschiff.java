
import java.util.*;
// Die Klasse Rettungsschiff hat sämtlich attribute und Methoden der Kleinen Rettungskapsel in sich gespeichert.

public class  Rettungsschiff{     
    private int speedX, speedY, posX, posY, length, bulletFreq, maxCount, mCounter, bCounter, state;
    private ArrayList<Beobachter> beobachter;
    private int key;
    private boolean shoot;
    
    
    public Rettungsschiff(int x, int y, int bf, int mf, int l) {
        
        shoot = false; // gibt an ob die Kapsel getroffen wurde
        posX = x; // gibt die Position in X Richtung an
        posY = y;   // gibt die Position in Y Richtung an
        speedX = 0;  //gibt die Anfangsgeschwindigkeit in X Richtung an an
        speedY = 3;  //gibt die Anfangsgeschwindigkeit in Y Richtung an an
      
        key = 0;  // gibt die art des Objektes an
        length=l;  // gibt die Objekt Große an
    }
    
    public int getLength(){ // durch diese Methode lässt sich die größe der Kapsel für ander Klassen feststellen.
        return length;
    }
    
    public void anmelden(Beobachter b){   // durch diese Methode können sich Beobachter Selbst der Beobachter liste hinzufügen, um so
        beobachter.add(b);                 // später durch die Methode alleInformieren über änderungen informiert zu werden.
    }

    public int getSpeedX(){ // durch diese Methode können andere Klassen den Wert der bewegungsveränderung in X-Richtung erhalten, beispielsweise um die Kapsel richtig zu zeichnen
        return speedX;
    }

    public int getSpeedY(){  //// durch diese Methode können andere Klassen den Wert der bewegungsveränderung in Y-Richtung erhalten, beispielsweise um die Kapsel richtig zu zeichnen
        
        return speedY;
    }

    public int getX(){// durch diese Methode können andere Klassen den Wert der XPosition erhalten, beispielsweise um die Kapsel richtig zu zeichnen
        return posX;
    }

    public int getY(){  // durch diese Methode können andere Klassen den Wert der YPosition erhalten,  beispielsweise um die Kapsel richtig zu zeichnen
        return posY;
    }
    
   
    public void alleInformieren() {  // durch diese methode werden alle in der Beobachter liste gespeicherten Beobachter darüber informiert dass sich etwas getan hatt.
        for(Beobachter b: beobachter){
            b.geändert();
        }
    }
    
    public int getKey(){ // durch diese Methode bekommen andere Klassen den key, welcher aussagt um welche Art von Raumschiff es sich handelt.
        return key;
    }
    
    public void stopMove(){// diese Metode sorgt dafür dass wenn die Kapsel Abgesschossen wurde sie aufhört sich zu bewegen
        speedY = 0;
        speedX = 0;
        state=4;
    }
    
    public void move(){ //die methode gibt an auf welche weise sich die Kapsel durch das Bild bewegt.
       
        if(mCounter == 0){  //der Move Counter ist dafür da , dass  sich die Bewegungsrichtung nicht bei jedem fram ändert,  
                            // sonder nur nach der im Move Counter Anggegebenen Anzahl von Frames ( hier 80)
               
                  mCounter=80;
                  if(state == 0){  // Um das Spiel nicht zu einfach zu machen ändert die Kapsel immerwieder ihre Richtung.
                                    // dazu wird die Geschwindigkeit in einer Bestimmten Reihenfolge immer geändert.
                state = 1;      // der state sorgt dafür das der Speed die immer in der gleichen Reihenfolge geändert wird.
                speedX = 2;     // hier wird der speed in X-Richtung auf 2 gesetzt und in Y Richtung auf 2.
                speedY = 2;     // ausserdem wird der state auf 1 gesetzt, so dass die Geschwindigkeit nach ablaufen des Move Counters sich auf state 2 ändert
            }
            else if(state == 1){
                state = 2;
                speedX = 0;
                speedY = 1;
            }
            else if(state == 2){
                state = 3;
                speedX = 2;
                speedY = 0;
            }
            else if (state==3){
                state = 0;
                speedX = 0;
                speedY = 1;
            }
            else{
                state=0; // sorgt für ein kurzes Anhalten der Kapsel
                
            }
        }
        else{
            posX = posX + speedX;                   //hier wir dafür gesorgt, dass sich die Kapsel bewegt so wid die geschwindigkeit zur Position addiert.
            if(posX < 0){posX = 1024 + posX;}
            if(posX > 1024){posX = posX - 1024;}        // sobald dass Schiff das Bild verlässt wird es hier auf die andere Bildseite in den Rand gesetzt um von dort zu Starten
            posY = posY + speedY;
            if(posY > 1024){posY = -64;}
            mCounter--;                     //Nach jedem ändern der position wird der Move Counter um 1 verringert
          
        }
    }
    public void setX(int x) // ermöglicht es Anderen Klassen die Position der Rettungskapsel in X Richtung zu Verändern 
    {
        posX=x;
       
    }
     public void setY(int y) // ermöglicht es Anderen Klassen die Position der Rettungskapsel in Y Richtung zu Verändern 
    {
        posY=y;
       
    }
}
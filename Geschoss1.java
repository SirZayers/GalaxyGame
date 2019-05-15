import java.util.ArrayList;
public class Geschoss1 implements Geschoss{
    private int PosX;
    private int PosY;
    private int SizeX;
    private int SizeY;
    private int Speed;
    private int key;
    private ArrayList<Beobachter> beobachter;
    public Geschoss1(int x, int y, int s){
        PosX = x;
        PosY = y;
        SizeX = 4;
        SizeY = 12;
        Speed = s;
        key=0;
        beobachter=new ArrayList<Beobachter>();
    }

    public int getX(){
        return PosX;
    }

    public int getY(){
        return PosY;
    }
    
    public void setX(int x){
        PosX=x;
    }
    
    public void setY(int y){
        PosY=y;
    }

    public int getSizeX(){
        return SizeX;
    }

    public int getSizeY(){
        return SizeY;
    }

    public int getSpeed(){
        return Speed;
    }

    public void setSpeed(int s){
        Speed = s;
    }

    public void move(){ //Bewegt das Geschoss in Y Richtung
        PosY = PosY - Speed;
    }
    
    public int getKey(){
        return key;
    }
    
    public void anmelden(Beobachter b){
        beobachter.add(b);
    }
    
    public void alleInformieren(){
        for(Beobachter b: beobachter){
            b.geändert();
        }
    }
}
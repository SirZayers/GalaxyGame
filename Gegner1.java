public class Gegner1 implements Gegner{
    private int speed, bulletFrequency, xValue, yValue, length;
    public Gegner1(int s, int b, int x, int y, int l) {
        speed=s;
        bulletFrequency=b;
        xValue=x;
        yValue=y;
        length=l;
    }
    
    public void setSpeed(int s){
       speed=s;
    }
    
    public int getSpeed(){
        return speed;
    }
    
    public void setBulletFrequency(int b){
        bulletFrequency=b;
    }
    
    public int getBulletFrequency(){
        return bulletFrequency;
    }
    
    
}

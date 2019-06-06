
import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
public class Ansicht extends javax.swing.JComponent implements Beobachter{
    private Spieler spieler;
    private ArrayList<Gegner> gegner;
    private ArrayList<Geschoss> geschoss;
    private ArrayList<Image> gegnerImages, geschossImages;
    private boolean hit, spielerHit,beginn;
    private int xHit, yHit, counter;
    private int kills, health;
    private int backgroundMoving;
    private int blackBackground;
     private Rettungsschiff rettungsschiff;
     private int timeSOS,start;
     private int screenHeight, screenWidth;
    
   
    public Ansicht(Spieler q, ArrayList<Gegner> g, ArrayList<Geschoss> ges,  Rettungsschiff r){
          rettungsschiff=r;
        gegnerImages=new ArrayList<Image>();
        geschossImages=new ArrayList<Image>();
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        spieler=q;
        gegner=g;
        geschoss=ges;
        spieler.anmelden(this);
        for(Gegner ge: gegner){
            ge.anmelden(this);
        }
        for(Geschoss gesch: geschoss){
            gesch.anmelden(this);
        }
        setFocusable(true);
        xHit=0;
        yHit=0;
        kills=0;
        health=3;
        beginn=true;
       start=1;
    }

    public void paint(Graphics g){
         Toolkit zeugs=getToolkit();
if(beginn==false){
       
        Image backgroundIMG=zeugs.getImage("background.png");
        Image backgroundIMG2=zeugs.getImage("background2.png");
        if(backgroundMoving<1024){
            
            g.drawImage(backgroundIMG, 0, backgroundMoving++, this);
            g.drawImage(backgroundIMG2, 0, backgroundMoving-1024, this);
        }else{
            backgroundMoving=0;
            g.drawImage(backgroundIMG, 0, backgroundMoving++, this);
            g.drawImage(backgroundIMG2, 0, backgroundMoving-1024, this);
        }
        for(int i=0;i<gegner.size();i++){
            if(gegner.get(i).getKey()==1){
                gegnerImages.add(zeugs.getImage("gegner.png"));
            }
        }

        Image spielerIMG=zeugs.getImage("spieler.png");
        g.drawImage(spielerIMG,spieler.getX(), spieler.getY(), 64, 64, this);
        for(int i=0;i<gegner.size();i++){
            g.drawImage(gegnerImages.get(i),gegner.get(i).getX(), gegner.get(i).getY(), 64, 64, this);
        }
  g.fillRect(1024, 0, 256, 1024);
        try{
            Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("JURASSIC.TTF"));
            font=font.deriveFont(16F);
            g.setFont(font);
        }catch(Exception e){
            e.printStackTrace();
        }
        g.setColor(Color.RED);
        g.drawString("Kills", 1030, 50);
        g.drawString(""+kills, 1030, 70);
        if(hit){
            if(spielerHit){
                Image explosion1=zeugs.getImage("explosion1.png");
                Image explosion2=zeugs.getImage("explosion2.png");
                Image explosion3=zeugs.getImage("explosion3.png");
                if(counter==1){
                    g.drawImage(explosion1, xHit, yHit, 64, 64, this);
                }else if(counter==2){
                    explosion1.flush();
                    g.drawImage(explosion2, xHit, yHit, 64, 64, this);
                }else if(counter==3){
                    explosion2.flush();
                    g.drawImage(explosion3, xHit, yHit, 64, 64, this);
                }
            }
            Image explosion1=zeugs.getImage("explosion1.png");
            Image explosion2=zeugs.getImage("explosion2.png");
            Image explosion3=zeugs.getImage("explosion3.png");
            if(counter==1){
                g.drawImage(explosion1, xHit, yHit, 64, 64, this);
            }else if(counter==2){
                explosion1.flush();
                g.drawImage(explosion2, xHit, yHit, 64, 64, this);
            }else if(counter==3){
                explosion2.flush();
                g.drawImage(explosion3, xHit, yHit, 64, 64, this);
            }
        }
        Image rettungsSchiff=zeugs.getImage("Sos-Schiff.png");
        Image rettungsSchiff2=zeugs.getImage("Sos-Schiff2.png");
        if(timeSOS==0){
            g.drawImage(rettungsSchiff, rettungsschiff.getX(), rettungsschiff.getY(), 64, 64, this);
            timeSOS=1;
        }else{
            g.drawImage(rettungsSchiff2, rettungsschiff.getX(),rettungsschiff.getY(), 64, 64, this);
            timeSOS=0;
        }
        for(int i=0;i<geschoss.size();i++){
            if(geschoss.get(i).getKey()==0){
                geschossImages.add(i, zeugs.getImage("Blaues_Geschoss.png"));
            }else if(geschoss.get(i).getKey()==1){
                geschossImages.add(i, zeugs.getImage("Rotes_Geschoss.png"));
            }
        }
        for(int i=0;i<geschoss.size();i++){
            g.drawImage(geschossImages.get(i),geschoss.get(i).getX(), geschoss.get(i).getY(), 4, 12, this);
        }
        g.setColor(Color.GRAY);
        g.fillRect(1024, 0, 256, 1024);
        g.setColor(Color.RED);
        Image planet=zeugs.getImage("planet.png");
        g.drawImage(planet, 1115, 835, 100, 100, this);
        Image rakete=zeugs.getImage("rakete2.png");
        g.drawImage(rakete, 995, 700, 280, 280, this);
        try{
            Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("STARVADER.TTF"));
            font=font.deriveFont(16F);
            g.setFont(font);
        }catch(Exception e){
            e.printStackTrace();
        }
        g.drawString("LIFES", 1135, 870);
        Image herz1=zeugs.getImage("herz.png");
        Image herz2=zeugs.getImage("herz.png");
        Image herz3=zeugs.getImage("herz.png");
        if(blackBackground==1){
            Image blackBG=zeugs.getImage("verdunklung1.png");
            g.drawImage(blackBG, 0, 0, this);
        }else if(blackBackground==2){
            Image blackBG=zeugs.getImage("verdunklung1.png");
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
        }else if(blackBackground==3){
            Image blackBG=zeugs.getImage("verdunklung1.png");
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
        }else if(blackBackground==4){
            Image blackBG=zeugs.getImage("verdunklung1.png");
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
        }else if(blackBackground==5){
            Image blackBG=zeugs.getImage("verdunklung1.png");
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
        }else if(blackBackground==6){
            Image blackBG=zeugs.getImage("verdunklung1.png");
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
        }else if(blackBackground==7){
            Image blackBG=zeugs.getImage("verdunklung1.png");
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
        }else if(blackBackground>=8){
            Image blackBG=zeugs.getImage("verdunklung1.png");
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
            g.drawImage(blackBG, 0, 0, this);
        }
        if(health==3){
            g.drawImage(herz1, 1130, 880, this);
            g.drawImage(herz2, 1155, 880, this);
            g.drawImage(herz3, 1180, 880, this);
        }else if(health==2){
            g.drawImage(herz1, 1130, 880, this);
            g.drawImage(herz2, 1155, 880, this);
        }else if(health==1){
            g.drawImage(herz1, 1130, 880, this);
        }else{
            try{
                Font bigFont=Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("JURASSIC.TTF"));
                bigFont=bigFont.deriveFont(100F);
                g.setFont(bigFont);
            }catch(Exception e){
                e.printStackTrace();
            }
            g.drawString("GAME OVER", 150, 400);
        }
    }
    else
    {
       Image starte=zeugs.getImage("start.png");
        g.drawImage(starte,0,0,this);
        Image play=zeugs.getImage("spiela.png");
       
        g.drawImage (play,200*start,800,this);
  
        }
    }

    public void setHit(boolean b, int x, int y, int c, boolean s){
        hit=b;
        xHit=x;
        yHit=y;
        counter=c;
        spielerHit=s;
    }

    public void geändert(){
        repaint();
    }

    public void increaseKills(){
        kills++;
    }

    public void decreaseHealth(){
        health--;
    }
    
    public void increaseHealth(){
      if(health<3){  health++;}
    }
    public void setBlackBackground(int b){
        blackBackground=b;
    }
    public void setBeginn(boolean h){
    beginn=h;}
    
    public void setStart(int h)
    {start=h;}
   
    public void setNewGame(){
        health=3;
        blackBackground=0;
        kills=0;
        spieler.setX(512);
    }
}
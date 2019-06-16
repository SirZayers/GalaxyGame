import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class Ansicht extends javax.swing.JComponent implements Beobachter{
    private Spieler spieler;
    private ArrayList<Gegner> gegner;
    private ArrayList<Geschoss> geschoss;
    private ArrayList<Image> gegnerImages, geschossImages;
    private boolean hit, spielerHit;
    private int xHit, yHit, counter;
    private int kills, health;
    private int backgroundMoving;
    private int blackBackground;
    private Rettungsschiff rettungsschiff;
    private int timeSOS,start,bg , score;
    private int screenHeight, screenWidth;
    public Ansicht(Spieler q, ArrayList<Gegner> g, ArrayList<Geschoss> ges,  Rettungsschiff r,int s){
        rettungsschiff=r;
        gegnerImages=new ArrayList<Image>();
        geschossImages=new ArrayList<Image>();
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight=(int)screenSize.getHeight();
         screenHeight= (int) (screenHeight*0.92);
        spieler=q;
        gegner=g;
        start=s;
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
    }
private static String ladeDatei(String datName) {
        String l=null;
        File file = new File(datName);

        if (!file.canRead() || !file.isFile())
            System.exit(0);

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(datName));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
                l=zeile;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
        return l;
    }

    public  String reade() {
        String dateiName = "test.txt";
        String h=ladeDatei(dateiName);
        return h;
    }


    public void paint(Graphics g){

        Toolkit zeugs=getToolkit();
        if (start==1)
        {
            Image backgroundIMG=zeugs.getImage("background.png");
            Image backgroundIMG2=zeugs.getImage("background2.png");
            String s;
            s="Score:"+score;
            
            if(backgroundMoving<screenHeight){

                g.drawImage(backgroundIMG, 0, backgroundMoving++, screenHeight, screenHeight, this);
                g.drawImage(backgroundIMG2, 0, backgroundMoving-screenHeight, screenHeight, screenHeight, this);
            }else{
                backgroundMoving=0;
                g.drawImage(backgroundIMG, 0, backgroundMoving++, this);
                g.drawImage(backgroundIMG2, 0, backgroundMoving-screenHeight, this);
            }
            for(int i=0;i<gegner.size();i++){
                if(gegner.get(i).getKey()==1){
                    gegnerImages.add(zeugs.getImage("gegner.png"));
                }
            }
            
            Image spielerIMG=zeugs.getImage("spieler.png");
            g.drawImage(spielerIMG,spieler.getX(), spieler.getY(),(int) (screenHeight*0.0625),(int) (screenHeight*0.0625), this);
            for(int i=0;i<gegner.size();i++){
                g.drawImage(gegnerImages.get(i),gegner.get(i).getX(), gegner.get(i).getY(), screenHeight/(screenHeight/gegner.get(i).getLength()), screenHeight/(screenHeight/gegner.get(i).getLength()), this);
            }
            try{
                Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("JURASSIC.TTF"));
                font=font.deriveFont(16F);
                g.setFont(font);
            }catch(Exception e){
                e.printStackTrace();
            }
            g.setColor(Color.RED);
            if(hit){
                if(spielerHit){
                    Image explosion1=zeugs.getImage("explosion1.png");
                    Image explosion2=zeugs.getImage("explosion2.png");
                    Image explosion3=zeugs.getImage("explosion3.png");
                    if(counter==1){
                        g.drawImage(explosion1, xHit, yHit,(int) (screenHeight*0.0625),(int) (screenHeight*0.0625), this);
                    }else if(counter==2){
                        explosion1.flush();
                        g.drawImage(explosion2, xHit, yHit, (int) (screenHeight*0.0625), (int) (screenHeight*0.0625), this);
                    }else if(counter==3){
                        explosion2.flush();
                        g.drawImage(explosion3, xHit, yHit, (int) (screenHeight*0.0625), (int) (screenHeight*0.0625), this);
                    }
                }
                Image explosion1=zeugs.getImage("explosion1.png");
                Image explosion2=zeugs.getImage("explosion2.png");
                Image explosion3=zeugs.getImage("explosion3.png");
                if(counter==1){
                    g.drawImage(explosion1, xHit, yHit, (int) (screenHeight*0.0625), (int) (screenHeight*0.0625), this);
                }else if(counter==2){
                    explosion1.flush();
                    g.drawImage(explosion2, xHit, yHit, (int) (screenHeight*0.0625), (int) (screenHeight*0.0625), this);
                }else if(counter==3){
                    explosion2.flush();
                    g.drawImage(explosion3, xHit, yHit, (int) (screenHeight*0.0625), (int) (screenHeight*0.0625), this);
                }
            }
            Image rettungsSchiff=zeugs.getImage("Sos-Schiff.png");
            Image rettungsSchiff2=zeugs.getImage("Sos-Schiff2.png");
            if(timeSOS==0){
                g.drawImage(rettungsSchiff, rettungsschiff.getX(), rettungsschiff.getY(), (int) (screenHeight*0.0625), (int) (screenHeight*0.0625), this);
                timeSOS=1;
            }else{
                g.drawImage(rettungsSchiff2, rettungsschiff.getX(),rettungsschiff.getY(), (int) (screenHeight*0.0625), (int) (screenHeight*0.0625), this);
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
                g.drawImage(geschossImages.get(i),geschoss.get(i).getX(), geschoss.get(i).getY(), screenHeight/256, screenHeight/85, this);
            }
            g.setColor(Color.GRAY);
            g.fillRect(screenHeight, 0,(int) (screenHeight*0.25), screenHeight);
            g.setColor(Color.RED);
            Image planet=zeugs.getImage("planet.png");
            g.drawImage(planet,(int) (screenHeight/0.91838565),(int) (screenHeight/1.226347),(int) (screenHeight/10.24),(int) (screenHeight/10.24), this);
            Image rakete=zeugs.getImage("rakete2.png");
            g.drawImage(rakete,(int) (screenHeight/1.029146),(int) (screenHeight/1.462857),(int) (screenHeight/3.6571428), (int) (screenHeight/3.6571428), this);
            g.setColor(Color.RED);
            g.drawString(s,(int) (screenHeight*1.02), (int) (screenHeight*0.07));
            g.setColor(Color.RED);
            String t="Highscore:"+reade();
            g.drawString(t,(int) (screenHeight*1.02), (int) (screenHeight*0.17));
            try{
                Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("STARVADER.TTF"));
                font=font.deriveFont(16F);
                g.setFont(font);
            }catch(Exception e){
                e.printStackTrace();
            }
            g.drawString("LIFES",(int) (screenHeight/0.9022026432),(int) (screenHeight/1.177011464));
            Image herz1=zeugs.getImage("herz.png");
            Image herz2=zeugs.getImage("herz.png");
            Image herz3=zeugs.getImage("herz.png");
            if(blackBackground==1){
                Image blackBG=zeugs.getImage("verdunklung1.png");
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
            }else if(blackBackground==2){
                Image blackBG=zeugs.getImage("verdunklung1.png");
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
            }else if(blackBackground==3){
                Image blackBG=zeugs.getImage("verdunklung1.png");
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
            }else if(blackBackground==4){
                Image blackBG=zeugs.getImage("verdunklung1.png");
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
            }else if(blackBackground==5){
                Image blackBG=zeugs.getImage("verdunklung1.png");
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
            }else if(blackBackground==6){
                Image blackBG=zeugs.getImage("verdunklung1.png");
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
            }else if(blackBackground==7){
                Image blackBG=zeugs.getImage("verdunklung1.png");
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
            }else if(blackBackground>=8){
                Image blackBG=zeugs.getImage("verdunklung1.png");
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
                g.drawImage(blackBG, 0, 0, screenHeight, screenHeight, this);
            }
            if(health==3){
                g.drawImage(herz1, (int) (screenHeight/0.902203), (int) (screenHeight/1.163163), this);
                g.drawImage(herz2, (int) (screenHeight/0.886580), (int) (screenHeight/1.163163), this);
                g.drawImage(herz3, (int) (screenHeight/0.867797), (int) (screenHeight/1.163163), this);
            }else if(health==2){
                g.drawImage(herz1, (int) (screenHeight/0.902203), (int) (screenHeight/1.163163), this);
                g.drawImage(herz2, (int) (screenHeight/0.886580), (int) (screenHeight/1.163163), this);
            }else if(health==1){
                g.drawImage(herz1, (int) (screenHeight/0.902203), (int) (screenHeight/1.163163), this);
            }else{
                try{
                    Font bigFont=Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("JURASSIC.TTF"));
                    bigFont=bigFont.deriveFont(100F);
                    g.setFont(bigFont);
                }catch(Exception e){
                    e.printStackTrace();
                }
                g.drawString("GAME OVER",(int) (screenHeight*0.1), (int) (screenHeight*0.4));
            }
        }
        else if(start==0)
        {Image sat=zeugs.getImage("start.png");
            g.drawImage(sat, 0, 0,(int) (screenHeight*1.25), screenHeight, this);
            Image sap=zeugs.getImage("spieler.png");
            if (bg==0)
            {g.drawImage(sap, (int) (screenHeight*0.2), (int) (screenHeight*0.7), (int) (screenHeight*0.1),(int) (screenHeight*0.1), this);}
            else if(bg==1)
            {g.drawImage(sap, (int) (screenHeight*0.52),(int) (screenHeight*0.7), (int) (screenHeight*0.1),(int) (screenHeight*0.1), this);}
            else{g.drawImage(sap, (int) (screenHeight*0.9), (int) (screenHeight*0.7), (int) (screenHeight*0.1),(int) (screenHeight*0.1), this);}

        }
        else{
        Image sat=zeugs.getImage("score.png");
            g.drawImage(sat, 0, 0,(int) (screenHeight*1.25), screenHeight, this);
           try{
                    Font bigFont=Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("JURASSIC.TTF"));
                    bigFont=bigFont.deriveFont(100F);
                    g.setFont(bigFont);
                }catch(Exception e){
                    e.printStackTrace();
                }
           g.setColor(Color.RED);
            g.drawString(reade(),(int) (screenHeight*0.5), (int) (screenHeight*0.57));
        }

    }

    public void setHit(boolean b, int x, int y, int c, boolean s){
        hit=b;
        xHit=x;
        yHit=y;
        counter=c;
        spielerHit=s;
    }

    public void setbg(int h){
        bg=h;
    }
    
    public void setStart(int s){
        start=s;
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
    public void setScore(int i)
    {score=i;}
    public void setBlackBackground(int b){
        blackBackground=b;
    }

    public void setNewGame(){
        health=3;
        blackBackground=0;
        kills=0;
        spieler.setX(512);
    }
}
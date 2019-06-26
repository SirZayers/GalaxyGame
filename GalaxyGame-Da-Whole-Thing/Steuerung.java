import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter; 
public class Steuerung implements KeyListener{
    private ArrayList<Gegner> gegner;
    private ArrayList<Geschoss> geschoss;
    private Spieler spieler;
    private JFrame fenster;
    private Ansicht ansicht;
    private Timer timer;

    private boolean leftPressed, rightPressed, hit;
    private int hitsSpieler;
    private int periodeGegnerSpawn, currentTime;
    private Button newGame;
    private Action actionlistener;
    private final int timeStart;
    private int gegnerNumber, ShouldBossSpawn ;
    private int waveCounter;
    private boolean shouldGegnerSpawn;
    private int maxGegner, start, bg,score;
    private Rettungsschiff sos;
    private int screenHeight, screenWidth;
    public Steuerung(){

        gegner=new ArrayList<Gegner>();
        geschoss=new ArrayList<Geschoss>();
        Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
        screenHeight=(int)screenSize.getHeight();
        screenHeight= (int) (screenHeight*0.92);
        screenWidth=(int)screenSize.getWidth();
        sos=new Rettungsschiff(0,0,30,45,(int) (screenHeight*0.0625));
        spieler=new Spieler(5, (int) (screenHeight*0.0625),screenHeight);
        start=0;
        ansicht=new Ansicht(spieler, gegner, geschoss, sos,start);
        fenster=new JFrame();
        Image img=Toolkit.getDefaultToolkit().createImage("icon.png");
        fenster.setIconImage(img);
        fenster=new JFrame();
        Toolkit tool=ansicht.getToolkit();
        fenster.setSize((int)(screenHeight*1.25), screenHeight);
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.setVisible(true);
        fenster.add(ansicht);
        newGame=new Button("Neues Spiel");
        newGame.setBounds(412, 462, 200, 100);
        actionlistener=new Action();
        newGame.addActionListener(actionlistener);
        timeStart=(int) System.currentTimeMillis();
        maxGegner = 10;
        shouldGegnerSpawn = true;
        waveCounter = 1;
        ShouldBossSpawn = 10;
        try{
            Font font=Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("starvader.ttf"));
            font=font.deriveFont(30F);
            newGame.setFont(font);
            newGame.setForeground(Color.WHITE);
            newGame.setBackground(Color.RED);
        }catch(Exception e){
            e.printStackTrace();
        }

        fenster.addKeyListener(this);
        fenster.requestFocus();
        ansicht.repaint();
        timer=new Timer();
        timer.schedule(new TimerTask(){
                public void run(){
                    if (start==1){
                        for(Gegner ge: gegner){
                            ge.move();
                            if(ge.getShoot()){
                                shoot(ge);
                            }

                        }
                        for(int i=0;i<geschoss.size();i++){
                            geschoss.get(i).move();
                        }
                        sos.move();
                        if(leftPressed){
                            spieler.goLeft();
                        }else if(rightPressed){
                            spieler.goRight();
                        }
                        detectHitbox();
                        ansicht.repaint();
                    }
                }
            },0, 30);
    }

    public static void main(String[] args){
        Steuerung main=new Steuerung();
    }

    public void addGegner(Gegner g){
        gegner.add(g);
    }

    public void storage (String z)
    { PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter("test.txt")));
            pWriter.println(z);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (pWriter != null){
                pWriter.flush();
                pWriter.close();
            } 
        }
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

    public void keyReleased(KeyEvent e){
        if(start==1){
            if(e.getKeyCode()==KeyEvent.VK_SPACE){
                geschoss.add(new Geschoss(spieler.getX()+(int)(screenHeight/34.1333333), spieler.getY(),(int) (screenHeight/204.8), 0));
            }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                rightPressed=false;
            }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
                leftPressed=false;
            }
            else if(e.getKeyCode()==KeyEvent.VK_ENTER){
                start=0;
                ansicht.setStart(0);
                score=0;
                ansicht.setScore(0);
            }

        }else if (start==0){
            if(e.getKeyCode()==KeyEvent.VK_LEFT){
                if(bg>0)
                {bg--;};
            }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                if(bg<2)
                {bg++;};
            }else if(e.getKeyCode()==KeyEvent.VK_ENTER){
                if(bg==1){
                    start=1;
                    ansicht.setStart(1);
                    setGegnerSpawn();
                }
                else if(bg==0)
                {ansicht.setStart(2);
                    start=2;
                }
                else if(bg==2)
                {ansicht.setStart(3);
                    start=3;
                }
            }
            ansicht.setbg(bg);
            ansicht.repaint();
        }else{
            if(e.getKeyCode()==KeyEvent.VK_ENTER){
                ansicht.setStart(0);}
            start=0;

            ansicht.repaint();
        }
    }

    public void keyPressed(KeyEvent e){
        if(start==1){
            if(e.getKeyCode()==KeyEvent.VK_RIGHT){
                rightPressed=true;
            }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
                leftPressed=true;
            }
        }
    }

    public void keyTyped(KeyEvent e){

    }

    public void detectHitbox(){

        for(int i=0;i<geschoss.size();i++){
            if(geschoss.get(i).getKey()==0){
                for(int z=0;z<gegner.size();z++){
                    if(geschoss.get(i).getX()>=sos.getX() &&geschoss.get(i).getX()<=sos.getX()+sos.getLength() && geschoss.get(i).getY()<=sos.getY()+sos.getLength() && geschoss.get(i).getY()>=sos.getY())
                    {
                        sos.stopMove();
                        if(hitsSpieler>0){
                            hitsSpieler--;
                        }
                        score += 50;
                        
                        ansicht.setScore(score);
                        int xk=sos.getX();
                        int yk=sos.getY();
                        Timer t1=new Timer();
                        ansicht.setHit(true, sos.getX(), sos.getY(), 1, false);
                        t1.schedule(new TimerTask(){
                                public void run(){
                                    ansicht.setHit(true, xk, yk, 2, false);
                                }
                            }, 70);
                        playSound("explosionSound.wav");
                        Timer t2=new Timer();
                        t2.schedule(new TimerTask(){
                                public void run(){
                                    ansicht.setHit(true, xk, yk, 3, false);
                                } 
                            }, 140);
                        Timer t3=new Timer();
                        t3.schedule(new TimerTask(){
                                public void run(){
                                    ansicht.setHit(false, -100, -100, 0, false);
                                }
                            }, 210);
                        t1.purge();
                        t2.purge();
                        t3.purge();
                        sos.setX(-200);
                        sos.setY(-200);
                        geschoss.get(i).setX(-50);
                        geschoss.get(i).setY(-50);
                        ansicht.increaseHealth();

                    }
                    if(geschoss.get(i).getX()>=gegner.get(z).getX() &&geschoss.get(i).getX()<=gegner.get(z).getX()+gegner.get(z).getLength() && geschoss.get(i).getY()<=gegner.get(z).getY()+gegner.get(z).getLength() && geschoss.get(i).getY()>=gegner.get(z).getY()){

                        int xk=gegner.get(z).getX();
                        int yk=gegner.get(z).getY();
                        gegner.get(z).stopMove();
                        score += 20;
                        ansicht.setScore(score);
                        Timer t1=new Timer();
                        ansicht.setHit(true, gegner.get(z).getX(), gegner.get(z).getY(), 1, false);
                        t1.schedule(new TimerTask(){
                                public void run(){
                                    ansicht.setHit(true, xk, yk, 2, false);
                                }
                            }, 70);
                        playSound("explosionSound.wav");
                        Timer t2=new Timer();
                        t2.schedule(new TimerTask(){
                                public void run(){
                                    ansicht.setHit(true, xk, yk, 3, false);
                                } 
                            }, 140);
                        Timer t3=new Timer();
                        t3.schedule(new TimerTask(){
                                public void run(){
                                    ansicht.setHit(false, -100, -100, 0, false);
                                }
                            }, 210);
                        t1.purge();
                        t2.purge();
                        t3.purge();
                        geschoss.get(i).setX(-50);
                        geschoss.get(i).setY(-50);
                        for(int k=z;k<gegner.size()-1;k++){
                            gegner.set(k, gegner.get(k+1));
                        }
                        gegner.remove(gegner.size()-1);
                        z--;
                        ansicht.increaseKills();
                    }
                }
            }else if(geschoss.get(i).getKey()>0){

                if(geschoss.get(i).getX()>=spieler.getX() && geschoss.get(i).getX()<=spieler.getX()+spieler.getLength() && geschoss.get(i).getY()>=spieler.getY() && geschoss.get(i).getY()<=spieler.getY()+spieler.getLength()){
                    if(geschoss.get(i).getKey()!=0){
                        if(hitsSpieler<2){
                            hitsSpieler++;
                            ansicht.decreaseHealth();

                            geschoss.get(i).setX(-50);
                            geschoss.get(i).setY(-50);
                        }else{
                            int xk=spieler.getX();
                            int yk=spieler.getY();
                            Timer t1=new Timer();
                            ansicht.decreaseHealth();
                            spieler.stopMove();
                            

                            ansicht.setHit(true, xk, yk, 1, true);
                            t1.schedule(new TimerTask(){
                                    public void run(){
                                        ansicht.setHit(true, xk, yk, 2, true);
                                    }
                                }, 70);

                            spieler.setX(-200);
                            Timer t2=new Timer();
                            t2.schedule(new TimerTask(){
                                    public void run(){
                                        ansicht.setHit(true, xk, yk, 3, true);
                                    }
                                }, 140);
                            Timer t3=new Timer();
                            t3.schedule(new TimerTask(){
                                    public void run(){
                                        ansicht.setHit(false, -100, -100, 0, false);
                                    }
                                }, 210);
                            t1.purge();
                            t2.purge();
                            t3.purge();
                            Timer tb=new Timer();
                            tb.schedule(new TimerTask(){
                                    private int i;
                                    public void run(){
                                        ansicht.setBlackBackground(i++);
                                        if(i==7){
                                            tb.cancel();
                                            tb.purge();
                                        }
                                    }
                                }, 210, 150);
                            for(int k=0;k<gegner.size();k++){
                                gegner.get(k).stopMove();
                            }
                            geschoss.get(i).setX(-50);
                            geschoss.get(i).setY(-50);
                            ansicht.add(newGame);
                            playSound("lose.wav");
                            String d=""+score;

                            String numberString = reade();
                            int h = Integer.parseInt(numberString);
                            if(h<score)
                            {
                            storage(d);
                        }
                        score=0;
                        ansicht.setScore(0);
                        }
                    }
                }
            }
             if(geschoss.get(i).getY()>screenHeight || geschoss.get(i).getY()<0){
                for(int k=i;k<geschoss.size()-1;k++){
                    geschoss.set(k, geschoss.get(k+1));
                }
                geschoss.remove(geschoss.size()-1);
                i--;
            }

        }
    }

        
    

    public void setGegnerSpawn(){
        //setzt einen Timer, der festlegt, wann neue Gegner spawnen sollen (nachfolgend genauer erklärt)
        Timer gegnerSpawn=new Timer();
        periodeGegnerSpawn=ThreadLocalRandom.current().nextInt(2000, 3000); //setzt die Periode, in der neue Gegner spawnen sollen, zufällig (hier alle 2-3 Sek)
        gegnerSpawn.schedule(new TimerTask(){
                public void run(){//alles in diesem Block run() wird in der oben festgelegten Periode ausgeführt, damit ein neuer Gegner gespawnt wird
                    int x=ThreadLocalRandom.current().nextInt((int)(screenHeight*0.039), (int)(screenHeight*0.78125)); //hier wird zufällig der x-Wert des Gegners festgelegt
                    int y=-64;
                    int z=500;
                    /*
                     * Der nachfolgende Teil mit der time Variable ist mein Lösungsvorschlag dafür, dass mit der Zeit mehr Gegner spawnen sollen,
                     * ist dementsprechend unwichtig bzw. kann durch andere Wege ersetzt werden, ich habe ihn nur aufgenommen, weil das hier mein 
                     * erster Gegneralgorithmus zur Probe ist
                     * */
                    int time= (int) (System.currentTimeMillis()-timeStart)/2; //oben im Konstruktor wurde mit timeStart die Anfangszeit gemessen,das wird
                    //von der aktuellen Zeit (currentTimeMillis()) abgezogen -> man erhält die Zeit,
                    //die das Programm läuft; das habe ich noch durch 2 geteilt, 
                    //damit es nicht arg beeinflusst(siehe unten)
                    if(2000-time<0){/*wenn die Zeit >2000, dann erhält man einen negativen Wert, der
                         * als Periode nicht viel Sinn ergibt -> wenn so viel Zeit abgelaufen ist, dass time>2000, ist die am schnellsten mögliche Spawnrate
                         * erreicht
                         */
                        periodeGegnerSpawn=ThreadLocalRandom.current().nextInt(50, 400);//diese wird hier festgelegt
                    }else{
                        periodeGegnerSpawn=ThreadLocalRandom.current().nextInt(2000-time, 3000-time);//hier wird die GegnerSpawnPeriode neu gesetzt, 
                        //wobei von den normalen Werten die Laufzeit abgezogen wird
                        //-> je mehr Zeit vergangen, desto schneller spawnen Gegner
                    }
                    if(maxGegner==gegnerNumber){
                        shouldGegnerSpawn = false;
                        if(gegner.size()==0){
                            score += waveCounter * 1000;
                            waveCounter++;
                            ansicht.setWaveCounter(waveCounter);
                            ansicht.setShowWave(true);
                            try{Thread.sleep(3500);}
                            catch(Exception e){e.printStackTrace();}
                            ansicht.setShowWave(false);
                            try{Thread.sleep(1500);}
                            catch(Exception e){e.printStackTrace();}
                            maxGegner=maxGegner+2;
                            shouldGegnerSpawn=true;
                            gegnerNumber=0;
                        }
                    }
                    if(shouldGegnerSpawn){
                        addGegner(new Gegner2(x, y, 30, 45, (int)(screenHeight*0.0625))); //hier wird der neue Gegner mit den oben zufällig bestimmten x- und y-Werten und der festgelegten bf und mf erzeugt.
                        gegnerNumber++;
                        ShouldBossSpawn--;
                    }
                    if(ShouldBossSpawn < 1){
                        addGegner(new Gegner3(z, y, 8, 20, (int)(screenHeight*0.0625)));
                        ShouldBossSpawn = 10 - waveCounter;
                    }

                    currentTime= (int) System.currentTimeMillis();
                }
            }, 0, periodeGegnerSpawn);//die Variable wird hier als Periode eingesetzt -> der Timer feuert nach dieser Zeit, die jedes Mal neu bestimmt wird (siehe oben)
    }

    public void shoot(Gegner ge){
        if(ge.getKey()==1){
            geschoss.add(new Geschoss(ge.getX()+30, ge.getY()+ge.getLength(),(int) (-(screenHeight/170.666)), 1));
            ge.setShoot(false);
        }
                if(ge.getKey()==2){
            geschoss.add(new Geschoss(ge.getX()+30, ge.getY()+ge.getLength(),(int) (-(screenHeight/170.666)), 2));
            ge.setShoot(false);
        }

    }

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
                // The wrapper thread is unnecessary, unless it blocks on the
                // Clip finishing; see comments.
                public void run() {
                    try {
                        Clip clip = AudioSystem.getClip();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                                Steuerung.class.getResourceAsStream(url));
                        clip.open(inputStream);
                        clip.start(); 
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                }
            }).start();
    }
    private class Action implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==newGame){
                hitsSpieler=0;
                ansicht.remove(newGame);
                ansicht.setNewGame();
                geschoss.clear();
                gegner.clear();
                spieler.setSpeed(5);
                fenster.requestFocus();
                ansicht.repaint();
            }
        }
    }
}
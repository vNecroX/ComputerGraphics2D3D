package relojanalogico;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class RelojAnalogico extends JFrame implements Runnable{
    Image imageBuffer, buffer;
    Graphics graphics;
    Thread thread;
    
    int hour, min, sec;
    
    int hourHandle = 60;
    int minHandle = 90;
    int secHandle = 140;
    
    double angle;
    
    Color hourHandleColor = new Color(0, 0, 0);
    Color minHandleColor = new Color(231, 25, 181);
    Color secHandleColor = new Color(185, 39, 150);
    
    public RelojAnalogico(){
        setTitle("Reloj analogico");
        setResizable(false);
        setSize(500,500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] args) { new RelojAnalogico(); }
    
    @Override
    public void run(){
        while(true){
            try{
                repaint();
                thread.sleep(1000);
            }catch(InterruptedException ex){ }
        }
    }
    
    @Override
    public void paint(Graphics g){
        if(imageBuffer == null){
            imageBuffer = createImage(getWidth(), getHeight());
            graphics = imageBuffer.getGraphics();
            graphics.setClip(0, 0, getWidth(), getHeight());
            ImageIcon imageIcon = new ImageIcon("img/watch.png");
            graphics.drawImage(imageIcon.getImage(), 0, 20, getWidth()-10, getHeight()-20, this);
        }
        update(g);
    }
    
    public void update(Graphics g){
        double xHour, yHour, hourAngle;
        double xMin, yMin, minAngle;
        double xSec, ySec, secAngle;
        
        g.setClip(0, 0, getWidth(), getHeight());
        
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR);
        min = cal.get(Calendar.MINUTE);
        sec = cal.get(Calendar.SECOND);

        buffer = createImage(getWidth(), getHeight());
        graphics = buffer.getGraphics();
        graphics.setClip(0, 0, getWidth(), getHeight());
        graphics.drawImage(imageBuffer, 0, 0, this);

        hourAngle = oneFifthSixtyAngle(hour);
        xHour = getX(hourAngle, hourHandle);
        yHour = getY(hourAngle, hourHandle);
        graphics.setColor(hourHandleColor);
        graphics.drawLine(getWidth()/2, getHeight()/2, (getHeight()/2) + (int) xHour, (getHeight()/2) + (int) yHour);
        
        minAngle = sixtyAngle(min);
        xMin = getX(minAngle, minHandle);
        yMin = getY(minAngle, minHandle);
        graphics.setColor(minHandleColor);
        graphics.drawLine(getWidth()/2, getHeight()/2, (getWidth()/2) + (int) xMin, (getHeight()/2) + (int) yMin);

        secAngle = sixtyAngle(sec);
        xSec = getX(secAngle, secHandle);
        ySec = getY(secAngle, secHandle);
        graphics.setColor(secHandleColor);
        graphics.drawLine(getWidth()/2, getHeight()/2, (getWidth()/2) + (int) xSec, (getHeight()/2) + (int) ySec);
        
        g.drawImage(buffer, 0, 0, this);
        System.out.println(hour + ":" + min + ":" + sec);
    }
    
    private double oneFifthSixtyAngle(int hourAngle) {
        angle = (360/12) * hourAngle;
        return -angle+180;
    }

    private double sixtyAngle(int minSecAngle) {
        angle = (360/60) * minSecAngle;
        return -angle+180;
    }
    
    private double getX(double angle, int radio){
        double x = (double) radio * (double) (Math.sin(Math.toRadians(angle)));
        return x;
    }
    
    private double getY(double angle, int radio){
        double y = (double) radio * (double) (Math.cos(Math.toRadians(angle)));
        return y;
    }
}

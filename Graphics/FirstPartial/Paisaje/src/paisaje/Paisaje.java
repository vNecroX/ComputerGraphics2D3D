package paisaje;

import java.awt.Color;
import static java.awt.Color.black;
import static java.awt.Color.white;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;

public class Paisaje extends JFrame{

    private final BufferedImage landBuffer;
    
    private final BufferedImage moonBuffer;
    int moonWidth = 100, moonHeight = 100;
    int moonXShadow = 30, moonYShadow = 30;
    
    Random rand0 = new Random();
    Random rand1 = new Random();
    int rand0value, rand1value;
    
    Color roadColor = new Color(112,115,110);
    
    private final BufferedImage roadStripsBuffer;
    Color roadStripesColor = new Color(248,255,189);
    int roadStripeX = 20, roadStripeY = 487, roadStripes = 7;
    
    Color lightColor = new Color(255,241,189);
    
    public Paisaje(){
        setTitle("Paisaje");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        landBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        moonBuffer = new BufferedImage(moonWidth, moonHeight, BufferedImage.TYPE_INT_RGB);
        roadStripsBuffer = new BufferedImage(40, 1, BufferedImage.TYPE_INT_RGB);
    }
    
    public static void main(String[] args) { new Paisaje(); }

    public void paint(Graphics g){
        super.paint(g);
        drawMethod(g);
    }
    
    public void drawMethod(Graphics g){
        drawLandscape(black);
        drawMoon(g);
        drawStars(white);
        drawRoad();
        drawRoadStripes(g, roadStripesColor);
    }
    
    public void drawLandscape(Color c){
        for(int i=0; i<500; i++){
            for(int j=0; j<500; j++)
                putPixel(i, j, c);
        }
    }
    
    public void drawMoon(Graphics g){
        g.setColor(white);
        //g.draw
        g.fillOval(345, 75, moonBuffer.getWidth(), moonBuffer.getHeight());
        g.setColor(black);
        g.fillOval(345, 75, moonBuffer.getWidth()-moonXShadow, moonBuffer.getHeight()-moonYShadow);
       
        g.drawString("aaaaaaaaaaaa", 375, 95);
    }
    
    
    public void drawStars(Color c){
        for(int i=0; i<500; i++){
            for(int j=0; j<420; j++)
                randStars(i, j, c);
        }
    }
    
    public void randStars(int i, int j, Color c){  
        if(j < 320){
            rand0value = rand0.nextInt(550-1) + 1;
            rand1value = rand1.nextInt(550-1) + 1;
        }
        else{
            rand0value = rand0.nextInt(1100-1) + 1;
            rand1value = rand1.nextInt(1100-1) + 1;
        }
            
        if(rand0value == rand1value)
            putPixel(i, j, c);
    }
    
    public void drawRoad(){    
        for(int i=0; i<500; i++){
            for(int j=480; j<500; j++)
                putPixel(i, j, roadColor);
        }
    }
    
    public void drawRoadStripes(Graphics g, Color c){
        g.setColor(c);
        
        for(int i=0; i<roadStripes; i++){
            g.fillRect(roadStripeX, roadStripeY, roadStripsBuffer.getWidth(), roadStripsBuffer.getHeight());
            
            roadStripeX += 70;
        }
    }
    
    public void putPixel(int x, int y, Color c){
        landBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(landBuffer, x, y, this);
    }
}

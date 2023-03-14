package circulopolar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class CirculoPolar extends JFrame{
    Color figureColor = new Color(112, 115, 110);
    
    public class Draw{
        public Draw(){ }
        
        public void PolarCircle(int xc, int yc, int r){
            double theta = Math.toRadians(0);
            double xd, yd;
            
            int x = r;
            int y = 0;
            
            System.out.println("x : " + x);
            System.out.println("y : " + y);
            
            while(theta <= 2*Math.PI){
                putPixel((xc + x), (yc + y), figureColor);
                
                theta += Math.toRadians(1);
               
                xd = r*Math.cos(theta);
                x = (int)Math.round(xd);
                
                yd = r*Math.sin(theta);
                y = (int)Math.round(yd);
                
                System.out.println("00. theta : " + theta);
                System.out.println("00. xd : " + xd);
                System.out.println("00. x : " + x);
                System.out.println("00. yd : " + yd);
                System.out.println("00. y : " + y);
                System.out.println();
            }
        }
    }

    Draw draw = new Draw();
    
    BufferedImage figureBuffer;
    
    int xc = 50, yc = 50, r = 55;
    
    public CirculoPolar(){
        setTitle("Circulo polar");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        figureBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public static void main(String[] args){ new CirculoPolar(); }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        draw.PolarCircle(xc, yc, r);
    }
    
    public void putPixel(int x, int y, Color c){
        figureBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(figureBuffer, x+100, y+100, this);
    }
}

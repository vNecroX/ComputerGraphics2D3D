package circulonormal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public final class CirculoNormal extends JFrame{
    Color figureColor = new Color(112, 115, 110);
    
    public class Draw{
        public Draw(){ }
        
        public void CircleMidPoint(int xc, int yc, int r){
            int x, y, p;
            
            x = 0;
            y = r;
            p = 1-r;
            
            putPixel(xc, yc, x, y, figureColor);
            
            System.out.println("x : " + x);
            System.out.println("y : " + y);
            System.out.println("p : " + p);
            
            while(x <= y){
                x += 1;
                
                if(p < 0) p += 2*x + 1;
                else{
                    y -= 1;
                    p += 2*(x-y) + 1;
                }
                
                putPixel(xc, yc, x, y, figureColor);
                
                System.out.println("p : " + p);
                System.out.println("00. x : " + x);
                System.out.println("00. y : " + y);
                System.out.println();
            }
        }
    }

    Draw draw = new Draw();
    
    BufferedImage figureBuffer;
    
    int xc = 50, yc = 50, r = 60;
    
    public CirculoNormal(){
        setTitle("Circulo normal");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        figureBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }
    
    public static void main(String[] args){ new CirculoNormal(); }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        draw.CircleMidPoint(xc, yc, r);
    }
    
    public void putPixel(int xc, int yc, int x, int y, Color c){
        figureBuffer.setRGB(0, 0, c.getRGB());
        
        this.getGraphics().drawImage(figureBuffer, (xc + x) + 100, (yc + y) + 95, this);
        this.getGraphics().drawImage(figureBuffer, (xc - x) + 105, (yc + y) + 95, this);
        this.getGraphics().drawImage(figureBuffer, (xc + x) + 100, (yc - y) + 100, this);
        this.getGraphics().drawImage(figureBuffer, (xc - x) + 105, (yc - y) + 100, this);
        this.getGraphics().drawImage(figureBuffer, (xc + y) + 100, (yc + x) + 95, this);
        this.getGraphics().drawImage(figureBuffer, (xc - y) + 105, (yc + x) + 95, this);
        this.getGraphics().drawImage(figureBuffer, (xc + y) + 100, (yc - x) + 100, this);
        this.getGraphics().drawImage(figureBuffer, (xc - y) + 105, (yc - x) + 100, this);
    }
}

package elipse;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public final class Elipse extends JFrame{
    Color figureColor = new Color(112, 115, 110);
    
    public class Draw{
        public Draw(){ }
        
        public void EllipseMidPoint(int xc, int yc, int rx, int ry){
            int x, y, p, pX, pY;
            int rx2, ry2, tx2, ty2;
            
            ry2 = ry*ry;
            rx2 = rx*rx;
            
            ty2 = 2*ry2;
            tx2 = 2*rx2;
            
            x = 0;
            y = ry;
            
            System.out.println("ry2 : " + ry2);
            System.out.println("rx2 : " + rx2);
            System.out.println("ty2 : " + ty2);
            System.out.println("tx2 : " + tx2);
            
            p = (int)Math.round(ry2 - rx2*ry + (0.25*rx2));
            pX = 0;
            pY = tx2*y;
            
            while(pX < pY){
                x += 1;
                pX += ty2;
                
                if(p < 0) p += ry2 + pX;
                else{
                    y -= 1;
                    pY -= tx2;
                    p += ry2 + pX - pY;
                }
                
                putPixel(xc, yc, x, y, figureColor);
                
                System.out.println("00. p : " + p);
                System.out.println("00. x : " + x);
                System.out.println("00. y : " + y);
                System.out.println();
            }
            
            p = (int)Math.round(ry2*(x+0.5)*(x+0.5) + rx2*(y-1)*(y-1) - rx2*ry2);
            pX = 0;
            pY = tx2*y;
            
            while(y > 0){
                y -= 1;
                pY -= tx2;
                
                if(p > 0) p += rx2 - pY;
                else{
                    x += 1;
                    pX += ty2;
                    p += rx2 - pY + pX;
                }
                
                putPixel(xc, yc, x, y, figureColor);
                
                System.out.println("11. p : " + p);
                System.out.println("11. x : " + x);
                System.out.println("11. y : " + y);
                System.out.println();
            }
        }
    }

    Draw draw = new Draw();
    
    BufferedImage figureBuffer;
    
    int xc = 125, yc = 125, rx = 120, ry = 20;
    
    public Elipse(){
        setTitle("Elipse");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        figureBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public static void main(String[] args){ new Elipse(); }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        draw.EllipseMidPoint(xc, yc, rx, ry);
    }
    
    public void putPixel(int xc, int yc, int x, int y, Color c){
        figureBuffer.setRGB(0, 0, c.getRGB());
        
        this.getGraphics().drawImage(figureBuffer, (xc + x) + 100, (yc + y) + 100, this);
        this.getGraphics().drawImage(figureBuffer, (xc - x) + 120, (yc + y) + 100, this);
        this.getGraphics().drawImage(figureBuffer, (xc + x) + 100, (yc - y) + 100, this);
        this.getGraphics().drawImage(figureBuffer, (xc - x) + 120, (yc - y) + 100, this);
    }
}

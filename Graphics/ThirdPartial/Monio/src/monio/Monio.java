package monio;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import javax.swing.JFrame;

public final class Monio extends JFrame{
    
    public class Draw{
        Draw(){}
        
        public void Curve(int xCoord, int yCoord, int t){
            double x0 = 0, y0 = 0;
            double x1, y1; 
            int scale = 3;
            
            double auxT = 0;
            
            while(auxT <= t){
                x1 = x0;
                y1 = y0;
                
                x0 = auxT-(3*Math.sin(auxT));
                y0 = 4-(3*Math.cos(auxT))*-1;
                
                auxT += 0.1;
                
                if(x0 != x1)
                    DDA(round((float)x0*scale) + xCoord, round((float)y0*scale) + yCoord, round((float)x1*scale) + xCoord, round((float)y1*scale) + yCoord, curveColor);
            }
        }
        
        
        private void DDA(int x0, int y0, int x1, int y1, Color c){
            int dx = x1-x0;
            int dy = y1-y0;
            int steps;
            
            if(abs(dx) > abs(dy)) steps = abs(dx);
            else steps = abs(dy);
            
            float xinc = (float) dx/steps;
            float yinc = (float) dy/steps;
            float x = x0;
            float y = y0;
            
            putPixel(round(x), round(y), c);
            
            for(int k=1; k<=steps; k++){
                x += xinc;
                y += yinc;
                putPixel(round(x), round(y), c);
            }
        } 
    }
    
    Draw draw = new Draw();
    
    Color curveColor;
    
    BufferedImage buffer;
    
    public Monio(){
        super("Monio");
        
        curveColor = new Color(0, 0, 0);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        setSize(700, 400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) { new Monio(); }
    
    @Override
    public void paint(Graphics g){ drawCurve(); }
    
    public void drawCurve(){
        draw.Curve(175, 200, 100);
    }
    
    public void putPixel(int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }
    
}

package curvas5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import javax.swing.JFrame;

public final class Curvas5 extends JFrame{
    
    public class Draw{
        Draw(){}
        
        public void Curve(double xCoord, double yCoord, int traces){
            double x0, y0;
            double inc = Math.PI/traces;
            double xA = 0, yA = 0;
            double xB = 0, yB = 0;
            double t = 0;
            int count = 1;
            
            int scale = 4;
            
            while(t < Math.PI*14){
                x0 = (17*Math.cos(t) + 7*Math.cos((2.428571428571429)*t))*scale + xCoord;
                y0 = (17*Math.sin(t) - 7*Math.sin((2.428571428571429)*t))*scale + yCoord;
                
                if((count%2) == 1){
                    if(count != 1) 
                        DDA((int)x0, (int)y0, (int)xB, (int)yB, curveColor);
                    
                    xA = x0;
                    yA = y0;
                }
                else{
                    DDA((int)x0, (int)y0, (int)xA, (int)yA, curveColor);
                    
                    xB = x0;
                    yB = y0;
                }
                
                t += inc;
                count++;
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
    
    public Curvas5(){
        super("Curvas5");
        
        curveColor = new Color(0, 0, 0);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        setSize(450, 450);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) { new Curvas5(); }
    
    @Override
    public void paint(Graphics g){ drawCurve(); }
    
    public void drawCurve(){
        draw.Curve(200, 200, 80);
    }
    
    public void putPixel(int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }
}

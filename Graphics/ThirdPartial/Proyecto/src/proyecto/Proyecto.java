package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import javax.swing.JFrame;

public final class Proyecto extends JFrame{
    public class Draw{
        Draw(){}
        
        public void Curve(int xCoord, int yCoord, int traces){
            double x0 = 0, y0 = 0, x1, y1, auxx1, auxy1;
            int scale = 20;
            
            int auxTraces = traces;
            
            while(auxTraces > 0){
                x1 = x0;
                y1 = y0;
                
                auxx1 = x0;
                auxy1 = y0;
                
                y0 -= (float)(Math.PI*2)/traces;
                x0 = (float)y0*Math.atan(8/y0) * -0.20;
                
                auxTraces--;
                
                DDA(round((float)x0*scale) + xCoord, round((float)y0*scale) + yCoord, round((float)x1*scale) + xCoord, round((float)y1*scale) + yCoord, edgeColor);
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
    
    Draw draw[] = new Draw[4];
    
    Color edgeColor;
    Color upSegmentColor, leftSegmentColor, frontSegmentColor;
    
    BufferedImage buffer;
    Image image;
    Graphics imageG;
    
    public Proyecto(){
        super("Proyecto");
        
        edgeColor = new Color(0, 0, 0);
        
        upSegmentColor = new Color(143, 0, 69);
        leftSegmentColor = new Color(226, 216, 164);
        frontSegmentColor = new Color(215, 189, 226);
        
        for(int i=0; i<4; i++) draw[i] = new Draw();
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        setSize(800, 800);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) { new Proyecto(); }
    
    @Override
    public void paint(Graphics g){ drawCube(); }
    
    public void drawCube(){
        image = createImage(getWidth(), getHeight());
        imageG = image.getGraphics();

        imageG.drawImage(buffer, 0, 0, this);

        draw[0].Curve(175, 350, 45000);
        draw[1].Curve(177, 350, 45000);

        try{ Thread.sleep(2);
        }catch(InterruptedException ex){ System.out.print(ex); }
    }
    
    public void putPixel(int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x+52, y+82, this);
    }
}

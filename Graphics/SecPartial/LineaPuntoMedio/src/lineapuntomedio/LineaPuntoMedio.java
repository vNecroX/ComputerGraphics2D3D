package lineapuntomedio;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public final class LineaPuntoMedio extends JFrame{
    Color lineMidPointColor = new Color(112,115,110);
    
    public class Draw{
        public Draw(){ }
        
        public void LineMidPoint(int x0, int y0, int x1, int y1){
            int dx = x1-x0;
            int dy = y1-y0;
            int p = 2*dy - dx;
            int incE = 2*dy;
            int incNE = 2*(dy-dx);
            
            System.out.println("dx : " + dx);
            System.out.println("dy : " + dy);
            System.out.println("p : " + p);
            System.out.println("incE : " + incE);
            System.out.println("incNE : " + incNE);
            
            int x, y, xEnd;
            
            if(x0 > x1){
                x = x1;
                y = y1;
                xEnd = x0;
            }
            else{
                x = x0;
                y = y0;
                xEnd = x1;
            }
            
            System.out.println("0. x : " + x);
            System.out.println("0. y : " + y);
            System.out.println("xEnd : " + xEnd);
            
            while(x <= xEnd){
                putPixel(x, y, lineMidPointColor);
                x += 1;
                
                if(p <= 0) p += incE;
                else{
                    y += 1;
                    p += incNE;
                }
                
                System.out.println("p : " + p);
                System.out.println("00. x : " + x);
                System.out.println("00. y : " + y);
                System.out.println();
            }
        }
    }

    Draw draw = new Draw();
    
    BufferedImage lineMidPointBuffer;
    
    int x0 = 150, y0 = 100, x1 = 300, y1 = 180;
    
    public LineaPuntoMedio(){
        setTitle("Linea de punto medio");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        lineMidPointBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public static void main(String[] args){ new LineaPuntoMedio(); }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        draw.LineMidPoint(x0, y0, x1, y1);
    }
    
    public void putPixel(int x, int y, Color c){
        lineMidPointBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(lineMidPointBuffer, x, y, this);
    }
}

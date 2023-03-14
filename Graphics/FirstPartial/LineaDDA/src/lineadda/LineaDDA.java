package lineadda;

import java.awt.Color;
import static java.awt.Color.black;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import javax.swing.JFrame;

public class LineaDDA extends JFrame{
    private final BufferedImage ddaBuffer;
    Color ddaColor = new Color(112,115,110);
    int x1 = 80, y1 = 80, x0 = 30, y0 = 30;

    public LineaDDA(){
        setTitle("Linea DDA");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ddaBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }
    
    public static void main(String[] args) { new LineaDDA(); }
    
    public void paint(Graphics g){
        super.paint(g);
        drawDDA(x1, y1, x0, y0, black);
    }
    
    public void drawDDA(int x1, int y1, int x0, int y0, Color c){
        int dx = x1-x0;
        int dy = y1-y0;
        
        int steps = abs(dy);
        System.out.println("Steps:" + steps);
        
        int xinc = dx/steps; 
        int yinc = dy/steps;
        System.out.println("xinc:" + xinc);
        System.out.println("yinc:" + yinc);
        
        int x = x0;
        int y = y0;
        System.out.println("x:" + x);
        System.out.println("y:" + y);
        System.out.println("/ / / /");
        
        for(int i=0; i<steps; i++){
            putPixel(250+x, 250+y, c);
            x += xinc;
            y += yinc;
            System.out.println("x:" + x);
            System.out.println("y:" + y);
        }
    }
    
    public void putPixel(int x, int y, Color c){
        ddaBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(ddaBuffer, x, y, this);
    }
}

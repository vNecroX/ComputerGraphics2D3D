package mallas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public final class Mallas extends JFrame{
    
    public class DrawMesh{
        public DrawMesh(){}
        
        public void Point(int x, int y, Color c){
            putPixel((x + 50), (y + 50), c);
        }
        
        public void XLine(int x0, int x1, int y, Color c){
            while(x0 < x1){
                putPixel((x0 + 50), (y + 50), c);
                x0++;
            }
        }
        
        public void YLine(int y0, int y1, int x, Color c){
            while(y0 < y1){
                putPixel((x + 50), (y0 + 50), c);
                y0++;
            }
        }
    }
    
    DrawMesh drawMesh = new DrawMesh();
    
    Color pointColor, lineColor;
    
    BufferedImage buffer;
    
    int x[] = {100, 200, 300, 400, 500};
    int y[] = {100, 200, 300, 400, 500};
    
    public Mallas(){
        super("Mallas");
        
        pointColor = new Color(0, 0, 0);
        lineColor = new Color(215, 189, 226);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        setSize(750, 750);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){ new Mallas(); }
    
    @Override
    public void paint(Graphics g){ drawMesh(); }
    
    public void drawMesh(){
        drawLine();
        drawPoint();
    }
    
    public void drawLine(){
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++){
                if(j != 4){
                    drawMesh.XLine(x[j], x[j+1], y[i], lineColor);
                    drawMesh.YLine(y[j], y[j+1], x[i], lineColor);
                }
                else{
                    drawMesh.XLine(x[j], x[j], y[i], lineColor);
                    drawMesh.YLine(y[j], y[j], x[i], lineColor);
                }
            }
        }
    }
            
    public void drawPoint(){
        for(int i=0; i<5; i++)
            for(int j=0; j<5; j++)
                drawMesh.Point(x[j], y[i], pointColor);
    }
    
    public void putPixel(int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }
}

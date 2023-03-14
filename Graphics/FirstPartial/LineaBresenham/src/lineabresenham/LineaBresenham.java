package lineabresenham;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import javax.swing.JFrame;

public class LineaBresenham extends JFrame{
    Color bresenhamColor = new Color(112, 115, 110);
    //int x0 = 0, y0 = 100, x1 = 200, y1 = 350;
    int x0 = 100, y0 = 150, x1 = 0, y1 = 100;
    
    private final BufferedImage bresenhamBuffer;
    
    public LineaBresenham(){
        setTitle("Linea Bresenham");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        bresenhamBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public static void main(String[] args) { new LineaBresenham(); }
    
    public void paint(Graphics g){
        super.paint(g);
        drawBresenham(x0, y0, x1, y1, bresenhamColor);
    }
    
    public void drawBresenham(int x0, int y0, int x1, int y1, Color c){
        int dy = y1-y0;
        int dx = x1-x0;
        double m = (double) (Double.valueOf(dy)/(dx));
        
        int x = x0;
        int y;
        
        int A, B;
        int p;
        
        System.out.println("dx:" + dx);
        System.out.println("dy:" + dy);
        System.out.println("m:" + m);
        System.out.println("x:" + x);
        
                                                                    
        if((0<m) && (m<1)){
            y = y0;
            System.out.println("y:" + y);
            
            putPixel(x, y, c);
            
            A = 2*dy;
            B = 2*dy - 2*dx;
            
            p = 2*dy - dx;
            
            if((dx > 0) && (dy > 0)){
                while(x <= x1){
                    System.out.print("x:" + x + " ");

                    if(p < 0){
                        putPixel(x, y, c);
                        p += A;
                    }
                    else if(p >= 0){
                        System.out.print("y:" + y);
                        putPixel(x, y, c);
                        p += B;
                        y++;
                    }
                    x++;

                    System.out.println();
                }
            }
            
            if((dx < 0) && (dy < 0)){
                int auxX = 0;
                y = abs(dy);
                System.out.println("y:" + y);
            
                while(x >= x1){
                    System.out.print("x:" + x + " ");
                        
                    if(p < 0){
                        putPixel(x, y, c);
                        p += A;
                    }
                    else if(p >= 0){
                        putPixel(x, y, c);
                        p += B;
                        
                        if(auxX == 2){
                            auxX = 0;
                            y++;
                        }
                        
                        System.out.print("y:" + y);
                    }
                    auxX++;
                    x--;
                    
                    System.out.println();
                }
            }
        }
    }
    
    public void putPixel(int x, int y, Color c){
        bresenhamBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(bresenhamBuffer, x+100, y+100, this);
    }
}

package linearecta;

import java.awt.Color;
import static java.awt.Color.red;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public final class LineaRecta extends JFrame{
    
    private BufferedImage buffer;
    //int x1 = 300, y1 = 300, x2 = 415, y2 = 405;
    //int x1 = 300, y1 = 300, x2 = 415, y2 = 300;
    int x1 = 300, y1 = 300, x2 = 300, y2 = 415; 
    
    public LineaRecta(){
        setTitle("Ventana");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public static void main(String[] args) { new LineaRecta(); }
    
    public void paint(Graphics g){
        super.paint(g);
        verifyValues();
        
        if((x1 != x2) && (y1 != y2))
            putStraight(x1, y1, x2, y2);
        
        if((x1 < x2) && (y1 == y2))
            putX(x1, x2, y1);
        
        if((x1 == x2) && (y1 < y2))
            putY(x1, y1, y2);
    }
    
    public void verifyValues(){
        int auxX, auxY;
        
        if(x2 < x1){
            auxX = x1;
            x1 = x2;
            x2 = auxX;
        }
        
        if(y2 < y1){
            auxY = y1;
            y1 = y2;
            y2 = auxY;
        }
    }
    
    public void putX(int x1, int x2, int y){
        while(x1 < x2){
            putPixel(x1, y, red);
            x1++;
        }
    }
    
    public void putY(int x, int y1, int y2){
        while(y1 < y2){
            putPixel(x, y1, red);
            y1++;
        }
    }
    
    private void putStraight(int x1, int y1, int x2, int y2){
        int new_m = 2 * (y2-y1);
        int new_slope_err = new_m - (x2-x1);
        
        for(int x=x1, y=y1; x<=x2; x++){
            putPixel(x, y, red);
            new_slope_err += new_m;
            
            if(new_slope_err >= 0)
                new_slope_err -= 2 * (x2-x1);
        }
    }
    
    public void putPixel(int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }
}

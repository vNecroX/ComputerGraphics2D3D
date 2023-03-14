
package pixel;

import java.awt.Color;
import static java.awt.Color.red;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Pixel extends JFrame{
    
    private BufferedImage buffer;
    
    public Pixel(){
        setTitle("Ventana");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public static void main(String[] args){ new Pixel(); }
    
    public void paint(Graphics g){
        super.paint(g);
        putPixel(300, 300, red);
    }
    
    public void putPixel(int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }
}                               
package ortogonal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import javax.swing.JFrame;

public final class Ortogonal extends JFrame{
    public class Draw{
        public Draw(){ }
        
        public void square(float currentCoords[]){
            edgeX(Math.round(currentCoords[0]), Math.round(currentCoords[1]), Math.round(currentCoords[2])); 
            edgeY(Math.round(currentCoords[1]), Math.round(currentCoords[2]), Math.round(currentCoords[3]));
            edgeX(Math.round(currentCoords[1]), Math.round(currentCoords[0]), Math.round(currentCoords[3]));
            edgeY(Math.round(currentCoords[0]), Math.round(currentCoords[3]), Math.round(currentCoords[2]));
        }
        
        private void edgeX(int x1, int x2, int y){
            while(x1 < x2 || x1 > x2){
                putPixel(x1, y, figureColor);

                if(x1 < x2) x1++;
                else if(x1 > x2) x1--;
            }
        }
        
        private void edgeY(int x, int y1, int y2){
            while(y1 < y2 || y1 > y2){
                putPixel(x, y1, figureColor);

                if(y1 < y2) y1++;
                else if(y1 > y2) y1--;
            }
        }
        
        public void DDA(int x0, int y0, int x1, int y1){
            int dx = x1-x0;
            int dy = y1-y0;
            int steps;
            
            if(abs(dx) > abs(dy)) steps = abs(dx);
            else steps = abs(dy);
            
            float xinc = (float) dx/steps;
            float yinc = (float) dy/steps;
            float x = x0;
            float y = y0;
            
            putPixel(round(x), round(y), figureColor);
            
            for(int k=1; k<=steps; k++){
                x += xinc;
                y += yinc;
                putPixel(round(x), round(y), figureColor);
            }
        }
    }
    
    Draw draw = new Draw();
    
    Color figureColor;
    
    BufferedImage buffer;
    
    float pos[] = {500, 200, 300};
    
                      //x1, y1, z1
    float coord[][] = {{100, 100, 0},
                       {200, 100, 0},
                       {200, 200, 0},
                       {100, 200, 0},
                       {175, 200, 0},
                       {275, 200, 0},
                       {275, 100, 0},
                       {175, 100, 0}};
    
    float newCoord[][] = new float[8][2];
    float currentCoords[] = new float[4];
    
    public Ortogonal(){
        super("Ortogonal");
        
        figureColor = new Color(112, 115, 110);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        setSize(400, 400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){ new Ortogonal(); }
    
    @Override
    public void paint(Graphics g){ drawCube(); }
    
    public void drawCube(){
        for(int i=0; i<8; i++){
            newCoord[i][0] = Math.round(coord[i][0] + pos[0]* -1 * (coord[i][2]/pos[2])); 
            System.out.print("x[" + i + "]:" + newCoord[i][0] + " ");
            
            newCoord[i][1] = Math.round(coord[i][1] + pos[1]* -1 * (coord[i][2]/pos[2])); 
            System.out.println("y[" + i + "]:" + newCoord[i][1]);
        }
        
        drawSquare(newCoord[0][0], newCoord[2][0], newCoord[0][1], newCoord[2][1]);
        drawLine(newCoord[0][0], newCoord[0][1], newCoord[4][0], newCoord[6][1]);
        drawLine(newCoord[1][0], newCoord[1][1], newCoord[5][0], newCoord[7][1]);
        drawLine(newCoord[2][0], newCoord[2][1], newCoord[6][0], newCoord[4][1]);
        drawLine(newCoord[3][0], newCoord[3][1], newCoord[7][0], newCoord[5][1]);
        drawSquare(newCoord[4][0], newCoord[6][0], newCoord[4][1], newCoord[6][1]);
    }
    
    public void drawSquare(float c0, float c1, float c2, float c3){
        currentCoords[0] = c0;
        currentCoords[1] = c1;
        currentCoords[2] = c2;
        currentCoords[3] = c3;
        draw.square(currentCoords);
    }
    
    public void drawLine(float x0, float y0, float x1, float y1){
        draw.DDA(Math.round(x0), Math.round(y0), Math.round(x1), Math.round(y1));
    }   
    
    public void putPixel(int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x+52, y+49, this);
    }
}

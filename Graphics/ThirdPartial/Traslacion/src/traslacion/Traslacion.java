package traslacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import javax.swing.JFrame;

public final class Traslacion extends JFrame{
    public class Draw{
        public Draw(){ }
        
        public void square(float currentCoords[], Graphics g){
            edgeX(Math.round(currentCoords[0]), Math.round(currentCoords[1]), Math.round(currentCoords[2]), g); 
            edgeY(Math.round(currentCoords[1]), Math.round(currentCoords[2]), Math.round(currentCoords[3]), g);
            edgeX(Math.round(currentCoords[1]), Math.round(currentCoords[0]), Math.round(currentCoords[3]), g);
            edgeY(Math.round(currentCoords[0]), Math.round(currentCoords[3]), Math.round(currentCoords[2]), g);
        }
        
        private void edgeX(int x1, int x2, int y, Graphics g){
            while(x1 < x2 || x1 > x2){
                putPixel(x1, y, figureColor, g);

                if(x1 < x2) x1++;
                else if(x1 > x2) x1--;
            }
        }
        
        private void edgeY(int x, int y1, int y2, Graphics g){
            while(y1 < y2 || y1 > y2){
                putPixel(x, y1, figureColor, g);

                if(y1 < y2) y1++;
                else if(y1 > y2) y1--;
            }
        }
        
        public void DDA(int x0, int y0, int x1, int y1, Graphics g){
            int dx = x1-x0;
            int dy = y1-y0;
            int steps;
            
            if(abs(dx) > abs(dy)) steps = abs(dx);
            else steps = abs(dy);
            
            float xinc = (float) dx/steps;
            float yinc = (float) dy/steps;
            float x = x0;
            float y = y0;
            
            putPixel(round(x), round(y), figureColor, g);
            
            for(int k=1; k<=steps; k++){
                x += xinc;
                y += yinc;
                putPixel(round(x), round(y), figureColor, g);
            }
        }
    }
    
    Draw draw = new Draw();
    
    Color figureColor;
    
    BufferedImage buffer;
    Image image;
    Graphics imageG;
    
    float pos[] = {500, 200, 300};
    
                      //x1, y1, z1
    float coord[][] = {{100, 100, 0},
                       {200, 100, 0},
                       {200, 200, 0},
                       {100, 200, 0},
                       {100, 200, 25},
                       {200, 200, 25},
                       {200, 100, 25},
                       {100, 100, 25}};
    
    float newCoord[][] = new float[8][2];
    float currentCoords[] = new float[4];
    
    float tx = 150, ty = 100, tz = 50;
    float xLim = coord[2][0] + tx, yLim = coord[2][1] + ty, zLim = coord[2][2] + tz;
    
    public Traslacion(){
        super("Traslacion");
        
        figureColor = new Color(112, 115, 110);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        setSize(400, 400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args){ new Traslacion(); }
    
    @Override
    public void paint(Graphics g){ drawCube(); }
    
    public void drawCube(){
        while(coord[2][0] < xLim){
            image = createImage(getWidth(), getHeight());
            imageG = image.getGraphics();
            
            imageG.drawImage(buffer, 0, 0, this);
            
            for(int i=0; i<8; i++){
                newCoord[i][0] = Math.round(coord[i][0] + pos[0]* -1 * (coord[i][2]/pos[2])); 
                System.out.print("x[" + i + "]:" + newCoord[i][0] + " ");

                newCoord[i][1] = Math.round(coord[i][1] + pos[1]* -1 * (coord[i][2]/pos[2])); 
                System.out.println("y[" + i + "]:" + newCoord[i][1]);
            }

            drawSquare(newCoord[0][0], newCoord[2][0], newCoord[0][1], newCoord[2][1], imageG);
            drawLine(newCoord[0][0], newCoord[0][1], newCoord[4][0], newCoord[6][1], imageG);
            drawLine(newCoord[1][0], newCoord[1][1], newCoord[5][0], newCoord[7][1], imageG);
            drawLine(newCoord[2][0], newCoord[2][1], newCoord[6][0], newCoord[4][1], imageG);
            drawLine(newCoord[3][0], newCoord[3][1], newCoord[7][0], newCoord[5][1], imageG);
            drawSquare(newCoord[4][0], newCoord[6][0], newCoord[4][1], newCoord[6][1], imageG);
            
            this.getGraphics().drawImage(image, 0, 0, getWidth(), getHeight(), this);

            getNewCube();
            
            try{ Thread.sleep(4);
            }catch(InterruptedException ex){ System.out.print(ex); }
        }
    }
    
    public void drawSquare(float c0, float c1, float c2, float c3, Graphics g){
        currentCoords[0] = c0;
        currentCoords[1] = c1;
        currentCoords[2] = c2;
        currentCoords[3] = c3;
        draw.square(currentCoords, g);
    }
    
    public void drawLine(float x0, float y0, float x1, float y1, Graphics g){
        draw.DDA(Math.round(x0), Math.round(y0), Math.round(x1), Math.round(y1), g);
    }   
    
    public void getNewCube(){
        for(int i=0; i<8; i++){
            for(int j=0; j<3; j++){
                switch (j){
                    case 0 -> coord[i][j] += 1;
                    case 1 -> coord[i][j] += 1;
                    case 2 -> coord[i][j] += 0.2;
                    default -> { }
                }
            }
        }
    }
    
    public void putPixel(int x, int y, Color c, Graphics g){
        buffer.setRGB(0, 0, c.getRGB());
        g.drawImage(buffer, x+32, y+30, this);
    }   
}

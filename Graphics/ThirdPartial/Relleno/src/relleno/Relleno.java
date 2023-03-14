package relleno;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import javax.swing.JFrame;

public final class Relleno extends JFrame{
    public class Draw{
        public Draw(){ }
        
        public void square(float currentCoords[], int callUpSegment, int callLeftSegment, int callFrontSegment, Graphics g){
            edgeX(Math.round(currentCoords[0]), Math.round(currentCoords[1]), Math.round(currentCoords[2]), g); 
            edgeY(Math.round(currentCoords[1]), Math.round(currentCoords[2]), Math.round(currentCoords[3]), g);
            edgeX(Math.round(currentCoords[1]), Math.round(currentCoords[0]), Math.round(currentCoords[3]), g);
            edgeY(Math.round(currentCoords[0]), Math.round(currentCoords[3]), Math.round(currentCoords[2]), g);
            
            if(callUpSegment == 1){
                fillUpParam[0][0] = currentCoords[0];
                fillUpParam[0][1] = currentCoords[1];
                fillUpParam[0][2] = currentCoords[2];
            }
            else if(callUpSegment == 2) fillUpParam[1][2] = currentCoords[3];
            
            if(callLeftSegment == 1){
                fillLeftParam[0][0] = currentCoords[0];
                fillLeftParam[0][1] = currentCoords[3];
                fillLeftParam[0][2] = currentCoords[2];
            }
            else if(callLeftSegment == 2) fillLeftParam[1][0] = currentCoords[0];
            
            if(callFrontSegment == 1){
                fillFrontParam[0][0] = currentCoords[0];
                fillFrontParam[0][1] = currentCoords[1];
                fillFrontParam[0][2] = currentCoords[2];
                fillFrontParam[1][2] = currentCoords[3];
                
                System.out.println("C00:" + fillFrontParam[0][0]);
                System.out.println("C01:" + fillFrontParam[0][1]);
                System.out.println("C02:" + fillFrontParam[0][2]);
                System.out.println("C12:" + fillFrontParam[1][2]);
            }
        }
        
        private void edgeX(int x1, int x2, int y, Graphics g){
            while(x1 < x2 || x1 > x2){
                putPixel(x1, y, edgeColor, g);

                if(x1 < x2) x1++;
                else if(x1 > x2) x1--;
            }
        }
        
        private void edgeY(int x, int y1, int y2, Graphics g){
            while(y1 < y2 || y1 > y2){
                putPixel(x, y1, edgeColor, g);

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
            
            putPixel(round(x), round(y), edgeColor, g);
            
            for(int k=1; k<=steps; k++){
                x += xinc;
                y += yinc;
                putPixel(round(x), round(y), edgeColor, g);
            }
        }
        
        public void fillUpSegment(float x0, float x1, float y, float _y, Graphics g){
            float auxX1 = x1;
            
            while(y >= _y){
                while(x1 >= x0){
                    putPixel(Math.round(x1), Math.round(y), upSegmentColor, g);
                    x1--;
                }
                
                x0 -= 2.5;
                auxX1 -= 2.5;
                x1 = auxX1;
                y--;
            }
        }
        
        public void fillLeftSegment(float x, float _x, float y0, float y1, Graphics g){
            float auxY1 = y1;
           
            x -= 1;
            
            while(x >= _x){
                while(y1 <= y0-2){
                    y1++;
                    putPixel(Math.round(x), Math.round(y1), leftSegmentColor, g);
                }
                
                y0 -= 0.41;
                auxY1 -= 0.41;
                y1 = auxY1;
                x--;
            }
        }
        
        public void fillFrontSegment(float x0, float x1, float y, float _y, Graphics g){
            float auxX1 = x1;
            
            System.out.println("y:" + y);
            System.out.println("_y:" + _y);
            
            while(y <= _y){
                while(x1 >= x0){
                    putPixel(Math.round(x1), Math.round(y), frontSegmentColor, g);
                    x1--;
                }
                
                x1 = auxX1;
                y++;
            }
        }
    }
    
    Draw draw = new Draw();
    
    Color edgeColor;
    Color upSegmentColor, leftSegmentColor, frontSegmentColor;
    
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
    
    float fillUpParam[][] = new float[2][4];
    float fillLeftParam[][] = new float[2][4];
    float fillFrontParam[][] = new float[2][4];
    
    public Relleno(){
        super("Relleno");
        
        edgeColor = new Color(0, 0, 0);
        
        upSegmentColor = new Color(143, 0, 69);
        leftSegmentColor = new Color(226, 216, 164);
        frontSegmentColor = new Color(215, 189, 226);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        setSize(400, 400);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args) { new Relleno(); }
    
    @Override
    public void paint(Graphics g){ drawCube(); }
    
    public void drawCube(){
        image = createImage(getWidth(), getHeight());
        imageG = image.getGraphics();
            
        imageG.drawImage(buffer, 0, 0, this);
        
        for(int i=0; i<8; i++){
            newCoord[i][0] = Math.round(coord[i][0] + pos[0]* -1 * (coord[i][2]/pos[2])); 
            System.out.print("x[" + i + "]:" + newCoord[i][0] + " ");
            
            newCoord[i][1] = Math.round(coord[i][1] + pos[1]* -1 * (coord[i][2]/pos[2])); 
            System.out.println("y[" + i + "]:" + newCoord[i][1]);
        }
        
        drawSquare(newCoord[0][0], newCoord[2][0], newCoord[0][1], newCoord[2][1], 1, 1, 1, imageG);
        fill2D(imageG);
        drawSquare(newCoord[0][0], newCoord[2][0], newCoord[0][1], newCoord[2][1], 1, 1, 1, imageG);
        
        drawLine(newCoord[0][0], newCoord[0][1], newCoord[4][0], newCoord[6][1], imageG);
        drawLine(newCoord[1][0], newCoord[1][1], newCoord[5][0], newCoord[7][1], imageG);
        drawLine(newCoord[2][0], newCoord[2][1], newCoord[6][0], newCoord[4][1], imageG);
        drawLine(newCoord[3][0], newCoord[3][1], newCoord[7][0], newCoord[5][1], imageG);
        
        drawSquare(newCoord[4][0], newCoord[6][0], newCoord[4][1], newCoord[6][1], 2, 2, 1, imageG);
        fill3D(imageG);
        drawSquare(newCoord[4][0], newCoord[6][0], newCoord[4][1], newCoord[6][1], 2, 2, 1, imageG);
        
        this.getGraphics().drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
    
    public void drawSquare(float c0, float c1, float c2, float c3, int callUpSegment, int callLeftSegment, int callFrontSegment, Graphics g){
        currentCoords[0] = c0;
        currentCoords[1] = c1;
        currentCoords[2] = c2;
        currentCoords[3] = c3;
        draw.square(currentCoords, callUpSegment, callLeftSegment, callFrontSegment, g);
    }
    
    public void fill2D(Graphics g){
        fillFrontSegment(fillFrontParam[0][0], fillFrontParam[0][1], fillFrontParam[0][2], fillFrontParam[1][2], g);
    }
    
    public void fillFrontSegment(float x0, float x1, float y, float _y, Graphics g){
        draw.fillFrontSegment(x0, x1, y, _y, g);
    }
    
    public void drawLine(float x0, float y0, float x1, float y1, Graphics g){
        draw.DDA(Math.round(x0), Math.round(y0), Math.round(x1), Math.round(y1), g);
    }   
    
    public void fill3D(Graphics g){
        fillUpSegment(fillUpParam[0][0], fillUpParam[0][1], fillUpParam[0][2], fillUpParam[1][2], g);
        fillLeftSegment(fillLeftParam[0][0], fillLeftParam[1][0], fillLeftParam[0][1], fillLeftParam[0][2], g);
    }
    
    public void fillUpSegment(float x0, float x1, float y, float _y, Graphics g){
        draw.fillUpSegment(x0, x1, y, _y, g);
    }
    
    public void fillLeftSegment(float x, float _x, float y0, float y1, Graphics g){
        draw.fillLeftSegment(x, _x, y0, y1, g);
    }
    
    public void putPixel(int x, int y, Color c, Graphics g){
        buffer.setRGB(0, 0, c.getRGB());
        g.drawImage(buffer, x+52, y+52, this);
    }
}

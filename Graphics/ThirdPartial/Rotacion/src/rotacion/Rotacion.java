package rotacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import javax.swing.JFrame;

public final class Rotacion extends JFrame{
    public class Draw{
        public Draw(){ }
        
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
    
    int windowWidth = 400, windowHeight = 400, contador = 0, contadorDias = 0;
    
    float coord[][][] = {{{100, 100, 100}, 
                          {200, 100, 100},
                          {200, 200, 100}, 
                          {100, 200, 100}}, 
                         {{100, 100, 200},
                          {200, 100, 200},
                          {200, 200, 200}, 
                          {100, 200, 200}}};
    Graphics graPixel;
    
    public Rotacion(){
        super("Rotacion");
        
        figureColor = new Color(112, 115, 110);
        
        buffer = new BufferedImage(1,1,BufferedImage.TYPE_INT_RGB);
        
        setSize(windowWidth,windowHeight);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) { new Rotacion(); }
    
    @Override
    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        imageG = image.getGraphics();
        
        drawFigure(coord, 300, 200, 0, 0, 1, imageG);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        
        rotate(coord, 300, 200, 0, 0, 1, 100, 30, 50, 75, imageG);
        
        drawFigure(coord, 300, 200, 0, 0, 1, imageG);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }

    private void drawFigure(float[][][] coord, int x0, int y0, int xp, int yp, int zp, Graphics g) {
        for(int i=0; i<coord[0].length-1;i++){
            draw.DDA(xp, yp, xp, yp, g);
            draw.DDA(round(coord[0][i][0] + (xp * (-coord[0][i][2]/zp))) + x0, round(coord[0][i][1] + (yp * (-coord[0][i][2]/zp))) + y0, round(coord[0][i+1][0] + (xp * (-coord[0][i+1][2]/zp))) + x0, round(coord[0][i+1][1] + (yp * (-coord[0][i+1][2]/zp))) + y0, g);
            draw.DDA(round(coord[1][i][0] + (xp * (-coord[1][i][2]/zp))) + x0, round(coord[1][i][1] + (yp * (-coord[1][i][2]/zp))) + y0, round(coord[1][i+1][0] + (xp * (-coord[1][i+1][2]/zp))) + x0, round(coord[1][i+1][1] + (yp * (-coord[1][i+1][2]/zp))) + y0, g);
            draw.DDA(round(coord[0][i][0] + (xp * (-coord[0][i][2]/zp))) + x0, round(coord[0][i][1] + (yp * (-coord[0][i][2]/zp))) + y0, round(coord[1][i][0] + (xp * (-coord[1][i][2]/zp))) + x0, round(coord[1][i][1] + (yp * (-coord[1][i][2]/zp))) + y0, g);
        }
        draw.DDA(round(coord[0][coord[0].length-1][0] + (xp * (-coord[0][coord[0].length-1][2]/zp))) + x0, round(coord[0][coord[0].length-1][1] + (yp * (-coord[0][coord[0].length-1][2]/zp))) + y0,round(coord[0][0][0] + (xp * (-coord[0][0][2]/zp))) + x0, round(coord[0][0][1] + (yp* (-coord[0][0][2]/zp))) + y0, g);
        draw.DDA(round(coord[1][coord[1].length-1][0] + (xp * (-coord[1][coord[1].length-1][2]/zp))) + x0, round(coord[1][coord[1].length-1][1] + (yp * (-coord[1][coord[1].length-1][2]/zp))) + y0,round(coord[1][0][0] + (xp * (-coord[1][0][2]/zp))) + x0, round(coord[1][0][1] + (yp* (-coord[1][0][2]/zp))) + y0, g);
        draw.DDA(round(coord[0][coord[0].length-1][0] + (xp * (-coord[0][coord[0].length-1][2]/zp))) + x0, round(coord[0][coord[0].length-1][1] + (yp * (-coord[0][coord[0].length-1][2]/zp))) + y0,round(coord[1][coord[1].length-1][0] + (xp * (-coord[1][coord[1].length-1][2]/zp))) + x0, round(coord[1][coord[1].length-1][1] + (yp * (-coord[1][coord[1].length-1][2]/zp))) + y0, g);
    }
    
    private void rotate(float[][][] coord, int x0, int y0, int xp, int yp, int zp, int steps, float xAngle, float yAngle, float zAngle, Graphics g) {
        float xAngleInc = xAngle/steps;
        float yAngleInc = yAngle/steps;
        float zAngleInc = zAngle/steps;
        
        for(int temp=0; temp<steps; temp++){
            for(int i=0; i<coord.length; i++){
                coord[i] = rotateX(coord[i], xAngleInc);
                coord[i] = rotateY(coord[i], yAngleInc);
                coord[i] = rotateZ(coord[i], zAngleInc);
            }
            
            clearPixel(g);
            drawFigure(coord, x0, y0, xp, yp, zp, g);
            
            this.getGraphics().drawImage(image, 0, 0, getWidth(), getHeight(), this);
            
            try{ Thread.sleep(10);
            }catch(InterruptedException ex){ System.out.println(ex); }
        }
    }
    
    private float[][] rotateX(float[][] coord, float xAngle) {
        double radxAngle = Math.toRadians(xAngle);
        
        float m[][] = {{1, 0, 0, 0},
                       {0, (float) Math.cos(radxAngle), (float) Math.sin(radxAngle), 0},
                       {0, -(float) Math.sin(radxAngle), (float) Math.cos(radxAngle), 0}, 
                       {0, 0, 0, 1}};
        float mFigure[][] = new float[coord.length][4];
        
        for(int i=0; i<coord.length;i++){
            for(int j=0; j<4; j++){
                if(j<3) mFigure[i][j] = coord[i][j];
                else mFigure[i][j] = 1;
            }
        }
        return getResultMatriz(m, mFigure);
    }
    
    private float[][] rotateY(float[][] coord, float yAngle) {
        double radyAngle = Math.toRadians(yAngle);
        
        float m[][] = {{(float) Math.cos(radyAngle), 0, -(float) Math.sin(radyAngle), 0},
                       {0, 1, 0, 0},
                       {(float) Math.sin(radyAngle), 0, (float) Math.cos(radyAngle), 0}, 
                       {0, 0, 0, 1}};
        float mFigure[][] = new float[coord.length][4];
        
        for(int i=0; i<coord.length;i++){
            for(int j=0; j<4; j++){
                if(j<3) mFigure[i][j] = coord[i][j];
                else mFigure[i][j] = 1;
            }
        }
        return getResultMatriz(m, mFigure);
    }
    
    private float[][] rotateZ(float[][] coord, float zAngle) {
        double radzAngle = Math.toRadians(zAngle);
        
        float m[][] = {{(float) Math.cos(radzAngle), (float) Math.sin(radzAngle), 0, 0},
                       {-(float) Math.sin(radzAngle), (float) Math.cos(radzAngle), 0, 0},
                       {0, 0, 1, 0}, 
                       {0, 0, 0, 1}}; 
        float mFigure[][] = new float[coord.length][4];
        
        for(int i=0; i<coord.length;i++){
            for(int j=0; j<4; j++){
                if(j<3) mFigure[i][j] = coord[i][j];
                else mFigure[i][j] = 1;
            }
        }
        return getResultMatriz(m, mFigure);
    }

    private float[][] getResultMatriz(float[][] m, float[][] mFigure) {
        float coord[][] = new float[mFigure.length][3];
        
        for(int i=0; i<mFigure.length;i++){
            for(int j=0; j<4; j++){
                coord[i][0] += (float) (m[0][j] * mFigure[i][j]);
                coord[i][1] += (float) (m[1][j] * mFigure[i][j]);
                coord[i][2] += (float) (m[2][j] * mFigure[i][j]);
            }
        }
        return coord;
    }
    
    public void putPixel(int x, int y, Color c, Graphics g){
        buffer.setRGB(0, 0, c.getRGB());
        g.drawImage(buffer, x-220, windowHeight+70-y, this);
    }
    
    public void clearPixel(Graphics g){
        buffer.setRGB(0, 0, this.getBackground().getRGB());
        g.drawImage(buffer, 0, 0, getWidth(), getHeight(), this);
    }
}

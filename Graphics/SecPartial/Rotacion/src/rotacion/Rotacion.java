package rotacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import javax.swing.JFrame;

public final class Rotacion extends JFrame implements Runnable{
    Color figureColor = new Color(112, 115, 110);
    
    public class Draw{
        public Draw(){ }
        
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
            
            buffer.setRGB(0, 0, figureColor.getRGB());
            putPixel(round(x), round(y), figureColor);
            
            for(int k=1; k<=steps; k++){
                x += xinc;
                y += yinc;
                buffer.setRGB(0, 0, figureColor.getRGB());
                putPixel(round(x), round(y), figureColor);
            }
        }
    }

    Draw draw = new Draw();
    Thread thread;
    boolean rot = false;
    
    BufferedImage buffer;
    
    float rotAngle = 75f, incAngle= 0f;
    float rotSteps = Math.abs(rotAngle);
    float rotK = 0f; 
    
    float pOneX = 50f; float pOneY = 50f;             
    float pTwoX = 90f; float pTwoY = 50f;
    float pThreeX = 90f; float pThreeY = 90f;
    float pFourX = 50f; float pFourY = 90f;
    float coords[][] = new float [3][4]; 
    
    public Rotacion(){
        setTitle("Rotacion");
        setSize(800, 800);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initCoords();

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        thread = new Thread(this);
        thread.start();
    }
    
    public static void main(String[] args){ new Rotacion(); }
    
    public void initCoords(){
        System.out.println("coords:");
        
        coords[0][0] = pOneX; coords[0][1] = pTwoX; coords[0][2] = pThreeX; coords[0][3] = pFourX;
        coords[1][0] = pOneY; coords[1][1] = pTwoY; coords[1][2] = pThreeY; coords[1][3] = pFourY;
        
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                if(i == 2)
                    coords[i][j] = 1;
                
                System.out.print("[" + i + "][" + j + "]:" + coords[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);

        drawFigure(coords);
        currentCoords();

        initRotation();
        update();
    }

    public void update(){ 
        clearPixel();
        
        if(Math.abs(rotK) < Math.abs(rotSteps)){
            coords = rotate(coords, incAngle);
            drawFigure(coords);
            rotK += incAngle;
        }
        else{
            if(!rot) currentCoords();
            
            drawFigure(coords);
            rot = true;
        }
    }
    
    public void drawFigure(float[][] coords){
        for(int i=0; i<coords.length;i++)
            draw.DDA(round(coords[0][i]), round(coords[1][i]), round(coords[0][i+1]), round(coords[1][i+1]));
        
        draw.DDA(round(coords[0][3]), round(coords[1][3]), round(coords[0][0]), round(coords[1][0]));
    }
    
    public void currentCoords(){
        System.out.println("P1 : ("+round(coords[0][0])+", "+round(coords[1][0])+")");
        System.out.println("P2 : ("+round(coords[0][1])+", "+round(coords[1][1])+")");
        System.out.println("P3 : ("+round(coords[0][2])+", "+round(coords[1][2])+")");
        System.out.println("P4 : ("+round(coords[0][3])+", "+round(coords[1][3])+")");
    }
    
    public void initRotation(){
        incAngle = rotAngle/rotSteps;
        coords = rotate(coords, incAngle);
        drawFigure(coords);
        rotK += incAngle;
    }
    
    public float[][] rotate(float[][] m, float a){
        double radAngle = Math.toRadians((double)a);
        float angle[][] = {{((float)Math.cos(radAngle)), ((float)Math.sin(radAngle)), 0}, {-((float)Math.sin(radAngle)), ((float)Math.cos(radAngle)), 0}, {0, 0, 1}};
        float coords[][] = new float[3][4];
        
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print("angle[" + i + "][" + j + "] : " + angle[i][j]+ " ");
                
                if(j == 2) System.out.println();
            }
        }
        System.out.println();
        
        for(int i=0; i<3; i++){
            for(int j=0; j<4; j++){
                coords[i][j] = m[i][j];
                
                if(i == 2)
                    coords[i][j] = 1;
                
                System.out.print("coords[" + i + "][" + j + "] : " + coords[i][j] + " ");
                
                if(j == 3) System.out.println();
            }
        }
        System.out.println();
        
        return newM(angle, coords);
    }
    
    public float[][] newM(float[][] angle, float[][] coords){
        float newCoords[][] = new float[3][4];
        float res = 0, auxRes;
        
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                for(int k=0; k<3; k++){
                    auxRes = angle[j][k] * coords[k][i];
                    System.out.println("k : " + k + " -> angle(" + angle[j][k] + ") * coords(" + coords[k][i] + ") -> " + auxRes);
                    
                    res += auxRes;
                    
                    if(k == 2){
                        newCoords[j][i] = res;
                        res = 0;
                        System.out.println("New coords[" + j + "][" + i + "] : " + newCoords[j][i] + " ");
                        System.out.println();
                    }
                }
            }
        }
        
        return newCoords;
    }
    
    @Override
    public void run(){
        while(!rot){
            try{
                repaint();
                thread.sleep(100);
            }catch(InterruptedException ex){ System.out.println(ex); }
        }
        thread.stop();
    }
    
    public void putPixel(int x, int y, Color c){
        buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(buffer, x+325, y+325, this);
    }
    
    public void clearPixel(){
        buffer.setRGB(0, 0, this.getBackground().getRGB());
        this.getGraphics().drawImage(buffer, 0, 0, getWidth(), getHeight(), this);
    }
}

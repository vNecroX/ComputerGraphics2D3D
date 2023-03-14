package scanline;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public final class ScanLine extends JFrame implements Runnable{
    Color figureColor = new Color(112, 115, 110);
    
    public class Draw{
        public Draw(){ }
        
        public void square(int currentCoords[][], Graphics g){
            int px00 = currentCoords[0][0], px01 = currentCoords[0][1];
            int py02 = currentCoords[0][2], py22 = currentCoords[2][2];
            
            int _px00 = px00;
            
            while(py02 <= py22){
                
                System.out.println("py02:" + py02);
                
                while(px00 <= px01){
                    
                    System.out.println("px00:" + px00);
                    
                    putPixel(px00, py02, figureColor, g);
                    
                    px00++;
                }
                
                px00 = _px00;
                py02++;
            }
        }
    }
    
    Draw draw = new Draw();
    Thread thread;
    boolean tras = false;
    
    BufferedImage figureBuffer;
    Image image;
    Graphics imageGraphics;
    
    int distX = 400; int distY = 50;
    int dist[][] = new int [3][3];  
    
    int pOneX = 25; int pOneY = 25;             
    int pTwoX = 75; int pTwoY = 25;
    int pThreeX = 75; int pThreeY = 75;
    int pFourX = 25; int pFourY = 75;
    int coords[][] = new int [3][4]; 
    
    int _pOneX; int _pOneY;
    int _pTwoX; int _pTwoY;
    int _pThreeX; int _pThreeY;
    int _pFourX; int _pFourY;
    int newCoords[][] = new int [4][3]; 
    
    public ScanLine(){
        setTitle("ScanLine");
        setSize(800, 800);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initDist();
        initCoords();
        getNewCoords();

        figureBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        thread = new Thread(this);
        thread.start();
    }
    
    public static void main(String[] args){ new ScanLine(); }
    
    public void initDist(){
        System.out.println("dist:");
        
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(i == j)
                    dist[i][j] = 1;
                else{
                    dist[i][j] = 0;
                    
                    if((i == 0) && (j == 2))
                        dist[i][j] = distX;
                    else if((i == 1) && (j == 2))
                        dist[i][j] = distY;
                }
                System.out.print("[" + i + "][" + j + "]:" + dist[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
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
    
    public void getNewCoords(){
        int res = 0;
        
        System.out.println("New coords:");
        
        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                for(int k=0; k<3; k++){
                    res += coords[k][i] * dist[j][k];
                
                    if(k == 2){
                        newCoords[i][j] = res;
                        res = 0;
                        System.out.print("[" + i + "][" + j + "]:" + newCoords[i][j] + " ");
                    }
                }
            }
            System.out.println();
        }
        System.out.println();
        
        _pOneX = newCoords[0][0]; _pOneY = newCoords[0][1];
        _pTwoX = newCoords[1][0]; _pTwoY = newCoords[1][1];
        _pThreeX = newCoords[2][0]; _pThreeY = newCoords[2][1];
        _pFourX = newCoords[3][0]; _pFourY = newCoords[3][1];
    }
    
    @Override
    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        imageGraphics = image.getGraphics();
        
        imageGraphics.drawImage(figureBuffer, 0, 0, this);
        
        clearPixel(imageGraphics);
        drawFigure(imageGraphics);
        clearPixel(imageGraphics);
        
        if(pOneX == _pOneX) tras = true;
    }
    
    public void drawFigure(Graphics g){
        int currentCoords[][] = {
            { pOneX, pTwoX, pOneY},
            { pTwoX, pTwoY, pThreeY},
            { pThreeX, pFourX, pThreeY},
            { pFourX, pFourY, pOneY}
        };
        
        draw.square(currentCoords, g);
        this.getGraphics().drawImage(image, 0, 0, getWidth(), getHeight(), this);
        
        pOneX += 4; pOneY += 4;
        pTwoX += 4; pTwoY += 4;
        pThreeX += 4; pThreeY += 4;
        pFourX += 4; pFourY += 4;
    }

    @Override
    public void run(){
        while(!tras){
            try{
                repaint();
                thread.sleep(0);
            }catch(InterruptedException ex){ System.out.println(ex); }
        }
        thread.stop();
    }
    
    public void clearPixel(Graphics g){
        figureBuffer.setRGB(0, 0, this.getBackground().getRGB());
        g.drawImage(figureBuffer, 0, 0, getWidth(), getHeight(), this);
    }
    
    public void putPixel(int x, int y, Color c, Graphics g){
        figureBuffer.setRGB(0, 0, c.getRGB());
        g.drawImage(figureBuffer, x+25, y+25, this);
    }
}
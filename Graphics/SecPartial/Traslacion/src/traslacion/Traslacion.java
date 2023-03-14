package traslacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public final class Traslacion extends JFrame implements Runnable{
    Color figureColor = new Color(112, 115, 110);
    
    public class Draw{
        public Draw(){ }
        
        public void square(int currentCoords[][], Graphics g){
            int id[] = { currentCoords[0][3], currentCoords[1][3], currentCoords[2][3], currentCoords[3][3]};
        
            if(id[0] == 0)
                edgeX(currentCoords[0][0], currentCoords[0][1], currentCoords[0][2], g); 
            
            if(id[1] == 1)
                edgeY(currentCoords[1][0], currentCoords[1][1], currentCoords[1][2], g);
            
            if(id[2] == 0)
                edgeX(currentCoords[2][0], currentCoords[2][1], currentCoords[2][2], g);
            
            if(id[3] == 1)
                edgeY(currentCoords[3][0], currentCoords[3][1], currentCoords[3][2], g);
        }
        
        public void edgeX(int x1, int x2, int y, Graphics g){
            while(x1 < x2 || x1 > x2){
                putPixel(x1, y, figureColor, g);

                if(x1 < x2)
                    x1++;
                else if(x1 > x2)
                    x1--;
            }
        }
        
        public void edgeY(int x, int y1, int y2, Graphics g){
            while(y1 < y2 || y1 > y2){
                putPixel(x, y1, figureColor, g);

                if(y1 < y2)
                    y1++;
                else if(y1 > y2)
                    y1--;
            }
        }
    }
    
    Draw draw = new Draw();
    Thread thread;
    boolean back = false, tras = false;
    
    BufferedImage figureBuffer;
    Image image;
    Graphics imageGraphics;
    
    int distX = 150; int distY = 100;
    int dist[][] = new int [3][3];  
    
    int pOneX = 50; int pOneY = 50;             
    int pTwoX = 110; int pTwoY = 50;
    int pThreeX = 110; int pThreeY = 110;
    int pFourX = 50; int pFourY = 110;
    int coords[][] = new int [3][4]; 
    
    int _pOneX; int _pOneY;
    int _pTwoX; int _pTwoY;
    int _pThreeX; int _pThreeY;
    int _pFourX; int _pFourY;
    int newCoords[][] = new int [4][3]; 
    
    public Traslacion(){
        setTitle("Traslacion");
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
    
    public static void main(String[] args){ new Traslacion(); }
    
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
        
        if(!back){
            back = true;
            
            try{ thread.sleep(2000);
            }catch(InterruptedException ex){ System.out.println(ex); }
            
            clearPixel(imageGraphics);
        }
        
        if(pOneX == _pOneX) tras = true;
    }
    
    public void drawFigure(Graphics g){
        int currentCoords[][] = {
            { pOneX, pTwoX, pOneY, 0},
            { pTwoX, pTwoY, pThreeY, 1},
            { pThreeX, pFourX, pThreeY, 0},
            { pFourX, pFourY, pOneY, 1}
        };
        
        draw.square(currentCoords, g);
        this.getGraphics().drawImage(image, 0, 0, getWidth(), getHeight(), this);
        
        pOneX += 1; pOneY += 1;
        pTwoX += 1; pTwoY += 1;
        pThreeX += 1; pThreeY += 1;
        pFourX += 1; pFourY += 1;
    }

    @Override
    public void run(){
        while(!tras){
            try{
                repaint();
                thread.sleep(12);
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
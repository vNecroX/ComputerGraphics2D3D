package inundacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import javax.swing.JFrame;

public final class Inundacion extends JFrame{
    Color figureColor = new Color(112, 115, 110);
    
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
            
            buffer.setRGB(0, 0, figureColor.getRGB());
            putPixel(round(x), round(y), figureColor, g);
            
            for(int k=1; k<=steps; k++){
                x += xinc;
                y += yinc;
                buffer.setRGB(0, 0, figureColor.getRGB());
                putPixel(round(x), round(y), figureColor, g);
            }
        }
    }

    Draw draw = new Draw();

    private final BufferedImage buffer;
    private Image image;
    private Graphics imageGraphics;
    
    private float currentCoords[][] = {
        {35, 35},
        {75, 35},
        {75, 75}, 
        {35, 75}
    };
    
    public Inundacion(){
        setTitle("Inundacion");
        setSize(500, 500);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }
    
    public static void main(String[] args) { new Inundacion(); }
    
    @Override
    public void paint(Graphics g){
        try{
            clearPixel(this.getGraphics());
            image = createImage(getWidth(), getHeight());
            imageGraphics = image.getGraphics();
            clearPixel(imageGraphics);
            
            buffer.setRGB(0, 0, figureColor.getRGB());
            
            drawFigure(currentCoords, imageGraphics);
            floodFillFigure(imageGraphics);
            
            Thread.sleep(1000);
            
            moveFigure(255, 200, imageGraphics);
        }catch(InterruptedException ex){ System.out.println(ex); }
    }
    
    public void drawFigure(float[][] coords, Graphics g){
        for(int i=0; i<coords.length-1; i++)
            draw.DDA(round(coords[i][0]), round(coords[i][1]), round(coords[i+1][0]), round(coords[i+1][1]), g);
        
        draw.DDA(round(coords[coords.length-1][0]), round(coords[coords.length-1][1]), round(coords[0][0]), round(coords[0][1]), g);
    }
    
    public void moveFigure(int x, int y, Graphics g){
        int xTranslation = x, yTranslation = y;
        
        int translationSteps;
        float xInc, yInc;
        
        if(abs(xTranslation) > abs(yTranslation)) translationSteps = abs(xTranslation);
        else translationSteps = abs(yTranslation);
        
        xInc = ((float)xTranslation/(float)translationSteps);
        yInc = ((float)yTranslation/(float)translationSteps);
        
        currentCoords = moveTo(currentCoords, xInc, yInc);
        clearPixel(g);
        drawFigure(currentCoords, g);
        this.getGraphics().drawImage(image, 0, 0 ,getWidth(), getHeight(), this);
        
        for(int t = 1; t<translationSteps; t++){
            clearPixel(g);
            currentCoords = moveTo(currentCoords, xInc, yInc);
            
            drawFigure(currentCoords, g);
            floodFillFigure(g);
            
            try{ Thread.sleep(4);
            }catch(InterruptedException ex){ System.out.println(ex); }
        }
        clearPixel(g);
        
        System.out.println("FP: (" + round(currentCoords[0][0]) + ", " + round(currentCoords[0][1]) + ")");
    }
    
    public float[][] moveTo(float[][] figure, float dx, float dy){
        float dist[][] = {
            {1, 0, dx},
            {0, 1, dy},
            {0, 0, 1}
        };
        
        float coords[][] = new float[figure.length][3];
        
        for(int i=0; i<figure.length; i++){
            for(int j=0; j<3; j++){
                if(j < 2) coords[i][j] = figure[i][j];
                else coords[i][j] = 1;
            }
        }
        return getNewCoords(dist, coords);
    }
    
    public float[][] getNewCoords(float[][] dist, float[][] coords){
        float figure[][] = new float[coords.length][2];
        float tempX, tempY;
        
        for(int i=0; i<coords.length; i++){
            tempX = 0;
            tempY = 0;
            
            for(int j=0; j<3; j++){
                tempX += (dist[0][j] * coords[i][j]);
                tempY += (dist[1][j] * coords[i][j]);
            }
            
            figure[i][0] = tempX;
            figure[i][1] = tempY;
        }
        return figure;
    }
    
    public void floodFillFigure(Graphics g){
        floodFill(
                round((smallestX(currentCoords) + biggestX(currentCoords))/2), 
                round((smallestY(currentCoords) + biggestY(currentCoords))/2),
                figureColor, 
                g
        );
        this.getGraphics().drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
    
    public void floodFill(int x, int y, Color c, Graphics g){
        BufferedImage temp = (BufferedImage)image;
        
        if((x >= 0 && x < getWidth()) && (y >= 0 && y < getHeight()) && 
            (temp.getRGB(x, y) == this.getBackground().getRGB()) && (temp.getRGB(x, y) != c.getRGB())){
                putPixel(x, y, c, g);
                floodFill((x + 1), y, c, g);
                floodFill((x - 1), y, c, g);
                floodFill(x, (y + 1), c, g); 
                floodFill(x, (y - 1), c, g);
        }
    }

    public float smallestX(float[][] figure){
        float x = figure[0][0];
        
        for(float[] figure1 : figure)
            if(x > figure1[0]) x = figure1[0];
        
        return x;
    }

    public float biggestX(float[][] figure){
        float x = figure[0][0];
        
        for(float[] figure1 : figure)
            if(x < figure1[0]) x = figure1[0];
        
        return x;
    }

    public float smallestY(float[][] figure){
        float y = figure[0][1];
        
        for(float[] figure1 : figure)
            if(y > figure1[1]) y = figure1[1];
        
        return y;
    }

    public float biggestY(float[][] figure){
        float y = figure[0][0];
        
        for(float[] figure1 : figure)
            if(y < figure1[1]) y = figure1[1];
        
        return y;
    }
    
    public void putPixel(int x, int y, Color c, Graphics g){
        g.drawImage(buffer, x, y, this);
    }
    
    public void clearPixel(Graphics g){
        buffer.setRGB(0, 0, this.getBackground().getRGB());
        g.drawImage(buffer, 0, 0, getWidth(), getHeight(), this);
    }
}

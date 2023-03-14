package proyecto;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JFrame;

public final class Proyecto extends JFrame{
    
    public class Draw{
        public Draw(){}
        
        public void Peak(int x0, int y0, int x1, int y1, Graphics g){
            while(y0 <= y1){
                peak(x0, y0, x1, y1, g);

                x0--;
                x1--;
                y0++;
            }
        }
        
        private void peak(int x0, int y0, int x1, int y1, Graphics g){
            int dy = y1-y0;
            int dx = x1-x0;
            double m = (double) (Double.valueOf(dy)/(dx));

            int x = x0;
            int y, auxY;

            int A, B;
            int p;

            System.out.println("dx:" + dx);
            System.out.println("dy:" + dy);
            System.out.println("m:" + m);
            System.out.println("x:" + x);

            y = y0;
            System.out.println("y:" + y);
            auxY = 0;

            A = 2*dy;
            B = 2*dy - 2*dx;

            p = 2*dy - dx;

            if((dx > 0) && (dy > 0)){
                while(x <= x1){
                    System.out.print("x:" + x + " ");

                    if(auxY < 9) putPixel(x, y, peakColor, g);
                    else putPixel(x, y, snowColor, g);

                    if(p < 0) p += A;
                    else if(p >= 0){
                        System.out.print("y:" + y);
                        p += B;
                        y++;

                        auxY++;
                    }
                    x++;

                    System.out.println();
                }
            }
        }
        
        public void Land(int x0, int x1, int y0, int y1, int landscapeElement, Graphics g){
            int _x0 = x0;

            while(y0 <= y1){
                System.out.println("y0:" + y0);

                while(x0 <= x1){
                    System.out.println("x0:" + x0);
                    
                    if(landscapeElement == 0) putPixel(x0, y0, horizonColor, g);
                    else putPixel(x0, y0, roadColor, g);
                    
                    x0++;
                }

                x0 = _x0;
                y0++;
            }
        }
        
        public void Vehicle(int x0, int x1, int y0, int y1, Color c, Graphics g){
            int dx, dy;
            
            int _x0 = x0;
            int auxX = 0;
                    
            while(y0 <= y1){
                System.out.println("y0:" + y0);

                while(x0 <= x1){
                    System.out.println("x0:" + x0);
                    
                    dx = x1-x0;
                    dy = y1-y0;
                    
                    if((dx <= 15) && (y0 <= 286)) putPixel(x0+7, y0, carWindow, g);
                    else if((auxX <= 6) && (dy <= 6) || (dx <= 6) && (dy <= 6)){
                        auxX++;
                        putPixel(x0+7, y0, carTire, g);
                    }
                    else putPixel(x0+7, y0, c, g);
                    
                    x0++;
                }
                
                auxX = 0;

                x0 = _x0;
                y0++;
            }
        }
        
        public void SpaceStuff(int r, int xc, int yc, Graphics g){
            g.fillOval(xc, yc, r, r);
        }
        
        /*
        public void Horizon(int x0, int x1, int y0, int y1, Color c, Graphics g){
            int _x0 = x0;

            while(y0 <= y1){
                System.out.println("y0:" + y0);

                while(x0 <= x1){
                    System.out.println("x0:" + x0);
                    putPixel(x0, y0, c, g);
                    x0++;
                }

                x0 = _x0;
                y0++;
            }
        }
        */
    }
    
    Draw draw = new Draw();
    
    Color horizonColor, roadColor, peakColor, snowColor;
    Color goldenColor, lavenderColor, limeColor, silverColor, stainingColor;
    
    BufferedImage buffer;
    
    Image image, backImage;
    Graphics imageG, backImageG;
    
    Vehicle vehicle[] = new Vehicle[50];
    Color carWindow, carTire;
    int vehicleMoveConstX = 100;
    
    Color sun, moon;
    int r = 40, xc = 200, yc = -20, auxXC = xc, auxYC = yc;
    int sunMoveConstXC = 4, sunMoveConstYC = 3;
    int spaceStuffElement = 0;
    
    String c[] = new String[10];
    Color count;
    int goldenColorCount, lavenderColorCount, limeColorCount, silverColorCount, stainingColorCount;
    int auxColorCount = 0;
    
    /*
    Color horizonTransColor[] = new Color[50];
    int horizonG = 174;
    */
    
    public Proyecto(){
        super("Proyecto");
        
        horizonColor = new Color(94, 174, 233);
        roadColor = new Color(117, 142, 155);
        peakColor = new Color(145, 205, 232);
        snowColor = new Color(245, 245, 245);
        
        goldenColor = new Color(226, 216, 164);
        lavenderColor = new Color(215, 189, 226);
        limeColor = new Color(218, 247, 166);
        silverColor = new Color(208, 208, 208);
        stainingColor = new Color(143, 0, 69);
        
        carWindow = new Color(104, 104, 104);
        carTire = new Color(40, 40, 40);
        
        sun = new Color(236, 188, 104);
        moon = new Color(192, 192, 192);
        
        count = new Color(0, 0, 0);
        
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        
        setSize(450, 340);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public static void main(String[] args){ new Proyecto(); }
    
    @Override
    public void paint(Graphics g){
        drawBackground();
        drawCloseUp();
    }
    
    public void drawBackground(){
        backImage = createImage(getWidth(), getHeight());
        backImageG = backImage.getGraphics();
        backImageG.drawImage(buffer, 0, 0, this);
        
        drawLandscape(backImageG);
    }
    
    public void drawLandscape(Graphics g){
        int x0 = 0, x1 = 450, y0, y1;
        int landscapeElement;
        
        y0 = 0; y1 = 299; landscapeElement = 0;
        draw.Land(x0, x1, y0, y1, landscapeElement, g);

        y0 = 300; y1 = 340; landscapeElement = 1;
        draw.Land(x0, x1, y0, y1, landscapeElement, g);
        
        drawPeak(g);
    }
    
    public void drawPeak(Graphics g){
        int x0, y0, x1, y1;
        
        x0 = 340; y0 = 200; x1 = 439; y1 = 299;
        draw.Peak(x0, y0, x1, y1, g);
        
        x0 = 100; y0 = 100; x1 = 299; y1 = 299;
        draw.Peak(x0, y0, x1, y1, g);
    }
    
    public void drawCloseUp(){
        Random randomVehicle = new Random();
        int b = randomVehicle.nextInt(10) + 20;
        
        Random randomColor = new Random();
        Random auxRandomColor = new Random();
        int c, auxC = -1;
        
        for(int i=0; i<b; i++){
            c = randomColor.nextInt(5);
            
            if(c != auxC) auxC = c;
            else 
                while(c == auxC) 
                    c = auxRandomColor.nextInt(5);
            
            setVehicleProperties(i, c);
            
            for(int j=0; j<5; j++){
                moveElements(i);
                
                try{ Thread.sleep(400);
                }catch(InterruptedException ex){ System.out.println(ex); }
            } 
               
            auxColorCount = 0;
        }
    }
    
    public void setVehicleProperties(int i, int c){
        switch(c){
            case 0 -> vehicle[i] = new Vehicle(goldenColor);
            case 1 -> vehicle[i] = new Vehicle(lavenderColor);
            case 2 -> vehicle[i] = new Vehicle(limeColor);
            case 3 -> vehicle[i] = new Vehicle(silverColor);
            case 4 -> vehicle[i] = new Vehicle(stainingColor);
        }
        
        vehicle[i].setX0(10); vehicle[i].setX1(49);
        vehicle[i].setY0(279); vehicle[i].setY1(299);
    }
    
    public void moveElements(int i){
        image = createImage(getWidth(), getHeight());
        imageG = image.getGraphics();
        imageG.drawImage(backImage, 0, 0, this);

        moveVehicle(i, imageG);
        moveSpaceStuff(imageG);
        
        imageG.setColor(count);
        imageG.drawString(c[0] + c[1] + c[2] + c[3] + c[4], 155, 60);
        this.getGraphics().drawImage(image, 0, 0, this);
    }
    
    public void moveVehicle(int i, Graphics g){
        int x0 = vehicle[i].getX0(), x1 = vehicle[i].getX1();
        int y0 = vehicle[i].getY0(), y1 = vehicle[i].getY1();
        
        Color vehicleColor = vehicle[i].getColor();
        int vehicleRGB = vehicleColor.getRGB();
        
        draw.Vehicle(x0, x1, y0, y1, vehicleColor, g);
        
        vehicle[i].setX0(vehicleMoveConstX + vehicle[i].getX0());
        vehicle[i].setX1(vehicleMoveConstX + vehicle[i].getX1());
        
        if(auxColorCount == 0){
            auxColorCount++;
            
            if(vehicleRGB == goldenColor.getRGB()) goldenColorCount++;
            if(vehicleRGB == lavenderColor.getRGB()) lavenderColorCount++;
            if(vehicleRGB == limeColor.getRGB()) limeColorCount++;
            if(vehicleRGB == silverColor.getRGB()) silverColorCount++;
            if(vehicleRGB == stainingColor.getRGB()) stainingColorCount++;
            
            c[0] = "Dorado " + goldenColorCount + ", ";
            c[1] = "Lavanda: " + lavenderColorCount + ", ";
            c[2] = "Lima: " + limeColorCount + ", ";
            c[3] = "Plateado: " + silverColorCount + ", ";
            c[4] = "Tinto: " + stainingColorCount;
        }  
    }
    
    public void moveSpaceStuff(Graphics g){
        if(xc <= 450){
            if(spaceStuffElement == 0) g.setColor(sun);
            else g.setColor(moon);
        }
        else{
            if(spaceStuffElement == 0) spaceStuffElement = 1;
            else spaceStuffElement = 0;
            
            xc = auxXC;
            yc = auxYC;
        }
        
        draw.SpaceStuff(r, xc, yc, g);
        
        xc += sunMoveConstXC;
        yc += sunMoveConstYC;
    }
    
    /*
    public void moveHorizon(int i, Graphics g){
        int x0 = 0, x1 = 450;
        int y0 = 0, y1 = 2;
        
        horizonTransColor[i] = new Color(94, horizonG, 233);
        draw.Horizon(x0, x1, y0, y1, horizonTransColor[i], imageG);
        
        horizonG -= 15;
    }
    */
    
    public void putPixel(int x, int y, Color c, Graphics g){
        buffer.setRGB(0, 0, c.getRGB());
        g.drawImage(buffer, x, y, this);
    }
}

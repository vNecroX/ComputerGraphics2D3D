package figuras;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.lang.Math.abs;
import javax.swing.JFrame;

public final class Figuras extends JFrame{
    Color figureColor = new Color(112, 115, 110);
    
    public class Draw{
        public Draw(){ }
        
        public void BresenhamLine(int x0, int x1, int y0, int y1, int xc, int yc){
            int dy = y1-y0;
            int dx = x1-x0;
            double m = (double) (Double.valueOf(dy)/(dx));

            int x = x0;
            int y;

            int A, B;
            int p;

            System.out.println("dx:" + dx);
            System.out.println("dy:" + dy);
            System.out.println("m:" + m);
            System.out.println("x:" + x);


            if((0<m) && (m<1)){
                y = y0;
                System.out.println("y:" + y);

                A = 2*dy;
                B = 2*dy - 2*dx;

                p = 2*dy - dx;

                if((dx > 0) && (dy > 0)){
                    while(x <= x1){
                        System.out.print("x:" + x + " ");
                        
                        putBresenhamLinePixel((x + xc), (y + yc), figureColor);
                        
                        if(p < 0) p += A;
                        else if(p >= 0){
                            System.out.print("y:" + y);
                            p += B;
                            y++;
                        }
                        x++;

                        System.out.println();
                    }
                }

                if((dx < 0) && (dy < 0)){
                    int auxX = 0;
                    y = abs(dy);
                    System.out.println("y:" + y);

                    while(x >= x1){
                        System.out.print("x:" + x + " ");
                        
                        putBresenhamLinePixel((x+ xc), (y + yc), figureColor);

                        if(p < 0) p += A;
                        else if(p >= 0){
                            p += B;

                            if(auxX == 2){
                                auxX = 0;
                                y++;
                            }

                            System.out.print("y:" + y);
                        }
                        auxX++;
                        x--;

                        System.out.println();
                    }
                }
            }
        }
        
        public void XLine(int x1, int x2, int y, int xc, int yc){ 
            while(x1 < x2 || x1 > x2){
                putLinePixel((x1 + xc), (y + yc), figureColor);

                if(x1 < x2) x1++;
                else if(x1 > x2) x1--;
            }
        }
        
        public void Rectangle(int rectCoords[], int xc, int yc){
            edgeX(rectCoords[0], rectCoords[1], rectCoords[2], xc, yc); 
            edgeY(rectCoords[1], rectCoords[2], rectCoords[3], xc, yc);
            edgeX(rectCoords[1], rectCoords[0], rectCoords[3], xc, yc);
            edgeY(rectCoords[0], rectCoords[3], rectCoords[2], xc, yc);
        }
        
        public void Ellipse(int rx, int ry, int xc, int yc){
            int x, y, p, pX, pY;
            int rx2, ry2, tx2, ty2;
            
            ry2 = ry*ry;
            rx2 = rx*rx;
            
            ty2 = 2*ry2;
            tx2 = 2*rx2;
            
            x = 0;
            y = ry;
            
            System.out.println("ry2 : " + ry2);
            System.out.println("rx2 : " + rx2);
            System.out.println("ty2 : " + ty2);
            System.out.println("tx2 : " + tx2);
            
            p = (int)Math.round(ry2 - rx2*ry + (0.25*rx2));
            pX = 0;
            pY = tx2*y;
            
            while(pX < pY){
                x += 1;
                pX += ty2;
                
                if(p < 0) p += ry2 + pX;
                else{
                    y -= 1;
                    pY -= tx2;
                    p += ry2 + pX - pY;
                }
                
                putEllipsePixel(x, y, xc, yc, figureColor);
                
                System.out.println("00. p : " + p);
                System.out.println("00. x : " + x);
                System.out.println("00. y : " + y);
                System.out.println();
            }
            
            p = (int)Math.round(ry2*(x+0.5)*(x+0.5) + rx2*(y-1)*(y-1) - rx2*ry2);
            pX = 0;
            pY = tx2*y;
            
            while(y > 0){
                y -= 1;
                pY -= tx2;
                
                if(p > 0) p += rx2 - pY;
                else{
                    x += 1;
                    pX += ty2;
                    p += rx2 - pY + pX;
                }
                
                putEllipsePixel(x, y, xc, yc, figureColor);
                
                System.out.println("11. p : " + p);
                System.out.println("11. x : " + x);
                System.out.println("11. y : " + y);
                System.out.println();
            }
        }
        
        public void Circle(int r, int xc, int yc){
            double theta = Math.toRadians(0);
            double xd, yd;
            
            int x = r;
            int y = 0;
            
            System.out.println("x : " + x);
            System.out.println("y : " + y);
            
            while(theta <= 2*Math.PI){
                putCirclePixel((xc + x), (yc + y), figureColor);
                
                theta += Math.toRadians(1);
               
                xd = r*Math.cos(theta);
                x = (int)Math.round(xd);
                
                yd = r*Math.sin(theta);
                y = (int)Math.round(yd);
                
                System.out.println("00. theta : " + theta);
                System.out.println("00. xd : " + xd);
                System.out.println("00. x : " + x);
                System.out.println("00. yd : " + yd);
                System.out.println("00. y : " + y);
                System.out.println();
            }
        }
        
        private void edgeX(int x1, int x2, int y, int xc, int yc){
            while(x1 < x2 || x1 > x2){
                putLinePixel((x1 + xc), (y + yc), figureColor);

                if(x1 < x2) x1++;
                else if(x1 > x2) x1--;
            }
        }
        
        private void edgeY(int x, int y1, int y2, int xc, int yc){
            while(y1 < y2 || y1 > y2){
                putLinePixel((x + xc), (y1 + yc), figureColor);

                if(y1 < y2) y1++;
                else if(y1 > y2) y1--;
            }
        }
    }

    Draw draw = new Draw();
    
    BufferedImage bresenhamLineBuffer;
    BufferedImage lineBuffer;
    BufferedImage rectangleBuffer;
    BufferedImage ellipseBuffer;
    BufferedImage circleBuffer;
    
    public Figuras(){
        setTitle("Figuras");
        setSize(700, 475);
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        bresenhamLineBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        lineBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        rectangleBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        ellipseBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        circleBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }
    
    public static void main(String[] args){ new Figuras(); }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        drawBresenhamLines();
        drawXLines();
        drawRectangles();
        drawEllipses();
        drawCircles();
    }
    
    public void drawBresenhamLines(){
                          //x0, x1, y0, y1, xc, yc
        draw.BresenhamLine(0, 100, 100, 150, 50, 30);
        draw.BresenhamLine(100, 0, 150, 100, 350, 80);
    }
    
    public void drawXLines(){
                  //x0, x1, y, xc, yc
        draw.XLine(100, 0, 120, 200, 32); 
        draw.XLine(0, 100, 100, 500, 52);
    }
    
    public void drawRectangles(){
        int rectCoords[] = new int[10];
        
                  /*[pOneX, pThreeX, pOneY, pThreeY], xc, yc*/
        rectCoords[0] = 20; rectCoords[1] = 150; 
        rectCoords[2] = 30; rectCoords[3] = 90; 
        draw.Rectangle(rectCoords, 199, 249);
        
        rectCoords[0] = 46; rectCoords[1] = 124; 
        rectCoords[2] = 47; rectCoords[3] = 72; 
        draw.Rectangle(rectCoords, 199, 249);
    }
    
    public void drawEllipses(){
                  //rx, ry, xc, yc
        draw.Ellipse(120, 20, 384, 215);
        draw.Ellipse(100, 15, 384, 215);
        draw.Ellipse(80, 10, 384, 215);
        draw.Ellipse(60, 5, 384, 215);
    }
    
    public void drawCircles(){
                  //r, xc, yc
        draw.Circle(13, 128, 312);
        draw.Circle(12, 128, 312);
        draw.Circle(11, 128, 312);
        draw.Circle(10, 128, 312);
    }
    
    public void putBresenhamLinePixel(int x, int y, Color c){
        bresenhamLineBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(bresenhamLineBuffer, x, y, this);
    }
    
    public void putLinePixel(int x, int y, Color c){
        lineBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(lineBuffer, x, y, this);
    }
    
    public void putRectanglePixel(int x, int y, Color c){
        rectangleBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(rectangleBuffer, x, y, this);
    }
    
    public void putCirclePixel(int x, int y, Color c){
        circleBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(circleBuffer, x, y, this);
    }
    
    public void putEllipsePixel(int x, int y, int xc, int yc, Color c){
        ellipseBuffer.setRGB(0, 0, c.getRGB());
        
        this.getGraphics().drawImage(ellipseBuffer, (xc + x) + 100, (yc + y) + 100, this);
        this.getGraphics().drawImage(ellipseBuffer, (xc - x) + 120, (yc + y) + 100, this);
        this.getGraphics().drawImage(ellipseBuffer, (xc + x) + 100, (yc - y) + 100, this);
        this.getGraphics().drawImage(ellipseBuffer, (xc - x) + 120, (yc - y) + 100, this);
    }
}

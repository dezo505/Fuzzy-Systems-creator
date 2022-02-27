package FuzzySystem.FuzzyGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import javax.imageio.ImageIO;

public class MyImage{
    private BufferedImage im=null;
    private WritableRaster raster=null;
    private int width = 0;
    private int height = 0;
    private String type;

    public MyImage(String fileName) throws IOException {	//copy an image from given path		
        im=ImageIO.read(new File(fileName));
        raster = im.getRaster();
        width = raster.getWidth();
        height = raster.getHeight();
        type = "RGB";
    }
    
    public MyImage(int height, int width) {			//create "empty image" with given dimension
    	im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	raster = im.getRaster();
        this.width = raster.getWidth();
        this.height = raster.getHeight();
        type = "RGB";
    }
    
    public MyImage(int height, int width, int[] color) {			//create "empty image" with given dimension and color
    	im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	raster = im.getRaster();
        this.width = raster.getWidth();
        this.height = raster.getHeight();

        for(int x = 0; x < getWidth(); x++) {
        	for(int y = 0; y < getHeight(); y++) {
        		setPixel(x, y, color);
        	}
        }
    }
    
    public MyImage(int height, int width, double[] color) {			//create "empty image" with given dimension and color
    	im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    	raster = im.getRaster();
        this.width = raster.getWidth();
        this.height = raster.getHeight();

        for(int x = 0; x < getWidth(); x++) {
        	for(int y = 0; y < getHeight(); y++) {
        		setPixel(x, y, color);
        	}
        }
    }
    
    public void save(String type, String fileName) throws IOException{    //Save image of given type (JPG, PNG) at given path
        ImageIO.write(im,type,new File(fileName));
    }
    
    public int getHeight(){
    	return height;
    }
    
    public int getWidth(){
    	return width;
    }
    
    public int[] getPixel(int x, int y){
    	try {
    	int[] pixel = new int[3];
    	raster.getPixel(x, y, pixel);
    	return pixel;
    	}catch(ArrayIndexOutOfBoundsException e) {
    		System.out.println("H: " + getHeight() + " W:" + getWidth() + " x:" + x + " y:" + y);
    	}
		return null;
    }
    
    public int[] getPixelHSV(int x, int y) {
    	
    	float[] hsv = new float[3];
    	int[] pixel = getPixel(x,y);
    	Color.RGBtoHSB(pixel[0],pixel[1],pixel[2],hsv);
    	
    	//System.out.println(hsv[0] + " " + hsv[1] + " " + hsv[2]);
    	
    	return new int[]{Math.min(Math.round(hsv[0] * 360),359),Math.min(Math.round(hsv[1] * 100),99),Math.min(Math.round(hsv[2] * 100),99)};
    }
    
    public void setPixelHSV(int x, int y, int[] pixelHSV) {
    	double C = pixelHSV[1] * pixelHSV[2];
    	double M = pixelHSV[2] - C;
    	double X = (1 - Math.abs((pixelHSV[0]/60) - 1)); 
    
    	double h = pixelHSV[0];
    	
    	if(h >= 0 && h <= 60)setPixel(x,y, new double[]{C+M,X+M,M});
    	else if(h <= 120) setPixel(x,y, new double[] {X+M, C+M, M});
    	else if(h <= 180) setPixel(x,y, new double[] {M, C+M, X+M});
    	else if(h <= 240) setPixel(x,y, new double[] {M, X+M, C+M});
    	else if(h <= 300) setPixel(x,y, new double[] {X+M, M,C+M});
    	else if(h <= 360) setPixel(x,y, new double[] {C+M, M, X+M});
    	else  setPixel(x,y, new double[] {M, M, M});
    }
    
    public void setPixel(int x, int y, int[] pixel) {
    	raster.setPixel(x, y, pixel);
    }
    
    public void setPixel(int x, int y, int red, int green, int blue) {
    	raster.setPixel(x, y, new int[] {red, green, blue});
    }
    
    public void setPixel(int x, int y, double[] pixel) {
    	raster.setPixel(x, y, pixel);
    }
    
    public void setPixel(int x, int y, double red, double green, double blue) {
    	raster.setPixel(x, y, new double[] {red, green, blue});
    }
    
    public Image getImage() {
    	return (Image)im;
    }
    
    public BufferedImage getBufferedImage() {
    	return im;
    }
    
    public MyImage copyOf() {
    	MyImage newImage = new MyImage(height, width);
    	
    	for(int y = 0; y < height; y++) {
    		for(int x = 0; x < width; x++) {
    			newImage.setPixel(x, y, getPixel(x,y));
    		}
    	}
    	return newImage;
    }
    
    public String getType() {
    	return type;
    }
}


package FuzzySystem.FuzzyGraphics;

import java.awt.Color;

public class MyGraphics {
	
	public static MyImage toColorRGB(MyImage image, int color){
		if(color < 0 || color > 2) {
			System.out.println("argument " + color + " isn't supported for color, supported arguments: 0 - red, 1 - green,2 - blue");
		}
		
		MyImage newImage = new MyImage(image.getHeight(), image.getWidth());
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				double[] tempPixel = new double[3];
				tempPixel[color] = image.getPixel(x, y)[color];
				newImage.setPixel(x, y, tempPixel);
			}
		}
		return newImage;
	}
	
	public static MyImage toGrayScaleRGB(MyImage image, double[] proportions) {
		MyImage newImage = new MyImage(image.getHeight(), image.getWidth());
		
		if(proportions.length != 3) {
			System.out.println("Proportions should contain exactly 3 values");
			return null;
		}
		
		double tempIntensity;
		double propSum = proportions[0] + proportions[1] + proportions[2];
		
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				int[] tempPixel = image.getPixel(x, y);
				tempIntensity = (tempPixel[0] * proportions[0] + tempPixel[1] * proportions[1] + tempPixel[2] * proportions[2])/propSum;
				tempPixel = new int[]{(int)tempIntensity, (int)tempIntensity, (int)tempIntensity};
				newImage.setPixel(x, y, tempPixel);
			}
		}
		return newImage;
	}
	
	public static MyImage toGrayScaleRGB(MyImage image) {
		double[] proportions = new double[]{1,1,1}; 
		return toGrayScaleRGB(image, proportions);
	}
	
	public static MyImage binarizationRGB(MyImage image, int threshold, int[] color1, int[] color2){
		MyImage newImage = new MyImage(image.getHeight(), image.getWidth());
		MyImage grayImage = MyGraphics.toGrayScaleRGB(image.copyOf());
		
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				int[] tempPixel = ((grayImage.getPixel(x, y))[0] < threshold)? color1: color2;
				newImage.setPixel(x, y, tempPixel);
			}
		}
		
		return newImage;
	}
	
	public static MyImage OtsuAlgorythmRGB(MyImage image) {
		return OtsuAlgorythmRGB(image, new int[] {0,0,0}, new int[] {255,255,255});
	}
	
	public static MyImage OtsuAlgorythmRGB(MyImage image, int[] color1, int[] color2){
		MyImage grayImage = MyGraphics.toGrayScaleRGB(image.copyOf());
		
		int pixelNumber = image.getHeight() * image.getWidth();
		
		int [] histogram = new int[256];
		
		for(int i = 0; i < 256; i++)histogram[i]++;
		
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				histogram[(int)(grayImage.getPixel(x, y))[0]]++;
			}
		}
		
		double[] propability = new double[256];
		
		for(int i = 0; i < 256; i++) {
			propability[i] = (double)histogram[i] / (double)(pixelNumber);
		}
		
		double[] w0 = new double[256];
		double[] w1 = new double[256];
		
		w0[0] = propability[0];
		w1[0] = 1 - propability[0];
		
		for(int i = 1; i < 256; i++) {
			w0[i] = w0[i-1] + propability[i];
			w1[i] = w1[i-1] - propability[i];
		}
		
		double[] u0 = new double[256];
		double[] u1 = new double[256];
		
		for(int k = 0; k < 256; k++){
			for(int i = 0; i < 256; i++) {
				if(i < k) {
					u0[k] += (w0[i] != 0)? i * propability[i] / w0[i]: 0;
				}else {
					u1[k] += (w1[i] != 0)?i * propability[i] / w1[i]: 0;
				}
			}
		}
		
		double max = -1;
		double maxK = -1;

		for(int k = 0; k < 256; k++) {
			System.out.println(w0[k] + " " +  w1[k] + " " + (u1[k] - u0[k]) + " " + (u1[k] - u0[k]) +  w0[k] * w1[k] * (u1[k] - u0[k]) * (u1[k] - u0[k]));
			if(w0[k] * w1[k] * (u1[k] - u0[k]) * (u1[k] - u0[k]) > max) {
				max = w0[k] * w1[k] * (u1[k] - u0[k]) * (u1[k] - u0[k]);
				maxK = k;
			}
		}
		
		return binarizationRGB(grayImage, (int)maxK, color1, color2);
	}
		
	public static MyImage applyFilterRGB(MyImage image, int[][][] filter) {
		if(filter.length == 0 || filter[0].length == 0) {
			System.out.println("Filter can't be empty");
			return null;
		}
		if(filter.length != filter[0].length) {
			System.out.println("Filter should be square");
			return null;
		}
		if(filter.length % 2 != 1) {
			System.out.println("Filter size should be odd");
			return null;
		}
		if(filter[0][0].length != 3) {
			System.out.println("Filter should contain only 3 colors (last dimension of filter)");
			return null;
		}
		
		MyImage newImage = image.copyOf();
		
		int[] weightSum = new int[] {0,0,0};
		
		for(int y = 0; y < filter.length; y++)
		{
			for(int x = 0; x < filter.length; x++) {
				weightSum[0] += filter[y][x][0];
				weightSum[1] += filter[y][x][1];
				weightSum[2] += filter[y][x][2];
			}
		}
		
		weightSum[0] = (weightSum[0] == 0)?1:weightSum[0];
		weightSum[1] = (weightSum[1] == 0)?1:weightSum[1];
		weightSum[2] = (weightSum[2] == 0)?1:weightSum[2];
		
		int filterCenter = (filter.length - 1)/2;
		
		int[] valueSum = new int[] {0,0,0};
		
		for(int yi = filterCenter; yi < image.getHeight() - filterCenter; yi++) {
			for(int xi = filterCenter; xi < image.getWidth() - filterCenter; xi++) {
				valueSum = new int[] {0,0,0};
				for(int yf = 0; yf < filter.length; yf++) {
					for(int xf = 0; xf < filter.length; xf++) {
						for(int i = 0; i < 3; i++) {
							valueSum[i] += filter[yf][xf][i] * image.getPixel(xi - filterCenter + xf, yi - filterCenter + yf)[i];
						}
					}
				}
				int[] tempPixel = new int[3];
				for(int i = 0; i < 3; i++) {
					tempPixel[i] = Math.max(Math.min(valueSum[i]/weightSum[i], 255), 0);
				}
				newImage.setPixel(xi, yi, tempPixel);
			}
		}
		
		return newImage;
	}
	
	public static MyImage applyFilterRGB(MyImage image, int[][] filter) {
		if(filter.length == 0 || filter[0].length == 0) {
			System.out.println("Filter can't be empty");
			return null;
		}
		int[][][] filter3D = new int[filter.length][filter[0].length][3];
		for(int y = 0; y < filter.length; y++) {
			for(int x = 0; x < filter[0].length; x++) {
				for(int i = 0; i < 3; i++){
					filter3D[y][x][i] = filter[y][x];
				}
			}
		}
		return applyFilterRGB(image, filter3D);
	}
	
	public static MyImage negativeRGB(MyImage image) {
		MyImage newImage = new MyImage(image.getHeight(), image.getWidth());
		
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				double[] tempPixel = new double[3];
				tempPixel[0] = 255 - image.getPixel(x, y)[0];
				tempPixel[1] = 255 - image.getPixel(x, y)[1];
				tempPixel[2] = 255 - image.getPixel(x, y)[2];
				newImage.setPixel(x, y, tempPixel);
			}
		}
		
		return newImage;
	}
	
	public static MyImage mirrorRGB(MyImage image, boolean horizontally){
		MyImage newImage = new MyImage(image.getHeight(), image.getWidth());
		
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				newImage.setPixel(x, y, image.getPixel((!horizontally)?x:image.getWidth()-1-x, (horizontally)?y:image.getHeight()-1-y));
			}
		}
		
		return newImage;
	}
	
	public static MyImage rotateRGB(MyImage image) {
		MyImage newImage = new MyImage(image.getWidth(), image.getHeight());
		
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				newImage.setPixel(y, image.getWidth() - x - 1, image.getPixel(x,y));
			}
		}
		
		return newImage;
	}
	
	public static MyImage rotateRGB(MyImage image, int rotationsNumber) {
		MyImage newImage = image.copyOf();
		for(int i = 0; i < rotationsNumber; i++) {
			newImage = rotateRGB(newImage);
		}
		return newImage;
	}
	
	public static void drawLine(MyImage image, int[] color, int x1, int y1, int x2, int y2, int width){
		
		double length = Math.sqrt((x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2));
		
			double cx = Math.min(image.getWidth(), Math.max(x1, 0));
			double cy = Math.min(image.getHeight(), Math.max(y1, 0));
				
			double vectorX = (double)(x1 - x2) / length;
			double vectorY = (double)(y1 - y2) / length;
				
				
 			while((vectorX * vectorY == 0)?((vectorY == 0)?((vectorX == 1)?cx >= x2:cx <= x2):((vectorY == 1)?cy >= y2:cy <= y2)):(((vectorX < 0)?cx <= x2:cx >= x2)||((vectorY < 0)?cy <= y2:cy >= y2))) {
				cx -= vectorX;
				cy -= vectorY;
				MyGraphics.drawSquare(image, color, (int)Math.round(cx), (int)Math.round(cy), width);
			}

	}
	
	public static void drawSquare(MyImage image, int[] color, int x, int y, int width) {
		for(int i = Math.max(x-width , 0); i <= Math.min(image.getWidth() - 1, x + width); i++) {
			for(int j = Math.max(y-width , 0); j <= Math.min(image.getHeight() - 1, y + width); j++) {
				image.setPixel(i,  j, color);
			}
		}
	}
	
	public static void drawSquare(MyImage image, Color color, int x, int y, int width) {
		for(int i = Math.max(x-width , 0); i <= Math.min(image.getWidth() - 1, x + width); i++) {
			for(int j = Math.max(y-width , 0); j <= Math.min(image.getHeight() - 1, y + width); j++) {
				image.setPixel(i,  j, new int[] {color.getRed(), color.getGreen(), color.getBlue()});
			}
		}
		
	}
}

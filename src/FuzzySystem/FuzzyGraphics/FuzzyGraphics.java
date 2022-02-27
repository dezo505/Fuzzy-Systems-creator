package FuzzySystem.FuzzyGraphics;

import FuzzySystem.FuzzySystemCore.FuzzySets.FuzzySet;
import FuzzySystem.FuzzySystemCore.VarFuzzification;

public class FuzzyGraphics {
	public static MyImage variableFuzzificationImage(VarFuzzification vf, int width, int height, int[][] colors){
		MyImage resultImage = new MyImage(height, width, new int[] {169,169,169});
		
		int a = 3;
		int b = 3;
		int c = 1;
		
		int horizontalLines = 9;
		int verticalLines = 9;
		
		for(int i = 1; i <= horizontalLines; i++)
			MyGraphics.drawLine(resultImage, new int[] {128,128,128},  0, height -  (height- 10*b) * i/horizontalLines, width, height -  (height- 10*b) * i/horizontalLines, 0);
		
		for(int i = 1; i <= verticalLines; i++) 
			MyGraphics.drawLine(resultImage, new int[] {128,128,128},  width * i/(verticalLines + 1), 0, width * i/(verticalLines + 1), height, 0);

		
		if(vf.getSetsNumber() == 0) {
			MyGraphics.drawLine(resultImage, new int[] {0,0,0},  width - 1, height - 1, 1, height - 1, 1);
			MyGraphics.drawLine(resultImage, new int[] {0,0,0}, 1, 1, 1, height - 1, 1);
			return resultImage;
		}
		
		int acc = 10;
			
		double values[][] = new double[vf.getSetsNumber()][(width - 6) * acc];
		
		double min = vf.getSet(0).getMinX();
		double max = vf.getSet(0).getMaxX();
		double delta = (max - min) / (width - 2*a) / acc;
		
		for(int s = 0; s < vf.getSetsNumber(); s++) {
			for(int j = 0; j < values[s].length; j++) {
				values[s][j] = vf.getSet(s).calculateValue(min + j * delta);
			}
		}
		
		for(int s = 0; s < vf.getSetsNumber(); s++) {
			for(int i = 0; i < width - 2*a; i++) {
				for(int x = 0; x < acc; x++) {
					MyGraphics.drawSquare(resultImage, vf.getSet(s).getColor(), i + 3, height -(int)Math.round((height - 10*b) * values[s][i*acc + x]), c);
				}
			}
		}
		
		MyGraphics.drawLine(resultImage, new int[] {0,0,0},  width - 1, height - 1, 1, height - 1, 1);
		MyGraphics.drawLine(resultImage, new int[] {0,0,0}, 1, 1, 1, height - 1, 1);
		return resultImage;
	}
	
	public static MyImage fuzzySetImage(FuzzySet fs, int width, int height, int[] color){
		
		VarFuzzification vf = new VarFuzzification(fs.getMinX(), fs.getMinX(), "-");
		vf.addSet(fs);
		
		int[][] colors = new int[1][3];
		colors[0] = color;
		
		MyImage resultImage = variableFuzzificationImage(vf, width, height, colors);
		
		return resultImage;
	}
}

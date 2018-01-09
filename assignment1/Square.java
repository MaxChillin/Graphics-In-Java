package assignment1;

import java.awt.Polygon;

public class Square extends Polygon {
	private static final long serialVersionUID = 1L;
	
	public Square(){
		
	}// end of constructor
	

	public Square(int radius){
		super.npoints = 4;
		super.xpoints = new int[4];
		super.ypoints = new int[4];
		
		for(int i = 0; i < 4; i++){
			super.xpoints[i] = (int) (radius + radius*Math.cos(Math.PI/4 + (i*Math.PI/2)));
			super.ypoints[i] = (int) (radius + radius*Math.sin(Math.PI/4 + (i*Math.PI/2)));
		}
	}// end of constructor

	
	public Square(int centerX, int centerY, int radius){
		super.npoints = 4;
		super.xpoints = new int[4];
		super.ypoints = new int[4];
		
		for(int i = 0; i < 4; i++){
			super.xpoints[i] = (int) (centerX + radius*Math.cos(Math.PI/4 + (i*Math.PI/2)));
			super.ypoints[i] = (int) (centerY + radius*Math.sin(Math.PI/4 + (i*Math.PI/2)));
		}
		
	}// end of constructor
	
	
	public Square(int centerX, int centerY, int radius, double rotation){
		super.npoints = 4;
		super.xpoints = new int[4];
		super.ypoints = new int[4];
		
		for(int i = 0; i < 4; i++){
			super.xpoints[i] = (int) (centerX + radius*Math.cos(Math.PI/4 + (i*Math.PI/2) + rotation));
			super.ypoints[i] = (int) (centerY + radius*Math.sin(Math.PI/4 + (i*Math.PI/2) + rotation));
		}
		
	}// end of constructor
	
	
}// end of Square class
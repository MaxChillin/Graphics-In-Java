package assignment1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

public class Hexagon extends Polygon {
	private static final long serialVersionUID = 1L;
	
	public Hexagon(){
		
	}
	
	public Hexagon(int radius){
		super.npoints = 6;
		super.xpoints = new int[6];
		super.ypoints = new int[6];
		
		for(int i = 0; i < 6; i++){
			super.xpoints[i] = (int) (radius + radius*Math.cos(i*(Math.PI/3)));
			super.ypoints[i] = (int) (radius + radius*Math.sin(i*(Math.PI/3)));
		}
	}// end of constructor
	
	public Hexagon(int centerX, int centerY, int radius) {
		super.npoints = 6;
		super.xpoints = new int[6];
		super.ypoints = new int[6];
		
		for(int i = 0; i < 6; i++){
			super.xpoints[i] = (int) (centerX + radius*Math.cos(i*(Math.PI/3)));
			super.ypoints[i] = (int) (centerY + radius*Math.sin(i*(Math.PI/3)));
		}
		
		
	}// end of constructor

	void draw(Graphics2D g) {
		g.setColor(Color.black);
		g.drawPolygon(this);
	}

	
//	public Polygon honeycomb(int windowWidth, int windowHeight){
//		
//	}
	
}// end of Hexagon class

package finalProject.PongBrickBreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Brick extends Rectangle2D {
	Color color;
	Random random;
	Area area;
	Point point;
	private int x, y, width, height, red = 50, green = 50, blue = 50;

	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Brick(int x, int y, int width, int height) {
		random = new Random();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		red = random.nextInt(170);
		green = random.nextInt(170);
		blue = random.nextInt(170);
		color = new Color(red, green, blue);
		area = new Area(this);
	}// end of constructor

	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRoundRect(this.x, this.y, this.width, this.height, 30, 90);
	}

	public int getRightSide() {
		return x + width;
	}

	@Override
	public Rectangle2D createIntersection(Rectangle2D arg0) {
		return null;
	}

	@Override
	public Rectangle2D createUnion(Rectangle2D arg0) {
		return null;
	}

	@Override
	public int outcode(double arg0, double arg1) {
		return 0;
	}

	@Override
	public void setRect(double arg0, double arg1, double arg2, double arg3) {

	}

	@Override
	public double getHeight() {
		return height;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	public String toString() {
		return "x " + x + " y " + y;
	}

}

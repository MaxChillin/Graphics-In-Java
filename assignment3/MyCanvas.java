package assignment3;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

@SuppressWarnings("serial")
public class MyCanvas extends Canvas  {
	int maxX, maxY, distance;
	float pixelWidth, pixelHeight, rWidth = 10.0F, rHeight = 7.5F, xP = -1, yP;
	double theta, PI = Math.PI;
	boolean first = true, second = false;
	Dimension dimension;
	Graphics2D g2d;
	Point A, B;
	int Red, Green, Blue;
	Random random = new Random();


	public MyCanvas() {
		dimension = getSize();
		maxX = (int) dimension.getWidth();
		maxY = (int) dimension.getHeight();
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				if(first){
					A = new Point();
					A.x = evt.getX();
					A.y = evt.getY();
				}else{
					B = new Point();
					B.x	= evt.getX();
					B.y = evt.getY();
					second = !second;
					repaint();
				}// end of if/else

				first = !first;
			}// end of mousePressed method
		});// end of  addMouseListener method
	}// end of constructor
	
	
	public double dist(Point A, Point B) {
		return Math.round(Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y)));
	}// end of dist method


	public void calculatePoints(Graphics g, Point A, Point B){
		//This is the break out of the recursive cycle
		if(Math.abs(B.x - A.x) <= 10 && Math.abs(B.y - A.y) <= 10) {
			return;
		}
		
		Point C = new Point();
		Point D = new Point();
		Point E = new Point();
		distance = (int) dist(A, B);
		theta = Math.atan2(A.y - B.y, A.x - B.x);
		draw(g, A, B);
		C.x = (int) (B.x + distance*(Math.cos(theta + PI/2)));
		C.y = (int) (B.y + distance*(Math.sin(theta + PI/2)));
		draw(g, B, C);
		theta = Math.atan2(B.y - C.y, B.x - C.x);
		D.x = (int) (C.x + distance*(Math.cos(theta + PI/2)));
		D.y = (int) (C.y + distance*(Math.sin(theta + PI/2)));
		draw(g, C, D);
		int tmp = (int) (distance/(2*Math.cos(3*PI/4)));
		E.x = (int) (C.x + tmp*(Math.cos(theta - PI/4)));
		E.y = (int) (C.y + tmp*(Math.sin(theta - PI/4)));
		draw(g, C, E);
		draw(g, D, E);
		draw(g, D, A);
 		
		calculatePoints(g, new Point(E), new Point(C));
		calculatePoints(g, new Point(D), new Point(E));
	}// end of calculatePoints method
	
	public void draw(Graphics g, Point A, Point B) {
		Red = random.nextInt(170);
		Green = random.nextInt(170);
		Blue = random.nextInt(170);
		g.setColor(new Color(Red, Green, Blue));
		g.drawLine(A.x, A.y, B.x, B.y);
		
	}// end of draw method

	public void paint(Graphics g) {
		g2d = (Graphics2D) g;
		g2d.setColor(Color.GREEN);
		g.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		String tip = "Use the mouse to choose two points by left-clicking anywhere on the canvas!";
		if(first && !second) {
			g2d.drawString(tip, (int) this.getWidth()/2 - tip.length()*7, (int) this.getHeight()/2);
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(3));
		if(second){
			second = !second;
			calculatePoints(g, new Point(A), new Point(B));
		}// end of if(second) statement

	}// end of paint method

}// end of myCanvas class


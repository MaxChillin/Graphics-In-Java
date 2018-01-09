package assignment2;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class CounterClockwiseSquares extends Frame {
	public static void main(String[] args) {
		
		new CounterClockwiseSquares();
	}

	CounterClockwiseSquares() {
		super("Counter Clockwise Squares");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(1280, 720);
		add("Center", new CvAnisotr());
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

@SuppressWarnings("serial")
class CvAnisotr extends Canvas {
	int maxX, maxY, firstX, firstY, secondX, secondY, thirdX, thirdY, forthX, forthY, distance;
	float pixelWidth, pixelHeight, rWidth = 10.0F, rHeight = 7.5F, xP = -1, yP;
	double theta, PI = Math.PI;
	boolean first = true, second = false;
	Dimension dimension;
	Graphics2D g2d;

	CvAnisotr() {
		dimension = getSize();
		maxX = (int) dimension.getWidth();
		maxY = (int) dimension.getHeight();
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
					if(first){
						firstX = evt.getX();
						firstY = evt.getY();
					}else{
						secondX = evt.getX();
						secondY = evt.getY();
						second = !second;
					}// end of if/else
					
				first = !first;
				calculatePoints();
				repaint();
			}// end of mousePressed method
		});// end of  addMouseListener method
	}// end of constructor

	void initgr() {
		maxX = dimension.width - 1;
		maxY = dimension.height - 1;
		pixelWidth = rWidth / maxX;
		pixelHeight = rHeight / maxY;
	}

	void calculatePoints(){
		distance = (int) Math.sqrt(Math.pow(secondX - firstX, 2) + Math.pow(secondY - firstY, 2));
		theta = Math.atan2(firstY - secondY, firstX - secondX);
		System.out.println("Theta" + theta);
		System.out.println("Dist" + distance);
		thirdX = (int) (secondX + distance*(Math.cos(theta + PI/2)));
		thirdY = (int) (secondY + distance*(Math.sin(theta + PI/2)));
		theta = Math.atan2(secondY - thirdY, secondX - thirdX);
		forthX = (int) (thirdX + distance*(Math.cos(theta + PI/2)));
		forthY = (int) (thirdY + distance*(Math.sin(theta + PI/2)));

	}
	
	public void buildTree(Graphics g) {
		g.setColor(Color.YELLOW);
		g.drawLine(firstX, firstY, secondX, secondY);
		g.drawLine(secondX, secondY, thirdX, thirdY);
		g.drawLine(thirdX, thirdY, forthX, forthY);
		g.drawLine(forthX, forthY, firstX, firstY);
		g.setColor(Color.BLUE);
		g.drawString("( A )", firstX+10, firstY-10);
		g.drawString("( B )", secondX+10, secondY-10);
		g.drawString("( C )", thirdX+10, thirdY-10);
		g.drawString("( D )", forthX+10, forthY-10);
		
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1280, 720);
		initgr();
		g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(3));
		if(second){
			second = !second;
			buildTree(g);
		}// end of if(second) statement
	
	}// end of paint method
	
}// end of CvAnisotr class


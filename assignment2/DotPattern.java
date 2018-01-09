package assignment2;
// Bresenham.java: Bresenham algorithms for lines and circles

//                 demonstrated by using superpixels.

// Copied from Appendix F (solution to Exercise 4.3) of
//    Ammeraal, L. and K. Zhang (2007). Computer Graphics for Java Programmers, 2nd Edition,
//       Chichester: John Wiley.
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class DotPattern extends Frame {
	public static void main(String[] args) {
		new DotPattern();
	}

	DotPattern() {
		super("Bresenham");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(1280, 720);
		setBackground(Color.BLACK);
		add("Center", new CvBresenham());
		setVisible(true);
	}
}

class CvBresenham extends Canvas {
	float rWidth = 10.0F, rHeight = 7.5F, pixelSize;
	int centerX, centerY, dGrid = 10, maxX, maxY, red, green, blue;
	File file;
	int numberOfLinesInFile = 0;
	Scanner fileScanner;
	String tokens[];
	Random random = new Random();

	void initgr() {
		Dimension d;
		d = getSize();
		file = new File("src/assignment2/input.txt");
		/**
		 * This is the make sure the file exists and if not then
		 * terminate the program.
		 */
		if (!file.exists()) {
			System.out.println("file does not exists");
			System.exit(2);
		}
		maxX = d.width - 1;
		maxY = d.height - 1;
		pixelSize = Math.max(rWidth / maxX, rHeight / maxY);
		centerX = maxX / 2;
		centerY = maxY / 2;
	}// end of initgr method

	int iX(float x) {
		return Math.round(centerX + x / pixelSize);
	}// end of iX method

	int iY(float y) {
		return Math.round(centerY - y / pixelSize);
	}// end of iY method

	void putPixel(Graphics g, int x, int y) {
		int x1 = x * dGrid, y1 = y * dGrid, h = dGrid / 2;
		red = random.nextInt(200);
		green = random.nextInt(200);
		blue = random.nextInt(200);
		g.setColor(new Color(red + 55, green + 55, blue + 55));
		g.drawOval(x1 - h, y1 - h, dGrid, dGrid);
	}// end of putPixel method

	void drawLine(Graphics g, int xP, int yP, int xQ, int yQ) {
		int x = xP, y = yP, D = 0, HX = xQ - xP, HY = yQ - yP, c, M, xInc = 1, yInc = 1;
		
		if (HX < 0) {
			xInc = -1;
			HX = -HX;
		}// end of if(HX < 0) statement
		
		if (HY < 0) {
			yInc = -1;
			HY = -HY;
		}// end of if(HY < 0) statement
		
		if (HY <= HX) {
			c = 2 * HX;
			M = 2 * HY;
			while(true){
				putPixel(g, x, y);
				if (x == xQ){
					break;
				}
				x += xInc;
				D += M;
				if (D > HX) {
					y += yInc;
					D -= c;
				}// end of if(D > HX) statement
			}// end of while(true) loop
		} else {
			c = 2 * HY;
			M = 2 * HX;
			while(true) {
				putPixel(g, x, y);
				if (y == yQ){
					break;
				}// end of if(y == yQ) statement
				y += yInc;
				D += M;
				if (D > HY) {
					x += xInc;
					D -= c;
				}// end of if(D > HY) statement
			}// end of while(true) loop
		}// end of if/else statement
	}// end of drawLine method

	void drawCircle(Graphics g, int xC, int yC, int r) {
		int x = 0, y = r, u = 1, v = 2 * r - 1, E = 0;
		while (x < y) {
			putPixel(g, xC + x, yC + y); // NNE
			putPixel(g, xC + y, yC - x); // ESE
			putPixel(g, xC - x, yC - y); // SSW
			putPixel(g, xC - y, yC + x); // WNW
			x++;
			E += u;
			u += 2;
			if (v < 2 * E) {
				y--;
				E -= v;
				v -= 2;
			}
			
			if (x > y){
				break;
			}
			putPixel(g, xC + y, yC + x); // ENE
			putPixel(g, xC + x, yC - y); // SSE
			putPixel(g, xC - y, yC - x); // WSW
			putPixel(g, xC - x, yC + y); // NNW
		}// end of while loop
	}// end of drawCircle method

	void showGrid(Graphics g) {
		for (int x = dGrid; x <= maxX; x += dGrid)
			for (int y = dGrid; y <= maxY; y += dGrid){
				red = random.nextInt(200);
				green = random.nextInt(200);
				blue = random.nextInt(200);
				g.setColor(new Color(red + 55, green + 55, blue + 55));
				g.drawLine(x, y, x, y);
			}
	}// end of showGrid method

	public void paint(Graphics g) {
//		g.setColor(Color.GREEN);
		initgr();
		showGrid(g);
		
		/**
		 * Try to read the file. If an error occurs then catch the
		 * exception and continue.
		 */
		try {
			fileScanner = new Scanner(file);
			numberOfLinesInFile = Integer.valueOf(fileScanner.nextLine());
			for(int i = 0; i < numberOfLinesInFile - 1; i++){
				tokens = fileScanner.nextLine().split(" ");
				drawLine(g, Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]), Integer.valueOf(tokens[3]));
			}// end of for loop
			tokens = fileScanner.nextLine().split(" ");
			drawCircle(g, Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// end of try/catch statement
		
	}// end of paint method

}// end of CvBresenham class

package assignment3;

public class Point {
	
	int x, y;
	
	public Point() {
		this.x = 0;
		this.y = 0;
	}

	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}

}

package finalProject.PongBrickBreaker;

public class Player {
	
	Paddle myPaddle;
	private int lives, points;

	
	/**
	 * 
	 */
	public void init() {
		lives = 3;
		points = 0;
	}// end of init method

	
	/**
	 * 
	 */
	public void point() {
		points++;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getPoints() {
		return points;
	}
	
	
	/**
	 * 
	 */
	public void loseLife() {
		lives--;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public int getLives() {
		return lives;
	}

}// end of Player class

package finalProject.PongBrickBreaker;

public class Collision {
	
	/**
	 * 
	 * @param ball which is an object of the Ball class
	 * @return
	 */
	public static boolean check(Ball ball) {
		boolean result = true;
		if (ball.getX() >= 0 && ball.getX() + ball.getRadius() <= Game.WIDTH && ball.getY() >= 0) {
			result = true;
		}
		return result;
	}// end of collision(Ball b) method

	/**
	 * 
	 * @param b which is an object of the Ball class
	 * @param p which is an object of the Paddle class
	 * @return boolean
	 */
	public static boolean check(Ball b, Paddle p) {
		boolean result = false;
		if (p.getY() < (Game.HEIGHT >> 1)) { // this is for player two paddle
			if(Game.multiplayer) {
				if (b.getX() + b.getRadius() >= p.getX() - b.getRadius() && b.getX() <= p.getRightSide() + b.getRadius() && b.getY() <= p.getY() + p.getHeight()*2) {
					result = true;
				}
			}// end of if(Game.multiplayer) statement
		} else { // this is for player one paddle
			if (b.getX() + b.getRadius() >= p.getX() - b.getRadius() && b.getX() <= p.getRightSide() + b.getRadius() && b.getY() + b.getRadius() >= p.getY()) {
				result = true;
			}
		}
		return result;
	}// end of collision(Ball b, Paddle p) method

	/**
	 * 
	 * @param ball which is an object of the Ball class
	 * @param brick which is an object of the Brick class
	 * @return boolean
	 */
	public static boolean check(Ball ball, Brick brick) {
		boolean result = false;
		if (brick.getY() < (Game.HEIGHT >> 1)) { // this is for player one bricks
			if ((int) ball.getX() + ball.getRadius() >= (int) brick.getX() - ball.getRadius() && (int) ball.getX() <= brick.getRightSide() + ball.getRadius() && ball.getY() <= brick.getY()) {
				result = true;
			}
		} else { // this is for player two bricks
			if(Game.multiplayer) {
				if ((int) ball.getX() + ball.getRadius() >= (int) brick.getX() - ball.getRadius() && (int) ball.getX() <= brick.getRightSide() + ball.getRadius() && ball.getY() >= brick.getY()) {
					result = true;
				}
			}// end of if(Game.multiplayer) statement
		}
		return result;
	}// end of collision(Ball ball, Brick brick) method

}// end of Collision class

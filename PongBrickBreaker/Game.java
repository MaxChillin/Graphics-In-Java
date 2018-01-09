package finalProject.PongBrickBreaker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 720;
	public static final int WIDTH = HEIGHT * 16 / 9;
	public static float SCALER = 1.0f;
	Random random;
	JPanel panel;
	JOptionPane menu;
	String[] options = { "1 Player", "2 Players", "Quit" };
	String ballBop = "ball.wav";
	String goal = "goal.wav";
	String boo = "boo.wav";
	String loser = "loser.wav";
	ImageIcon icon = new ImageIcon("src/finalProject/PongBrickBreaker/menuIcon.jpg");
	private Thread thread;
	static boolean isRunning = false, multiplayer = false, gameOver = true, playerOneWins = false,
			playerTwoWins = false;
	static boolean Pause = false, matchInProgress = false, playerOneServing = true, scored = false;
	private int FPS = 60, bonusTextTimer = 0;
	private long targetTime = 1000 / FPS;

	boolean served = false;

	int red = 0, green = 0, blue = 0;

	private Player player1;
	private Player player2;
	private Paddle playerOnePaddle;
	private Paddle playerTwoPaddle;
	private Ball ball, bonusBall;
	private ArrayList<Brick> playerOneBricks = new ArrayList<>();
	private ArrayList<Brick> playerTwoBricks = new ArrayList<>();
	private ArrayList<Ball> bonusBalls = new ArrayList<>();
	private ArrayList<String> songList = new ArrayList<>();

	public static void main(String[] args) {
		JFrame frame = new JFrame("Pong Breaker");
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(new Game(), BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public Game() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		addKeyListener(this);
		initialize();
		start();
	}

	public void initialize() {
		multiplayer = false;
		gameOver = true;
		playerOneWins = false;
		playerTwoWins = false;
		matchInProgress = false;
		playerOneServing = true;
		scored = false;
		if (gameOver) {
			int n;
			n = JOptionPane.showOptionDialog(panel,
					"Welcome to Pong Breaker!"
							+ "\nYou can play either 1 or 2 players."
							+ "\n\n\tMake a Selection!",
							"Pong Breaker!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, icon,
							options, options[0]);

			switch (n) {
			case 0:
				gameOver = false;
				multiplayer = false;
				break;
			case 1:
				gameOver = false;
				multiplayer = true;
				break;
			case 2:
			case -1:
				System.exit(0);
				break;
			}// end of switch

		} // end of if(gameOver) statement

		System.out.println("Initializing...");
		songList.add("Adventure_Club_Do_I_See_Color.wav");
		songList.add("Mord_Fustang_Lick_The_Rainbow.wav");
		songList.add("Polyphia_Crush.wav");

		if (Audio.track1 != null) {
			Audio.track1.stop();
		}

		random = new Random();
		int height = HEIGHT / 15, width = WIDTH / 17, offset = HEIGHT / 41, topBufferForText = 20;
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col * width < WIDTH - offset; col++) {
				playerOneBricks.add(new Brick((col * width) + offset, (row * height) + offset + topBufferForText,
						width - offset, height - offset));
				playerTwoBricks.add(new Brick((col * width) + offset, HEIGHT - (row * height) - (offset * 3),
						width - offset, height - offset));
			} // end of col for loop
		} // end of row for loop

		if (multiplayer) {
			playerOnePaddle = new Paddle(WIDTH * 6 / 13, HEIGHT * 33 / 41, WIDTH / 11, 30);
		} else {
			playerOnePaddle = new Paddle(WIDTH * 6 / 13, HEIGHT * 39 / 41, WIDTH / 11, 30);
		} // end of if/else statement

		playerTwoPaddle = new Paddle(WIDTH * 6 / 13, HEIGHT * 7 / 41, WIDTH / 11, 30);

		player1 = new Player();
		player2 = new Player();
		player1.init();
		player2.init();
		playerOneServe();

		Audio.playMusic(songList.get(random.nextInt(3)));
	}// end of initialize method

	/**
	 * 
	 */
	public void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}// end of start method

	/**
	 * This causes the game to pause for 2 seconds
	 */
	public void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end of sleep method

	/**
	 * 
	 */
	public void run() {
		long start, elapsed, wait;

		while (isRunning) {

			update();
			repaint();

			if (scored) {
				sleep(2000);
				scored = false;
			} // end of if(scored) statement

			start = System.nanoTime();
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;

			if (wait <= 0) {
				wait = 5;
			}

			sleep(wait);

		} // end of while(isRunning) loop
	}// end of run method

	/**
	 * 
	 */
	public void playerOneServe() {
		ball = null;
		ball = new Ball((int) playerOnePaddle.getCenterX() + HEIGHT / 41, (int) playerOnePaddle.getY(), HEIGHT / 41);
		bonusBall = new Ball(ball);
		ball.setAllBooleansToFalse();
		playerOneServing = true;
	}// end of playerOneServe method

	/**
	 * 
	 */
	public void playerTwoServe() {
		ball = null;
		ball = new Ball((int) playerTwoPaddle.getCenterX() + HEIGHT / 41,
				(int) (playerTwoPaddle.getY() + playerTwoPaddle.getHeight() * 4), HEIGHT / 41);
		bonusBall = new Ball(ball);
		ball.setAllBooleansToFalse();
		playerOneServing = false;
	}// end of playerTwoServe method

	/**
	 * 
	 */
	public void gameOver() {
		gameOver = true;
		Audio.track1.stop();
		if (playerOneWins) {

		} else if (playerTwoWins) {

		} else {

		}
		sleep(2000);
		initialize();
	}// end of the gameOver method

	/**
	 * This controls the animation for each item
	 */
	public void update() {
		if (multiplayer) {
			if (player1.getLives() == 0) {
				playerTwoWins = true;
				gameOver();
			}
			if (player2.getLives() == 0) {
				playerOneWins = true;
				gameOver();
			}
		} else {
			if (player1.getLives() == 0) {
				gameOver();
			}
		}

		if (!served) {
			/**********************************************************************
			 * This if/else statement is to make sure the ball tracks with the paddle before
			 * it is served.
			 */
			if (playerOneServing) {
				if (playerOnePaddle.isLeftCollision()) {
					ball.setLeft(false);
				} else {
					ball.setLeft(playerOnePaddle.isLeft());
				}

				if (playerOnePaddle.isRightCollision()) {
					ball.setRight(false);
				} else {
					ball.setRight(playerOnePaddle.isRight());
				}
			} else {
				if (playerTwoPaddle.isLeftCollision()) {
					ball.setLeft(false);
				} else {
					ball.setLeft(playerTwoPaddle.isLeft());
				}

				if (playerTwoPaddle.isRightCollision()) {
					ball.setRight(false);
				} else {
					ball.setRight(playerTwoPaddle.isRight());
				}
			} // ********************************************************************

			ball.setVelocity(playerOnePaddle.velocity);
			ball.update();

		} else {
			if(!multiplayer) {
				if(playerOneBricks.isEmpty()) {
					playerOneWins = true;
					gameOver = true;
				}
			}
			/********
			 * Ball
			 ********/
			if (ball.getY() <= 0) {
				if (multiplayer) {
					Audio.playSound(goal);
					scored = true;
					served = false;
					ball.setLeft(false);
					ball.setRight(false);
					ball.setUp(false);
					ball.setDown(false);
					bonusBalls.clear();
					player2.loseLife();
					if(player2.getLives() == 0) {
						playerOneWins = true;
						gameOver = true;
					}
					playerOneServe();
				} else {
					ball.setUp(false);
					ball.setDown(true);
				}
			} else if (ball.getY() >= HEIGHT) {
				scored = true;
				served = false;
				ball.setLeft(false);
				ball.setRight(false);
				ball.setUp(false);
				ball.setDown(false);
				bonusBalls.clear();
				player1.loseLife();
				if (multiplayer) {
					Audio.playSound(goal);
					if(player1.getLives() == 0) {
						playerTwoWins = true;
						gameOver = true;
					}
					playerTwoServe();
				} else {
					if(player1.getLives() == 0) {
						Audio.track1.stop();
						Audio.playSound(loser);
						gameOver = true;
					}else {
						Audio.playSound(boo);
						playerOneServe();

					}
				}
			}

			if (ball.getX() <= 0) { // this keeps the ball from going too LEFT off the screen
				Audio.playSound(ballBop);
				ball.setLeft(false);
				ball.setRight(true);

			} else if (ball.getX() >= WIDTH) { // this keeps the ball from going too RIGHT off the screen
				Audio.playSound(ballBop);
				ball.setLeft(true);
				ball.setRight(false);
			}

			/*************
			 * Bonus Balls
			 *************/
			/**
			 * This try/catch is needed because when multiple balls are created and then
			 * removed in a different order than they were created it throws and index out
			 * of bounds error
			 */
			try {
				if (!bonusBalls.isEmpty()) {
					for (int i = 0; i < bonusBalls.size(); i++) {
						if (bonusBalls.get(i).getX() <= 0) { // this keeps the ball from going too LEFT off the screen
							Audio.playSound(ballBop);
							bonusBalls.get(i).setLeft(false);
							bonusBalls.get(i).setRight(true);

						} else if (bonusBalls.get(i).getX() >= WIDTH) { // this keeps the ball from going too RIGHT off
							// the screen
							Audio.playSound(ballBop);
							bonusBalls.get(i).setLeft(true);
							bonusBalls.get(i).setRight(false);
						} // end of if/else

						if (bonusBalls.get(i).getY() <= 0 || bonusBalls.get(i).getY() >= HEIGHT) {
							bonusBalls.remove(i);
						}

						/**
						 * PLAYER ONE PADDLE
						 */
						if (Collision.check(bonusBalls.get(i), playerOnePaddle)) {
							Audio.playSound(ballBop);
							bonusBalls.get(i).setUp(true);
							bonusBalls.get(i).setDown(false);
							if (playerOnePaddle.isLeft() == false && playerOnePaddle.isRight() == false) {

							} else {
								bonusBalls.get(i).setLeft(playerOnePaddle.isLeft());
								bonusBalls.get(i).setRight(playerOnePaddle.isRight());
							} // end of if/else statement
						} // end of if(collision(bonusBalls.get(i), playerOnePaddle))

						/**
						 * PLAYER TWO PADDLE
						 */
						if (Collision.check(bonusBalls.get(i), playerTwoPaddle)) {
							Audio.playSound(ballBop);
							bonusBalls.get(i).setUp(false);
							bonusBalls.get(i).setDown(true);
							if (playerTwoPaddle.isLeft() == false && playerTwoPaddle.isRight() == false) {

							} else {
								bonusBalls.get(i).setLeft(playerTwoPaddle.isLeft());
								bonusBalls.get(i).setRight(playerTwoPaddle.isRight());
							} // end of if/else statement
						} // end of if(collision(bonusBalls.get(i), playerTwoPaddle))

						for (int j = 0; j < playerOneBricks.size(); j++) {
							if (Collision.check(bonusBalls.get(i), playerOneBricks.get(j))) {
								Audio.playSound(ballBop);
								bonusBalls.get(i).setUp(false);
								bonusBalls.get(i).setDown(true);
								player1.point();
								player1.point();
								playerOneBricks.remove(j);
							} // end of if(collision(bonusBalls.get(i), playerOneBricks.get(j)))
						} // end of for loop

						for (int j = 0; j < playerTwoBricks.size(); j++) {
							if (Collision.check(bonusBalls.get(i), playerTwoBricks.get(j))) {
								Audio.playSound(ballBop);
								bonusBalls.get(i).setUp(true);
								bonusBalls.get(i).setDown(false);
								player2.point();
								player2.point();
								playerTwoBricks.remove(j);
							} // end of if(collision(bonusBalls.get(i), playerTwoBricks.get(j)))
						} // end of for loop

					} // end of for(int i = 0; i < bonusBalls.size(); i++)
				} // end of if(!bonusBalls.isEmpty())
			} catch (Exception e) {
				System.out.println("Problem Caught");
			} // end of try/catch

			if (Collision.check(ball, playerOnePaddle)) {
				Audio.playSound(ballBop);
				ball.setUp(true);
				ball.setDown(false);
				if (playerOnePaddle.isLeft() == false && playerOnePaddle.isRight() == false) {

				} else {
					ball.setLeft(playerOnePaddle.isLeft());
					ball.setRight(playerOnePaddle.isRight());
				} // end of if/else statement
			} // end of if (collision(ball, playerOnePaddle))

			if (Collision.check(ball, playerTwoPaddle)) {
				Audio.playSound(ballBop);
				ball.setUp(false);
				ball.setDown(true);
				if (playerTwoPaddle.isLeft() == false && playerTwoPaddle.isRight() == false) {

				} else {
					ball.setLeft(playerTwoPaddle.isLeft());
					ball.setRight(playerTwoPaddle.isRight());
				} // end of if/else statement
			} // end of if (collision(ball, playerTwoPaddle))

			for (int i = 0; i < playerOneBricks.size(); i++) {
				if (Collision.check(ball, playerOneBricks.get(i))) {
					Audio.playSound(ballBop);
					ball.setUp(false);
					ball.setDown(true);
					player1.point();
					playerOneBricks.remove(i);
					//					if(playerOneBricks.isEmpty()) {
//					playerOneWins = true;
//					gameOver = true;
//					gameOver();
					//					}
					if (random.nextInt(10) >= 8) {
						ball.setBonusTextTimer(60);
						switch (random.nextInt(16)) {
						case 0:
						case 1:
						case 2:
						case 3:
							ball.setSlowMotion(true);
							break;
						case 4:
						case 5:
						case 6:
						case 7:
							ball.setSpeedUp(true);
							break;
						case 8:
						case 9:
						case 10:
						case 11:
							ball.setBigBall(true);
							break;
						case 12:
						case 13:
						case 14:
						case 15:
							ball.setMultiBall(true);
							bonusBalls.add(new Ball(ball));
							break;
						}// end of switch
					} // end of if statement
				} // end of if(collision(ball, playerOneBricks.get(i)))
			} // end of for loop

			for (int i = 0; i < playerTwoBricks.size(); i++) {
				if (Collision.check(ball, playerTwoBricks.get(i))) {
					Audio.playSound(ballBop);
					ball.setUp(true);
					ball.setDown(false);
					player2.point();
					playerTwoBricks.remove(i);
					if (random.nextInt(10) >= 8) {
						ball.setBonusTextTimer(60);
						switch (random.nextInt(16)) {
						case 0:
						case 1:
						case 2:
						case 3:
							ball.setSlowMotion(true);
							break;
						case 4:
						case 5:
						case 6:
						case 7:
							ball.setSpeedUp(true);
							break;
						case 8:
						case 9:
						case 10:
						case 11:
							ball.setBigBall(true);
							break;
						case 12:
						case 13:
						case 14:
						case 15:
							ball.setMultiBall(true);
							bonusBalls.add(new Ball(ball));
							break;
						}// end of switch
					} // end of if statement
				} // end of if(collision(ball, playerTwoBricks.get(i)))
			} // end of for loop

			if (playerOnePaddle.getX() <= 0) {
				playerOnePaddle.setLeftCollision(true);
			} else if (playerOnePaddle.getRightSide() >= WIDTH) {
				playerOnePaddle.setRightCollision(true);
			} else {
				playerOnePaddle.setRightCollision(false);
				playerOnePaddle.setLeftCollision(false);
			}

			if (playerTwoPaddle.getX() <= 0) {
				playerTwoPaddle.setLeftCollision(true);
			} else if (playerTwoPaddle.getRightSide() >= WIDTH) {
				playerTwoPaddle.setRightCollision(true);
			} else {
				playerTwoPaddle.setRightCollision(false);
				playerTwoPaddle.setLeftCollision(false);
			}

			ball.update();
			if (!bonusBalls.isEmpty()) {
				for (Ball b : bonusBalls) {
					b.update();
				}
			}

		} // end of the if/else for pause state

		if (playerOnePaddle.getX() <= 0) {
			playerOnePaddle.setLeftCollision(true);
		} else if (playerOnePaddle.getRightSide() >= WIDTH) {
			playerOnePaddle.setRightCollision(true);
		} else {
			playerOnePaddle.setRightCollision(false);
			playerOnePaddle.setLeftCollision(false);
		}

		if (playerTwoPaddle.getX() <= 0) {
			playerTwoPaddle.setLeftCollision(true);
		} else if (playerTwoPaddle.getRightSide() >= WIDTH) {
			playerTwoPaddle.setRightCollision(true);
		} else {
			playerTwoPaddle.setRightCollision(false);
			playerTwoPaddle.setLeftCollision(false);
		}

		playerOnePaddle.update();
		playerTwoPaddle.update();
	}// end of the update method

	// this method draws everything to the screen
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		// color for that level
		g.setColor(new Color(red, green, blue));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(playerOneWins) {
			g.drawString("Player 1 Wins!", WIDTH*10/41, HEIGHT >> 1);
		}

		if (ball.getBonusTextTimer() > 0) {
			if (ball.isSlowMotion()) {
				g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT * 3 / 17));
				g.setColor(Color.YELLOW);
				g.drawString("Slow Motion", WIDTH * 3 / 8, HEIGHT >> 1);
			}

			if (ball.isSpeedUp()) {
				g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT * 3 / 17));
				g.setColor(Color.YELLOW);
				g.drawString("Speed Boost", WIDTH * 3 / 8, HEIGHT >> 1);
			}

			if (ball.isBigBall()) {
				g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT * 3 / 17));
				g.setColor(Color.YELLOW);
				g.drawString("Krazy Ball", WIDTH * 3 / 8, HEIGHT >> 1);
			}

			if (ball.isMultiBall()) {
				g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT * 3 / 17));
				g.setColor(Color.YELLOW);
				g.drawString("Multiball", WIDTH * 3 / 8, HEIGHT >> 1);
			}
			ball.setBonusTextTimer(ball.getBonusTextTimer() - 1);
		}

		try {
			for (Brick b : playerOneBricks) {
				b.draw(g);
			}
		} catch (Exception e) {
			System.out.println("Brick issue...");
		}

		/***********************************************************************************
		 * This only draws the player 2 paddle and bricks is the game is set for 2
		 * players
		 */
		if (multiplayer) {
			playerTwoPaddle.draw(g);
			for (Brick b : playerTwoBricks) {
				b.draw(g);
			}
		} // *********************************************************************************

		playerOnePaddle.draw(g);
		ball.draw(g);
		if (!bonusBalls.isEmpty()) {
			for (Ball b : bonusBalls) {
				b.draw(g);
			}
		}

		// this sets the text for
		if (!matchInProgress) {
			g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT * 3 / 17));
			g.setColor(Color.CYAN);
			g.drawString("Pong Breaker!", WIDTH * 9 / 41, HEIGHT * 17 / 41);

			// this sets the text for the instructions
			int yTextPosition = HEIGHT * 21 / 41;
			g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT / 31));
			g.setColor(Color.CYAN);
			g.drawString("Player 1 uses the Left & Right arrows to move the paddle.", WIDTH * 10 / 41,
					yTextPosition += 40);
			g.drawString("Player 2 uses the A & D keys to move the paddle.", WIDTH * 12 / 41, yTextPosition += 40);
			g.drawString("In 1 Player mode, the player has 3 lives to try and break all the bricks.", WIDTH * 7 / 41,
					yTextPosition += 40);
			g.drawString("In 2 Player mode, each player has to first break through the bricks, in order to score.",
					WIDTH * 5 / 41, yTextPosition += 40);
			g.drawString("Press the SPACEBAR to serve the ball.", WIDTH * 14 / 41, yTextPosition += 40);
		}

		/***************************************************************************************************
		 * This block displays the text for each player's lives and points
		 */
		g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT / 41));
		g.setColor(Color.CYAN);
		g.drawString("~ Player 1 ~  Lives: " + player1.getLives(), WIDTH / 41, 30);
		g.drawString("Points: " + player1.getPoints(), WIDTH * 8 / 41, 30);


		if (multiplayer) {
			g.drawString("~ Player 2 ~  Lives: " + player2.getLives(), WIDTH * 30 / 41, 30);
			g.drawString("Points: " + player2.getPoints(), WIDTH * 37 / 41, 30);
		}//*************************************************************************************************

		
		if (scored) {
			if (multiplayer) {
				g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT / 7));
				g.setColor(Color.CYAN);
				if (playerOneWins) {
					g.drawString("Player 1 Wins!", WIDTH*10/41, 350);
				} else if (playerTwoWins) {
					g.drawString("Player 2 Wins!", WIDTH*10/41, 350);
				} else {
					g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT * 3 / 17));
					g.setColor(Color.GREEN);
					g.drawString("GOAL!!!", WIDTH * 3 / 8, HEIGHT >> 1);
				}
			} else {
				g.setFont(new Font("Comic Sans MS", Font.BOLD, HEIGHT * 3 / 17));
				g.setColor(Color.GREEN);
				if(gameOver) {
					g.drawString("LOSER!", WIDTH * 3 / 8, HEIGHT >> 1);
				}else {
					g.drawString("RIP!", WIDTH * 3 / 8, HEIGHT >> 1);
				}
			}

		}

		// when either the Player or AI wins the game is paused and this text is
		// displayed until the enter key is pressed
		if (Pause) {
			g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
			g.setColor(Color.CYAN);
			g.drawString("Press Enter to Continue", 200, 450);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			playerOnePaddle.setLeft(true);
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			playerOnePaddle.setRight(true);
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			playerTwoPaddle.setLeft(true);
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			playerTwoPaddle.setRight(true);
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER && Pause) {
			Pause = false; // when Enter is pressed the game is unpaused the win booleans are reset
			playerOneWins = false;
			matchInProgress = true; // this tells the game that the match is in progress
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(!served) {
				served = true;
				matchInProgress = true;

				if(playerOneServing) {
					ball.setUp(true);
					ball.setDown(false);
				} else {
					ball.setUp(false);
					ball.setDown(true);
				}
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}

		if (e.getKeyCode() == KeyEvent.VK_R) {
			if(red < 255) {
				System.out.println(++red);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_G) {
			if(green < 255) {
				System.out.println(++green);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			if(blue < 255) {
				System.out.println(++blue);
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_E) {
			if(red > 0) {
				System.out.println(--red);
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_F) {
			if(green > 0) {
				System.out.println(--green);
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_V) {
			if(blue > 0) {
				System.out.println(--blue);
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_4) {

		}
		if (e.getKeyCode() == KeyEvent.VK_5) {
			multiplayer = !multiplayer;
			initialize();
		}

		if (e.getKeyCode() == KeyEvent.VK_P) {
			if(Audio.track1.isRunning()) {
				Audio.track1.stop();
			} else {
				Audio.track1.start();
			}

		}

		if (e.getKeyCode() == KeyEvent.VK_EQUALS) { // this is the =/+ button to the left of the backspace
			Audio.increaseVolume();
		}

		if (e.getKeyCode() == KeyEvent.VK_MINUS) { // this is the -/_ button to the right of the Zero button
			Audio.decreaseVolume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			playerOnePaddle.setLeft(false);
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			playerOnePaddle.setRight(false);
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			playerTwoPaddle.setLeft(false);
		}

		if (e.getKeyCode() == KeyEvent.VK_D) {
			playerTwoPaddle.setRight(false);
		}


	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	//	@Override
	//	public void actionPerformed(ActionEvent event) {
	//		// if (event.getSource() == box) {
	//		// switch ((String) box.getSelectedItem()) {
	//		// case "1 Player":
	//		// // this.dispose();
	//		// // new Game(false);
	//		// break;
	//		// case "2 Player":
	//		// // this.dispose();
	//		// // new Game(true);
	//		// break;
	//		// case "Quit":
	//		// System.exit(0);
	//		// default:
	//		// // do nothing
	//		// }// end of switch statement
	//		// } // end of if statement
	//	}// end of actionPerformed method

} // end of class

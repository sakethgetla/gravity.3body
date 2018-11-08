package gravity;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import static java.lang.Math.*;

import javax.swing.JFrame;

public class movement extends Canvas implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread thread;
	boolean running = false, keyPush[] = new boolean[4];
	String name = "gravity";
	static int WIDTH = 1270;
	static int HEIGHT = (WIDTH / 3) * 2;
	int ballHeight = WIDTH / 15;
	int moonHeight = WIDTH / 40;
	int sunWidth = WIDTH / 5;
	int sunHeight = sunWidth, ballWidth = ballHeight, moonWidth = moonHeight;
	int updateCounter = -1, stopButtonWidth = WIDTH / 12, stopButtonHeight = stopButtonWidth / 3,
			resetButtonWidth = stopButtonWidth, resetButtonHeight = stopButtonHeight;

	double[] futureSunCentre = new double[2];
	double[] futureBallCentre = new double[2];

	int mousexy[] = new int[2];
	boolean gravityON = false, button1 = false, button2 = false, p = false;;

	int[] sun_xy = new int[2], lineStartxy = new int[2], moonlineStartxy = new int[2], ballxy = new int[2],
			sunCentre = new int[2], ballCentre = new int[2], stopButton = new int[2], mouseMove_xy = new int[2],
			moonxy = new int[2], moonCentre = new int[2], resetButton = new int[2];

	int[] line1 = new int[71];
	int[] line2 = new int[71];
	int[] line3 = new int[71];
	int[] line4 = new int[71];
	int[] line5 = new int[71];
	int[] line6 = new int[71];

	double[] ballAccel = new double[2], ballVel = new double[2], sunAccel = new double[2], sunVel = new double[2],
			moonAccel = new double[2], moonVel = new double[2];
	double accelTot = 0.01, sun2moon = (sunWidth * sunHeight) / (moonHeight * moonWidth),
			sun2ball = (sunWidth * sunHeight) / (ballHeight * ballWidth),
			ball2moon = (ballWidth * ballHeight) / (moonHeight * moonWidth);

	public static void main(String args[]) {
		new movement();

	}

	public movement() {
		reset();
		/*
		 * ballxy[0] = 200; ballxy[1] = 200; sun_xy[0] = (WIDTH - sunWidth) / 2;
		 * sun_xy[1] = (HEIGHT - sunHeight) / 2;
		 * 
		 * moonxy[0] = 700; moonxy[1] = 700;
		 * 
		 * ballVel[0] = 0; ballVel[1] = 0; ballCentre[0] = ballxy[0] + (ballWidth / 2);
		 * ballCentre[1] = ballxy[1] + (ballHeight / 2);
		 * 
		 * moonCentre[0] = moonxy[0] + (moonWidth / 2); moonCentre[1] = moonxy[1] +
		 * (moonHeight / 2);
		 * 
		 * futureBallCentre[0] = ballCentre[0]; futureBallCentre[1] = ballCentre[1];
		 * 
		 * lineStartxy[0] = ballCentre[0]; lineStartxy[1] = ballCentre[1];
		 * 
		 * sunCentre[0] = sun_xy[0] + (sunWidth / 2); sunCentre[1] = sun_xy[1] +
		 * (sunHeight / 2);
		 * 
		 * for (int i = 0; i <= 70; i++) {
		 * 
		 * line1[i] = ballCentre[0]; line2[i] = ballCentre[1]; line3[i] = sunCentre[0];
		 * line4[i] = sunCentre[1]; line5[i] = moonCentre[0]; line6[i] = moonCentre[1];
		 * }
		 * 
		 * 
		 */
		JFrame frame = new JFrame(name);
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		// frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.add(this);

		addKeyListener(new UserInput(this));

		addMouseListener(new MouseInput(this));
		addMouseMotionListener(new MouseInput(this));

		// stopButtonWidth = this.getWidth() / 12 ;

		// stopButton[0] = this.getWidth()-stopButtonWidth -5;
		stopButton[0] = WIDTH - stopButtonWidth - 5;
		stopButton[1] = 5;

		resetButton[0] = 5;
		resetButton[1] = 5;

		start();
	}

	/*
	 * 
	 * public void keyPressed(KeyEvent e) { switch (e.getKeyCode()) {
	 * 
	 * case KeyEvent.VK_UP: keyPush[2] = true;
	 * 
	 * break; case KeyEvent.VK_DOWN: keyPush[3] = true;
	 * 
	 * break; case KeyEvent.VK_LEFT: keyPush[0] = true;
	 * 
	 * break; case KeyEvent.VK_RIGHT: keyPush[1] = true;
	 * 
	 * break; case KeyEvent.VK_SPACE: if (gravityON == false) { gravityON = true; }
	 * else { gravityON = false; } break;
	 * 
	 * } }
	 * 
	 * public void keyReleased(KeyEvent e) { switch (e.getKeyCode()) {
	 * 
	 * case KeyEvent.VK_UP:
	 * 
	 * keyPush[2] = false; break;
	 * 
	 * case KeyEvent.VK_DOWN: keyPush[3] = false; break;
	 * 
	 * case KeyEvent.VK_LEFT: keyPush[0] = false; break;
	 * 
	 * case KeyEvent.VK_RIGHT: keyPush[1] = false; break;
	 * 
	 * } }
	 */
	public synchronized void start() {

		thread = new Thread(this);
		thread.start();
		running = true;

	}

	private synchronized void stop() {

		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void run() {
		int Fps = 40, FPScount = 0;
		long milliSecTimer = System.currentTimeMillis(), compRefresh = 0;

		double before = System.nanoTime();
		double timeDiff = 0, now = 0, amountOfNanos = 0;

		while (running) {
			amountOfNanos = 1000000000 / Fps;
			now = System.nanoTime();
			timeDiff += (now - before) / amountOfNanos;
			before = now;
			while (timeDiff >= 1) {
				tick();
				timeDiff--;
				FPScount++;
			}
			compRefresh++;

			render();

			while (System.currentTimeMillis() - milliSecTimer > 1000) {
				milliSecTimer += 1000;
				System.out.println("comp> " + compRefresh + "   fps> " + FPScount );
				compRefresh = 0;
				FPScount = 0;
			}

		}
		stop();

	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(2);
			return;

		}

		Graphics g = bs.getDrawGraphics();

		///////////////////////

		graphics(g);

		///////////////////////

		g.dispose();
		bs.show();

	}

	private void graphics(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		g.setColor(Color.ORANGE);
		g.fillOval(sun_xy[0], sun_xy[1], sunWidth, sunHeight);

		g.setColor(Color.white);
		g.fillOval(moonxy[0], moonxy[1], moonWidth, moonHeight);

		g.setColor(Color.BLUE);
		g.fillOval(ballxy[0], ballxy[1], ballWidth, ballHeight);

		g.setColor(Color.black);
		g.fillRect((this.getWidth() / 2) - 3, (this.getHeight() / 2) - 3, 6, 6);

		g.fillRect(lineStartxy[0] - 3, lineStartxy[1] - 3, 6, 6);
		g.fillRect(moonlineStartxy[0] - 3, moonlineStartxy[1] - 3, 6, 6);
		g.setColor(Color.WHITE);
		g.drawLine(lineStartxy[0], lineStartxy[1], ballCentre[0], ballCentre[1]);
		g.drawLine(moonlineStartxy[0], moonlineStartxy[1], moonCentre[0], moonCentre[1]);

		g.setColor(Color.blue);
		g.drawPolyline(line1, line2, 71);
		g.setColor(Color.yellow);
		g.drawPolyline(line3, line4, 71);
		g.setColor(Color.white);
		g.drawPolyline(line5, line6, 71);

		// g.drawLine(sunCentre[0], sunCentre[1], ballCentre[0] ,ballCentre[1]);
		g.fillRect(stopButton[0], stopButton[1], stopButtonWidth, stopButtonHeight);
		g.fillRect(resetButton[0], resetButton[1], resetButtonWidth, resetButtonHeight);

		g.setColor(Color.blue);
		g.setFont(new Font("", Font.BOLD, 20));
		String word;

		if (gravityON) {
			word = "stop";
		} else {
			word = "start";
			;
		}
		g.drawString(word, stopButton[0], stopButton[1] + stopButtonHeight);

		// g.setColor(Color.blue);
		// g.setFont(new Font("", Font.BOLD, 20));

		g.drawString("Reset", resetButton[0], resetButton[1] + resetButtonHeight);

	}

	private void tick() {

		double line2Vel = 0.25;

		/*
		 * if (keyPush[0]) { gravityON = false; lineStartxy[0] -= 3; } else if
		 * (keyPush[1]) { gravityON = false; lineStartxy[0] += 3; } if (keyPush[2]) {
		 * gravityON = false; lineStartxy[1] -= 3; } else if (keyPush[3]) { gravityON =
		 * false; lineStartxy[1] += 3; }
		 * 
		 */
		updateCounter += 1;

		sun_xy[0] = (this.getWidth() - sunWidth) / 2;
		 sun_xy[1] = (this.getHeight() - sunHeight) / 2;

		sunCentre[0] = sun_xy[0] + (sunWidth / 2);
		sunCentre[1] = sun_xy[1] + (sunHeight / 2);
		/*
		 * ballCentre[0] = ballxy[0] + (ballWidth / 2); ballCentre[1] = ballxy[1] +
		 * (ballHeight / 2);
		 * 
		 * moonCentre[0] = moonxy[0] + (moonWidth / 2); moonCentre[1] = moonxy[1] +
		 * (moonHeight / 2);
		 */
		// if (gravityON && (ballVel[0] != 0 || ballVel[1] != 0)){
		if (gravityON) {
			for (int i = 0; i <= 70; i++) {

				if (i == 70) {
					line1[70] = ballCentre[0];
					line2[70] = ballCentre[1];
					line3[70] = sunCentre[0];
					line4[70] = sunCentre[1];
					line5[70] = moonCentre[0];
					line6[70] = moonCentre[1];
				} else {

					line1[i] = line1[i + 1];
					line2[i] = line2[i + 1];
					line3[i] = line3[i + 1];
					line4[i] = line4[i + 1];
					line5[i] = line5[i + 1];
					line6[i] = line6[i + 1];

				}

			}
			futureBallCentre[0] = ballVel[0] + ballCentre[0];
			futureBallCentre[1] = ballVel[1] + ballCentre[1];

			/*
			 * if (futureBallCentre[0] - (ballWidth / 2) < 0 || futureBallCentre[0] +
			 * (ballWidth / 2) > this.getWidth()) { ballVel[0] *= -1; if
			 * (futureBallCentre[0] - (ballWidth / 2) < 0) { ballCentre[0] = ballWidth/2 ; }
			 * if(futureBallCentre[0] + (ballWidth / 2) > this.getWidth()){ ballCentre[0] =
			 * this.getWidth() - ballWidth/2 ; } } else if (futureBallCentre[1] -
			 * (ballHeight / 2) < 0 || futureBallCentre[1] + (ballHeight / 2) >
			 * this.getHeight()) { ballVel[1] *= -1; if (futureBallCentre[1] - (ballHeight /
			 * 2) < 0) { ballCentre[1] = ballHeight/2 ; } if(futureBallCentre[1] +
			 * (ballHeight / 2) > this.getHeight()){ ballCentre[1] = this.getHeight()
			 * -ballHeight/2 ; }
			 * 
			 * } else {
			 */
			double distXsun2moon = (sunCentre[0] - moonCentre[0]);
			double distYsun2moon = (sunCentre[1] - moonCentre[1]);

			double distXmoon2ball = (moonCentre[0] - ballCentre[0]);
			double distYmoon2ball = (moonCentre[1] - ballCentre[1]);

			double distXsun2ball = (sunCentre[0] - ballCentre[0]);
			double distYsun2ball = (sunCentre[1] - ballCentre[1]);

			double totDistmoon2ball = Math.sqrt((distXmoon2ball * distXmoon2ball) + (distYmoon2ball * distYmoon2ball));

			double totDistsun2ball = Math.sqrt((distXsun2ball * distXsun2ball) + (distYsun2ball * distYsun2ball));

			double totDistsun2moon = Math.sqrt((distXsun2moon * distXsun2moon) + (distYsun2moon * distYsun2moon));

			/*
			 * ballAccel[0] = ((accelTot * sun2ball) * (distXsun2ball / totDistsun2ball)) +
			 * ((accelTot ) * (distXmoon2ball / totDistmoon2ball));
			 * 
			 * ballAccel[1] = ((accelTot * sun2ball) * (distYsun2ball / totDistsun2ball)) +
			 * ((accelTot ) * (distYmoon2ball / totDistmoon2ball));
			 * 
			 * moonAccel[0] = ((accelTot * sun2moon) * (distXsun2moon / totDistsun2moon)) +
			 * ((accelTot * ball2moon) * (-1) * (distXmoon2ball / totDistmoon2ball));
			 * 
			 * moonAccel[1] = ((accelTot * sun2moon) * (distYsun2moon / totDistsun2moon)) +
			 * ((accelTot * ball2moon) * (-1) * (distYmoon2ball / totDistmoon2ball));
			 * 
			 * sunAccel[0] = ((accelTot ) * (-1) * (distXsun2moon / totDistsun2moon)) +
			 * ((accelTot ) * (-1) * (distXsun2ball / totDistsun2ball));
			 * 
			 * sunAccel[1] = ((accelTot ) * (-1) * (distYsun2moon / totDistsun2moon)) +
			 * ((accelTot ) * (-1) * (distYsun2ball / totDistsun2ball));
			 * 
			 */
			double Asun = sunHeight * sunWidth, Amoon = moonHeight * moonWidth, Aball = ballHeight * ballWidth;

//			
//			ballAccel[0] = ((accelTot *  Asun* (distXsun2ball / totDistsun2ball)) / (totDistsun2ball))
//					+ ((accelTot * Amoon * (distXmoon2ball / totDistmoon2ball) ) / (totDistmoon2ball ));
//			
//			ballAccel[1] = ((accelTot * Asun  * (distYsun2ball / totDistsun2ball)) / (totDistsun2ball ))
//					+ ((accelTot * Amoon * (distYmoon2ball / totDistmoon2ball)) / (totDistmoon2ball ));
//
//			moonAccel[0] = ((accelTot * Asun * (distXsun2moon / totDistsun2moon)) / (totDistsun2moon ))
//					+ (((accelTot *(-1)* Aball * (distXmoon2ball / totDistmoon2ball))  )/ (totDistmoon2ball ));
//			
//			moonAccel[1] = ((accelTot * Asun * (distYsun2moon / totDistsun2moon)) / (totDistsun2moon ))
//					+ (((accelTot * (-1)*Aball * (distYmoon2ball / totDistmoon2ball)) ) / (totDistmoon2ball));
//
//			sunAccel[0] = (((accelTot *(-1)* Amoon * (distXsun2moon / totDistsun2moon)) ) / (totDistsun2moon )) 
//					+ (((accelTot *(-1)* Aball* (distXsun2ball / totDistsun2ball) )  )/ (totDistsun2ball ));
//			
//			sunAccel[1] = (((accelTot *(-1)* Amoon*(distYsun2moon / totDistsun2moon) ) )/(totDistsun2moon ))
//					+ (((accelTot * (-1)*Aball * (distYsun2ball / totDistsun2ball) ) )/ (totDistsun2ball ));
//			

			ballAccel[0] = ((accelTot * Asun * (distXsun2ball / totDistsun2ball)) / (totDistsun2ball * totDistsun2ball))
					+ ((accelTot * Amoon * (distXmoon2ball / totDistmoon2ball))
							/ (totDistmoon2ball * totDistmoon2ball));

			ballAccel[1] = ((accelTot * Asun * (distYsun2ball / totDistsun2ball)) / (totDistsun2ball * totDistsun2ball))
					+ ((accelTot * Amoon * (distYmoon2ball / totDistmoon2ball))
							/ (totDistmoon2ball * totDistmoon2ball));

			moonAccel[0] = ((accelTot * Asun * (distXsun2moon / totDistsun2moon)) / (totDistsun2moon * totDistsun2moon))
					+ (((accelTot * (-1) * Aball * (distXmoon2ball / totDistmoon2ball)))
							/ (totDistmoon2ball * totDistmoon2ball));

			moonAccel[1] = ((accelTot * Asun * (distYsun2moon / totDistsun2moon)) / (totDistsun2moon * totDistsun2moon))
					+ (((accelTot * (-1) * Aball * (distYmoon2ball / totDistmoon2ball)))
							/ (totDistmoon2ball * totDistmoon2ball));

			sunAccel[0] = (((accelTot * (-1) * Amoon * (distXsun2moon / totDistsun2moon)))
					/ (totDistsun2moon * totDistsun2moon))
					+ (((accelTot * (-1) * Aball * (distXsun2ball / totDistsun2ball)))
							/ (totDistsun2ball * totDistsun2ball));

			sunAccel[1] = (((accelTot * (-1) * Amoon * (distYsun2moon / totDistsun2moon)))
					/ (totDistsun2moon * totDistsun2moon))
					+ (((accelTot * (-1) * Aball * (distYsun2ball / totDistsun2ball)))
							/ (totDistsun2ball * totDistsun2ball));

			int G = 400;
			ballAccel[0] *= G;
			ballAccel[1] *= G;

			moonAccel[0] *= G;
			moonAccel[1] *= G;

			sunAccel[0] *= G;
			sunAccel[1] *= G;

//			
//			  //2 BODY 
//			  ballAccel[0] =((accelTot * Amoon * (distXmoon2ball / totDistmoon2ball)
//			  ) / (totDistmoon2ball )); // + ((accelTot * Asun* (distXsun2ball /
//			  totDistsun2ball)) / (totDistsun2ball)) ;
//			  
//			  ballAccel[1] = ((accelTot * Amoon * (distYmoon2ball / totDistmoon2ball)) /
//			  (totDistmoon2ball )); // + ((accelTot * Asun * (distYsun2ball /
//			  totDistsun2ball)) / (totDistsun2ball ));
//			  
//			  moonAccel[0] =(((accelTot *(-1)* Aball * (distXmoon2ball / totDistmoon2ball))
//			  )/ (totDistmoon2ball )); // + ((accelTot * Asun * (distXsun2moon /
//			  totDistsun2moon)) / (totDistsun2moon ));
//			  
//			  moonAccel[1] = (((accelTot * (-1)*Aball * (distYmoon2ball /
//			  totDistmoon2ball)) ) / (totDistmoon2ball)); // +((accelTot * Asun *
//			(distYsun2moon / totDistsun2moon)) / (totDistsun2moon )) ;
//			  
			 
			
			
			//System.out.println(moonAccel[0] + " " + moonAccel[1]);
			moonVel[0] += moonAccel[0];
			moonVel[1] += moonAccel[1];

			ballVel[0] += ballAccel[0];
			ballVel[1] += ballAccel[1];

			sunVel[0] += sunAccel[0];
			sunVel[1] += sunAccel[1];

			futureBallCentre[0] = ballVel[0] + ballCentre[0];
			futureBallCentre[1] = ballVel[1] + ballCentre[1];

			futureSunCentre[0] = sunCentre[0] + sunVel[0];
			futureSunCentre[1] = sunCentre[1] + sunVel[1];
			/*
			 * 
			 * if (futureBallCentre[0] - (ballWidth / 2) < 0 || futureBallCentre[0] +
			 * (ballWidth / 2) > this.getWidth()) { ballVel[0] *= -1; }
			 * 
			 * if (futureBallCentre[1] - (ballHeight / 2) < 0 || futureBallCentre[1] +
			 * (ballHeight / 2) > this.getHeight()) { ballVel[1] *= -1; }
			 * 
			 * if (futureSunCentre[0] - (sunWidth / 2) < 0 || futureSunCentre[0] + (sunWidth
			 * / 2) > this.getWidth()) { sunVel[0] *= -1; } if (futureSunCentre[1] -
			 * (sunHeight / 2) < 0 || futureSunCentre[1] + (sunHeight / 2) >
			 * this.getHeight()) { sunVel[1] *= -1; }
			 */

			ballCentre[0] += ballVel[0];
			ballCentre[1] += ballVel[1];

			sunCentre[0] += sunVel[0];
			sunCentre[1] += sunVel[1];

			moonCentre[0] += moonVel[0];
			moonCentre[1] += moonVel[1];

			lineStartxy[0] = ballCentre[0];
			lineStartxy[1] = ballCentre[1];

			moonlineStartxy[0] = moonCentre[0];
			moonlineStartxy[1] = moonCentre[1];

			// }
		} else {
			if (lineStartxy[0] - ballCentre[0] == 0 && lineStartxy[1] - ballCentre[1] == 0) {

			} else {
				ballVel[0] = (-lineStartxy[0] + ballCentre[0]) * line2Vel;
				ballVel[1] = (-lineStartxy[1] + ballCentre[1]) * line2Vel;
			}
			if (moonlineStartxy[0] - moonCentre[0] == 0 && moonlineStartxy[1] - moonCentre[1] == 0) {
			} else {
				moonVel[0] = (-moonlineStartxy[0] + moonCentre[0]) * line2Vel;
				moonVel[1] = (-moonlineStartxy[1] + moonCentre[1]) * line2Vel;
			}

		}

		ballxy[0] = ballCentre[0] - (ballWidth / 2);
		ballxy[1] = ballCentre[1] - (ballHeight / 2);

		sun_xy[0] = sunCentre[0] - (sunWidth / 2);
		sun_xy[1] = sunCentre[1] - (sunHeight / 2);

		moonxy[0] = moonCentre[0] - (moonWidth / 2);
		moonxy[1] = moonCentre[1] - (moonHeight / 2);

		/// mouse input //////////////////
		/*
		 * if (mouseButtonPush){ if (lineStartxy[0] - ballxy[0] <= 5 && lineStartxy[0] -
		 * ballxy[0] >= 5 && lineStartxy[1] - ballxy[1] <= 5 && lineStartxy[1] -
		 * ballxy[1] >= 5 ){
		 * 
		 * mouseLine();
		 * 
		 * } }
		 */
	}

	private void reset() {

		ballAccel[1] = 0;
		ballAccel[0] = 0;
		ballVel[0] = 0;
		ballVel[1] = 0;

		sunAccel[0] = 0;
		sunAccel[1] = 0;
		sunVel[1] = 0;
		sunVel[0] = 0;

		moonVel[0] = 0;
		moonVel[1] = 0;
		moonAccel[0] = 0;
		moonAccel[1] = 0;

		ballxy[0] = 200;
		ballxy[1] = 200;

		sun_xy[0] = (WIDTH - sunWidth) / 2;
		sun_xy[1] = (HEIGHT - sunHeight) / 2;

		moonxy[0] = 100;
		moonxy[1] = 100;

		sunCentre[0] = sun_xy[0] + (sunWidth / 2);
		sunCentre[1] = sun_xy[1] + (sunHeight / 2);

		ballCentre[0] = ballxy[0] + (ballWidth / 2);
		ballCentre[1] = ballxy[1] + (ballHeight / 2);

		moonCentre[0] = moonxy[0] + (moonWidth / 2);
		moonCentre[1] = moonxy[1] + (moonHeight / 2);

		lineStartxy[0] = ballCentre[0];
		lineStartxy[1] = ballCentre[1];

		moonlineStartxy[0] = moonCentre[0];
		moonlineStartxy[1] = moonCentre[1];

		for (int i = 0; i <= 70; i++) {

			line1[i] = ballCentre[0];
			line2[i] = ballCentre[1];
			line3[i] = sunCentre[0];
			line4[i] = sunCentre[1];
			line5[i] = moonCentre[0];
			line6[i] = moonCentre[1];
		}

	}

	public void mousePressed(MouseEvent e) {
		mousexy[0] = e.getX();
		mousexy[1] = e.getY();

		if (e.getButton() == MouseEvent.BUTTON1) {
			button1 = true;
			if (mousexy[0] > stopButton[0] && mousexy[0] < stopButton[0] + stopButtonWidth && mousexy[1] > stopButton[1]
					&& mousexy[1] < stopButton[1] + stopButtonHeight) {

				if (gravityON) {
					gravityON = false;

				} else {
					gravityON = true;
				}
			}

			if (mousexy[0] > resetButton[0] && mousexy[0] < resetButton[0] + resetButtonWidth
					&& mousexy[1] > resetButton[1] && mousexy[1] < resetButton[1] + resetButtonHeight) {
				reset();
				gravityON = false;
			}
		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			button2 = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		mousexy[0] = e.getX();
		mousexy[1] = e.getY();
		if (e.getButton() == MouseEvent.BUTTON1) {

			p = false;
			button1 = false;

		}
		if (e.getButton() == MouseEvent.BUTTON3) {
			button2 = false;
			//System.out.println("clehf");
		}

	}

	public void mouseDragged(MouseEvent e) {
		mouseMove_xy[0] = e.getX();
		mouseMove_xy[1] = e.getY();
		if (gravityON == false) {

			if (pow(lineStartxy[0] - mouseMove_xy[0], 2) <= pow(ballWidth / 2, 2)
					&& (pow(lineStartxy[1] - mouseMove_xy[1], 2) <= pow(ballHeight / 2, 2))) {
				if (button1) {
					p = true;
					lineStartxy[0] = mouseMove_xy[0];
					lineStartxy[1] = mouseMove_xy[1];

				}

			} else if (pow(moonlineStartxy[0] - mouseMove_xy[0], 2) <= pow(moonWidth / 2, 2)
					&& (pow(moonlineStartxy[1] - mouseMove_xy[1], 2) <= pow(moonHeight / 2, 2))) {
				if (button1) {
					p = true;
					moonlineStartxy[0] = mouseMove_xy[0];
					moonlineStartxy[1] = mouseMove_xy[1];

				}

			}

			if (pow(ballCentre[0] - mouseMove_xy[0], 2) <= pow(ballWidth / 2, 2)
					&& (pow(ballCentre[1] - mouseMove_xy[1], 2) <= pow(ballHeight / 2, 2))) {
				if (button2) {
					ballCentre[0] = mouseMove_xy[0];
					ballCentre[1] = mouseMove_xy[1];
					lineStartxy[0] = mouseMove_xy[0];
					lineStartxy[1] = mouseMove_xy[1];
					for (int i = 0; i <= 70; i++) {

						line1[i] = ballCentre[0];
						line2[i] = ballCentre[1];

					}
				}
			} else if (pow(moonCentre[0] - mouseMove_xy[0], 2) <= pow(moonWidth / 2, 2)
					&& (pow(moonCentre[1] - mouseMove_xy[1], 2) <= pow(moonHeight / 2, 2))) {

				if (button2) {
					moonCentre[0] = mouseMove_xy[0];
					moonCentre[1] = mouseMove_xy[1];
					moonlineStartxy[0] = mouseMove_xy[0];
					moonlineStartxy[1] = mouseMove_xy[1];
					for (int i = 0; i <= 70; i++) {

						line5[i] = moonCentre[0];
						line6[i] = moonCentre[1];
					}
				}

			}
		}

	}

}

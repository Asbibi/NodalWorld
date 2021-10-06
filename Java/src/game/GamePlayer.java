package game;

import gamelogic.GameManager;
import gameinterface.GameFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
* Object in charge of playing and pausing the game simulation.
* 
* @see GameManager
* @see GameFrame
*/ 
public class GamePlayer {

	private GameManager game;
	private TimerTask task;
	private Timer timer;
	private long delay; // in ms
	private boolean playing;

	/**
	* @param game
	*/ 
	public GamePlayer(GameManager game, GameFrame window) {
		this.game = game;

		delay = 1000;

		playing = false;

		window.addPlayPauseListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) play();
				else if(e.getStateChange() == ItemEvent.DESELECTED) pause();
			}
		});

		window.addSlowDownListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				slowDown();
			}
		});

		window.addSpeedUpListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				speedUp();
			}
		});
	}

	/**
	* @return true if world simulation is occurring, otherwise false
	*/ 
	public boolean isPlaying() { return playing; }


	// ========== Actions ==========

	private void play() {
		initTask();
		initTimer();
		timer.scheduleAtFixedRate(task, 0, delay);
		playing = true;
	}

	private void pause() {
		timer.cancel();
		playing = false;
	}

	private void slowDown() {
		delay += 100;
		if(playing) {
			initTask();
			initTimer();
			timer.scheduleAtFixedRate(task, 0, delay);
		}
	}

	private void speedUp() {
		delay = Math.max(100, delay-100);
		if(playing) {
			initTask();
			initTimer();
			timer.scheduleAtFixedRate(task, 0, delay);
		}
	}


	// ========== Initialization ==========

	private void initTimer() {
		if(timer != null) {
			timer.cancel();
		}
		timer = new Timer();
	}

	private void initTask() {
		task = new TimerTask() {
			@Override
			public void run() {
				game.evolveGameState();
				//System.out.println(gameManager);
			}
		};
	}

}

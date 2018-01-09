package finalProject.PongBrickBreaker;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
	static Clip track1, track2;
	
	/**
	 * IMPORTANT!!!
	 * In order to get any sounds to work properly you need to make sure that all sound files are in the same location
	 * and then copy that file path from the "src" to the " / " slash right before the filename of the sound file you want
	 * to play. Then to call it from the Game class you just type Audio.playMusic("filename.wav");
	 */
	static String filepath = "src/finalProject/PongBrickBreaker/";

	// this is the method to play the music for each level
	public static void playMusic(String song) {
		try {
			track1 = AudioSystem.getClip();
			track1.open(AudioSystem.getAudioInputStream(new File(filepath + song)));
			FloatControl volume = (FloatControl) track1.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(-20.0f);
			track1.start();
		} catch (Exception ex) {
			System.out.println("To get sound to work properly, you must go to the"+
								"\nproperties of the sound file and make sure the"+
								"\nfilepath is correctly assigned in the Audio class."+
								"\nThe filepath should look something like -> src/... filepath .../");
		}
	}

	// this method plays different sound fx like the ball bounce, goal, and loser
	public static void playSound(String sound) {
		try {
			track2 = AudioSystem.getClip();
			track2.open(AudioSystem.getAudioInputStream(new File(filepath + sound)));
			track2.start();
		} catch (Exception ex) {
			System.out.println("To get sound to work properly, you must go to the"+
					"\nproperties of the sound file and make sure the"+
					"\nfilepath is correctly assigned in the Audio class."+
					"\nThe filepath should look something like -> src/... filepath .../");
		}
	}
	

	public static void startingVolume() {
		FloatControl volume = (FloatControl) track1.getControl(FloatControl.Type.MASTER_GAIN);
		volume.setValue(-20.0f);
		System.out.println("Volume " + volume.getValue());
	}
	
	
	public static void increaseVolume() {
		FloatControl volume = (FloatControl) track1.getControl(FloatControl.Type.MASTER_GAIN);
		if(volume.getValue() < 5.8f) {
			volume.setValue(volume.getValue() + 1.0f);
		}
		System.out.println("Volume " + volume.getValue());
	}

	public static void decreaseVolume() {
		FloatControl volume = (FloatControl) track1.getControl(FloatControl.Type.MASTER_GAIN);
		if(volume.getValue() > -79.8f) {
			volume.setValue(volume.getValue() - 1.0f);
		}
		System.out.println("Volume " + volume.getValue());
	}

}

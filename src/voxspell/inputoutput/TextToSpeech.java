package voxspell.inputoutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.concurrent.Task;

/**
 * <h1>TextToSpeech</h1> This class is responsible for taking text, synthesising
 * speech from it and then immediately playing it on the speakers
 * <p>
 * This happens on another thread so that the GUI doesn't freeze
 * 
 * @version 0.2.1
 * @author tkro003 (primary)
 * @author mkem114 (secondary)
 * @since 2016-09-03
 */
public class TextToSpeech {

	/**
	 * <h1>OS</h1> Represents the three main PC operating system categories
	 * (OSX, Linux, Windows)
	 * <p>
	 * 
	 * @version 1.0
	 * @author mkem114 (primary)
	 * @since 2016-09-16
	 */
	private enum OS {
		WINDOWS, OSX, LINUX
	}

	/**
	 * The location of festival in UG4
	 */
	public static final String festivalLocation = "/usr/share/festival/voices/english";
	public static final String scmStr = "./VOXSpell/.voice.scm";

	private static TextToSpeech _instance = null;
	private static OS _os;

	private Thread lastSpeech;
	private String _selectedVoice;
	private String _selectedNiceVoice;
	private ArrayList<String> _voices = new ArrayList<>();
	private HashMap<String, String> _niceVoices = new HashMap<>();

	/**
	 * This constructor determines the operating system and picks the default
	 * voice
	 */
	private TextToSpeech() {
		lastSpeech = null;
		if (System.getProperty("os.name").matches("Mac OS X")) {
			_os = OS.OSX;
		} else if (System.getProperty("os.name").matches("Linux")) {
			_os = OS.LINUX;
		} else {
			_os = OS.WINDOWS;
		}
		_niceVoices.put("KAL", "kal_diphone");
		_niceVoices.put("RAB", "rab_diphone");
		_niceVoices.put("Auckland", "akl_nz_jdt_diphone");
		makeVoices();
		_selectedNiceVoice = "Auckland";
		_selectedVoice = _niceVoices.get(_selectedNiceVoice);
	}

	public void introduceVoice() {
		speak("Hi, my name is " + _selectedNiceVoice);
	}

	/**
	 * The available voices' names
	 * 
	 * @return ArrayList of voice names to choose from
	 */
	public List<String> voices() {
		return new ArrayList<>(_niceVoices.keySet());
	}

	/**
	 * Select a voice to use by using the number of order it's in the list
	 * 
	 * @param index
	 *            Number along the list to choose
	 */
	public void chooseVoice(String niceVoice) {
		_selectedNiceVoice = niceVoice;
		_selectedVoice = _niceVoices.get(niceVoice);
	}

	/**
	 * Name of the current voice
	 * 
	 * @return Voice name
	 */
	public String selectedVoice() {
		return _selectedNiceVoice;
	}

	/**
	 * Access to the singleton instance of TextToSpeech
	 * 
	 * @return Singleton's reference
	 */
	public static TextToSpeech access() {
		if (_instance == null) {
			_instance = new TextToSpeech();
		}
		return _instance;
	}

	/**
	 * Speaks to the user through the speakers the text provided
	 * 
	 * @param speak Text to be spoken
	 */
	public void speak(String speak) {
		String cmd = null;
		if (_os == OS.OSX) {
			cmd = "say -v " + _selectedVoice + " " + speak;
		} else if (_os == OS.LINUX) {
			generateScheme(speak);
			cmd = "festival -b " + scmStr;
		} else {
			System.out.println("Not available on Windows.");
			System.out.println("You were going to say: " + speak);
		}
		if (_os != OS.WINDOWS) {
			Thread tmp = new Thread(new Speech(lastSpeech, cmd));
			tmp.start();
			generateScheme(speak);
			lastSpeech = tmp;
		}
	}

	/**
	 * Generates a scheme file so that the text can be spoken with the selected
	 * voice
	 * 
	 * @param speak Text to be spoken
	 *            
	 */
	private void generateScheme(String speak) {
		File scm = new File(scmStr);

		if (!scm.exists()) {
			try {
				scm.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Scheme file now exists
		try {
			PrintWriter writer = new PrintWriter(scm);
			writer.println("(Parameter.set 'Duration_Stretch 1.2)");
			writer.println("(voice_" + _selectedVoice + ")");
			writer.println("(SayText \"" + speak + "\")");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Finds all available voices on the current machine
	 */
	private void makeVoices() {
		String cmd = null;
		if (_os == OS.OSX) {
			try {
				// Usage of awk referenced from
				// http://stackoverflow.com/questions/2440414/
				cmd = "say -v '?' | awk '{print $1;}'";
				ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", cmd);
				Process p = pb.start();
				InputStream stdout = p.getInputStream();
				BufferedReader out = new BufferedReader(new InputStreamReader(stdout));
				String line = null;
				while ((line = out.readLine()) != null) {
					_voices.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (_os == OS.LINUX) {
			try {
				cmd = "ls " + festivalLocation;
				ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", cmd);
				Process p = pb.start();
				InputStream stdout = p.getInputStream();
				BufferedReader out = new BufferedReader(new InputStreamReader(stdout));
				String line = null;
				while ((line = out.readLine()) != null) {
					_voices.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Not available on Windows.");
			_voices.add("Temporary"); // temporary solution to prevent crashing
										// on Windows
			_voices.add("Solution");
			_voices.add("Windows");
		}
	}

	/***
	 * <h1>Speech</h1> This class is a one time use that speaks one piece of
	 * text, it links with what's to be said before it so that speaking doesn't
	 * occur simultaneously and queues up
	 * 
	 * @version 1.0
	 * @author mkem114
	 * @since 2016-09-19
	 */
	@SuppressWarnings("rawtypes")
	private class Speech extends Task {
		Thread _last;
		String _cmd;

		/**
		 * The only constructor requires what comes before and what to say
		 * 
		 * @param last
		 *            What's said first
		 * @param cmd
		 *            What to say
		 */
		public Speech(Thread last, String cmd) {
			super();
			_last = last;
			_cmd = cmd;
		}

		/**
		 * Actually says it
		 */
		protected Object call() throws Exception {
			if (_last != null) {
				_last.join();
			}
			ProcessBuilder pb = new ProcessBuilder("/bin/bash", "-c", _cmd);
			try {
				// testing concurrency
				Process p = pb.start();
				p.waitFor();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
package newage.test;

public abstract class AbstractTest {
	final String BLACK = (char) 27 + "[30m";
	final String RED = (char) 27 + "[31m";
	final String GREEN = (char) 27 + "[32m";
	final String YELLOW = (char) 27 + "[33m";
	final String BLUE = (char) 27 + "[34m";
	final String MAGENTA = (char) 27 + "[35m";
	final String CYAN = (char) 27 + "[36m";
	final String WHITE = (char) 27 + "[37m";
	final String CLEAN = (char)27 + "[0m";

	protected void println(String message) {
		System.out.println(message);
	}
	
	protected void printlnRed(String message) {
		printlnColour(message, RED);
	}
	
	protected void printlnGreen(String message) {
		printlnColour(message, GREEN);
	}

	private void printlnColour(String message, String colour){
		System.out.println(colour + message + CLEAN);
	}
}

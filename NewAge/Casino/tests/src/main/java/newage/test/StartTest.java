package newage.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class StartTest {
	private static JUnitCore junit = new JUnitCore();

	public static void main(String[] args) {
		runTests(new Class[]{WalletFunctionalTests.class, FunctionalTests.class});
	}

	public static void runTests(Class[] classes) {
		for (int i = 0; i < classes.length; i++) {
			System.out.println("Running " + classes[i].getName() + "...");
			Result result = junit.run(classes[i]);

			System.out.println("All tests: " + result.getRunCount());
			System.out.println("Failed: " + result.getFailureCount());
			System.out.println("Ignored: " + result.getIgnoreCount());
			System.out.println(
					"Successed: " + (result.getRunCount() - result.getFailureCount() - result.getIgnoreCount()));

			if (result.getFailureCount() != 0) {
				System.out.println("Failures:");

				result.getFailures().stream().forEach(x -> {
					System.out.println(x.getTestHeader());
					System.out.println(x.getTrace());
				});
			}

			System.out.println("============================================================");
		}
	}
}

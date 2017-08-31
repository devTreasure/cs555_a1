package A1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadTest implements Runnable {

	public int sendCounter = 0;
	public int receiveCounter = 0;
	public static final String EXIT_COMMAND = "exit";

	public void sendMethod() {

	}

	public void receiveCounterMethod() {
		System.out.println("Recieved messages to start count");

		synchronized (this) {

			for (int i = 0; i <= 5; i++) {
				this.receiveCounter += 1;
			}
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		receiveCounterMethod();

	}

	public static void main(String[] args) throws IOException {

		ThreadTest th = new ThreadTest();

		// ThreadTest t1 = new ThreadTest();
		// ThreadTest t2 = new ThreadTest();
		// ThreadTest t3 = new ThreadTest();

		Thread ts1 = new Thread(th);
		Thread ts2 = new Thread(th);
		Thread ts3 = new Thread(th);

		ts1.start();
		ts2.start();
		ts3.start();

		/*
		 * t1.run(); t2.run(); t3.run();
		 */

		boolean continueOperations = true;

		while (continueOperations) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String exitStr = br.readLine();
			System.out.println("Received command is:" + exitStr);

			if (EXIT_COMMAND.equalsIgnoreCase(exitStr)) {
				System.out.println("Exiting.");
				continueOperations = false;
			} else if ("start".equalsIgnoreCase(exitStr)) {
				th.receiveCounterMethod();
				System.out.println(th.receiveCounter);
			}

		}

		System.out.println("Bye.");		
		System.out.println("How to be.....");

	}

}

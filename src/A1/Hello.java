package A1;

import java.util.Scanner;

public class Hello {

	public Hello() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(
				"************************************\n 1.) Please select the ip SPACE port and enter in one line.\n 2.)"
						+ " Enter the START to start the node \n 3.) " + "Complete all the config IP entries"
						+ "\n 4.) veriy all the nodes stood up. \n 5.) Enter  \"RANDOM\" to select the communication node \n 6.) Enter \"START\" to start the round"
						+ "\n 7.) Enter \"EXIT\" to exit  and stop the node \n****************************************");

		String strExit = "";

		System.out.println("ENTER IP-SPACE-PORT number ");
		Scanner scanner = new Scanner(System.in);
		String ipPort = scanner.nextLine();

		System.out.println(ipPort);

		while (!strExit.equalsIgnoreCase("EXIT")) {

			strExit = scanner.nextLine();
			System.out.println(strExit);

			System.out.println("ENTER \"RANDOM\" to select the Sender NODE");
			strExit = scanner.next();
			System.out.println(strExit);

			System.out.println("ENTER \"START\" to send messages to the sender  NODE");
			strExit = scanner.next();
			System.out.println(strExit);

			System.out.println("ENTER \"EXIT\" to close the NODE");
			strExit = scanner.next();
			System.out.println(strExit);

			if (strExit.equalsIgnoreCase("EXIT")) {
				System.out.println("EXITING System..... ");
			}
		}

	}

}

package clientsocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientManager {

	private final Socket socket;
	private InputStream inStream;
	private OutputStream outStream;
	private Scanner in;
	private PrintWriter out;
	Scanner userInputScanner = new Scanner(System.in);

	public ClientManager(Socket socket) throws IOException {
		this.socket = socket;
		this.setUps();
		this.clientRun();
		this.closeConnection();
	}

	private void clientRun() {

		boolean validUsername = false;
		String userInput = "default";
		while (!validUsername) {
			System.out
					.println("Enter your name in the format USER <username>: ");
			userInput = userInputScanner.nextLine().trim();
			validUsername = validateUserInput(userInput);
		}
		String username = userInput;
		sendRequest(username);

		while (!userInput.equals("close")) {
			System.out
					.println("Type buy - to buy stocks \t sell - to sell stocks \t checkportfolio - to view your stocks");
			System.out
					.println("follow - to view info on a particular stock. You need to know the tickername \t close - to exit");
			System.out.println("\nEnter command:");
			userInput = userInputScanner.nextLine().trim();

			if (userInput.equalsIgnoreCase("follow")) {
				sendRequest("follow");
				requestStock();
			} else if (userInput.equalsIgnoreCase("buy")) {
				sendRequest("buy");
				buyStock();
			} else if (userInput.equalsIgnoreCase("sell")) {
				sendRequest("sell");
				sellStock();
			} else {
				sendRequest(userInput);
				receiveResponse();
			}
		}
	}

	private void setUps() throws IOException {
		inStream = socket.getInputStream();
		outStream = socket.getOutputStream();
		in = new Scanner(inStream);
		out = new PrintWriter(outStream);
	}

	private void sendRequest(String request) {
		out.print(request + "\n");
		out.flush();
		System.out.println("Sending: " + request);
	}

	private void receiveResponse() {
		System.out.println("\nSERVER RESPONSE:");

		if (in.hasNext()) {
			while (in.hasNextLine()) {
				String input = in.nextLine();
				if (input.equals("end")) {
					break;
				} else {
					System.out.println(input);
				}
			}

		}
		System.out.println("");
	}

	private void requestStock() {
		System.out
				.println("Enter stock tickername in this form QUERY <TICKERNAME> or q to quit: ");
		String userInput = userInputScanner.nextLine().trim();
		while (true) {
			if (!userInput.equals("q")) {
				sendRequest(userInput);
				receiveResponse();
				System.out
						.println("Enter stock tickername in this form QUERY <TICKERNAME> or q to quit: ");
				userInput = userInputScanner.nextLine().trim();
			} else {
				sendRequest("reset");
				receiveResponse();
				break;
			}
		}
	}

	private void buyStock() {
		System.out
				.println("Enter command in the form BUY <tickername> <no> or q to quit: ");
		String userInput = userInputScanner.nextLine().trim();
		while (true) {
			if (!userInput.equals("q")) {
				sendRequest(userInput);
				receiveResponse();
				System.out
						.println("Enter command in the form BUY <tickername> <no> or q to quit: ");
				userInput = userInputScanner.nextLine().trim();
			} else {
				sendRequest("reset");
				receiveResponse();
				break;
			}
		}
	}

	private void sellStock() {
		System.out
				.println("Enter command in the form SELL <TICKERNAME> <NO> or q to quit: ");
		String userInput = userInputScanner.nextLine().trim();
		while (true) {
			if (!userInput.equals("q")) {
				sendRequest(userInput);
				receiveResponse();
				System.out
						.println("Enter command in the form SELL <TICKERNAME> <NO> or q to quit: ");
				userInput = userInputScanner.nextLine().trim();
			} else {
				sendRequest("reset");
				receiveResponse();
				break;
			}
		}
	}

	private void closeConnection() throws IOException {
		in.close();
		out.close();
		inStream.close();
		outStream.close();
		socket.close();
	}

	private boolean validateUserInput(String username) {
		String[] comps = username.split("\\s+");
		if (comps.length == 2 && comps[0].equalsIgnoreCase("USER")
				&& comps[1].length() > 2 && comps[1].startsWith("<")
				&& comps[1].endsWith(">")) {
			return true;
		}
		return false;
	}	
}

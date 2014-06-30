/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 
 * @author Ali
 */
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
		String userInput = "yanki4";
		// I modified this line
		sendRequest(userInput);
		// modification ended here

		while (!userInput.equals("close")) {
			System.out.println("\nEnter command:");
			userInput = userInputScanner.nextLine();

			if (userInput.equals("follow")) {
				sendRequest("follow");
				requestStock();
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
		System.out.println("\n Server Response:");

		if (in.hasNext()) {
			while (in.hasNextLine()) {
				String input = in.nextLine();
				if (input.equals("end")) {
					break;
				} else {
					System.out.println(input);
				}
			}

		} else {
			receiveResponse();
		}

	}

	private void requestStock() {
		System.out.println("Enter stock tickername or q to quit: ");
		String userInput = userInputScanner.nextLine();
		while (true) {
			if (!userInput.equals("q")) {
				sendRequest(userInput);
				receiveResponse();
				System.out.println("Enter stock tickername or q to quit: ");
				userInput = userInputScanner.nextLine();
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
}

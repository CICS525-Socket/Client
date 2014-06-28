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
        String userInput = "dummi";
        while(!userInput.equals("close")){
            System.out.println("Enter command:");
            userInput = userInputScanner.next();
            sendRequest(userInput);
            receiveResponse();
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
        while (in.hasNextLine()) {
            String input = in.nextLine();
            System.out.println(input);
            if (input.equals("\n\n")) {
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

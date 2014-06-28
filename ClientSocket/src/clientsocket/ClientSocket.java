/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientsocket;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ali
 */
public class ClientSocket {

    public static void main(String[] args) {
        final String host = "127.0.0.1";
        Socket socket = null;
        int port = 8003;
        System.out.println("CREATING THE CONNECTION\n");
        try {
            try {
                socket = new Socket(host, port);
                new ClientManager(socket);

            } catch (IOException ex) {
                System.out.println("Could not create socket: " + ex.getMessage());
            }
        } finally {
            try {
                socket.close();
            } catch (IOException | NullPointerException ex) {
                System.out.println(ex.getMessage());
            }

        }

    }

}

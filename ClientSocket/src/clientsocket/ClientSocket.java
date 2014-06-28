/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clientsocket;

import java.io.IOException;
import java.net.Socket;


/**
 *
 * @author Ali
 */
public class ClientSocket {
   
    public static void main(String[] args) {
        final String host = "127.0.0.1";
        Socket socket;
        int port = 8003;
        System.out.println("CREATING THE CONNECTION\n");
        try {
            socket = new Socket(host, port);
            socket.close();
        } catch (IOException ex) {
            System.out.println("Could not create socket: " + ex.getMessage());
        }
        
    }
    
}

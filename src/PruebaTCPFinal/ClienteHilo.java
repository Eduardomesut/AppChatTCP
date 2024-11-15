/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PruebaTCPFinal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *
 * @author Eduardo
 */
public class ClienteHilo extends Thread{
    ObjectInputStream in;
    Socket socket;

    public ClienteHilo(ObjectInputStream in, Socket socket) {
        this.in = in;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            while (true){
                Mensaje recibir = (Mensaje) in.readObject();
                System.out.println(recibir.getMensaje());

            }

        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

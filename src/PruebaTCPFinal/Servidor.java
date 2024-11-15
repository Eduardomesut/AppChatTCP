/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PruebaTCPFinal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Eduardo
 */
public class Servidor {
    public static void main(String[] args) {
     try (ServerSocket serverSocket = new ServerSocket(10000)){
         System.out.println("Servidor conectado!!");
         ObjetoCompartido obj = new ObjetoCompartido();
         for (;;) {
             Socket cliente = serverSocket.accept();
             HiloServidor hilo = new HiloServidor(cliente, obj);
             hilo.start();

         }


     }catch (IOException e){
         e.printStackTrace();
     }
    }
}

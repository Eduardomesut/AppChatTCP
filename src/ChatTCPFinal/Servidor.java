/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatTCPFinal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author tarde
 */
public class Servidor {
    
    public static void main(String[] args) {
        ObjetoCompartido obj = new ObjetoCompartido();
        
        try(ServerSocket serverSocket = new ServerSocket(10000)){
            System.out.println("Servidor iniciado!!");
            for (;;) {
                Socket cliente = serverSocket.accept();
                HiloServidor hilo = new HiloServidor(obj, cliente);
                hilo.start();
            }
            

            
        }catch(IOException e){
            
            e.printStackTrace();
        }
        
        
    }
  
}

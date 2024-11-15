/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ChatTCPFinal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tarde
 */
public class HiloCliente extends Thread{
    Socket socket;

    ObjectInputStream in;
    
    public HiloCliente(Socket socket, ObjectInputStream in) {
        this.socket = socket;
        this.in = in;
    }
    
    @Override
    public void run(){
        Scanner sc = new Scanner (System.in);
        try{
            
            boolean salir = false;
            while(!salir){
                Mensaje recibido = (Mensaje) in.readObject();
                System.out.println(recibido.getUsuario() + ":" + recibido.getMensaje());
                
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
}

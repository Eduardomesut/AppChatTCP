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
public class Cliente {

    public static void main(String[] args) throws ClassNotFoundException {
        String nombreUsuario = "";
        Scanner sc = new Scanner(System.in);
        try (Socket socket = new Socket("localhost", 10000)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Te has conectado al servidor!");
            boolean registroHecho = false;
            boolean salir = false;
            while(!registroHecho){
                System.out.print("Porfavor, indica tu nombre de usuario: ");
                nombreUsuario = sc.nextLine();
                
                Mensaje registro = new Mensaje (nombreUsuario, "registro");
                out.writeObject(registro);
                out.flush();
                Mensaje registroBack = (Mensaje) in.readObject();
                if (registroBack.getMensaje().equals("Usuario registrado correctamente")) {
                    System.out.println(registroBack.getMensaje());
                    registroHecho = true;
                }else{
                    System.out.println(registroBack.getMensaje());
                }
            }
            
            HiloCliente hilo = new HiloCliente(socket, in);
            hilo.start();
            System.out.println("Escribe un mesaje: ");
            while(!salir){
                
                String mensaje = sc.nextLine();
                Mensaje normal = new Mensaje(nombreUsuario, mensaje);
                out.writeObject(normal);
                out.flush();
                
                
                
            }
          
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

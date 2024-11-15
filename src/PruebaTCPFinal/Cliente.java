/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PruebaTCPFinal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Eduardo
 */
public class Cliente {
    public static void main(String[] args) {
        ObjectOutputStream out = null;
        ObjectInputStream in = null;
        String usuario = null;
        Scanner sc = new Scanner(System.in);
        try (Socket socket = new Socket("localhost", 10000)){
            String nombreUsuario = null;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Has entrado en el servidor!!");
            boolean registroHecho = false;
            while (!registroHecho){
                System.out.print("Porfavor indica tu nombre de usuario:");
                nombreUsuario = sc.nextLine();
                Mensaje registro = new Mensaje(nombreUsuario, "registro");
                out.writeObject(registro);
                out.flush();
                Mensaje vueltaRegistro = (Mensaje) in.readObject();
                if (vueltaRegistro.getMensaje().equals("Usuario registrado correctamente!!")){
                    System.out.println(vueltaRegistro.getMensaje());

                    registroHecho = true;
                }else {
                    System.out.println(vueltaRegistro.getMensaje());
                }
            }
            ClienteHilo clienteHilo = new ClienteHilo(in, socket);
            clienteHilo.start();
            boolean salirChat = false;
            while (!salirChat){
                String mensajeEnviar = sc.nextLine();

                Mensaje envios = new Mensaje(nombreUsuario,  nombreUsuario + ": " + mensajeEnviar);
                out.writeObject(envios);
                out.flush();

            }

        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    
}

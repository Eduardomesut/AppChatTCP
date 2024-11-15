/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PruebaTCPFinal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Eduardo
 */
public class HiloServidor extends Thread{

    Socket cliente;
    ObjetoCompartido obj;

    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    String usuarionombre = null;

    public HiloServidor(Socket cliente, ObjetoCompartido obj) {
        this.cliente = cliente;
        this.obj = obj;
    }

    @Override
    public void run() {
        try {
            this.out = new ObjectOutputStream(cliente.getOutputStream());
            this.in = new ObjectInputStream(cliente.getInputStream());
            System.out.println("Se ha conectado un nuevo usuario!!");
            boolean registroHecho = false;
            while (!registroHecho){
                Mensaje registro = (Mensaje) in.readObject();
                if (registro.getUsuario() != null){
                    if (obj.addSocket(registro.getUsuario(), this)){
                        this.usuarionombre = registro.getUsuario();
                        Mensaje registroBien = new Mensaje(this.usuarionombre, "Usuario registrado correctamente!!");
                        out.writeObject(registroBien);
                        out.flush();
                        registroHecho = true;
                    }else {
                        Mensaje registroMal = new Mensaje(registro.getUsuario(), "El usuario " + registro.getUsuario() + " ya esta usado!");
                        out.writeObject(registroMal);
                    }
                }
            }
            while (true){
                Mensaje chatGeneral = (Mensaje) in.readObject();
                obj.escribirAGrupo(chatGeneral);


            }


        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
}

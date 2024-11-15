package ChatTCPFinal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloServidor extends Thread {

    ObjetoCompartido obj;
    Socket cliente;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    String nombreUsuario = null;

    public HiloServidor(ObjetoCompartido obj, Socket cliente) {
        this.obj = obj;
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try {
            this.out = new ObjectOutputStream(cliente.getOutputStream());
            this.in = new ObjectInputStream(cliente.getInputStream());
            System.out.println("Cliente unido al servidor!!");

            boolean registroHecho = false;
            while (!registroHecho) {
                Mensaje registro = (Mensaje) in.readObject();
                System.out.println(registro);
                if (registro.getUsuario() != null) {
                    this.nombreUsuario = registro.getUsuario();
                    if (this.obj.addSocket(nombreUsuario, this)) {
                        Mensaje respuestaRegistroBien = new Mensaje(this.nombreUsuario, "Usuario registrado correctamente");
                        out.writeObject(respuestaRegistroBien);
                        out.flush();
                        registroHecho = true;
                    } else {
                        Mensaje respuestaRegistro = new Mensaje(this.nombreUsuario, "Nombre ya en uso!!");
                        out.writeObject(respuestaRegistro);
                        out.flush();
                    }
                }
            }       
            while (true) {
                //Variante para funcionalidades
                Mensaje chatGeneral = (Mensaje) in.readObject();
                System.out.println(chatGeneral);
                obj.escribirAGrupo(chatGeneral); 
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Cliente " + nombreUsuario + " desconectado.");
            obj.sockets.remove(nombreUsuario); 
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (cliente != null) cliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

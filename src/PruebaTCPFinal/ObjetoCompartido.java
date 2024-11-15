/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PruebaTCPFinal;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Eduardo
 */
public class ObjetoCompartido {

    HashMap<String, HiloServidor> clientes = new HashMap<>();


    public synchronized boolean addSocket(String nombreUsuario, HiloServidor hiloServidor) {
        if (clientes.containsKey(nombreUsuario)) {
            return false;
        }
        this.clientes.put(nombreUsuario, hiloServidor);
        System.out.println(clientes);
        return true;
    }

    public synchronized void escribirAGrupo(Mensaje mensaje) {
        for (Map.Entry<String, HiloServidor> entry : clientes.entrySet()) {
            try {
                if (!entry.getKey().equals(mensaje.getUsuario())) {
                   entry.getValue().out.writeObject(mensaje);
                   entry.getValue().out.flush();
                }

            } catch (Exception e) {
                System.err.println("Error al enviar el mensaje a " + entry.getKey());
                e.printStackTrace();
            }
        }
    }


    
}

package ChatTCPFinal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ObjetoCompartido {

    HashMap<String, HiloServidor> sockets = new HashMap<>();

    public synchronized boolean addSocket(String nombreUsuario, HiloServidor hiloServidor) {
        if (sockets.containsKey(nombreUsuario)) {
            return false; 
        }
        this.sockets.put(nombreUsuario, hiloServidor);
        System.out.println(sockets);
        return true;
    }

    public synchronized void escribirAGrupo(Mensaje mensaje) {
        for (Map.Entry<String, HiloServidor> entry : sockets.entrySet()) {
            try {
                if (!entry.getKey().equals(mensaje.getUsuario())) {
                    entry.getValue().out.writeObject(mensaje);
                    entry.getValue().out.flush();
                }
                
            } catch (IOException e) {
                System.err.println("Error al enviar el mensaje a " + entry.getKey());
                e.printStackTrace();
            }
        }
    }
}

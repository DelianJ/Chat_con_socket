/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import socket.CanalEnvio;

/**
 *
 * @author Delian
 */
public class Servidor {

    public static void main(String[] args) {
        MarcoServidor mimarco = new MarcoServidor();

        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class MarcoServidor extends JFrame implements Runnable {

    private JTextArea areatext;
    CanalEnvio cE = new CanalEnvio();

    public MarcoServidor() {
        setBounds(600, 300, 280, 350);
        JPanel milamina = new JPanel();
        milamina.setLayout(new BorderLayout());
        areatext = new JTextArea();
        milamina.add(areatext, BorderLayout.CENTER);
        add(milamina);
        setVisible(true);

        Thread mihilo = new Thread(this);
        mihilo.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(9999);
            String nick, ip, mensaje;
            Datos paquete_resibido;
            //ArrayList<String> listaip = new ArrayList<String>();
            HashMap<String, String> ips = new HashMap<String, String>();

            while (true) {
                Socket misocket = servidor.accept();
                ObjectInputStream paquete_datos = new ObjectInputStream(misocket.getInputStream());
                paquete_resibido = (Datos) paquete_datos.readObject();

                nick = paquete_resibido.getNick();
                ip = paquete_resibido.getIp();
                mensaje = paquete_resibido.getMensaje();

                if (!mensaje.equals(" Online")) {
                    areatext.append(nick + ": " + mensaje + ". para: " + ip + "\n");
                    cE.enviarCarta(ip, 9998, paquete_resibido);
                    misocket.close();
                } else {/*--Detecta Online--*/
                    InetAddress localizacion = misocket.getInetAddress();
                    String ipremota = localizacion.getHostAddress();
                    areatext.append("Online: " + ipremota + "\n");
                    /*listaip.add(ipremota);
                    paquete_resibido.setIps(listaip);
                    listaip.forEach(t -> System.out.println(t));*/
                    ips.put(paquete_resibido.getNick(), ipremota);
                    paquete_resibido.setIps(ips);
                    for (Map.Entry<String, String> entry : ips.entrySet()) {
                        cE.enviarCarta(entry.getValue(), 9998, paquete_resibido);
                        misocket.close();
                    }
                    
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

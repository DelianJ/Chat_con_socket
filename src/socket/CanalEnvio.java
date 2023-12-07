/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package socket;

import chat.Datos;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Delian
 */
public class CanalEnvio {

    Socket canal;
    Datos carta;
    ObjectOutputStream paqueteDatos;

    public void enviarOnline(String ipServidor, int ipPuerto, String nick) {
        try {
            this.canal = new Socket(ipServidor, ipPuerto);
            this.carta = new Datos();
            this.carta.setNick(nick);
            this.carta.setMensaje(" Online");
            this.paqueteDatos = new ObjectOutputStream(this.canal.getOutputStream());
            this.paqueteDatos.writeObject(this.carta);
            this.canal.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "ocurrio un error al conectarce al servidor \n" + ex.getMessage());
        }
    }

    public void enviarCarta(String ipServidor, int ipPuerto, Datos carta) {
        try {
            this.canal = new Socket(ipServidor, ipPuerto);
            this.carta = carta;

            this.paqueteDatos = new ObjectOutputStream(this.canal.getOutputStream());
            this.paqueteDatos.writeObject(this.carta);
            this.paqueteDatos.close();
            this.canal.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "ocurrio un error al enviar la carta \n" + ex.getMessage());
        }

    }
}

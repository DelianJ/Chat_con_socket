/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package chat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import socket.CanalEnvio;

/**
 *
 * @author Delian
 */
public class Cliente {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MarcoCliente mimarco = new MarcoCliente();

        mimarco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

class MarcoCliente extends JFrame {

    public MarcoCliente() {
        setBounds(600, 300, 280, 350);
        String nickUsuario = JOptionPane.showInputDialog("Nick: ");
        String ipservidor = JOptionPane.showInputDialog("¿Cuan es la ip del servidor?");
        LaminaMarcoCliente milamina = new LaminaMarcoCliente(nickUsuario, ipservidor);
        add(milamina);
        setVisible(true);
        addWindowListener(new EnvioOnline(nickUsuario, ipservidor));
    }
}

class EnvioOnline extends WindowAdapter {
    private String nick;
    private String ip;

    EnvioOnline(String nickUsuario, String ip) {
        this.nick = nickUsuario;
        this.ip = ip;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        CanalEnvio online = new CanalEnvio();
        online.enviarOnline(this.ip, 9999, this.nick);
    }
}

class LaminaMarcoCliente extends JPanel implements Runnable {

    private JTextField campo1;
    private JComboBox ip;
    private JLabel nick;
    private JButton miboton;
    private JTextArea areachat;
    private String ipServidor;
    HashMap<String, String> ipsMenu = new HashMap<String, String>();

    public LaminaMarcoCliente(String nickUsuario, String ipServidor) {
        
        this.ipServidor = ipServidor;
        JLabel minick = new JLabel("Nick: ");
        add(minick);
        this.nick = new JLabel();
        this.nick.setText(nickUsuario);
        add(this.nick);
        JLabel texto = new JLabel("Online: ");
        texto.setHorizontalAlignment(JLabel.CENTER);
        add(texto);
        this.ip = new JComboBox();
        add(this.ip);
        this.areachat = new JTextArea(12, 20);
        add(this.areachat);
        this.campo1 = new JTextField(20);
        add(this.campo1);
        this.miboton = new JButton("Enviar");
        EnviarTexto enviar = new EnviarTexto(ipServidor);
        this.miboton.addActionListener(enviar);
        add(this.miboton);

        Thread mihilo = new Thread(this);
        mihilo.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket servidorcliente = new ServerSocket(9998);
            Socket socketCliente;
            Datos paqueteResibido;

            while (true) {
                socketCliente = servidorcliente.accept();
                ObjectInputStream flujoEntrada = new ObjectInputStream(socketCliente.getInputStream());
                paqueteResibido = (Datos) flujoEntrada.readObject();
                if (!paqueteResibido.getMensaje().equals(" Online")) {
                    areachat.append(paqueteResibido.getNick() + ": " + paqueteResibido.getMensaje() + "\n");
                } else {/*---limpia y agrega los usuarios conectados---*/
                    ip.removeAllItems();
                    ipsMenu = paqueteResibido.getIps();
                    for (Map.Entry<String, String> entry : ipsMenu.entrySet()) {
                        ip.addItem(entry.getKey());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private class EnviarTexto implements ActionListener {
        private String ipServidor;

        private EnviarTexto(String ipServidor) {
            this.ipServidor = ipServidor;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            areachat.append(campo1.getText() + "\n");
            CanalEnvio online = new CanalEnvio();
            online.enviarCarta(this.ipServidor, 9999, new Datos(nick.getText(),
                    ipsMenu.get(ip.getSelectedItem().toString()),
                    campo1.getText()));
            campo1.setText("");
        }

    }
}

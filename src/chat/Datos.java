/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chat;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Delian
 */
public class Datos implements Serializable{
    private String nick, ip, mensaje;
    //private ArrayList<String> ips;
    private HashMap<String, String> ips;

    public Datos(String nick, String ip, String mensaje) {
        this.nick = nick;
        this.ip = ip;
        this.mensaje = mensaje;
    }
    
    public Datos() {
    }

    /**
     * @return the nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * @param nick the nick to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the ips
     */
    public HashMap<String, String> getIps() {
        return ips;
    }

    /**
     * @param ips the ips to set
     */
    public void setIps(HashMap<String, String> ips) {
        this.ips = ips;
    }
    
    
}

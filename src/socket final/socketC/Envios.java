package socketC;

import java.io.Serializable;

public class Envios implements Serializable {
    private String nick,ip,mensaje ;

    public Envios(String nick, String ip, String mensaje) {
        this.nick = nick;
        this.ip = ip;
        this.mensaje = mensaje;
    }

    public void setMensaje(String mensaje) {this.mensaje = mensaje;}
    public String getNick() {return nick;}
    public String getIp() {return ip;}
    public String getMensaje() {return mensaje;}
}

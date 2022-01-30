package socketC;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Servidor {
    public static void main(String[] args){
        MarcoServidor marco = new MarcoServidor() ;
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
//se pone hilo porque tiene que estar a la vez escuchando y exribiendo
class MarcoServidor extends JFrame implements Runnable{
    private JTextArea areaTexto ;
    private JPanel panel;
    public MarcoServidor(){
        setBounds(1200,300,280,350);
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        areaTexto = new JTextArea() ;
        areaTexto.setFont(new Font("Arial", Font.PLAIN, 15));
        areaTexto.setForeground(Color.WHITE);
        areaTexto.setBackground(Color.darkGray);

        panel.add(areaTexto,BorderLayout.CENTER);
        add(panel);
        setVisible(true);
        Thread mihilo = new Thread(this);
        mihilo.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(4444); //Creamos un socket de tipo servidor que es el que va a estar a la escucha
            String nick,ip,message;
            Envios packet ;
            do {
                //Recibir datos
                Socket conexion = servidor.accept(); // Esto lo que va a hacer es que cuando una ip se vaya a conectar a nuestro equipo se acepte esa conexion y despues se transforma a socket
                ObjectInputStream buffer = new ObjectInputStream(conexion.getInputStream()) ;
                packet = (Envios)(buffer.readObject()) ;
                nick = packet.getNick();
                message = packet.getMensaje() ;
                ip = packet.getIp() ;
                areaTexto.append("[" + nick + "]=>" + message + " para:" + ip + "\n");
                buffer.close();
                conexion.close();
                if(message.charAt(0)=='/'){
                    String retorno;
                    try {
                        String command = message.substring(1);
                        if (command.length()>=8) {
                            if(command.substring(0,8).equalsIgnoreCase("piramide")){
                                try {
                                    retorno = piramide(Integer.parseInt(command.substring(9)));
                                } catch (NumberFormatException e) {
                                    retorno = "Has introducido un formato incorrecto: /piramide numeroFilas";
                                }
                            }else{
                                retorno = "Has introducido un formato incorrecto: /piramide numeroFilas";
                            }
                        } else if (command.equalsIgnoreCase("fecha")) {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = new Date();
                            retorno = formatter.format(date);
                        } else {
                            retorno = "El comando introducido no es correcto";
                        }
                    }catch (StringIndexOutOfBoundsException e){
                        retorno = "Has introducido un comando erroneo" ;
                    }
                    Socket client = new Socket(conexion.getInetAddress(), 5555);
                    ObjectOutputStream bufferS = new ObjectOutputStream(client.getOutputStream());
                    packet.setMensaje(retorno);
                    bufferS.writeObject(packet);
                    bufferS.close();
                    client.close();

                }else {
                    //Redirigir Datos
                    Socket client = new Socket(ip, 5555);
                    ObjectOutputStream bufferS = new ObjectOutputStream(client.getOutputStream());
                    bufferS.writeObject(packet);
                    bufferS.close();
                    client.close();
                }

            }while(true) ;
        } catch (IOException e) {
            System.out.println("Ha habido un error al lanzar el socket");
        } catch (ClassNotFoundException e) {
            System.out.println("Ha habido un error al recibir el paquete ");
        }
    }

    public String piramide(int numFilas) throws IOException{

        StringBuilder piramide = new StringBuilder();
        for(int altura = 1; altura<=numFilas; altura++){
            //Espacios en blanco
            for(int blancos = 1; blancos<=numFilas-altura; blancos++){
                piramide.append(" ");
            }

            //Asteriscos
            for(int asteriscos=1; asteriscos<=(altura*2)-1; asteriscos++){
               piramide.append("*");
            }
            piramide.append("\n") ;
        }

        return piramide.toString() ;
    }
}
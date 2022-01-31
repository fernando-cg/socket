package socketC;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args){
        MarcoServidor marco = new MarcoServidor() ;
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
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
                    //ejecucion en otro hilo
                    Funciones fun = new Funciones(message) ;
                    Thread funciones = new Thread(fun);
                    funciones.start();
                    funciones.join();
                    String retorno = fun.getRetorno();

                    //redireccion al mismo soket
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
        } catch (InterruptedException e) {
            System.out.println("Se ha producido un error al ejecutar las funciones por hilos");
        }
    }
}
package gui;

import seguridad.Cifrado;
import socketC.Envios;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatBox extends JFrame implements Runnable{
    private JPanel panel;
    private JButton submit;
    private JLabel etiqueta;
    private JTextField tfmessage;
    private JTextArea chat;
    JScrollPane scrollPane;
    Toolkit monitor = Toolkit.getDefaultToolkit();
    Dimension screen=monitor.getScreenSize();
    int width = screen.width;
    int height= screen.height;
    //anti MITM
    final String secretKey = "llevalatararaunvestidoblancollenodecascabeles";

    String  ip;
    String nick ;

//Dos hilos 1 escucha 2 envio

    public ChatBox(String ip,String nick){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Sistema de conexion por sockets");
        setSize(width/4,height/2);
        setLocation(width/3,height/5);
        setResizable(true);
        iniciarComponentes();
        setVisible(true);

        this.ip = ip ;
        this.nick = nick ;

    }

    private void iniciarComponentes() {
        colocarPanel();
        colocarEtiqueta();
        colocarElementos();
        Thread receptor = new Thread(this) ;
        receptor.start();
    }

    private void colocarPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
    }

    private void colocarEtiqueta() {
        etiqueta = new JLabel("Conexion Sockets");
        etiqueta.setBounds(150,0,200,40);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 20));
        etiqueta.setForeground(Color.black);
        panel.add(etiqueta);
    }

    private void colocarElementos(){

        chat = new JTextArea() ;
        chat.setFont(new Font("Arial", Font.PLAIN, 15));
        chat.setForeground(Color.WHITE);
        chat.setBackground(Color.darkGray);

        scrollPane = new JScrollPane(chat);
        scrollPane.setBounds(20,50,420,350);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane) ;

        submit = new JButton("Enviar");
        panel.add(submit);
        submit.setBounds(370,430, 70, 40);

        tfmessage = new JTextField() ;
        tfmessage.setBounds(20,430,350,40);
        tfmessage.setFont(new Font("Arial", Font.PLAIN, 15)) ;
        tfmessage.setForeground(Color.black);
        tfmessage.setBackground(Color.gray);
        panel.add(tfmessage) ;

        ActionListener eventoClic = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                chat.append("[Yo]=>"+tfmessage.getText()+"\n");
                try {
                    Socket socket = new Socket("localhost",4444);
                    Envios message = new Envios(nick,ip,Cifrado.encrypt(tfmessage.getText(),secretKey)) ;
                    ObjectOutputStream buffer = new ObjectOutputStream(socket.getOutputStream()) ;
                    buffer.writeObject(message);
                    buffer.close();
                    socket.close();
                    tfmessage.setText("");
                } catch (IOException ex) {
                    System.out.println("Se ha producido un error: " + ex.getMessage());

                }
            }
        };
        submit.addActionListener(eventoClic);

    }

    @Override
    public void run() {
        try {
            ServerSocket receptor = new ServerSocket(5555) ;
            do {
                Socket client = receptor.accept();
                ObjectInputStream input = new ObjectInputStream(client.getInputStream()) ;
                Envios message = (Envios) input.readObject();
                input.close();
                chat.append("[" + message.getNick() + "] =>" + Cifrado.decrypt(message.getMensaje(),secretKey) + "\n");
            }while(true) ;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Ha habido un error a la hora de ponerse en escucha","ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Ha habido un error a la hora de recibir el mensaje","ERROR",JOptionPane.ERROR_MESSAGE);

        }

    }
}

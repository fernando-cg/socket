package gui;

import socket.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBox extends JFrame {
    private JPanel panel;
    private JButton submit;
    private JLabel etiqueta;
    private JTextField tfmessage;
    private JTextArea chat;
    Toolkit monitor = Toolkit.getDefaultToolkit();
    Dimension screen=monitor.getScreenSize();
    int width = screen.width;
    int height= screen.height;



    public ChatBox(){



        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Sistema de conexion por sockets");
        setSize(width/4,height/2);
        setLocation(width/3,height/5);
        setResizable(false);
        iniciarComponentes();
        setVisible(true);
    }

    private void iniciarComponentes() {
        colocarPanel();
        colocarEtiqueta();
        colocarElementos();
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
        chat.setBounds(20,50,420,350);
        chat.setFont(new Font("Arial", Font.PLAIN, 15));
        chat.setForeground(Color.WHITE);
        chat.setBackground(Color.darkGray);
        panel.add(chat) ;

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


            }
        };
        submit.addActionListener(eventoClic);

    }
}

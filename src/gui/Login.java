package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel panel;
    private JButton submit;
    private JLabel etiqueta, ip,name ;
    private JTextField tfIpe,tfname;
    Toolkit monitor = Toolkit.getDefaultToolkit();
    Dimension screen=monitor.getScreenSize();
    int width = screen.width;
    int height= screen.height;

    public Login(){
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

    private void colocarEtiqueta() {
        etiqueta = new JLabel("Conexion Sockets");
        etiqueta.setBounds(150,0,200,40);
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 20));
        etiqueta.setForeground(Color.black);
        panel.add(etiqueta);
    }

    private void colocarPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        this.add(panel);
    }

    private void colocarElementos(){
        ip = new JLabel("Introduzca la IP:");
        ip.setBounds(170,50,200,40);
        ip.setFont(new Font("Arial", Font.PLAIN, 15));
        ip.setForeground(Color.black);
        panel.add(ip);

        tfIpe = new JTextField() ;
        tfIpe.setBounds(120,90,200,30);
        tfIpe.setFont(new Font("Arial", Font.PLAIN, 15)) ;
        tfIpe.setForeground(Color.black);
        tfIpe.setBackground(Color.gray);
        tfIpe.setVisible(true);
        panel.add(tfIpe);

        name = new JLabel("Introduzca un nick:");
        name.setBounds(145,130,200,40);
        name.setFont(new Font("Arial", Font.PLAIN, 15));
        name.setForeground(Color.black);
        panel.add(name);

        tfname = new JTextField() ;
        tfname.setBounds(120,170,200,30);
        tfname.setFont(new Font("Arial", Font.PLAIN, 15)) ;
        tfname.setForeground(Color.black);
        tfname.setBackground(Color.gray);
        tfname.setVisible(true);
        panel.add(tfname);

        submit = new JButton("Conectar");
        panel.add(submit);
        submit.setBounds(150,220, 140, 40);

        ActionListener eventoClic = new ActionListener() {
            //que te checkee si esta vacio o no los campos
            @Override
            public void actionPerformed(ActionEvent e) {
                String ipS = tfIpe.getText();
                String nameS = tfname.getText();
                try {
                    if(ipS.length()>0 && nameS.length()>0){

                        ChatBox c = new ChatBox(ipS,nameS) ;

                        setVisible(false);
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(null,"Porfavor no deje ningun campo vacio","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }catch (NumberFormatException n){
                    JOptionPane.showMessageDialog(null,"Introduzca un puerto correcto","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        submit.addActionListener(eventoClic);

    }
}


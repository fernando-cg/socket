package socketC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args){
        MarcoCliente marco = new MarcoCliente() ;
    }
}

class MarcoCliente extends JFrame{
    public MarcoCliente(){
        setBounds(600,300,280,350);

        LaminaMarcoCliente milamina = new LaminaMarcoCliente() ;
        add(milamina) ;
        setVisible(true);
    }
}

class LaminaMarcoCliente extends JPanel{
    private JTextField campo1 ;
    private JButton miboton ;

    public LaminaMarcoCliente(){
        JLabel texto = new JLabel("CLIENTE") ;
        add(texto) ;

        campo1 = new JTextField(20) ;
        add(campo1) ;

        miboton = new JButton("Enviar") ;
        add(miboton) ;

        ActionListener eventoClic = new ActionListener() {
            //que te checkee si esta vacio o no los campos
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket miSocket = new Socket("localhost",4444);//Con esto creamos el socket
                    DataOutputStream salida = new DataOutputStream(miSocket.getOutputStream()) ; // Creamos un flijo de salida y va a pasar por el soket
                    salida.writeUTF(campo1.getText()); //que viaje por el flujo lo que hay en el punto 1
                    salida.close();
                    campo1.setText("");
                } catch (IOException ex) {
                    System.out.println("Se ha producido un error: " + ex.getMessage());

                }
            }
        };
        miboton.addActionListener(eventoClic);
    }
}
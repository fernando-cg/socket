/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorHilo extends Thread {
    
    private Socket sc;
    private DataInputStream in;
    private DataOutputStream out;
    private String nombreCliente;
    
    public ServidorHilo(Socket sc, DataInputStream in, DataOutputStream out, String nombreCliente) {
        this.sc = sc;
        this.in = in;
        this.out = out;
        this.nombreCliente = nombreCliente;
    }
    
    @Override
    public void run() {
        
        int opcion;
        File f = new File("numeros.txt");
        boolean salir = false;
        while (!salir) {
            
            try {
                opcion = in.readInt();
                switch (opcion) {
                    case 1:
                        
                        int numFilas = in.readInt();
                       
                        pirammidee(numFilas);
                        System.out.println("Se escribio la piramide con las filas que el cliente: " + nombreCliente + "dijo");
                        
                        out.writeUTF("piramide shida realizada");
                        break;
                    
                    case 2:
                    
                    int fechahoy = in.readInt();
                  
                    fecha(fechahoy);
                    System.out.println("la fecha q: " + nombreCliente + "dijo");
               
                    out.writeUTF("fecha dicha");
                        break;
                    
                    case 3:
                      
                        
                        break;
                    
                    case 4:
                         
                        break;
                    
                    case 5:
                        
                        
                        break;
                    
                    case 6:
                        salir = true;
                        break;
                    default:
                        out.writeUTF("Solo numero del 1 al 6");
                }
                
            } catch (IOException ex) {
                Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        try {
            // Cierro el socket
            sc.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Conexion cerrada con el cliente " + nombreCliente);
        
    }
    
    
    private void fecha(int qfe) {
    	
    	Calendar c = Calendar.getInstance();
    	
    	
    	
    	if (qfe == 1) {
    		String dia = Integer.toString(c.get(Calendar.DATE));
    		System.out.println(dia);
    	}else if (qfe == 2) {
    		String mes = Integer.toString(c.get(Calendar.MONTH));
    		System.out.println(mes);
    	}else if(qfe == 3) {
    		String annio = Integer.toString(c.get(Calendar.YEAR));
    		System.out.println(annio);
    	}
	}

	public void pirammidee(int numFilas) throws IOException{
        
    	 System.out.println();
         for(int altura = 1; altura<=numFilas; altura++){
             //Espacios en blanco
             for(int blancos = 1; blancos<=numFilas-altura; blancos++){
                 System.out.print(" ");
             }
  
             //Asteriscos
             for(int asteriscos=1; asteriscos<=(altura*2)-1; asteriscos++){
                 System.out.print("*");
             }
             System.out.println();
         }
    }
}

package socket2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteHilo extends Thread {

    private DataInputStream in;
    private DataOutputStream out;

    public ClienteHilo(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    @Override
    public void run() {

        Scanner sn = new Scanner(System.in);

        String mensaje;
        int opcion = 0;
        boolean salir = false;

        while (!salir) {

            try {
                System.out.println("1. piramide");
                System.out.println("2. fecha");
                System.out.println("3. crear un archivo txt");
                System.out.println("4. ");
                System.out.println("5. ");
                System.out.println("6. Salir");
                
                opcion = sn.nextInt();
                out.writeInt(opcion);
                
                switch (opcion) {
        
                    case 1:    	
                    
                    	System.out.print("Introduzca numero de filas: ");
                        int numFilas = sn.nextInt();
                        System.out.println("numero generado: " + numFilas);
                        // Le mando al servidor el numero aleatorio
                        out.writeInt(numFilas);
                        // Recibo y muestro el mensaje
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        break;
                    case 2:
                       
                    	System.out.print("Introduzca dia(1) ,mes(2) o año(3) para saber en cual estas: ");
                        int fechahoy = sn.nextInt();
                        // Le mando al servidor el numero aleatorio
                        out.writeInt(fechahoy);
                        // Recibo y muestro el mensaje
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        
                        break;
                    case 3:
                    	System.out.print("Introduzca dia(1) ,mes(2) o año(3) para saber en cual estas: ");
                        int txt = sn.nextInt();
                        // Le mando al servidor el numero aleatorio
                        out.writeInt(txt);
                        // Recibo y muestro el mensaje
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        break;
                    case 4:
                         break;
                    case 5:
                        
                        
                        break;
                    case 6:
                        salir = true;
                        break;
                    default:
                        mensaje = in.readUTF();
                        System.out.println(mensaje);
                        
                }
            } catch (IOException ex) {
                Logger.getLogger(ClienteHilo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    
    public int generaNumeroAleatorio(int minimo,int maximo){
       int num=(int)Math.floor(Math.random()*(maximo-minimo+1)+(minimo));
       return num;
   }
}

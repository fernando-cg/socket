package socket;

import java.io.IOException;
import java.util.Scanner;

//import sockets.Cliente;


//Clase principal que hará uso del cliente
public class MainCliente
{
    public static void main(String[] args) throws IOException
    {
    	Scanner teclado = new Scanner(System.in);
    	
    	System.out.println("Introduzca la ip a la que quiera conectarse:");
    	String HOST=teclado.nextLine();
    	
        Cliente cli = new Cliente(HOST); //Se crea el cliente

        System.out.println("Iniciando cliente\n");
        cli.startClient(); //Se inicia el cliente
        
        teclado.close();
    }
}
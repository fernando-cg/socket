package socket;

import java.io.IOException;
//import sockets.Servidor;

import socket.Servidor;


//Clase principal que har? uso del servidor
public class MainServidor
{
  public static void main(String[] args) throws IOException
  {
      Servidor serv = new Servidor(); //Se crea el servidor

      System.out.println("Iniciando servidor\n");
      serv.startServer(); //Se inicia el servidor
  }
}

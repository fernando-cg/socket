package socketC;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Funciones implements Runnable{
    private volatile String message ;
    private volatile String retorno ;
    public Funciones(String message) {
        this.message = message ;
    }

    public String getRetorno() {return retorno;}

    @Override
    public void run() {

        try {
            String command = message.substring(1);
            if (command.length()>=8) {
                if(command.substring(0,8).equalsIgnoreCase("piramide")){
                    try {
                        retorno = piramide(Integer.parseInt(command.substring(9)));
                    } catch (NumberFormatException e) {
                        retorno = "Has introducido un formato incorrecto: /piramide numeroFilas";
                    } catch (IOException e) {
                        retorno = "Se ha producido un error" ;
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
    }
    public String piramide(int numFilas) throws IOException {

        StringBuilder piramide = new StringBuilder();
        piramide.append("\n") ;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salvando_walle;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;


/**
 * El main se encarga de iniciar el programa,  al dar el comienzo a la lectura y las instrucciones subsecuentes
 * @author Hugo
 * @version 1.1
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Lectura lectura = new Lectura();
        PrintStream salida = new PrintStream(new FileOutputStream("WALLE.out"));
        System.setOut(salida);
        lectura.iniciarLectura();
    }
    
}

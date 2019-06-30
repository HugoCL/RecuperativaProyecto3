/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WallECodigo;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;


/**
 * El main se encarga de iniciar el programa,  al dar el comienzo a la lectura y las instrucciones subsecuentes
 * @version 1.1
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        //TEST HUGO
        Recinto recinto = new Recinto();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                recinto.crearRecinto(i, j, 0);
            }
        }



        recinto.newWallE(4,4);
        Posicion posicionA = new Posicion(0,0);
        recinto.setPosicionActual(posicionA);
        Recorredor recorrer = new Recorredor();
        recinto.setLimiteColumnas(5);
        recinto.setLimiteFilas(5);
        recinto.nuevaOrientacion('O');
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(recinto.getRecintoCompleto()[i][j]);
            }
            System.out.println("");
        }
        recinto.crearRecinto(0, 1, 1);
        List<Posicion> ruta = recorrer.resolver(recinto);
        System.out.println(ruta);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(recinto.getRecintoCompleto()[i][j]);
            }
            System.out.println("");
        }
        ArrayList<Character> instruc = recorrer.traductorInstrucciones(ruta, recinto);
        System.out.println(instruc);
    }
    // FIN TEST HUGO
}

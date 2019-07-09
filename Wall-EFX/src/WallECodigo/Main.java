/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WallECodigo;


import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 * El main se encarga de iniciar el programa,  al dar el comienzo a la lectura y las instrucciones subsecuentes
 * @version 1.1
 */
public class Main {

    public static Recinto recinto = new Recinto();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        //TEST HUGO


        Recinto recinto2 = new Recinto();
        int[][] maze =
                {
                        {0, 0, 0, 1},
                        {0, 0, 0, 1},
                        {0, 0, 0, 0},
                        {1, 1, 1, 0}
                };
        recinto2.setRecintoCompleto(maze);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(recinto2.getRecintoCompleto()[i][j]);
            }
            System.out.println("");
        }
        // LO MISMO QUE ARRIBA
        recinto2.newWallE(new Posicion (3,3), new Posicion(3,3));
        Posicion posicionA2 = new Posicion(0,0);
        recinto2.setpActual(posicionA2);
        recinto2.setLimiteColumnas(4);
        recinto2.setLimiteFilas(4);
        recinto2.nuevaOrientacion('S');
        List<Posicion> rutaPrototipo = new ArrayList<>();
        boolean[][] visitado = new boolean[4][4];
        Serializador serial = new Serializador();
        serial.serializar(recinto2);
        serial.desSerializar();
        Recorredor recorrer = new Recorredor();
        List <Posicion> ruta = recorrer.resolverRapido(recinto2, visitado, 2);
        //System.out.println(Recorredor.getTodasLasRutas());

        List<List<Posicion>> rutasTotales = recorrer.getTodasLasRutas();
        for (List<Posicion> lista: rutasTotales){
            for (Posicion posicionAc : lista){
                System.out.print(posicionAc.getpFila()+","+posicionAc.getpColumna()+" || ");
            }
            System.out.println("");
        }
        //List<Posicion> ruta = recorrer.resolver(recinto,1);
        //List<Posicion> rutaRapida = recorrer.resolverRapido(recinto);
        //Collections.reverse(rutaRapida);
        //System.out.println(ruta);

        /*
        for (int i = 0; i < pp.getFilas(); i++) {
            for (int j = 0; j < pp.getColumnas(); j++) {
                System.out.print(recinto.getRecintoCompleto()[i][j]);
            }
            System.out.println("");
        }*/

        //ArrayList<Character> instruc = recorrer.traductorInstrucciones(rutaRapida, recinto);
        //System.out.println(instruc);
    }
    // FIN TEST HUGO
}

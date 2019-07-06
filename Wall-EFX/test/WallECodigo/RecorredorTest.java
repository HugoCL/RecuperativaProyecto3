/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WallECodigo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fi_an
 */
public class RecorredorTest {
    /**
     * Test of resolver method, of class Recorredor. Se testea que el recorrido 
     * sea el correcto en una matriz 3*1, en donde walle comienza en {0,0} y el 
     * objetivo en {2,0};
     */
    @Test
    public void testResolver() {
        System.out.println("resolver");
        
        Recinto instance = new Recinto();        
        instance.newWallE(2, 0);
        Posicion posActual = new Posicion(0,0);
        instance.setpActual(posActual);
        
        Recorredor recorredor = new Recorredor();
        List<Posicion> result = recorredor.resolver(instance, 1);
        
        ArrayList<Posicion> posicionesDePrueba = new ArrayList<>();
        Posicion pos0 = new Posicion(0,0);
        Posicion pos1 = new Posicion(1,0);
        Posicion pos2 = new Posicion(2,0);
        posicionesDePrueba.add(pos0);
        posicionesDePrueba.add(pos1);
        posicionesDePrueba.add(pos2);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(posicionesDePrueba.get(i).getpColumna(), result.get(i).getpColumna());
            assertEquals(posicionesDePrueba.get(i).getpFila(), result.get(i).getpFila());
        }
        //resultado esperado arraylist(posiciones) en una matriz 3*1
        //for   compar posiciones (asserEquals)
        
    }

    /**
     * Test of resolverRapido method, of class Recorredor. Comprueba que el
     * recorrido en "posicionesDePrueba" sea igual al dado en el metodo resolverRapido
     * Se compara en una matriz 4*3.
     */
    @Test
    public void testResolverRapido() {
        System.out.println("resolverRapido");
        
        Recinto instance = new Recinto();
        
        instance.newWallE(4, 1);
        Posicion posActual = new Posicion(0,1);
        instance.setpActual(posActual);
        
        Recorredor recorredor = new Recorredor();
        
        ArrayList<Posicion> posicionesDePrueba = new ArrayList<>();
        Posicion pos0 = new Posicion(0,1);
        Posicion pos1 = new Posicion(1,1);
        Posicion pos2 = new Posicion(2,1);
        Posicion pos3 = new Posicion(3,1);
        Posicion pos4 = new Posicion(4,1);
        posicionesDePrueba.add(pos0);
        posicionesDePrueba.add(pos1);
        posicionesDePrueba.add(pos2);
        posicionesDePrueba.add(pos3);
        posicionesDePrueba.add(pos4);
        
        List<Posicion> resul = recorredor.resolverRapido(instance);
        for (int i = 0; i < resul.size(); i++) {
            assertEquals(posicionesDePrueba.get(i).getpColumna(), resul.get(i).getpColumna());
            assertEquals(posicionesDePrueba.get(i).getpFila(), resul.get(i).getpFila());
        }
        //resultado esperado arraylist(posiciones mas rapidas) en una matriz 4*2
        //for(for   compar posiciones (asserEquals))
    }

    /**
     * Test of traductorInstrucciones method, of class Recorredor. Se compara la 
     * ruta dada por la funcion con una que tiene los movimientos esperados.
     * matriz 4*4.
     */
    @Test
    public void testTraductorInstrucciones() {
        System.out.println("traductorInstrucciones");
        
        ArrayList<Posicion> ruta = new ArrayList<>();
        ruta.add(new Posicion(1,0));
        ruta.add(new Posicion(1,1));
        ruta.add(new Posicion(2,1));
        ruta.add(new Posicion(3,1));
        ruta.add(new Posicion(3,2));
        Recinto recinto = new Recinto();
        recinto.nuevaOrientacion('N');
        Recorredor instance = new Recorredor();
        ArrayList<Character> expResult = new ArrayList<>();
        //movimientos esperados. ruta actual. 
        expResult.add('D');      //0 0 0 0 0
        expResult.add('A');      //x x 0 0 0
        expResult.add('D');      //0 x 0 0 0
        expResult.add('A');      //0 x x 0 0
        expResult.add('A');      //0 0 0 0 0
        expResult.add('I');
        expResult.add('A');
       
        ArrayList<Character> result = instance.traductorInstrucciones(ruta, recinto);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
        
        //ArrayList de caracteres
        //dar matriz 4*1 ArrayList esperado{i,d,etc}
    }
    
}

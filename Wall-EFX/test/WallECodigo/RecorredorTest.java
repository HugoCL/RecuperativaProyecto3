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
 * @author Hugo
 */
public class RecorredorTest {


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

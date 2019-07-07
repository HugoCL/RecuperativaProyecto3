/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WallECodigo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fi_an
 */
public class RecintoTest {
    /**
     * Test of newWallE method, of class Recinto.
     */
    @Test
    public void testNewWallE() {
        System.out.println("newWallE");
        Recinto instance = new Recinto();
        instance.newWallE(new Posicion(1,1),new Posicion(5,5));
        
        // TODO review the generated test code and remove the default call to fail.
        assertEquals(1, WallE.getWallE().getpDPlanta().getpFila());
        assertEquals(1, WallE.getWallE().getpDPlanta().getpColumna());
        assertEquals(5, WallE.getWallE().getpDZonaSegura().getpFila());
        assertEquals(5, WallE.getWallE().getpDZonaSegura().getpColumna());
    }

    /**
     * Test of esMuroBomba method, of class Recinto.
     */
    @Test
    public void testEsMuroBomba() {
        System.out.println("esMuroBomba");
        int fila = 5;
        int columna = 5;
        Recinto instance = new Recinto();
        instance.crearRecinto(fila, columna, 1);
        boolean result = instance.esMuroBomba(instance, fila, columna);
        assertTrue(result);
    }

    /**
     * Test of esSeguro method, of class Recinto.
     */
    @Test
    public void testEsSeguro() {
        System.out.println("esSeguro");
        int[][] recintoCompleto = null;
        int fila = 0;
        int columna = 0;
        Recinto instance = new Recinto();
        boolean result = instance.esSeguro(instance.getRecintoCompleto(), fila, columna);
        assertFalse(result);
    }

    /**
     * Test of yaExplorado method, of class Recinto.
     */
    @Test
    public void testYaExplorado() {
        System.out.println("yaExplorado");
        int fila = 1;
        int columna = 1;
        Recinto instance = new Recinto();
        instance.crearRecinto(1, 1, 5);
        boolean result = instance.yaExplorado(instance, fila, columna);
        assertTrue(result);
    }

    /**
     * Test of esDestinoPlanta method, of class Recinto.
     */
    @Test
    public void testEsDestinoPlanta() {
        System.out.println("esDestinoPlanta");
        int fila = 1;
        int columna = 1;
        Recinto instance = new Recinto();
        instance.newWallE(new Posicion(1,1), new Posicion(5,5));
        boolean result = instance.esDestinoPlanta(fila, columna);
        assertTrue(result);
    }

    /**
     * Test of esDestinoZonaSegura method, of class Recinto.
     */
    @Test
    public void testEsDestinoZonaSegura() {
        WallE walle = null;
        System.out.println("esDestinoZonaSegura");
        Recinto instance = new Recinto();
        instance.newWallE(new Posicion(1,1),new Posicion(5,5) );
        boolean result = instance.esDestinoZonaSegura(5, 5);
        assertTrue(result);
    }

}

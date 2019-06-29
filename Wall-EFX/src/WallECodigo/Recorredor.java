package WallECodigo;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Recorredor{

    //                                       1: ESTE; 2:SUR ; 3:OESTE ; 4: NORTE
    private static int[][] DIRECCIONES = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public List<Posicion> resolver(Recinto recinto){
        ArrayList<Posicion> ruta = new ArrayList<>();
        if (explorar(recinto, recinto.getPosicionActual().getPosicionFila(),
                recinto.getPosicionActual().getPosicionColumna(), ruta)){
            return ruta;
        }
        return Collections.emptyList();
    }



    private boolean explorar(Recinto recinto, int fila, int columna, List<Posicion> ruta){
        if (!recinto.esSeguro(recinto.getRecintoCompleto(), fila, columna) ||
                recinto.esMuroBomba(recinto, fila, columna) ||  recinto.yaExplorado(recinto, fila, columna)){
            return false;
        }
        ruta.add(new Posicion(fila, columna));
        // SE MARCA COMO VISITADA LA CASILLA
        recinto.getRecintoCompleto()[fila][columna] = 5;
        if (recinto.esDestino(recinto, fila, columna)){
            return true;
        }
        for (int [] direccion: DIRECCIONES){
            Posicion posicion2 = getSiguientePosicion(fila, columna, direccion[0], direccion[1]);
            if (explorar(recinto, posicion2.getPosicionFila(), posicion2.getPosicionColumna(), ruta)){
                return true;
            }
        }

        ruta.remove(ruta.size()-1);
        return false;
    }
    private Posicion getSiguientePosicion(int fila, int columna, int i, int j){
        return new Posicion((fila+i), (columna+j));
    }
}

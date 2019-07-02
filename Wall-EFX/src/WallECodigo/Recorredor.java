package WallECodigo;

import javax.xml.soap.Node;
import java.util.*;

import static java.lang.Math.abs;

public class Recorredor{

    //                                       1: ESTE; 2:SUR ; 3:OESTE ; 4: NORTE
    private static final int[][] DIRECCIONES = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    private static HashMap<Character, Integer> orientaciones = new HashMap<>();

    private static HashMap<Integer, Character> orientacionesReversas = new HashMap<>();

    Recorredor(){
        orientaciones.put('N', 1);
        orientaciones.put('E', 2);
        orientaciones.put('S', 3);
        orientaciones.put('O', 4);
        orientacionesReversas.put(1, 'N');
        orientacionesReversas.put(2, 'E');
        orientacionesReversas.put(3, 'S');
        orientacionesReversas.put(4, 'O');
    }

    /***
     *
     * @param recinto Es el recinto en el que se esta trabajando
     * @param flag Flag de tipo int que indica que tipo de recursion se hara. 1 para recursion Wall-E a Planta, 2 para
     *             recursion Planta a Zona Segura
     * @return Retorna la lista con las posiciones que debe recorrer Wall-E para llegar a la posicion seleccionada
     */
    public List<Posicion> resolver(Recinto recinto, int flag){
        ArrayList<Posicion> ruta = new ArrayList<>();
        if (explorar(recinto, recinto.getPosicionActual().getPosicionFila(),
                recinto.getPosicionActual().getPosicionColumna(), ruta, 1)){
            return ruta;
        }
        return Collections.emptyList();
    }

    public List<Posicion> resolverRapido(Recinto recinto) {
        LinkedList<Posicion> sigVisitar = new LinkedList<>();
        Posicion inicio = recinto.getPosicionActual();
        sigVisitar.add(inicio);

        while (!sigVisitar.isEmpty()) {
            Posicion cur = sigVisitar.remove();

            if (!recinto.esSeguro(recinto.getRecintoCompleto(), cur.getPosicionFila(), cur.getPosicionColumna())
                    || recinto.yaExplorado(recinto, cur.getPosicionFila(), cur.getPosicionColumna())
            ) {
                continue;
            }

            if (recinto.esMuroBomba(recinto, cur.getPosicionFila(), cur.getPosicionColumna())) {
                recinto.getRecintoCompleto()[cur.getPosicionFila()][cur.getPosicionColumna()] = 5;
                continue;
            }

            if (recinto.esDestinoPlanta(cur.getPosicionFila(), cur.getPosicionColumna())) {
                return rutaBacktracking(cur);
            }

            for (int[] direccion : DIRECCIONES) {
                Posicion posicion = new Posicion (cur.getPosicionFila() + direccion[0],
                        cur.getPosicionColumna() + direccion[1], cur);
                sigVisitar.add(posicion);
                recinto.getRecintoCompleto()[cur.getPosicionFila()][cur.getPosicionColumna()] = 5;
            }
        }
        return Collections.emptyList();
    }

    private List<Posicion> rutaBacktracking(Posicion cur) {
        List<Posicion> path = new ArrayList<>();
        Posicion iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.getPosicionPasada();
        }

        return path;
    }
    private boolean explorar(Recinto recinto, int fila, int columna, List<Posicion> ruta, int flag){
        if (!recinto.esSeguro(recinto.getRecintoCompleto(), fila, columna) ||
                recinto.esMuroBomba(recinto, fila, columna) ||  recinto.yaExplorado(recinto, fila, columna)){
            return false;
        }
        ruta.add(new Posicion(fila, columna));
        // SE MARCA COMO VISITADA LA CASILLA
        recinto.getRecintoCompleto()[fila][columna] = 5;
        if (flag == 1){
            if (recinto.esDestinoPlanta(fila, columna)){
                return true;
            }
        }
        else if (flag == 2){
            if (recinto.esDestinoZonaSegura(fila, columna)){
                return true;
            }
        }

        for (int [] direccion: DIRECCIONES){
            Posicion posicion2 = getSiguientePosicion(fila, columna, direccion[0], direccion[1]);
            if (explorar(recinto, posicion2.getPosicionFila(), posicion2.getPosicionColumna(), ruta, flag)){
                return true;
            }
        }

        ruta.remove(ruta.size()-1);
        return false;
    }
    private Posicion getSiguientePosicion(int fila, int columna, int i, int j){
        return new Posicion((fila+i), (columna+j));
    }

    public ArrayList<Character> traductorInstrucciones(List<Posicion> ruta, Recinto recinto){

        char orientacion = recinto.getOrientacion();
        char nextMovimiento = 'N';
        ArrayList<Character> instrucciones = new ArrayList<>();
        for (int i = 1; i < ruta.size(); i++) {
            Posicion posicionI = ruta.get(i-1);
            Posicion posicionS = ruta.get(i);
            if (posicionI.getPosicionColumna() == posicionS.getPosicionColumna()) {
                if (posicionI.getPosicionFila() > posicionS.getPosicionFila()) {
                    nextMovimiento = 'N';
                } else if (posicionI.getPosicionFila() < posicionS.getPosicionFila()) {
                    nextMovimiento = 'S';
                }
            }
            else if (posicionI.getPosicionFila() == posicionS.getPosicionFila()) {
                if (posicionI.getPosicionColumna() > posicionS.getPosicionColumna()) {
                    nextMovimiento = 'O';
                } else if (posicionI.getPosicionColumna() < posicionS.getPosicionColumna()) {
                    nextMovimiento = 'E';
                }
            }
            else{
                nextMovimiento = orientacion;
            }

            int valorOrientacionI = orientaciones.get(orientacion);
            int valorOrientacionS = orientaciones.get(nextMovimiento);

            if (valorOrientacionI == 1 && valorOrientacionS == 4){
                instrucciones.add('I');
            }
            else if (valorOrientacionS == 1 && valorOrientacionI == 4){
                instrucciones.add('D');
            }
            else if (valorOrientacionI == valorOrientacionS){
                instrucciones.add('A');
            }
            else{
                 if (valorOrientacionI > valorOrientacionS){
                     while (valorOrientacionI > valorOrientacionS){
                         instrucciones.add('I');
                         valorOrientacionI--;
                     }
                     instrucciones.add('A');
                 }
                 else if (valorOrientacionI < valorOrientacionS){
                     while (valorOrientacionI < valorOrientacionS){
                         instrucciones.add('D');
                         valorOrientacionI++;
                     }
                     instrucciones.add('A');
                 }
            }
            orientacion = orientacionesReversas.get(valorOrientacionS);
        }
        return instrucciones;
    }
}

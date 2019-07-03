package WallECodigo;

import java.util.*;

public class Recorredor{

    //                                             1: ESTE; 2:SUR ; 3:OESTE ; 4: NORTE
    private static final int[][] DIRECCIONES = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    private static HashMap<Character, Integer> orientaciones = new HashMap<>();

    private static HashMap<Integer, Character> orientacionesReversas = new HashMap<>();

    public Recorredor(){
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
        if (explorar(recinto, recinto.getpActual().getpFila(),
                recinto.getpActual().getpColumna(), ruta, 1)){
            return ruta;
        }
        return Collections.emptyList();
    }
/*
    public List<List<Posicion>> resolverTodas (Recinto recinto, int flag){
        // if destination (bottom-rightmost cell) is found,
        // increment the path count
        if (recinto.getpActual().getpFila() == recinto.getWalle().getPosicionDestinoPlanta().getpFila() &&
                recinto.getpActual().getpColumna() == recinto.getWalle().getPosicionDestinoPlanta().getpColumna() &&
                flag == 1)
        {
            count++;
            return count;
        }

        // mark current cell as visited
        recinto.getRecintoCompleto()[recinto.getpActual().getpFila()][recinto.getpActual().getpColumna()] = 5;

        // if current cell is a valid and open cell
        if (recinto.esSeguro(recinto.getRecintoCompleto(), recinto.getpActual().getpFila(), recinto.getpActual().getpColumna()) &&
                !recinto.esMuroBomba(recinto, recinto.getpActual().getpFila(), recinto.getpActual().getpColumna()) &&
                !recinto.yaExplorado(recinto, recinto.getpActual().getpFila(), recinto.getpActual().getpColumna()))
        {
            // go down (x, y) --> (x + 1, y)
            if (recinto.getpActual().getpFila() + 1 < recinto.getlimiteFilas() && !recinto.yaExplorado(recinto, recinto.getpActual().getpFila() + 1, recinto.getpActual().getpColumna()))
                count = resolverTodas(maze, recinto.getpActual().getpFila() + 1, recinto.getpActual().getpColumna(), recinto.yaExplorado(), count);

            // go up (x, y) --> (x - 1, y)
            if (recinto.getpActual().getpFila() - 1 >= 0 && !recinto.yaExplorado()[recinto.getpActual().getpFila() - 1][recinto.getpActual().getpColumna()])
                count = resolverTodas(maze, recinto.getpActual().getpFila() - 1, recinto.getpActual().getpColumna(), recinto.yaExplorado(), count);

            // go right (x, y) --> (x, y + 1)
            if (recinto.getpActual().getpColumna() + 1 < N && !recinto.yaExplorado()[recinto.getpActual().getpFila()][recinto.getpActual().getpColumna() + 1])
                count = resolverTodas(maze, recinto.getpActual().getpFila(), recinto.getpActual().getpColumna() + 1, recinto.yaExplorado(), count);

            // go left (x, y) --> (x, y - 1)
            if (recinto.getpActual().getpColumna() - 1 >= 0 && !recinto.yaExplorado()[recinto.getpActual().getpFila()][recinto.getpActual().getpColumna() - 1])
                count = resolverTodas(maze, recinto.getpActual().getpFila(), recinto.getpActual().getpColumna() - 1, recinto.yaExplorado(), count);
        }

        // backtrack from current cell and remove it from current path
        recinto.getRecintoCompleto()[recinto.getpActual().getpFila()][recinto.getpActual().getpColumna()] = 5;

        return count;
    }
*/
    public List<Posicion> resolverRapido(Recinto recinto) {
        LinkedList<Posicion> sigVisitar = new LinkedList<>();
        Posicion inicio = recinto.getpActual();
        sigVisitar.add(inicio);

        while (!sigVisitar.isEmpty()) {
            Posicion cur = sigVisitar.remove();

            if (!recinto.esSeguro(recinto.getRecintoCompleto(), cur.getpFila(), cur.getpColumna())
                    || recinto.yaExplorado(recinto, cur.getpFila(), cur.getpColumna())
            ) {
                continue;
            }

            if (recinto.esMuroBomba(recinto, cur.getpFila(), cur.getpColumna())) {
                recinto.getRecintoCompleto()[cur.getpFila()][cur.getpColumna()] = 5;
                continue;
            }

            if (recinto.esDestinoPlanta(cur.getpFila(), cur.getpColumna())) {
                return rutaBacktracking(cur);
            }

            for (int[] direccion : DIRECCIONES) {
                Posicion posicion = new Posicion (cur.getpFila() + direccion[0],
                        cur.getpColumna() + direccion[1], cur);
                sigVisitar.add(posicion);
                recinto.getRecintoCompleto()[cur.getpFila()][cur.getpColumna()] = 5;
            }
        }
        return Collections.emptyList();
    }

    private List<Posicion> rutaBacktracking(Posicion cur) {
        List<Posicion> path = new ArrayList<>();
        Posicion iter = cur;

        while (iter != null) {
            path.add(iter);
            iter = iter.getpPasada();
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
            if (explorar(recinto, posicion2.getpFila(), posicion2.getpColumna(), ruta, flag)){
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
            if (posicionI.getpColumna() == posicionS.getpColumna()) {
                if (posicionI.getpFila() > posicionS.getpFila()) {
                    nextMovimiento = 'N';
                } else if (posicionI.getpFila() < posicionS.getpFila()) {
                    nextMovimiento = 'S';
                }
            }
            else if (posicionI.getpFila() == posicionS.getpFila()) {
                if (posicionI.getpColumna() > posicionS.getpColumna()) {
                    nextMovimiento = 'O';
                } else if (posicionI.getpColumna() < posicionS.getpColumna()) {
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

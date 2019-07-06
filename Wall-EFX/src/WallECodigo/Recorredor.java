package WallECodigo;

import java.util.*;

public class Recorredor{

    //                                             1: ESTE; 2:SUR ; 3:OESTE ; 4: NORTE
    private static final int[][] DIRECCIONES = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    private static HashMap<Character, Integer> orientaciones = new HashMap<>();

    private static HashMap<Integer, Character> orientacionesReversas = new HashMap<>();

    private List<List<Posicion>> todasLasRutas = new ArrayList<>();

    private Posicion destinoProvisorio;

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
     *             recursion Planta a Zona Segura, 3 reservado para busquedas faltantes
     * @return Retorna la lista con las posiciones que debe recorrer Wall-E para llegar a la posicion seleccionada
     */
    public List<Posicion> resolver(Recinto recinto, int flag){
        ArrayList<Posicion> ruta = new ArrayList<>();
        boolean[][] visitado = new boolean[recinto.getlimiteFilas()][recinto.getlimiteColumnas()];
        if (explorar(recinto, recinto.getpActual().getpFila(),
                recinto.getpActual().getpColumna(), ruta, flag, visitado)){
            return ruta;
        }
        return Collections.emptyList();
    }


    public int allRutas(Recinto recinto, int fila, int columna, int count, boolean[][] visitado, List<Posicion> ruta, int flag)
    {
        // if destination (bottom-rightmost cell) is found,
        // increment the path count
        if (recinto.esDestinoPlanta(fila,columna) && flag == 1)
        {
            ruta.add(new Posicion(fila, columna));

            if (count >= 1 && count < 15){
                destinoProvisorio = ruta.get(0);
                List<Posicion> rutaFaltante = resolver(recinto, 3);
                rutaFaltante.remove(rutaFaltante.size()-1);
                List<Posicion> rutaFinal = new ArrayList<>();
                rutaFinal.addAll(rutaFaltante);
                rutaFinal.addAll(ruta);
                copiarRutasAll(rutaFinal);
                ruta.clear();
            }
            else if (count > 15){
                ruta.clear();
            }
            else{
                copiarRutasAll(ruta);
                ruta.clear();
            }
            count++;
            return count;
        }
        else if (recinto.esDestinoZonaSegura(fila,columna) && flag == 2){
            ruta.add(new Posicion(fila, columna));

            if (count >= 1 && count < 15){
                destinoProvisorio = ruta.get(0);
                List<Posicion> rutaFaltante = resolver(recinto, 3);
                rutaFaltante.remove(rutaFaltante.size()-1);
                List<Posicion> rutaFinal = new ArrayList<>();
                rutaFinal.addAll(rutaFaltante);
                rutaFinal.addAll(ruta);
                copiarRutasAll(rutaFinal);
                ruta.clear();
            }
            else if (count > 15){
                ruta.clear();
            }
            else{
                copiarRutasAll(ruta);
                ruta.clear();
            }
            count++;
            return count;
        }

        // mark current cell as visited
        visitado[fila][columna] = true;
        ruta.add(new Posicion(fila,columna));
        // if current cell is a valid and open cell
        if (recinto.esSeguro(recinto.getRecintoCompleto(), fila, columna) && !recinto.esMuroBomba(recinto, fila, columna))
        {
            // go down (x, y) --> (x + 1, y)
            if (fila + 1 < recinto.getlimiteFilas() && !visitado[fila + 1][columna])
                count = allRutas(recinto, fila + 1,columna, count, visitado, ruta, flag);

            // go up (x, y) --> (x - 1, y)
            if (fila - 1 >= 0 && !visitado[fila - 1][columna])
                count = allRutas(recinto, fila - 1, columna, count, visitado, ruta, flag);

            // go right (x, y) --> (x, y + 1)
            if (columna + 1 < recinto.getlimiteColumnas() && !visitado[fila][columna + 1])
                count = allRutas(recinto, fila, columna + 1, count, visitado, ruta, flag);

            // go left (x, y) --> (x, y - 1)
            if (columna - 1 >= 0 && !visitado[fila][columna - 1])
                count = allRutas(recinto, fila, columna - 1, count, visitado, ruta, flag);
        }

        // backtrack from current cell and remove it from current path
        visitado[fila][columna] = false;
        if (!ruta.isEmpty()){
            ruta.remove(ruta.size()-1);
        }

        return count;
    }

    public void copiarRutasAll(List<Posicion> rutaCopiar){
        List<Posicion> listaAux = new ArrayList<>();
        for (Posicion posicion : rutaCopiar){
            listaAux.add(posicion.clonarPosicion());
        }
        todasLasRutas.add(listaAux);
    }
    public List<Posicion> resolverRapido(Recinto recinto, boolean [][] visitado ,int flag) {
        LinkedList<Posicion> sigVisitar = new LinkedList<>();
        Posicion inicio = recinto.getpActual();
        sigVisitar.add(inicio);

        while (!sigVisitar.isEmpty()) {
            Posicion posActual = sigVisitar.remove();

            if (!recinto.esSeguro(recinto.getRecintoCompleto(), posActual.getpFila(), posActual.getpColumna())
                    || recinto.yaExplorado(recinto, posActual.getpFila(), posActual.getpColumna())
            ) {
                continue;
            }

            if (recinto.esMuroBomba(recinto, posActual.getpFila(), posActual.getpColumna())) {
                visitado[posActual.getpFila()][posActual.getpColumna()] = true;
                continue;
            }

            if (recinto.esDestinoPlanta(posActual.getpFila(), posActual.getpColumna()) && flag == 1) {
                return rutaBacktracking(posActual);
            }
            else if (recinto.esDestinoZonaSegura(posActual.getpFila(), posActual.getpColumna()) && flag == 2){
                return rutaBacktracking(posActual);
            }

            for (int[] direccion : DIRECCIONES) {
                Posicion posicion = new Posicion (posActual.getpFila() + direccion[0],
                        posActual.getpColumna() + direccion[1], posActual);
                sigVisitar.add(posicion);
                recinto.getRecintoCompleto()[posActual.getpFila()][posActual.getpColumna()] = 5;
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
    private boolean explorar(Recinto recinto, int fila, int columna, List<Posicion> ruta, int flag, boolean[][] visitado){
        if (!recinto.esSeguro(recinto.getRecintoCompleto(), fila, columna) ||
                recinto.esMuroBomba(recinto, fila, columna) ||  visitado[fila][columna]){
            return false;
        }
        ruta.add(new Posicion(fila, columna));
        // SE MARCA COMO VISITADA LA CASILLA
        visitado[fila][columna] = true;
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
        else if (flag == 3){
            if (destinoProvisorio.getpFila() == fila && destinoProvisorio.getpColumna() == columna){
                return true;
            }
        }

        for (int [] direccion: DIRECCIONES){
            Posicion posicion2 = getSiguientePosicion(fila, columna, direccion[0], direccion[1]);
            if (explorar(recinto, posicion2.getpFila(), posicion2.getpColumna(), ruta, flag, visitado)){
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

    public List<List<Posicion>> getTodasLasRutas() {
        return todasLasRutas;
    }
    
}

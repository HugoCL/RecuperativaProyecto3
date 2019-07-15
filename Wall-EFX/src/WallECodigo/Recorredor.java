package WallECodigo;

import java.io.Serializable;
import java.util.*;

/***
 * Clase encargada de la búsqueda y manejo de las rutas que deberá seguir Wall-E para llegar a sus objetivos.
 * Los algoritmos fueron  basados en ejemplos open source encontrados en internet.
 */
public class Recorredor implements Serializable {

    //                                            1: ESTE; 2:SUR ; 3:OESTE ; 4: NORTE
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
     * Metodo que se encargada de buscar una ruta aleatoria hacia el destino establecido por el flag
     * @param recinto Es el recinto en el que se esta trabajando
     * @param flag Flag de tipo int que indica que tipo de recursion se hara. 1 para recursion Wall-E a Planta, 2 para
     *             recursion Planta a Zona Segura, 3 reservado para busquedas faltantes
     * @return Retorna la lista con las posiciones que debe recorrer Wall-E para llegar a la posicion seleccionada
     */
    public List<Posicion> resolver(Recinto recinto, int flag, boolean[][] visitado){
        ArrayList<Posicion> ruta = new ArrayList<>();
        if (visitado.length != 0){
            visitado = new boolean[recinto.getlimiteFilas()][recinto.getlimiteColumnas()];
        }
        if (explorar(recinto, recinto.getpActual().getpFila(),
                recinto.getpActual().getpColumna(), ruta, flag, visitado)){
            return ruta;
        }
        return Collections.emptyList();
    }

    /***
     * Metodo que se encarga de retornar todas las rutas disponilbles para llegar al destino señalado por el flag (Planta o Zona segura)
     * @param recinto Objeto de tipo Recinto que contiene el laberinto en el que se mueve Wall-E
     * @param filaA Fila inicial de Wall-E y que sirve para llevar el rastro de la posicion de Wall-E
     * @param columnaA
     * Fila inicial de Wall-E y que sirve para llevar el rastro de la posicion de Wall-E
     * @param count Contador inicializado en 0 y que lleva la cuenta de las rutas encontradas mediante recursion
     * @param visitado Matriz de booleanos que sirve para llevar el rastro de las rutas de Wall-E
     * @param ruta Lista de Posiciones que posee la ruta en formación
     * @param flag Flag que indica que clase de recursion se hará. 1 para Wall-E -> Planta, 2 para Planta -> Zona segura
     * @return Retorna todas las rutas encontradas (Max 15) como una lista de listas de Posiciones.
     */
    /***
     * Metodo que se encarga de retornar todas las rutas disponilbles para llegar al destino señalado por el flag (Planta o Zona segura)
     * @param recinto Objeto de tipo Recinto que contiene el laberinto en el que se mueve Wall-E
     * @param fila Fila inicial de Wall-E y que sirve para llevar el rastro de la posicion de Wall-E
     * @param columna Fila inicial de Wall-E y que sirve para llevar el rastro de la posicion de Wall-E
     * @param count Contador inicializado en 0 y que lleva la cuenta de las rutas encontradas mediante recursion
     * @param visitado Matriz de booleanos que sirve para llevar el rastro de las rutas de Wall-E
     * @param ruta Lista de Posiciones que posee la ruta en formación
     * @param flag Flag que indica que clase de recursion se hará. 1 para Wall-E -> Planta, 2 para Planta -> Zona segura
     * @return Retorna todas las rutas encontradas (Max 15) como una lista de listas de Posiciones.
     */
    public int allRutas(Recinto recinto, int fila, int columna, int count, boolean[][] visitado, List<Posicion> ruta, int flag)
    {

        if (count < 10){
            if (recinto.esDestinoPlanta(fila,columna) && flag == 1)
            {
                ruta.add(new Posicion(fila, columna));
                if (count >= 1){
                    destinoProvisorio = ruta.get(0);
                    List<Posicion> rutaFaltante = resolver(recinto, 3, visitado);
                    rutaFaltante.remove(rutaFaltante.size()-1);
                    List<Posicion> rutaFinal = new ArrayList<>();
                    rutaFinal.addAll(rutaFaltante);
                    rutaFinal.addAll(ruta);
                    if (repetidoChecker(rutaFinal)){
                        ruta.clear();
                        count--;
                    }
                    else{
                        copiarRutasAll(rutaFinal);
                        ruta.clear();
                    }
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

                if (count >= 1){
                    destinoProvisorio = ruta.get(0);
                    List<Posicion> rutaFaltante = resolver(recinto, 3, visitado);
                    rutaFaltante.remove(rutaFaltante.size()-1);
                    List<Posicion> rutaFinal = new ArrayList<>();
                    rutaFinal.addAll(rutaFaltante);
                    rutaFinal.addAll(ruta);
                    if (repetidoChecker(rutaFinal)){
                        ruta.clear();
                        count--;
                    }
                    else{
                        copiarRutasAll(rutaFinal);
                        ruta.clear();
                    }
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
            if (recinto.esSeguro(recinto.getRecintoCompleto(), fila, columna) && !recinto.esMuroBomba(recinto, fila, columna)
                    && recinto.getRecintoCompleto()[fila][columna] != 4 && flag == 1)
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
        }
        return count;
    }



    /***
     * Este metodo se encarga de clonar una lista para evitar errores de referencia, para luego añadir la lista a todaslasRutas
     * @param rutaCopiar Lista a clonar
     */
    public void copiarRutasAll(List<Posicion> rutaCopiar){
        List<Posicion> listaAux = new ArrayList<>();
        for (Posicion posicion : rutaCopiar){
            listaAux.add(posicion.clonarPosicion());
        }
        todasLasRutas.add(listaAux);
    }

    /***
     * Metodo encargado de la búsqueda de la ruta más rapida hacia el destino deseado
     * @param recinto Objeto de tipo Recinto que contiene el lugar donde se mueve Wall-E
     * @param visitado Matriz de booleanos que sirve para llevar un registro de por donde se mueve Wall-E
     * @param flag Int que sirve para indicar que tipo de recursión se hará 1 para Wall-E -> Planta, 2 para Planta -> Zona segura
     * @return Retornal la ruta más rapida como una lista de Posiciones
     */
    public List<Posicion> resolverRapido(Recinto recinto, boolean [][] visitado ,int flag) {
        LinkedList<Posicion> sigVisitar = new LinkedList<>();
        Posicion inicio = recinto.getpActual();
        sigVisitar.add(inicio);

        while (!sigVisitar.isEmpty()) {
            Posicion posActual = sigVisitar.remove();

            if (!recinto.esSeguro(recinto.getRecintoCompleto(), posActual.getpFila(), posActual.getpColumna())
                    || visitado[posActual.getpFila()][posActual.getpColumna()]) {
                continue;
            }

            if (recinto.esMuroBomba(recinto, posActual.getpFila(), posActual.getpColumna()) ||
                    recinto.getRecintoCompleto()[posActual.getpFila()][posActual.getpColumna()] == 4 && flag == 1) {
                visitado[posActual.getpFila()][posActual.getpColumna()] = true;
                continue;
            }

            if (recinto.esDestinoPlanta(posActual.getpFila(), posActual.getpColumna()) ||
                    recinto.getRecintoCompleto()[posActual.getpFila()][posActual.getpColumna()] == 3 && flag == 1) {
                return rutaBacktracking(posActual);
            }
            else if (recinto.esDestinoZonaSegura(posActual.getpFila(), posActual.getpColumna()) ||
                    recinto.getRecintoCompleto()[posActual.getpFila()][posActual.getpColumna()] == 4 && flag == 2){
                return rutaBacktracking(posActual);
            }

            for (int[] direccion : DIRECCIONES) {
                Posicion posicion = new Posicion (posActual.getpFila() + direccion[0],
                        posActual.getpColumna() + direccion[1], posActual);
                sigVisitar.add(posicion);
                visitado[posActual.getpFila()][posActual.getpColumna()] = true;
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
                recinto.esMuroBomba(recinto, fila, columna) ||  visitado[fila][columna] ||
                recinto.getRecintoCompleto()[fila][columna] == 4 && flag == 1){
            return false;
        }
        ruta.add(new Posicion(fila, columna));
        visitado[fila][columna] = true;
        if (flag == 1){
            if (recinto.esDestinoPlanta(fila, columna) || recinto.getRecintoCompleto()[fila][columna] == 3){
                return true;
            }
        }
        else if (flag == 2){
            if (recinto.esDestinoZonaSegura(fila, columna)|| recinto.getRecintoCompleto()[fila][columna] == 4){
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

    /***
     * Método utilitario para identificar el siguiente movimiento que hará Wall-E
     * @param fila Fila actual de Wall-E
     * @param columna Columna actual de Wall-E
     * @param i Int que indica el movimiento de la fila
     * @param j Int que indica el movimiento de la columna
     * @return Retorna un objeto de tipo Posicion con la siguiente posicion deseada
     */
    private Posicion getSiguientePosicion(int fila, int columna, int i, int j){
        return new Posicion((fila+i), (columna+j));
    }

    /***
     * Método que traduce una lista de Posiciones a una lista de Char con instrucciones del tipo "A, I o D"
     * @param ruta Lista de Posiciones con la ruta a "traducir"
     * @param recinto Objeto de tipo Recinto donde se mueve Wall-E
     * @return Retorna una lista de Chars con las instrucciones a realizar por Wall-E
     */
    public ArrayList<Character> traductorInstrucciones(List<Posicion> ruta, Recinto recinto){

        char orientacion = recinto.getOrientacion();
        char nextMovimiento = 'N';
        ArrayList<Character> instrucciones = new ArrayList<>();
        for (int i = 1; i < ruta.size(); i++) {
            int mismaOrientacion = 0;
            System.out.println("------------------------------------------------");
            System.out.println("INSTRUCCION "+i);
            Posicion posicionI = ruta.get(i-1);
            Posicion posicionS = ruta.get(i);
            System.out.println("Posicion Fila Inicial "+posicionI.getpFila()+" Posicion Columna "+posicionI.getpColumna());
            System.out.println("Posicion Fila Siguiente "+posicionS.getpFila()+" Posicion Columna SIGUIENTE "+posicionS.getpColumna());
            System.out.println("Mi orientacion es: "+ orientacion);
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
            System.out.println("Mi siguiente movimiento es "+nextMovimiento);
            int valorOrientacionI = orientaciones.get(orientacion);
            int valorOrientacionS = orientaciones.get(nextMovimiento);

            if (valorOrientacionI == 1 && valorOrientacionS == 4){
                instrucciones.add('I');
                System.out.println("Me muevo a la izquierda");
            }
            else if (valorOrientacionS == 1 && valorOrientacionI == 4){
                instrucciones.add('D');
                System.out.println("Me muevo a la D");
            }
            else if (valorOrientacionI == valorOrientacionS){
                instrucciones.add('A');
                System.out.println("Avanzo");
                mismaOrientacion = 1;
            }
            else{
                 if (valorOrientacionI > valorOrientacionS){
                     while (valorOrientacionI > valorOrientacionS){
                         instrucciones.add('I');
                         valorOrientacionI--;
                         System.out.println("Me muevo a la izquierda");
                     }
                 }
                 else {
                     while (valorOrientacionI < valorOrientacionS){
                         instrucciones.add('D');
                         System.out.println("Me muevo a la derecha");
                         valorOrientacionI++;
                     }
                 }
            }
            orientacion = orientacionesReversas.get(valorOrientacionS);
            if (orientacion == nextMovimiento && mismaOrientacion != 1){
                instrucciones.add('A');
                System.out.println("Avanzo");
            }
            System.out.println("Mi orientacion es "+orientacion);
        }
        return instrucciones;
    }

    /***
     * Método que ayuda a detectar rutas repetidas obtenidas del metodo que otorga todas las rutas
     * @param ruta Objeto de tipo posicion que contiene la ruta a comprobar
     * @return Retorna True si la ruta encontrada ya existe, False en caso contrario
     */
    public boolean repetidoChecker (List<Posicion> ruta){
        int repetidoCont = 0;
        if (!todasLasRutas.isEmpty()){
            for (List<Posicion> listaActual : todasLasRutas) {
                if (listaActual.size() == ruta.size()) {
                    for (int j = 0; j < listaActual.size(); j++) {
                        Posicion pActual = listaActual.get(j);
                        if (pActual.getpFila() == ruta.get(j).getpFila() && pActual.getpColumna() == ruta.get(j).getpColumna()) {
                            repetidoCont++;
                        }
                    }
                    if (repetidoCont == listaActual.size()) {
                        return true;
                    }
                    else{
                        repetidoCont = 0;
                    }
                }
            }
        }
        return false;
    }
    public List<List<Posicion>> getTodasLasRutas() {
        return todasLasRutas;
    }

    public void clearAllRutas(){
        todasLasRutas.clear();
    }

}

package salvando_walle;

/***
 *
 * Esta clase es la que se encarga de manejar la matriz en la que se mueve WallE, modificando su posicion y orientacion.
 * @author Hugo
 * @version 1.1
 *
 */
public class Recinto {

    private int limiteFilas;

    private int limiteColumnas;

    private int[][] recintoCompleto = new int [100][100];

    private int posicionWEx;

    private int posicionWEy;

    private char orientacion;

    private WallE walle = new WallE();

    public Recinto() {

    }

    /***
     * Es el metodo que se encarga de construir la matriz que conforma al recinto en el que se mueve WallE
     * @param coordX Coordenada x  de donde se ubicará el valor correspondiente a la informacion del archivo
     * @param coordY Coordenada y  de donde se ubicará el valor correspondiente a la informacion del archivo
     * @param valor Es el valor que poseerá la celda en la matriz, ya sea un 0 (Nada) o un 1 (Si es una bomba)
     */
    public void crearRecinto(int coordX, int coordY, int valor){
        recintoCompleto[coordX][coordY] = valor;
    }

    /***
     * Este metodo le ajusta el destino a WallE, usando el setter de la clase WallE
     * @param m Int que tiene la coordenada X del destino
     * @param n Int que tiene la coordenada Y del destino
     */
    public void newWallE(int m , int n){
        walle.setDestinoX(n);
        walle.setDestinoY(m);
    }

    /***
     * Es el metodo que hace que WallE realice sus instrucciones y se comprueba su correcto funcionamiento
     * @param nInstruc Es el int que contiene el numero de instrucciones a realizar
     */
    public void empezarWallE(int nInstruc){
        char instruccionActual;
        boolean valido;

        // Ciclo for que lee cada una de las instrucciones con el numero de instrucciones dados en el input
        for (int i = 0; i < nInstruc; i++) {

            // Se extrae la instruccion
            instruccionActual = walle.ejecutarInstruccion(i);

            // Se realizan if/else if para ver que orientación tiene WallE y que instrucción hará
            // Si la orientación e instrucción implica movimiento en la matriz, se verifica que el movimiento sea
            // valido
            if (orientacion == 'N' && instruccionActual == 'A'){

                // Se comprueba que el movimiento siguiente no salga de los limites de la matriz
                valido = walle.comprobarValidez(posicionWEx - 1 , getlimiteColumnas());
                if (valido) {

                    // Si ya se verifico que no sale de los limites, se comprueba que no se llegue a una bomba
                    if (recintoCompleto[posicionWEx - 1][posicionWEy] == 0){
                        int nPx = posicionWEx - 1;
                        nuevaPosicionX(nPx);
                    }
                    // Si lleva a una bomba, se informa del error y se termina la ejecucion
                    else{
                        System.out.println("X");
                        System.out.println("Datos de la falla: ¡Bomba! en la coordenada "+(posicionWEx-1)+" "+(posicionWEy));
                        System.exit(0);
                    }
                }
            }
            else if (orientacion == 'N' && instruccionActual == 'I'){
                nuevaOrientacion('O');
            }
            else if (orientacion == 'N' && instruccionActual == 'D'){
                nuevaOrientacion('E');
            }
            else if (orientacion == 'E' && instruccionActual == 'A'){

                // Se comprueba que el movimiento siguiente no salga de los limites de la matriz
                valido = walle.comprobarValidez(posicionWEy + 1 , getlimiteFilas());

                // Si ya se verifico que no sale de los limites, se comprueba que no se llegue a una bomba
                if (valido) {
                    if (recintoCompleto[posicionWEx][posicionWEy + 1] == 0) {
                        int nPy = posicionWEy + 1;
                        nuevaPosicionY(nPy);
                    }
                    // Si lleva a una bomba, se informa del error y se termina la ejecucion
                    else {
                        System.out.println("X");
                        System.out.println("Datos de la falla: ¡Bomba! en la coordenada "+(posicionWEx)+" "+(posicionWEy+1));
                        System.exit(0);
                    }
                }
            }
            else if (orientacion == 'E' && instruccionActual == 'I'){
                nuevaOrientacion('N');
            }
            else if (orientacion == 'E' && instruccionActual == 'D'){
                nuevaOrientacion('S');
            }
            else if (orientacion == 'S' && instruccionActual == 'A'){

                // Se comprueba que el movimiento siguiente no salga de los limites de la matriz
                valido = walle.comprobarValidez(posicionWEx + 1 , getlimiteColumnas());

                // Si ya se verifico que no sale de los limites, se comprueba que no se llegue a una bomba
                if (valido) {
                    if (recintoCompleto[posicionWEx + 1][posicionWEy] == 0){
                        int nPx = posicionWEx + 1;
                        nuevaPosicionX(nPx);
                    }
                    // Si lleva a una bomba, se informa del error y se termina la ejecucion
                    else{
                        System.out.println("X");
                        System.out.println("Datos de la falla: ¡Bomba! en la coordenada "+(posicionWEx+1)+" "+(posicionWEy));
                        System.exit(0);
                    }
                }
            }
            else if (orientacion == 'S' && instruccionActual == 'I'){
                nuevaOrientacion('E');
            }
            else if (orientacion == 'S' && instruccionActual == 'D'){
                nuevaOrientacion('O');
            }
            else if (orientacion == 'O' && instruccionActual == 'A'){

                // Se comprueba que el movimiento siguiente no salga de los limites de la matriz
                valido = walle.comprobarValidez(posicionWEy - 1 , getlimiteFilas());

                // Si ya se verifico que no sale de los limites, se comprueba que no se llegue a una bomba
                if (valido) {
                    if (recintoCompleto[posicionWEx][posicionWEy - 1] == 0) {
                        int nPy = posicionWEy - 1;
                        nuevaPosicionY(nPy);
                    }
                    // Si lleva a una bomba, se informa del error y se termina la ejecucion
                    else {
                        System.out.println("X");
                        System.out.println("Datos de la falla: ¡Bomba! en la coordenada "+(posicionWEx)+" "+(posicionWEy-1));
                        System.exit(0);
                    }
                }
            }
            else if (orientacion == 'O' && instruccionActual == 'I'){
                nuevaOrientacion('S');
            }
            else if (orientacion == 'O' && instruccionActual == 'D'){
                nuevaOrientacion('N');
            }
        }

        // Una vez realizadas todas las instrucciones, se revisa si se llego al destino
        boolean isDestino = walle.comprobarDestino(posicionWEy, posicionWEx);
        if (isDestino){
            System.out.println("E");
            System.exit(0);
        }
        // Si no hubo exito, se informa del fallo
        else
            System.out.println("X");
            System.out.println("Datos de la falla: No se llego al destino");
            System.exit(0);
    }

    /***
     * Este metodo se encarga de cargar las instrucciones en la collecion de instrucciones
     * @param orden Es un int que indica el orden numerico actual de la orden a cargar
     * @param accion Es el char que contiene la instruccion (I, D o A)
     */
    public void cargarInstrucciones(int orden, char accion){
        walle.cargarInstrucciones(orden, accion);
    }
    /***
     * Este metodo define cual es el limite de las filas y para iterar a lo largo de la matriz
     * @param limiF Numero entero que posee el limite de las filas, obtenido del archivo
     */
    public void setLimiteFilas(int limiF) {
        this.limiteFilas = limiF;
    }
    /***
     * Este metodo define cual es el limite de las columnas y para iterar a lo largo de la matriz
     * @param limiC Numero entero que posee el limite de las columnas, obtenido del archivo
     */
    public void setLimiteColumnas(int limiC) {
        this.limiteColumnas = limiC;
    }

    /***
     * Metodo que guarda la nueva orientacion de WallE luego de un movimiento
     * @param orientacion Es el char que posee la nueva orientacion de WallE. Proviene de la clase WallE.
     */
    public void nuevaOrientacion(char orientacion) {
        this.orientacion = orientacion;
    }

    /***
     * Metodo que asigna la nueva ubicacion en el eje Y de WallE en la matriz
     * @param pY Es un int que posee la ubicacion en Y luego de un movimiento, proviente de la clase WallE
     */
    public void nuevaPosicionY(int pY) {
        this.posicionWEy = pY;
    }
    /***
     * Metodo que asigna la nueva ubicacion en el eje X de WallE en la matriz
     * @param pX Es un int que posee la ubicacion en X luego de un movimiento, proviente de la clase WallE
     */
    public void nuevaPosicionX(int pX) {
        this.posicionWEx = pX;
    }

    /***
     * Es el getter que entrega el limite de las filas
     * @return Retorna el limite de las filas
     */
    public int getlimiteFilas() {
        return limiteFilas;
    }

    /***
     * Es el getter que entrega el limite de las columnas
     * @return Retorna el limite de las columnas
     */
    public int getlimiteColumnas() {
        return limiteColumnas;
    }

    /***
     * Getter que entrega la orientacion actual de WallE
     * @return Retorna un char con la orientacion
     */
    public char getOrientacion(){
        return orientacion;
    }

    /***
     * Getter que entrega la posicion actual en X de WallE
     * @return Retornal la posicion X de WallE
     */
    public int getPosicionWEx() {
        return posicionWEx;
    }
    /***
     * Getter que entrega la posicion actual en Y de WallE
     * @return Retornal la posicion Y de WallE
     */
    public int getPosicionWEy(){
        return  posicionWEy;
    }
}

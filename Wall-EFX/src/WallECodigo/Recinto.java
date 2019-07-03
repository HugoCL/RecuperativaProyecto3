package WallECodigo;

/***
 *
 * Esta clase es la que se encarga de manejar la matriz en la que se mueve WallE, modificando su posicion y orientacion.
 * @version 1.1
 *
 */
public class Recinto {

    private int limiteFilas;

    private int limiteColumnas;

    private int[][] recintoCompleto = new int [50][50];

    private Posicion posicionActual;

    private int tamañoFilas;

    private int tamañaColumnas;

    private char orientacion;

    private WallE walle = new WallE();

    public Recinto() {

    }

    /***
     * Es el metodo que se encarga de construir la matriz que conforma al recinto en el que se mueve Wall-E
     * @param valor Es el valor que poseerá la celda en la matriz, ya sea un 0 (Nada) o un 1 (Si es una bomba)
     */
    public void crearRecinto(int Filas, int Columnas, int valor){
        recintoCompleto[Filas][Columnas] = valor;
        tamañoFilas = Filas;
        tamañaColumnas = Columnas;
    }

    /***
     * Este metodo le ajusta el destino a WallE, usando el setter de la clase WallE
     * @param fila Int que tiene la coordenada de la fila del destino
     * @param columna Int que tiene la coordenada de la columna del destino
     */
    public void newWallE(int fila, int columna){
        Posicion posicionD = new Posicion(fila, columna);
        posicionD.setPosicionColumna(columna);
        posicionD.setPosicionFila(fila);
        walle.setPosicionDestinoPlanta(posicionD);
    }

    boolean esMuroBomba(Recinto recinto, int fila, int columna){
        return (recinto.getRecintoCompleto()[fila][columna] == 1);
    }
    boolean esSeguro(int [][] recintoCompleto, int fila, int columna) {
        return (fila >= 0 && fila < limiteFilas && columna >= 0 && columna < limiteColumnas && recintoCompleto[fila][columna] != 1);
    }

    boolean yaExplorado (Recinto recinto, int fila, int columna){
        return (recinto.getRecintoCompleto()[fila][columna] == 5);
    }

    boolean esDestinoPlanta(int fila, int columna){
        return (getWalle().getPosicionDestinoPlanta().getPosicionFila() == fila &&
                getWalle().getPosicionDestinoPlanta().getPosicionColumna() == columna);
    }

    boolean esDestinoZonaSegura (int fila, int columna){
        return (getWalle().getPosicionDestinoZonaSegura().getPosicionFila() == fila &&
                getWalle().getPosicionDestinoZonaSegura().getPosicionColumna() == columna);
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

    public int[][] getRecintoCompleto() {
        return recintoCompleto;
    }
 
    public Posicion getPosicionActual() {
        return posicionActual;
    }

    public void setPosicionActual(Posicion posicionActual) {
        this.posicionActual = posicionActual;
    }

    public WallE getWalle() {
        return walle;
    }   
}

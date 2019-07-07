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

    private int[][] recintoCompleto = new int [20][30];

    private Posicion pActual;

    private char orientacion;

    public Recinto(int limiteFilas, int limiteColumnas, char orientacion) {
        this.limiteFilas = limiteFilas;
        this.limiteColumnas = limiteColumnas;
        this.orientacion = orientacion;
    }

    public Recinto(){        
    }
    
    /***
     * Es el metodo que se encarga de construir la matriz que conforma al recinto en el que se mueve Wall-E
     * @param valor Es el valor que poseerá la celda en la matriz, ya sea un 0 (Nada) o un 1 (Si es una bomba)
     */
    public void crearRecinto(int Filas, int Columnas, int valor){
        recintoCompleto[Filas][Columnas] = valor;        
    }

    /***
     * Este metodo crea un Wall-E (Singleton) con sus destinos
     * @param destinoPlanta Objeto de tipo Posicion que contiene la ubicacion de la planta
     * @param destinoZonaSegura Objeto de tipo Posicion que contiene la ubicacion de la zona segura
     */
    public void newWallE(Posicion destinoPlanta, Posicion destinoZonaSegura){
        WallE.getWallE(destinoPlanta, destinoZonaSegura);
    }

    public WallE returnWallE(){
        return WallE.getWallE();
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

    public boolean esDestinoPlanta(int fila, int columna){
        return (returnWallE().getpDPlanta().getpFila() == fila &&
                returnWallE().getpDPlanta().getpColumna() == columna);
    }

    boolean esDestinoZonaSegura (int fila, int columna){
        return (returnWallE().getpDZonaSegura().getpFila() == fila &&
                returnWallE().getpDZonaSegura().getpColumna() == columna);
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

    public Posicion getpActual() {
        return pActual;
    }

    public void setpActual(Posicion pActual) {
        this.pActual = pActual;
    }


    public void setRecintoCompleto(int[][] recintoCompleto) {
        this.recintoCompleto = recintoCompleto;
    }
    
}

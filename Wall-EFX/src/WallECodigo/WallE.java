package WallECodigo;

/***
 * En esta clase de Walle se comprueban sus movimientos, se guardan y se ejectutan sus instrucciones, ademas se guarda el destino al que debe llegar.
 * @version 1.1
 *
 */
public class WallE {

    private boolean isDestino = true;

    private boolean isMovimientoValido = true;

    private Instrucciones[] instruccion = new Instrucciones[40];

    private Posicion posicionDestino;


    public WallE() {
    }

    /***
     * Este metodo tiene como objetivo el comprobar si el movimiento realizado llevo a WallE a su destino
     * @param x Es un int que posee la coordenada X de la ultima posicion de WallE
     * @param y Es un int que posee la coordenada Y de la ultima posicion de WallE
     * @return Se retorna true si ya se ha llegado al destino y false en caso contrario
     */
    public boolean comprobarDestino(int x , int y) {
        if (x == posicionDestino.getPosicionColumna() && y == posicionDestino.getPosicionFila()){
            return isDestino;
        }
        else{
            return false;
        }
    }

    /***
     * El metodo se encarga de realizar una comprobacion de la "integridad" de WallE, por si sale de los limites o
     * si toco una bomba
     * @return Se retorna true si el movimiento es valido (No es una bomba o limite) y false en caso contrario
     * @param posicionWE Es un int que contiene la posicion en la que WallE estará despues del movimiento
     * @param limite Es un int que contiene el limite de las columnas/filas
     */
    public boolean comprobarValidez(int posicionWE, int limite) {
        if (posicionWE < 0 || posicionWE >= limite){
            System.out.println("X");
            System.out.println("Datos de la falla: Fuera de los limites");
            System.exit(0);
        }
        return isMovimientoValido;
    }

    /***
     * Este metodo se encarga de hacer las acciones dadas por el archivo, cambiando la posicion y/o orientacion de WallE
     * @param ordenActual Es un entero que indica la instruccion a realizar, dada por un orden numérico
     * @return Se retorna la instruccion que se encuentra en la X posicion de la collecion
     */
    public char ejecutarInstruccion(int ordenActual) {
        return instruccion[ordenActual].getInstruccion();
    }

    /***
     * Este metodo se encarga de cargar las instrucciones en la collecion de instrucciones
     * @param orden Es un int que indica el orden numerico actual de la orden a cargar
     * @param accion Es el char que contiene la instruccion (I, D o A)
     */
    public void cargarInstrucciones(int orden, char accion){
        instruccion[orden] = new Instrucciones(accion);
    }


    public Posicion getPosicionDestino() {
        return posicionDestino;
    }

    public void setPosicionDestino(Posicion posicionDestino) {
        this.posicionDestino = posicionDestino;
    }
}

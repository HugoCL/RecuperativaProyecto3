package WallECodigo;

/***
 * En esta clase de Walle se comprueban sus movimientos, se guardan y se ejectutan sus instrucciones, ademas se guarda el destino al que debe llegar.
 * @version 1.1
 *
 */
public class WallE {

    private Instrucciones[] instruccion = new Instrucciones[40];

    private Posicion pDPlanta;

    private Posicion posicionDestinoZonaSegura;

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


    public Posicion getPosicionDestinoPlanta() {
        return pDPlanta;
    }

    public void setPosicionDestinoPlanta(Posicion posicionDestinoPlanta) {
        this.pDPlanta = posicionDestinoPlanta;
    }

    public Posicion getPosicionDestinoZonaSegura() {
        return posicionDestinoZonaSegura;
    }

    public void setPosicionDestinoZonaSegura(Posicion posicionDestinoZonaSegura) {
        this.posicionDestinoZonaSegura = posicionDestinoZonaSegura;
    }
}

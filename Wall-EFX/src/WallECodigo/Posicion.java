package WallECodigo;

import java.io.Serializable;

/***
 * Clase que se encarga del manejo de las coordenadas (Filas y columnas) del programa
 */
public class Posicion implements Serializable {

    private int pFila;
    private int pColumna;
    private Posicion pPasada;

    public Posicion(int fila, int columna){
        this.pFila = fila;
        this.pColumna = columna;
    }

    public Posicion (int fila, int columna, Posicion PosPasada){
        this.pPasada = PosPasada;
        this.pFila = fila;
        this.pColumna = columna;
    }

    public Posicion (){

    }

    /***
     * Método que realiza una copia de esta posición
     * @return Retorna un Objeto de tipo Posicion con los mismos atributos
     */
    public Posicion clonarPosicion(){
        return new Posicion(this.pFila, this.pColumna);
    }

    public int getpFila() {
        return pFila;
    }

    public void setpFila(int pFila) {
        this.pFila = pFila;
    }

    public int getpColumna() {
        return pColumna;
    }

    public void setpColumna(int pColumna) {
        this.pColumna = pColumna;
    }

    public Posicion getpPasada() {
        return pPasada;
    }


}

package WallECodigo;

public class Posicion {

    public int pFila;
    public int pColumna;
    public Posicion pPasada;

    public Posicion(int fila, int columna){
        this.pFila = fila;
        this.pColumna = columna;
    }

    public Posicion (int fila, int columna, Posicion PosPasada){
        this.pPasada = PosPasada;
        this.pFila = fila;
        this.pColumna = columna;
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

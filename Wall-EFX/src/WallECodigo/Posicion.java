package WallECodigo;

public class Posicion {

    public int posicionFila;
    public int posicionColumna;
    public Posicion PosicionPasada;

    Posicion(int fila, int columna){
        this.posicionFila = fila;
        this.posicionColumna = columna;
    }

    Posicion (int fila, int columna, Posicion PosPasada){
        this.PosicionPasada = PosPasada;
        this.posicionFila = fila;
        this.posicionColumna = columna;
    }

    public int getPosicionFila() {
        return posicionFila;
    }

    public void setPosicionFila(int posicionFila) {
        this.posicionFila = posicionFila;
    }

    public int getPosicionColumna() {
        return posicionColumna;
    }

    public void setPosicionColumna(int posicionColumna) {
        this.posicionColumna = posicionColumna;
    }

    public Posicion getPosicionPasada() {
        return PosicionPasada;
    }
}

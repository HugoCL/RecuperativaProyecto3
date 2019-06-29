package WallECodigo;

public class Posicion {

    public int posicionFila;
    public int posicionColumna;

    Posicion(int fila, int columna){
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
}

package WallECodigo;

/***
 * En esta clase de Walle se comprueban sus movimientos, se guardan y se ejectutan sus instrucciones, ademas se guarda el destino al que debe llegar.
 * @version 1.1
 *
 */
public class WallE {

    private Posicion pDPlanta;

    private Posicion pDZonaSegura;

    public Posicion getPDPlanta() {
        return pDPlanta;
    }

    public void setPosicionDestinoPlanta(Posicion posicionDestinoPlanta) {
        this.pDPlanta = posicionDestinoPlanta;
    }

    public Posicion getpDZonaSegura() {
        return pDZonaSegura;
    }

    public void setpDZonaSegura(Posicion pDZonaSegura) {
        this.pDZonaSegura = pDZonaSegura;
    }
}

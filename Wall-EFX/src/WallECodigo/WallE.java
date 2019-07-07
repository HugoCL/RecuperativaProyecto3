package WallECodigo;

import java.io.Serializable;

public class WallE implements Serializable {

    private Posicion pDPlanta;

    private Posicion pDZonaSegura;

    private static WallE myWallE;

    /***
     * Método del Singleton que permite crear una instacia de Wall-E si no existe y la retorna en casi de que ya este creada
     * @param planta Objeto de tipo Posicion que contiene la ubicacion de la Planta
     * @param zonaSegura Objeto de tipo Posicion que contiene la ubicacion de la Zona Segura
     * @return Retorna la instancia creada o existente de Wall-E
     */

    public static synchronized WallE getWallE (Posicion planta, Posicion zonaSegura){
        if (myWallE == null){
            myWallE = new WallE(planta, zonaSegura);
        }
        return myWallE;
    }

    /***
     * Método del Singleton que permite obtener la instacia de Wall-E
     * @return Retorna la instancia creada o existente de Wall-E
     */
    public static WallE getWallE(){
        if (myWallE == null){
            return null;
        }
        return myWallE;
    }

    /***
     * Constructor privado que evita que se creen instacias extra de Wall-E
     * @param planta Objeto de tipo Posicion que contiene la ubicacion de la Planta
     * @param zonaSegura Objeto de tipo Posicion que contiene la ubicacion de la Zona Segura
     */
    private WallE(Posicion planta, Posicion zonaSegura){
        this.pDPlanta = planta;
        this.pDZonaSegura = zonaSegura;
    }


    public Posicion getpDPlanta() {
        return pDPlanta;
    }

    public Posicion getpDZonaSegura() {
        return pDZonaSegura;
    }

}

package WallECodigo;

import java.io.Serializable;

public class WallE implements Serializable {

    private Posicion pDPlanta;

    private Posicion pDZonaSegura;

    private static WallE myWallE;

    public static WallE getWallE (Posicion planta, Posicion zonaSegura){
        if (myWallE == null){
            myWallE = new WallE(planta, zonaSegura);
        }
        return myWallE;
    }

    public static WallE getWallE(){
        if (myWallE == null){
            return null;
        }
        return myWallE;
    }

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

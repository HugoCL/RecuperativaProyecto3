package WallECodigo;

import java.io.*;

/***
 * Clase que se encargar de realizar el proceso de serializar y des-serializar
 */
public class Serializador {

    /***
     * Método encargado de serializar los datos relevantes para continuar el programa
     * @param recinto Recinto a serializar
     * @throws FileNotFoundException Se lanza esta excepción en caso de que no se encuentre el archivo
     */

    public void serializar(Recinto recinto) throws FileNotFoundException {
        FileOutputStream salida =  new FileOutputStream("Serializacion.out");
        try{
            ObjectOutputStream salidaObj = new ObjectOutputStream(salida);
            salidaObj.writeObject(recinto);
            salidaObj.writeObject(WallE.getWallE());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***
     * Métddo que des-serializa la información contenida en el archivo generado anteriormente
     * @return Retorna el recinto des-serializado. No retorna Wall-E porque se pasan los datos al Singleton
     * @throws FileNotFoundException Se lanza esta excepción en caso de que no se encuentre el archivo
     */
    public Recinto desSerializar() throws FileNotFoundException {
        FileInputStream entrada = new FileInputStream("serializacion.out");
        try {
            ObjectInputStream in = new ObjectInputStream(entrada);
            Recinto recinto = (Recinto) in.readObject();
            WallE wal = (WallE) in.readObject();
            //Se crea el singleton del WallE con los datos serializados
            WallE.getWallE(wal.getpDPlanta(), wal.getpDZonaSegura());
            // Se retorna el recinto serializado
            return recinto;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


}

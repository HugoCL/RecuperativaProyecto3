package WallECodigo;

import java.io.*;

public class Serializador {

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
        }
        return null;
    }


}

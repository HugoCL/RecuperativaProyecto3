/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WallECodigo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author 19473409
 */
public class Serializable {
    
    /**
     * Serializa los datos ingresados a un archivo definido. 
     * @param a Datos a serializar.
     * @param direccion nombre del archivo txt en donde se serializara la informacion.
     */
    public void serializar(Object a, String direccion){
        try{
            FileOutputStream fs = new FileOutputStream(direccion);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(a);
            os.close();
            
        }catch(Exception e ){
            System.out.println("Error en la serializacion");
        }
    }
    
    /**
     * Lee el un archivo en la carpeta raiz del programa y devuelve un dato de tipo
     * Object.
     * @param direccion Direccion del archivo .txt que se leera.
     * @return Dato leido desde el archivo .txt (se debe hacer el casteo correspondiente).
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public Object leerSerializable(String direccion) throws IOException, ClassNotFoundException{
        try{
            FileInputStream fis = new FileInputStream(direccion);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object archivo = (Object) ois.readObject();
            ois.close();
            return archivo;
        } catch(FileNotFoundException e){
            e.printStackTrace();
            return null;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}

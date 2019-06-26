package salvando_walle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/***
 * Esta clase realiza la lectura y el paso de datos a las demas clases
 * @author Hugo
 * @version 1.1
 *
 */
public class Lectura {

    private String nombreArchivo = "WallE.in";

    /***
     * Este metodo se encarga de la lectura del archivo y se pasar sus datos a las demas clases
     *
     */
    public void iniciarLectura() throws FileNotFoundException {

        Scanner entrada = new Scanner(new File(nombreArchivo));
        entrada.useDelimiter("\\s");

        //Se extraen los limites del recinto y se comprueba que estén dentro de los valores indicados
        int m, n;
        m = entrada.nextInt();
        n = entrada.nextInt();
        if (m > 100 || n > 100) {
            System.out.println("¡Los limites son mayores a 100! (M y N < 100) Ejecucion terminada incorrectamente");
            System.exit(0);
        }
        else if (m < 1 || n < 1) {
            System.out.println("¡Los limites deben ser mayores a 1 (M y N > 1) Ejecucion terminada incorrectamente");
            System.exit(0);
        }
        // Si los valor son correctos, se procede con la extraccion de los demas datos
        else {
            entrada.next();

            // Se crea el recinto y se empieza a pasar el valor de cada casilla
            Recinto recinto = new Recinto();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    recinto.crearRecinto(i, j, entrada.nextInt());
                }
                entrada.next();
            }
            // Se especifican los limites en la clase recinto
            recinto.setLimiteFilas(n);
            recinto.setLimiteColumnas(m);

            // Se capturan los datos de la posicion de WallE
            m = entrada.nextInt();
            n = entrada.nextInt();
            recinto.nuevaPosicionX(n - 1);
            recinto.nuevaPosicionY(m - 1);
            entrada.next();

            //Se capturan los datos del destino de WallE
            m = entrada.nextInt();
            n = entrada.nextInt();
            recinto.newWallE(m - 1, n - 1);
            entrada.next();

            // Se lee la orientacion inicial de WallE
            char orientacion;
            orientacion = entrada.next().charAt(0);

            // Se comprueba que sea una orientacion valida
            if (orientacion != 'N' && orientacion != 'E' && orientacion != 'S' && orientacion != 'O') {
                System.out.println("La orientacion no es correcta, debe ser N, E, S u O. Ejecucion terminada incorrectamente");
                System.exit(0);
            }

            // Se continua si es valida
            else {

                // Se guarda la orientacion en Recinto
                recinto.nuevaOrientacion(orientacion);
                entrada.next();

                // Se lee el numero de instrucciones y se comprueba que no sea mayor a 40
                int nInstrucciones = entrada.nextInt();
                if (nInstrucciones > 40){
                    System.out.println("Se supero el limite de instrucciones (>40). Ejecucion terminada incorrectamente");
                }

                // Se continua si es correcto
                else{
                    entrada.next();

                    // Se captura la primera instruccion
                    char priIns = entrada.next().charAt(0);
                    String ins = entrada.nextLine();

                    // Se captura el resto de las instrucciones y se guarda en un String separado por una RegEx
                    String[] separado = ins.split("\\s+");

                    // Se crea la coleccion de instrucciones de WallE
                    for (int i = 0; i < nInstrucciones; i++) {
                        if (i > 0) {
                            if (separado[i].charAt(0) != 'A' && separado[i].charAt(0) != 'I' && separado[i].charAt(0) != 'D'){
                                System.out.println("Hay instrucciones no validads. Ejecucion terminada incorrectamente");
                                System.exit(0);
                            }
                            else{
                                recinto.cargarInstrucciones(i, separado[i].charAt(0));
                            }
                        }
                        else {
                            if (priIns != 'A' && priIns != 'I' && priIns != 'D'){
                                System.out.println("Hay instrucciones no validas. Ejecucion terminada incorrectamente");
                                System.exit(0);
                            }
                            else{
                                recinto.cargarInstrucciones(i, priIns);
                            }
                        }
                    }

                    // Se cierra la lectura
                    entrada.close();

                    // Se comienza el funcionamiento de WallE
                    recinto.empezarWallE(nInstrucciones);
                }
            }
        }
    }
}

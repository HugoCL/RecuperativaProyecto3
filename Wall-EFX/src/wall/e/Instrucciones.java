package salvando_walle;

/***
 * Esta clase se encarga de guardar una instruccion y de retornarla.
 * @author Hugo
 * @version 1.1
 *
 */
public class Instrucciones {

    private char instruccion;

    /***
     * Es un constructor parametrizado que se encarga de cargar la instrucción en la variable "instruccion"
     * @param accion Es el char que contiene la instruccion a guardar
     */
    Instrucciones(char accion){
        setInstruccion(accion);
    }

    /***
     * Este setter guarda la instruccion en la variable "instruccion"
     * @param instr Es la instruccion a guardar, la misma que "accion"
     */
    public void setInstruccion(char instr) {
        this.instruccion = instr;
    }

    /***
     * Es el getter que devuelve la instruccion de esta clase
     * @return Se devuelve la instruccion contenida en esta clase
     */
    public char getInstruccion() {
        return instruccion;
    }
}

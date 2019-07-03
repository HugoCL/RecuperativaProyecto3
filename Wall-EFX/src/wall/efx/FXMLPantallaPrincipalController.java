package wall.efx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Carolyn
 */
public class FXMLPantallaPrincipalController implements Initializable {

    @FXML private AnchorPane root;
    @FXML private Accordion acordeon;
    @FXML private Button agregarWalle;
    @FXML private Button agregarPlanta;
    @FXML private Button agregarPunto;
    @FXML private Button agregarBombas;
    @FXML private GridPane grid;    //MATRIZ
    private final FXMLDatosController datosController=new FXMLDatosController();
    private final int filas=FXMLDatosController.f;
    private final int columnas=FXMLDatosController.c;
    private final int bombas=FXMLDatosController.b;
    private boolean walle=true, planta=true, punto=true, bomba=true;
    
    public void crearTablero(){
        //System.out.println(filas + "-"+columnas);
        grid.getColumnConstraints().get(0).setHgrow(Priority.SOMETIMES);
        grid.getColumnConstraints().get(0).setFillWidth(true);
        grid.getColumnConstraints().get(0).setMaxWidth(1000/(columnas-1));
        grid.getColumnConstraints().get(0).setPrefWidth(1000/(columnas-1));
        grid.getColumnConstraints().get(0).setMinWidth(1000/(columnas-1));
        grid.getRowConstraints().get(0).setVgrow(Priority.SOMETIMES);
        grid.getRowConstraints().get(0).setFillHeight(true);
        grid.getRowConstraints().get(0).setMaxHeight(550/(filas-1));
        grid.getRowConstraints().get(0).setMinHeight(550/(filas-1));
        grid.getRowConstraints().get(0).setPrefHeight(550/(filas-1));
        grid.setGridLinesVisible(true);
        grid.setHgap(2);
        grid.setVgap(2);
        grid.setMaxHeight(Double.MAX_VALUE);
        grid.setMaxWidth(Double.MAX_VALUE);
        //AGREGAR LAS FILAS QUE FALTEN
        if(filas>=10){
            for (int i = 0; i < filas-1; i++) {
                RowConstraints fila=new RowConstraints();
                fila.setFillHeight(true);
                fila.setVgrow(Priority.SOMETIMES);
                int alto=550/(filas-1);
                fila.setMaxHeight(alto);
                fila.setMinHeight(alto);
                fila.setPrefHeight(alto);
                grid.getRowConstraints().add(fila);
            }
        }
        //AGREGAR LAS COLUMNAS QUE FALTEN
        if(columnas>=10){
            for (int i = 0; i < columnas-1; i++) {
                ColumnConstraints columna=new ColumnConstraints();
                columna.setFillWidth(true);
                columna.setHgrow(Priority.SOMETIMES);
                double ancho=1000/(columnas-1);
                columna.setMaxWidth(ancho);
                columna.setMinWidth(ancho);
                columna.setPrefWidth(ancho);
                grid.getColumnConstraints().add(columna);
            }
        } 
        
        //RELLENA CON COLOR LAS CELDAS
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                AnchorPane an=new AnchorPane();
                an.setStyle("-fx-background-color: brown;");
                an.setPrefSize(1000/(columnas-1), 550/(filas-1));
                an.setId("anchor "+i+" "+j);
                //System.out.println(an.getId());
                grid.add(an, j, i);
            }
        }
    }
    
    @FXML
    public void mostrarRutas(){
        
    }
    
    @FXML
    public void ubicarWalle(ActionEvent e) throws InterruptedException{
        if(walle){  //SI AUN ESTA DISPONIBLE AGREGAR A WALL E
            walle=false;
            agregarWalle.setDisable(true);
            agregarPlanta.setDisable(true);
            agregarPunto.setDisable(true);
            agregarBombas.setDisable(true);
            System.out.println("BOTON CLICKEADO");
            //AGREGAR EL CLICK DEL MOUSE
            //Thread.sleep(3000);
            //VUELVO A PONER DISPONIBLES LOS BOTONES QUE AUN LO ESTABAN
            if(planta){
                agregarPlanta.setDisable(false);
            }
            if(punto){
                agregarPunto.setDisable(false);
            }
            if(bomba){
                agregarBombas.setDisable(false);
            }
        }
        
    }
    
    @FXML
    public void click(MouseEvent evento){
        Node source=(Node)evento.getSource();
        Integer columna=GridPane.getColumnIndex(source);
        Integer fila=GridPane.getRowIndex(source);        
        System.out.println(columna +" "+fila);

        boolean bandera=true;
        
        if (!walle && bandera) {
            // colocar imagen de walle
            System.out.print("walle colocado en la posicion: ");
            System.out.println(columna+" "+fila);
            bandera=false;
        }
        
        if (!planta && bandera) {
            // colocar imagen de planta
            System.out.print("planta colocado en la posicion: ");
            System.out.println(columna +" "+fila);
            bandera=false;
        }
        
        if (!punto && bandera) {
            // colocar imagen de punto
            System.out.print("punto colocado en la posicion: ");
            System.out.println(columna +" "+fila);
            bandera=false;
        }
        
        if (!bomba && bandera) {
            // colocar imagen de bomba
            System.out.print("bomba colocado en la posicion: ");
            System.out.println(columna +" "+fila);
            bandera=false;
        }
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crearTablero();
        MouseEvent event;
    }    
    
}

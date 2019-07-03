package wall.efx;

import WallECodigo.Main;
import static WallECodigo.Main.recinto;
import WallECodigo.Posicion;
import WallECodigo.Recinto;
import WallECodigo.Recorredor;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML private TitledPane rutaAleatoria;
    @FXML private TitledPane rutaTodas;
    @FXML private TitledPane rutaMejor;
    @FXML private Button agregarWalle;
    @FXML private Button agregarPlanta;
    @FXML private Button agregarPunto;
    @FXML private Button agregarBombas;
    @FXML private GridPane grid;    //MATRIZ
    @FXML private ImageView walleImagen;
    @FXML private ImageView plantaImagen;
    @FXML private ImageView puntoImagen;
    @FXML private ImageView bombaImagen;
    private final FXMLDatosController datosController=new FXMLDatosController();
    private final int filas=FXMLDatosController.f;
    private final int columnas=FXMLDatosController.c;
    private final int bombas=FXMLDatosController.b;
    private boolean walle=true, planta=true, punto=true, bomba=true;
    public static Recinto recinto = new Recinto();
    
    @FXML
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
                //GridPane.setConstraints(an, j, i);
                //System.out.println(an.getId());
                grid.add(an, j, i);
            }
        }
    }
    
    @FXML
    public void mostrarRutas(){
        Recorredor r=new Recorredor();
        List<Posicion> instrucciones=new ArrayList<>();
        instrucciones=r.resolver(Main.recinto, 1);
        if(instrucciones.isEmpty()){
            rutaAleatoria.setText("NO HAY RUTA.");
        }
        else{
            for (int i = 0; i < instrucciones.size(); i++) {
                rutaAleatoria.setText(instrucciones.get(i).toString()+"\n");
            }
        }
    }
    
    @FXML
    public void ubicarWalle(ActionEvent e){
        if(walle){  //SI AUN ESTA DISPONIBLE AGREGAR A WALL E
            walle=false;
            agregarWalle.setDisable(true);
            agregarPlanta.setVisible(false);
            agregarPunto.setVisible(false);
            agregarBombas.setVisible(false);
            System.out.println("BOTON CLICKEADO");
            //AGREGAR EL CLICK DEL MOUSE
        }
    }
    @FXML
    public void ubicarPlanta(ActionEvent e){
        if(planta){  //SI AUN ESTA DISPONIBLE AGREGAR A WALL E
            planta=false;
            agregarWalle.setVisible(false);
            agregarPlanta.setDisable(true);
            agregarPunto.setVisible(false);
            agregarBombas.setVisible(false);
            System.out.println("BOTON CLICKEADO");
            //AGREGAR EL CLICK DEL MOUSE
        }
    }
    @FXML
    public void ubicarPunto(ActionEvent e){
        if(punto){  //SI AUN ESTA DISPONIBLE AGREGAR A WALL E
            punto=false;
            agregarWalle.setVisible(false);
            agregarPlanta.setVisible(false);
            agregarPunto.setDisable(true);
            agregarBombas.setVisible(false);
            System.out.println("BOTON CLICKEADO");
            //AGREGAR EL CLICK DEL MOUSE
        }
    }
    @FXML
    public void ubicarBombas(ActionEvent e){
        if(planta){  //SI AUN ESTA DISPONIBLE AGREGAR A WALL E
            planta=false;
            agregarWalle.setVisible(false);
            agregarPlanta.setVisible(false);
            agregarPunto.setVisible(false);
            agregarBombas.setDisable(true);
            System.out.println("BOTON CLICKEADO");
            //AGREGAR EL CLICK DEL MOUSE
        }
    }
    
    @FXML
    public void click(MouseEvent evento){
        double x,y;
        int columna, fila,anch=1000/(columnas),alt=550/filas;
        x=evento.getX();
        y=evento.getY();
        columna=(int) (x/1000/(columnas-1));
        fila=(int)(y/550/(filas-1));
        boolean bandera=true;
        if (!walle && bandera) {
            walle=true;
            Posicion posicion = new Posicion(fila, columna);
            recinto.setpActual(posicion);
            recinto.getRecintoCompleto()[fila][columna]=2;
            // colocar imagen de walle
            walleImagen.setX(x);
            walleImagen.setY(y);
            walleImagen.setFitHeight(550/(filas-1));
            walleImagen.setFitWidth(1000/(columnas-1));
            walleImagen.setVisible(true);
            bandera=false;
            agregarWalle.setVisible(true);
            agregarPlanta.setVisible(true);
            agregarPunto.setVisible(true);
            agregarBombas.setVisible(true);
        }
        
        if (!planta && bandera) {
            planta=true;
            Posicion posicion = new Posicion(fila, columna);
            recinto.setpActual(posicion);
            recinto.getRecintoCompleto()[fila][columna]=2;
            // colocar imagen de planta
            plantaImagen.setX(x);
            plantaImagen.setY(y);
            plantaImagen.setFitHeight(550/(filas-1));
            plantaImagen.setFitWidth(1000/(columnas-1));
            plantaImagen.setVisible(true);
            System.out.print("planta colocado en la posicion: "+x+" "+y);
            //System.out.println(columna +" "+fila);
            bandera=false;
            agregarWalle.setVisible(true);
            agregarPlanta.setVisible(true);
            agregarPunto.setVisible(true);
            agregarBombas.setVisible(true);
        }
        
        if (!punto && bandera) {
            punto=true;
            Main.recinto.getRecintoCompleto()[fila][columna]=4;
            // colocar imagen de punto
            puntoImagen.setX(x);
            puntoImagen.setY(y);
            puntoImagen.setFitHeight(550/(filas-1));
            puntoImagen.setFitWidth(1000/(columnas-1));
            puntoImagen.setVisible(true);
            System.out.print("punto colocado en la posicion: ");
            //System.out.println(columna +" "+fila);
            bandera=false;
            agregarWalle.setVisible(true);
            agregarPlanta.setVisible(true);
            agregarPunto.setVisible(true);
            agregarBombas.setVisible(true);
        }
        
        if (!bomba && bandera) {
            bomba=true;
            Main.recinto.getRecintoCompleto()[fila][columna]=1;
            // colocar imagen de bomba
            bombaImagen.setX(x);
            bombaImagen.setY(y);
            bombaImagen.setFitHeight(550/(filas-1));
            bombaImagen.setFitWidth(1000/(columnas-1));
            bombaImagen.setVisible(true);
            System.out.print("bomba colocado en la posicion: ");
            //System.out.println(columna +" "+fila);
            bandera=false;
            agregarWalle.setVisible(true);
            agregarPlanta.setVisible(true);
            agregarPunto.setVisible(true);
            agregarBombas.setVisible(true);
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

    public void crearMatriz(){
        //CREA LA MATRIZ CON 0
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                recinto.crearRecinto(i, j, 0);
            }
        }
        //IMPRIMO EL RECINTO
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.println(recinto.getRecintoCompleto()[i][j]);
            }
            System.out.println("");
        }
    }
    
    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public int getBombas() {
        return bombas;
    }
}

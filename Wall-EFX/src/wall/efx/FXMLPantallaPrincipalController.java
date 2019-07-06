package wall.efx;

import WallECodigo.Main;
import static WallECodigo.Main.recinto;
import WallECodigo.Posicion;
import WallECodigo.Recinto;
import WallECodigo.Recorredor;
import WallECodigo.WallE;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    @FXML private Button iniciar;
    @FXML private GridPane grid;    //MATRIZ
    @FXML private ImageView walleImagen;
    @FXML private ImageView plantaImagen;
    @FXML private ImageView puntoImagen;
    @FXML private ImageView bombaImagen;
    private final FXMLDatosController datosController=new FXMLDatosController();
    public static Recinto recinto = new Recinto();
    private final int filas=FXMLDatosController.f;
    private final int columnas=FXMLDatosController.c;
    private final int bombas=FXMLDatosController.b;
    private int bombasPuestas=0;
    private boolean walle=true, planta=true, punto=true, bomba=true;
    
    @FXML
    public void click(MouseEvent evento){
        double x=0,y=0;
        int columna=0, fila=0;
        x=evento.getX();
        y=evento.getY();
        columna=(int) (x/(1000/(columnas-1)));
        fila=(int)(y/(550/(filas-1)));
        //System.out.println("columna: "+columna+" fila"+fila);
        boolean bandera=true;
        if (!walle && bandera) {
            if(recinto.getRecintoCompleto()[fila][columna]==0){
                walle=true;
                Posicion posicion = new Posicion(fila, columna);
                recinto.setpActual(posicion);
                recinto.getRecintoCompleto()[fila][columna]=2;
                mostrarMatriz();
                // colocar imagen de walle
                //System.out.println("x"+x+" y"+y);
                walleImagen.setX((columna * (1000/(columnas-1))) + 8 + ((1000/(columnas-1)/3)));
                walleImagen.setY((fila*(550/(filas-1)))+8 + ((550/(filas-1)/3)));
                walleImagen.setFitHeight(2*((550/(filas-1))/3));
                walleImagen.setFitWidth(2*((1000/(columnas-1)))/3);
                walleImagen.setVisible(true);
                walleImagen.setDisable(true);
                bandera=false;
                agregarWalle.setVisible(true);
                agregarPlanta.setVisible(true);
                agregarPunto.setVisible(true);
                agregarBombas.setVisible(true);
                iniciar.setVisible(true);
            }
            else{
                datosController.alerta("YA EXISTE UN ELEMENTO", "EN ESTA POSICION.");
            }
        }
        
        if (!planta && bandera) {
            if(recinto.getRecintoCompleto()[fila][columna]==0){
                planta=true;
                recinto.getRecintoCompleto()[fila][columna]=3;
                mostrarMatriz();
                // colocar imagen de planta
                plantaImagen.setX((columna * (1000/(columnas-1))) + 8 + ((1000/(columnas-1)/3)));
                plantaImagen.setY((fila*(550/(filas-1)))+8 + ((550/(filas-1)/3)));
                plantaImagen.setFitHeight(2*((550/(filas-1))/3));
                plantaImagen.setFitWidth(2*((1000/(columnas-1)))/3);
                plantaImagen.setVisible(true);
                plantaImagen.setDisable(true);
                //System.out.print("planta colocado en la posicion: "+x+" "+y);
                //System.out.println(columna +" "+fila);
                bandera=false;
                agregarWalle.setVisible(true);
                agregarPlanta.setVisible(true);
                agregarPunto.setVisible(true);
                agregarBombas.setVisible(true);
                iniciar.setVisible(true);
            }
            else{
                datosController.alerta("YA EXISTE UN ELEMENTO", "EN ESTA POSICION.");
            }
        }
        
        if (!punto && bandera) {
            if(recinto.getRecintoCompleto()[fila][columna]==0){
                punto=true;
                recinto.getRecintoCompleto()[fila][columna]=4;
                mostrarMatriz();
                // colocar imagen de punto
                puntoImagen.setX((columna * (1000/(columnas-1))) + 8 + ((1000/(columnas-1)/3)));
                puntoImagen.setY((fila*(550/(filas-1)))+8 + ((550/(filas-1)/3)));
                puntoImagen.setFitHeight(2*((550/(filas-1))/3));
                puntoImagen.setFitWidth(2*((1000/(columnas-1)))/3);
                puntoImagen.setVisible(true);
                puntoImagen.setDisable(true);
                System.out.print("punto colocado en la posicion: ");
                //System.out.println(columna +" "+fila);
                bandera=false;
                agregarWalle.setVisible(true);
                agregarPlanta.setVisible(true);
                agregarPunto.setVisible(true);
                agregarBombas.setVisible(true);
                iniciar.setVisible(true);
            }
            else{
                datosController.alerta("YA EXISTE UN ELEMENTO", "EN ESTA POSICION.");
            }
        }
        
        if (!bomba && bandera) {
            if(recinto.getRecintoCompleto()[fila][columna]==0){
                if(bombasPuestas<bombas){
                    if(bombasPuestas>=1){
                        recinto.getRecintoCompleto()[fila][columna]=1;
                        mostrarMatriz();
                        ImageView imagen2=new ImageView();
                        imagen2.setImage(bombaImagen.getImage());
                        imagen2.setX((columna * (1000/(columnas-1))) + 8 + ((1000/(columnas-1)/3)));
                        imagen2.setY((fila*(550/(filas-1)))+8 + ((550/(filas-1)/3)));
                        imagen2.setFitHeight(2*((550/(filas-1))/3));
                        imagen2.setFitWidth(2*((1000/(columnas-1)))/3);
                        imagen2.setVisible(true);
                        imagen2.setDisable(true);
                        bombasPuestas+=1;
                        if(bombasPuestas>=bombas){
                            bomba=true;
                            bandera=false;
                            agregarWalle.setVisible(true);
                            agregarPlanta.setVisible(true);
                            agregarPunto.setVisible(true);
                            agregarBombas.setVisible(true);
                            iniciar.setVisible(true);
                        }
                    }
                    else{
                        recinto.getRecintoCompleto()[fila][columna]=1;
                        mostrarMatriz();
                        // colocar imagen de bomba
                        bombaImagen.setX((columna * (1000/(columnas-1))) + 8 + ((1000/(columnas-1)/3)));
                        bombaImagen.setY((fila*(550/(filas-1)))+8 + ((550/(filas-1)/3)));
                        bombaImagen.setFitHeight(2*((550/(filas-1))/3));
                        bombaImagen.setFitWidth(2*((1000/(columnas-1)))/3);
                        bombaImagen.setVisible(true);
                        //System.out.print("bomba colocado en la posicion: ");
                        //System.out.println(columna +" "+fila);
                        bombasPuestas+=1;
                        if(bombasPuestas>=bombas){
                            bomba=true;
                            bandera=false;
                            agregarWalle.setVisible(true);
                            agregarPlanta.setVisible(true);
                            agregarPunto.setVisible(true);
                            agregarBombas.setVisible(true);
                            iniciar.setVisible(true);
                        }
                    }
                }
            }
            else{
                datosController.alerta("YA EXISTE UN ELEMENTO", "EN ESTA POSICION.");
            }
        }
        if(agregarWalle.isDisable() && agregarPlanta.isDisable() && agregarPunto.isDisable() && agregarBombas.isDisable()){
            iniciar.setDisable(false);
            iniciar.setVisible(true);
            agregarWalle.setVisible(false);
            agregarPlanta.setVisible(false);
            agregarPunto.setVisible(false);
            agregarBombas.setVisible(false);
            //REVISAR - AGREGAR JUEGO EN SI
        }
    }
    
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
                grid.add(an, j, i);
            }
        }
    }
    
    @FXML
    public void mostrarRutas(){
        Recorredor r=new Recorredor();
        List<Posicion> instrucciones=new ArrayList<>();
        ArrayList<Character> inst=new ArrayList<>();
        instrucciones=r.resolver(Main.recinto, 1);
        inst=r.traductorInstrucciones(instrucciones, recinto);
        for (int i = 0; i < inst.size(); i++) {
            System.out.println(inst.get(i).charValue());
        }
        if(inst.isEmpty()){
            rutaAleatoria.setText("NO HAY RUTA.");
        }
        else{
            for (int i = 0; i < inst.size(); i++) {
                rutaAleatoria.setText(inst.get(i).toString()+"\n");
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
            iniciar.setVisible(false);
            //AGREGAR EL CLICK DEL MOUSE
        }
    }
    @FXML
    public void ubicarPlanta(ActionEvent e){
        if(planta){  //SI AUN ESTA DISPONIBLE AGREGAR LA PLANTA
            planta=false;
            agregarWalle.setVisible(false);
            agregarPlanta.setDisable(true);
            agregarPunto.setVisible(false);
            agregarBombas.setVisible(false);
            iniciar.setVisible(false);
            //AGREGAR EL CLICK DEL MOUSE
        }
    }
    @FXML
    public void ubicarPunto(ActionEvent e){
        if(punto){  //SI AUN ESTA DISPONIBLE AGREGAR A EVA
            punto=false;
            agregarWalle.setVisible(false);
            agregarPlanta.setVisible(false);
            agregarPunto.setDisable(true);
            agregarBombas.setVisible(false);
            iniciar.setVisible(false);
            //AGREGAR EL CLICK DEL MOUSE
        }
    }
    @FXML
    public void ubicarBombas(ActionEvent e){
        if(bomba){  //SI AUN ESTA DISPONIBLE AGREGAR LAS BOMBAS
            bomba=false;
            agregarWalle.setVisible(false);
            agregarPlanta.setVisible(false);
            agregarPunto.setVisible(false);
            agregarBombas.setDisable(true);
            iniciar.setVisible(false);
            //AGREGAR EL CLICK DEL MOUSE
        }
    }
    
    @FXML
    public void botonIniciar(){
        Recinto r=new Recinto();
        Posicion destinoPlanta=new Posicion();
        Posicion destinoZonaSegura=new Posicion();
        Posicion wallePosicion=new Posicion();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if(recinto.getRecintoCompleto()[i][j]==2){
                    wallePosicion.setpFila(i);
                    wallePosicion.setpColumna(j);
                }
                if(recinto.getRecintoCompleto()[i][j]==3){
                    destinoPlanta.setpFila(i);
                    destinoPlanta.setpColumna(j);
                }
                if(recinto.getRecintoCompleto()[i][j]==4){
                    destinoZonaSegura.setpFila(i);
                    destinoZonaSegura.setpColumna(j);
                }
            }
        }
        r.newWallE(destinoPlanta, destinoZonaSegura);
        r.setpActual(wallePosicion);
        mostrarRutas();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        walleImagen.setVisible(false);
        plantaImagen.setVisible(false);
        bombaImagen.setVisible(false);
        puntoImagen.setVisible(false);
        crearMatriz();
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
        mostrarMatriz();
    }
    
    public void mostrarMatriz(){
        //IMPRIMO EL RECINTO
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(recinto.getRecintoCompleto()[i][j]);
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

    public int getBombasPuestas() {
        return bombasPuestas;
    }

    public void setBombasPuestas(int bombasPuestas) {
        this.bombasPuestas = bombasPuestas;
    }
}

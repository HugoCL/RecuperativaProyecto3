package wall.efx;
import WallECodigo.*;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Timer;


public class ControllerDatos {

    @FXML
    public TextField campoTextoBombas;
    @FXML
    public Button botonConfirmarDatos;
    @FXML
    public Text alertaBombas;
    @FXML
    public Text confirmacionListo;
    @FXML
    public Text mesFi;
    @FXML
    public Text mesCo;
    @FXML
    public Text mesBo;
    @FXML
    public BorderPane Borderpane;
    @FXML
    public Button BotonWallE;
    @FXML
    public Button BotonPlanta;
    @FXML
    public Button BotonBombas;
    @FXML
    public Button BotonZonaSegura;
    @FXML
    public Button botonInicio;
    @FXML
    public Text alertaRepetido;
    @FXML
    public TableView tablaDatos;
    @FXML
    private Slider sliderColumnas;
    @FXML
    private Slider sliderFilas;
    @FXML
    private Text valorSFilas;
    @FXML
    private Text valorSColumnas;

    @FXML
    private GridPane grid;

    private int numWallE = 1;
    private int numBombas = 0;
    private int numZonaSegura = 1;
    private int numPlanta = 1;
    private int filas;
    private int columnas;
    private boolean casillaSeleccionada = false;
    private Node ultimaCasilla;
    private Node ultimaCasillaWallE;
    private Posicion ultimaCasillaPos;
    private Posicion posicionWallE = new Posicion(-1, -1);
    private Posicion posicionPlanta = new Posicion(-1, -1);
    private Posicion posicionZS = new Posicion(-1, -1);
    private List<Posicion> listBombas = new ArrayList<>();
    private Image walle = new Image(getClass().getResourceAsStream("Wall-E.png"));
    private Image cuadrado = new Image(getClass().getResourceAsStream("cuadrado.jpg"));
    private Image cuadSelec = new Image(getClass().getResourceAsStream("cuadradoSeleccionado.jpg"));
    private Image planta = new Image(getClass().getResourceAsStream("Planta.png"));
    private Image bomba = new Image(getClass().getResourceAsStream("Bomba.png"));
    private Image EVA = new Image(getClass().getResourceAsStream("EVA.png"));
    private Recinto recinto = new Recinto();
    private Recorredor recorredor = new Recorredor();
    private Serializador serializador = new Serializador();
    private WallE wallE;
    private boolean juegoComenzado = false;


    @FXML
    public void mostrarValorFilas() {
        valorSFilas.setText(String.valueOf((int) sliderFilas.getValue()));
    }

    @FXML
    public void mostrarValorColumnas() {
        valorSColumnas.setText(String.valueOf((int) sliderColumnas.getValue()));
    }

    @FXML
    public int getFilas() {
        return (int) sliderFilas.getValue();
    }

    @FXML
    public int getColumnas() {
        return (int) sliderColumnas.getValue();
    }

    @FXML
    public boolean comprobarBombas() {
        return campoTextoBombas.getText().matches("[0-9]+");
    }

    @FXML
    public void comprobarInputBombas() throws IOException {
        if (comprobarBombas() && Integer.valueOf(campoTextoBombas.getText()) < (getFilas() * getColumnas() - 3)) {
            alertaBombas.setVisible(false);
            numBombas = Integer.valueOf(campoTextoBombas.getText());
            filas = getFilas();
            columnas = getColumnas();
            iniciarJuego();
        } else {
            alertaBombas.setVisible(true);
        }
    }

    @FXML
    public void iniciarJuego() {
        grid = new GridPane();
        grid.setOnMouseClicked(this::botonGridPresionado);
        grid.setMaxSize(700, 500);
        for (int i = 0; i < getFilas(); i++) {
            for (int j = 0; j < getColumnas(); j++) {
                ImageView imagen = new ImageView();
                imagen.setImage(cuadrado);
                imagen.setFitWidth(700 / columnas);
                imagen.setFitHeight(500 / filas);
                Label label = new Label();
                label.setGraphic(imagen);
                grid.add(label, j, i);
            }
        }
        Borderpane.setCenter(grid);
    }

    @FXML
    public void ingresarElementos() {
        if (numWallE != 0) {
            BotonWallE.setDisable(false);
        }
        if (numZonaSegura != 0) {
            BotonZonaSegura.setDisable(false);
        }
        if (numBombas != 0) {
            BotonBombas.setDisable(false);
        }
        if (numPlanta != 0) {
            BotonPlanta.setDisable(false);
        }
    }

    @FXML
    public void ocultarElementos() {
        BotonBombas.setDisable(true);
        BotonPlanta.setDisable(true);
        BotonZonaSegura.setDisable(true);
        BotonWallE.setDisable(true);
    }

    @FXML
    public void botonGridPresionado(javafx.scene.input.MouseEvent evento) {
        Node clickedNode = evento.getPickResult().getIntersectedNode();
        if (clickedNode != grid && !todoListo()) {
            // click on descendant node
            Node parent = clickedNode.getParent();
            while (parent != grid) {
                clickedNode = parent;
                parent = clickedNode.getParent();
            }

            ImageView imagen = new ImageView();
            imagen.setImage(cuadSelec);
            imagen.setFitWidth(700 / columnas);
            imagen.setFitHeight(500 / filas);
            Label label = new Label();
            label.setGraphic(imagen);
            Integer colIndex = GridPane.getColumnIndex(clickedNode);
            Integer rowIndex = GridPane.getRowIndex(clickedNode);
            if (rowIndex == posicionWallE.getpFila() && colIndex == posicionWallE.getpColumna() ||
                    rowIndex == posicionPlanta.getpFila() && colIndex == posicionPlanta.getpColumna() ||
                    rowIndex == posicionZS.getpFila() && colIndex == posicionZS.getpColumna()) {
                alertaRepetido.setVisible(true);
            } else {
                if (casillaSeleccionada) {
                    grid.getChildren().remove(ultimaCasilla);
                    Label label2 = new Label();
                    ImageView imagen2 = new ImageView(cuadrado);
                    imagen2.setFitWidth(700 / columnas);
                    imagen2.setFitHeight(500 / filas);
                    grid.add(imagen2, ultimaCasillaPos.getpColumna(), ultimaCasillaPos.getpFila());
                    casillaSeleccionada = false;
                }
                alertaRepetido.setVisible(false);
                grid.getChildren().remove(clickedNode);
                grid.add(label, colIndex, rowIndex);
                casillaSeleccionada = true;
                ultimaCasilla = clickedNode;
                ultimaCasillaPos = new Posicion(rowIndex, colIndex);
                ingresarElementos();
                System.out.println("Mouse clicked cell: " + colIndex + " And: " + rowIndex);
            }
        }
    }

    @FXML
    public void ingresarWallE() {
        numWallE--;
        casillaSeleccionada = false;
        ImageView imagen = new ImageView();
        imagen.setImage(walle);
        imagen.setFitWidth(700 / columnas);
        imagen.setFitHeight(500 / filas);
        Label label = new Label();
        label.setGraphic(imagen);
        grid.getChildren().remove(ultimaCasilla);
        grid.add(label, ultimaCasillaPos.getpColumna(), ultimaCasillaPos.getpFila());
        posicionWallE = new Posicion(ultimaCasillaPos.getpFila(), ultimaCasillaPos.getpColumna());
        ocultarElementos();
        todoListo();
        Platform.runLater(() -> Borderpane.requestFocus());
    }

    public void ingresarPlanta() {
        numPlanta--;
        casillaSeleccionada = false;
        ImageView imagen = new ImageView();
        imagen.setImage(planta);
        imagen.setFitWidth(700 / columnas);
        imagen.setFitHeight(500 / filas);
        Label label = new Label();
        label.setGraphic(imagen);
        grid.getChildren().remove(ultimaCasilla);
        grid.add(label, ultimaCasillaPos.getpColumna(), ultimaCasillaPos.getpFila());
        posicionPlanta = new Posicion(ultimaCasillaPos.getpFila(), ultimaCasillaPos.getpColumna());
        ocultarElementos();
        todoListo();
        Platform.runLater(() -> Borderpane.requestFocus());
    }

    @FXML
    public void insertarBombas() {
        numBombas--;
        casillaSeleccionada = false;
        ImageView imagen = new ImageView();
        imagen.setImage(bomba);
        imagen.setFitWidth(700 / columnas);
        imagen.setFitHeight(500 / filas);
        Label label = new Label();
        label.setGraphic(imagen);
        grid.getChildren().remove(ultimaCasilla);
        grid.add(label, ultimaCasillaPos.getpColumna(), ultimaCasillaPos.getpFila());
        listBombas.add(new Posicion(ultimaCasillaPos.getpFila(), ultimaCasillaPos.getpColumna()));
        ocultarElementos();
        todoListo();
        Platform.runLater(() -> Borderpane.requestFocus());
    }

    @FXML
    public void insertarZS() {
        numZonaSegura--;
        casillaSeleccionada = false;
        ImageView imagen = new ImageView();
        imagen.setImage(EVA);
        imagen.setFitWidth(700 / columnas);
        imagen.setFitHeight(500 / filas);
        Label label = new Label();
        label.setGraphic(imagen);
        grid.getChildren().remove(ultimaCasilla);
        grid.add(label, ultimaCasillaPos.getpColumna(), ultimaCasillaPos.getpFila());
        posicionZS = new Posicion(ultimaCasillaPos.getpFila(), ultimaCasillaPos.getpColumna());
        ocultarElementos();
        todoListo();
        Platform.runLater(() -> Borderpane.requestFocus());
    }

    @FXML
    public boolean todoListo() {
        if (numWallE == 0 && numBombas == 0 && numZonaSegura == 0 && numPlanta == 0) {
            if (!juegoComenzado){
                botonInicio.setDisable(false);
            }
            return true;
        }
        return false;
    }

    @FXML
    public void iniciarRecorrido() {
        grid.setOnMouseClicked(null);
        botonInicio.setDisable(true);
        setRecinto();
        Platform.runLater(() -> Borderpane.requestFocus());
        juegoComenzado = true;
    }

    public void setRecinto(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                recinto.crearRecinto(i, j, 0);
            }
        }
        recinto.setLimiteFilas(filas);
        recinto.setLimiteColumnas(columnas);
        recinto.newWallE(posicionPlanta, posicionZS);
        for (Posicion bomba : listBombas) {
            recinto.crearRecinto(bomba.getpFila(), bomba.getpColumna(), 1);
        }
        recinto.nuevaOrientacion('S');
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                System.out.print(recinto.getRecintoCompleto()[i][j]);
            }
            System.out.println("");
        }
        recinto.setpActual(posicionWallE);
        wallE = WallE.getWallE();
    }

    @FXML
    public void movimiento(KeyEvent ke) {
        if (todoListo()) {
            switch (ke.getCode()) {
                case SPACE:
                    avanzar();
                    break;
                case LEFT:
                    izquierda();
                    break;
                case RIGHT:
                    derecha();
                    break;
            }
        }
    }

    public void avanzar() {
        Posicion posicionActual = recinto.getpActual().clonarPosicion();
        if (recinto.getOrientacion() == 'S'){
            recinto.getpActual().setpFila(recinto.getpActual().getpFila()+1);
        }
        else if (recinto.getOrientacion() == 'N'){
            recinto.getpActual().setpFila(recinto.getpActual().getpFila()-1);
        }
        else if (recinto.getOrientacion() == 'E'){
            recinto.getpActual().setpColumna(recinto.getpActual().getpColumna()+1);
        }
        else if (recinto.getOrientacion() == 'O'){
            recinto.getpActual().setpColumna(recinto.getpActual().getpColumna()-1);
        }

        if (recinto.esSeguro(recinto.getRecintoCompleto(), recinto.getpActual().getpFila(), recinto.getpActual().getpColumna())){
            grid.getChildren().remove(getNodo(grid, posicionActual.getpColumna(), posicionActual.getpFila()));
            grid.getChildren().remove(getNodo(grid, recinto.getpActual().getpColumna(), recinto.getpActual().getpFila()));
            // Se actualiza WALLE
            ImageView imagen = new ImageView();
            imagen.setImage(walle);
            imagen.setFitWidth(700 / columnas);
            imagen.setFitHeight(500 / filas);
            Label label = new Label();
            label.setGraphic(imagen);
            grid.add(label, recinto.getpActual().getpColumna(), recinto.getpActual().getpFila());
            // Se actualiza la posicion anterior de WALLE
            ImageView imagen2 = new ImageView();
            imagen2.setImage(cuadrado);
            imagen2.setFitWidth(700 / columnas);
            imagen2.setFitHeight(500 / filas);
            Label label2 = new Label();
            label2.setGraphic(imagen2);
            grid.add(label2, posicionActual.getpColumna(), posicionActual.getpFila());
        }
        else{
            System.out.println("MORISTE, LA TIERRA ESTÁ CONDENADA");
        }
    }

    private Node getNodo(GridPane grid, int columna, int fila) {
        for (Node node : grid.getChildren()) {
            if (GridPane.getColumnIndex(node) == columna && GridPane.getRowIndex(node) == fila) {
                return node;
            }
        }
        return null;
    }

    public void izquierda(){
        if(recinto.getOrientacion() == 'N'){
            recinto.nuevaOrientacion('O');
            walle = new Image(getClass().getResourceAsStream("Wall-EO.png"));
        }
        else if (recinto.getOrientacion() == 'E'){
            recinto.nuevaOrientacion('N');
            walle = new Image(getClass().getResourceAsStream("Wall-EN.png"));
        }
        else if (recinto.getOrientacion() == 'S'){
            recinto.nuevaOrientacion('E');
            walle = new Image(getClass().getResourceAsStream("Wall-EE.png"));
        }
        else if (recinto.getOrientacion() == 'O') {
            recinto.nuevaOrientacion('S');
            walle = new Image(getClass().getResourceAsStream("Wall-E.png"));
        }

        grid.getChildren().remove(getNodo(grid, recinto.getpActual().getpColumna(), recinto.getpActual().getpFila()));
        ImageView imagen = new ImageView();
        imagen.setImage(walle);
        imagen.setFitWidth(700 / columnas);
        imagen.setFitHeight(500 / filas);
        Label label = new Label();
        label.setGraphic(imagen);
        grid.add(label, recinto.getpActual().getpColumna(), recinto.getpActual().getpFila());
    }

    public void derecha(){
        if(recinto.getOrientacion() == 'N'){
            recinto.nuevaOrientacion('E');
            walle = new Image(getClass().getResourceAsStream("Wall-EE.png"));
        }
        else if (recinto.getOrientacion() == 'E'){
            recinto.nuevaOrientacion('S');
            walle = new Image(getClass().getResourceAsStream("Wall-E.png"));
        }
        else if (recinto.getOrientacion() == 'S'){
            recinto.nuevaOrientacion('O');
            walle = new Image(getClass().getResourceAsStream("Wall-EO.png"));
        }
        else if (recinto.getOrientacion() == 'O') {
            recinto.nuevaOrientacion('N');
            walle = new Image(getClass().getResourceAsStream("Wall-EN.png"));
        }

        grid.getChildren().remove(getNodo(grid, recinto.getpActual().getpColumna(), recinto.getpActual().getpFila()));
        ImageView imagen = new ImageView();
        imagen.setImage(walle);
        imagen.setFitWidth(700 / columnas);
        imagen.setFitHeight(500 / filas);
        Label label = new Label();
        label.setGraphic(imagen);
        grid.add(label, recinto.getpActual().getpColumna(), recinto.getpActual().getpFila());
    }
}

package wall.efx;
import WallECodigo.*;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import org.junit.experimental.theories.FromDataPoints;

import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


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
    public Button botonRandomRuta;
    @FXML
    public Button botonRutaFast;
    @FXML
    public MenuButton botonAllRutas;
    @FXML
    public Text alertaRecalcular;
    @FXML
    public Text textMensajes;
    @FXML
    private Slider sliderColumnas;
    @FXML
    private Slider sliderFilas;
    @FXML
    private Text valorSFilas;
    @FXML
    private Text valorSColumnas;
    @FXML
    private ListView<Character> listaRutas;
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
    private boolean juegoComenzado = false;
    private List<Character> rutaAleatoriaT;
    private List<Character> rutaRapidaT;
    private List<List<Posicion>> todaslasRutas;
    private int rutaElegida = 0; //1 PARA LA RANDOM, 2 PARA LA RAPIDA, 3 PARA LA LISTA DE RUTAS
    private ObservableList<Character> listaObs;
    private int flag = 1; // 1 para Wall-E -> Planta, 2 para Planta -> Zona Segura
    private boolean noRutas = false;
    private boolean falloGeneral = false;

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
        listaRutas.setMouseTransparent(true);
        listaRutas.setFocusTraversable(false);
        juegoComenzado = true;
        listaRutas.setOnKeyPressed(null);
        listaRutas.setOnKeyReleased(null);
        settearRutaRandom();
        settearRutaRapida();
        settearAllRutas();
    }

    @FXML
    public void ocultarOpcionesRutas(){
        botonRandomRuta.setDisable(true);
        botonAllRutas.setDisable(true);
        botonRutaFast.setDisable(true);
    }
    @FXML
    public void settearRutaRandom(){
        botonRandomRuta.setDisable(false);
        boolean[][] visitado = new boolean[filas][columnas];
        List<Posicion> rutaAleatoria = recorredor.resolver(recinto, flag, visitado);
        if (rutaAleatoria.isEmpty()){
            textMensajes.setText("No hay una ruta entre Wall-E hacia la Planta o EVA :(");
            textMensajes.setVisible(true);
            noRutas = true;
            botonRandomRuta.setDisable(true);
            Platform.runLater(() -> Borderpane.requestFocus());
        }
        else{
            textMensajes.setVisible(false);
            noRutas = false;
            rutaAleatoriaT = recorredor.traductorInstrucciones(rutaAleatoria, recinto);
            Platform.runLater(() -> Borderpane.requestFocus());
        }
    }

    @FXML
    public void mostrarRutaRandom(){
        listaObs = FXCollections.observableArrayList(rutaAleatoriaT);
        listaRutas.setItems(listaObs);
        Platform.runLater(() -> Borderpane.requestFocus());
        ocultarOpcionesRutas();
        rutaElegida = 1;
        alertaRecalcular.setVisible(false);
    }

    public void settearRutaRapida(){
        botonRutaFast.setDisable(false);
        boolean[][] visitado = new boolean[filas][columnas];
        List<Posicion> rutaRapida = recorredor.resolverRapido(recinto, visitado, flag);
        if (rutaRapida.isEmpty()){
            textMensajes.setText("No hay una ruta entre Wall-E hacia la Planta o EVA :(");
            textMensajes.setVisible(true);
            noRutas = true;
            botonRutaFast.setDisable(true);
            Platform.runLater(() -> Borderpane.requestFocus());
        }
        else{
            textMensajes.setVisible(false);
            noRutas = false;
            Collections.reverse(rutaRapida);
            rutaRapidaT = recorredor.traductorInstrucciones(rutaRapida, recinto);
            Platform.runLater(() -> Borderpane.requestFocus());
        }
    }

    @FXML
    public void mostrarRutaRapida(){
        listaObs = FXCollections.observableArrayList(rutaRapidaT);
        listaRutas.setItems(listaObs);
        Platform.runLater(() -> Borderpane.requestFocus());
        ocultarOpcionesRutas();
        rutaElegida = 2;
        alertaRecalcular.setVisible(false);
    }

    public void settearAllRutas(){
        int cantRutas = 0;
        if (noRutas){
            textMensajes.setText("No hay una ruta entre Wall-E hacia la Planta o EVA :(");
            textMensajes.setVisible(true);
            noRutas = true;
            Platform.runLater(() -> Borderpane.requestFocus());
        }
        else{
            textMensajes.setVisible(false);
            noRutas = false;
            botonAllRutas.setDisable(false);
            boolean[][] visitado = new boolean[filas][columnas];
            List<Posicion> rutaPrototipo = new ArrayList<>();
            cantRutas = recorredor.allRutas(recinto, recinto.getpActual().getpFila(),
                    recinto.getpActual().getpColumna(), 0, visitado, rutaPrototipo, flag);
            todaslasRutas = recorredor.getTodasLasRutas();
            for (int i = 0; i < cantRutas; i++) {
                MenuItem botonRuta = new MenuItem("Ruta "+(i+1));
                int finalI = i;
                botonRuta.setOnAction(a-> mostrarRutas(finalI));
                botonAllRutas.getItems().add(botonRuta);
            }
        }
        Platform.runLater(() -> Borderpane.requestFocus());
    }
    @FXML
    private void mostrarRutas(int ruta) {
        List<Posicion> rutaSeleccionada = todaslasRutas.get(ruta);
        recorredor.clearAllRutas();
        List<Character> rutaElegidaT = recorredor.traductorInstrucciones(rutaSeleccionada, recinto);
        listaObs = FXCollections.observableArrayList(rutaElegidaT);
        listaRutas.setItems(listaObs);
        ocultarOpcionesRutas();
        rutaElegida = 3;
        alertaRecalcular.setVisible(false);
        Platform.runLater(() -> Borderpane.requestFocus());
    }

    public void setRecinto(){
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                recinto.crearRecinto(i, j, 0);
            }
        }
        recinto.setLimiteFilas(filas);
        recinto.setLimiteColumnas(columnas);
        recinto.getRecintoCompleto()[posicionZS.getpFila()][posicionZS.getpColumna()] = 4;
        recinto.getRecintoCompleto()[posicionPlanta.getpFila()][posicionPlanta.getpColumna()] = 3;
        recinto.getRecintoCompleto()[posicionWallE.getpFila()][posicionWallE.getpColumna()] = 2;
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
        WallE wallE = WallE.getWallE();
    }

    @FXML
    public void movimiento(KeyEvent ke) {
        if (todoListo() && !falloGeneral) {
            switch (ke.getCode()) {
                case SPACE:
                    if (listaObs != null && listaObs.size() != 0) {
                        if (listaObs.get(0) == 'A') {
                            listaObs.remove(0);
                            avanzar();
                            if (listaObs.size() == 0) {
                                flag = 2;
                                recalcular();
                            }
                        } else {
                            avanzar();
                            recalcular();
                        }
                    } else {
                        recalcular();
                        avanzar();
                    }
                    break;
                case LEFT:
                    if (listaObs != null && listaObs.size() != 0) {
                        if (listaObs.get(0) == 'I') {
                            listaObs.remove(0);
                            izquierda();
                            if (listaObs.size() == 0) {
                                flag = 2;
                                recalcular();
                            }
                        } else {
                            izquierda();
                            recalcular();
                        }
                    } else {
                        recalcular();
                        izquierda();
                    }
                    break;
                case RIGHT:
                    if (listaObs != null && listaObs.size() != 0) {
                        if (listaObs.get(0) == 'D') {
                            listaObs.remove(0);
                            derecha();
                            if (listaObs.size() == 0) {
                                flag = 2;
                                recalcular();
                            }
                        } else {
                            derecha();
                            recalcular();
                        }
                    } else {
                        recalcular();
                        izquierda();
                    }
                    break;
            }
        }
    }

    private void recalcular() {
        alertaRecalcular.setVisible(true);
        if (listaObs != null && !listaObs.isEmpty()){
            listaObs.clear();
        }
        settearRutaRandom();
        settearRutaRapida();
        clearAllRutas();
        settearAllRutas();
        Platform.runLater(() -> Borderpane.requestFocus());
    }

    private void clearAllRutas() {
        botonAllRutas.getItems().clear();
        todaslasRutas.clear();
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
            textMensajes.setText("La tierra está condenada sin ti Wall-E. Reinicia el juego para salvar a la Tierra");
            textMensajes.setVisible(true);
            falloGeneral = true;
            ocultarOpcionesRutas();
            ocultarElementos();
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

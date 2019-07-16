package wall.efx;
import WallECodigo.*;


import com.sun.xml.internal.ws.api.FeatureConstructor;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.experimental.theories.FromDataPoints;

import java.awt.*;
import java.beans.EventHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;


public class Controller {

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
    public Button botonDesSerializar;
    @FXML
    public Button botonRecalcular;
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

    private LocalTime fechaInicio;

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
            recinto.setNumBombas(numBombas);
            filas = getFilas();
            columnas = getColumnas();
            iniciarJuego();
        } else {
            alertaBombas.setVisible(true);
        }
    }

    @FXML
    public void reanudarJuego(){
        try {
            recinto = serializador.desSerializar();
            WallE walleSeri = WallE.getWallE();
            if (recinto == null && walleSeri == null){
                textMensajes.setText("No hay un archivo serializado creado correctamente, " +
                        "por lo que no podemos reanudar tu juego :(");
                textMensajes.setVisible(true);
            }
            else if (recinto != null && walleSeri == null){
                reanudarRecinto();
            }
            else{
                reanudarRecinto();
                reanudarWallE();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            textMensajes.setText("No hay un archivo serializado creado, por lo que no podemos reanudar tu juego :(");
            textMensajes.setVisible(true);
        }
    }

    private void reanudarRecinto() {
        filas = recinto.getlimiteFilas();
        columnas = recinto.getlimiteColumnas();
        fechaInicio = recinto.getInicioRecorrido();
        if (recinto.isPlantaAlcanzada()){
            flag = 2;
        }
        grid = new GridPane();
        grid.setOnMouseClicked(this::botonGridPresionado);
        grid.setMaxSize(700, 500);
        if (recinto.getpActual() != null){
            posicionWallE = recinto.getpActual();
        }
        numBombas = recinto.getNumBombas();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (recinto.getRecintoCompleto()[i][j] == 1){
                    numBombas--;
                    casillaSeleccionada = false;
                    ImageView imagen = new ImageView();
                    imagen.setImage(bomba);
                    imagen.setFitWidth(700 / columnas);
                    imagen.setFitHeight(500 / filas);
                    Label label = new Label();
                    label.setGraphic(imagen);
                    grid.add(label, j, i);
                    listBombas.add(new Posicion(i, j));
                }
                else if (recinto.getRecintoCompleto()[i][j] == 0){
                    ImageView imagen = new ImageView();
                    imagen.setImage(cuadrado);
                    imagen.setFitWidth(700 / columnas);
                    imagen.setFitHeight(500 / filas);
                    Label label = new Label();
                    label.setGraphic(imagen);
                    grid.add(label, j, i);
                }
                else if (recinto.getRecintoCompleto()[i][j] == 3){
                    numWallE = 0;
                    ImageView imagen = new ImageView();
                    if (recinto.getOrientacion() == 'N'){
                        walle = new Image(getClass().getResourceAsStream("Wall-EN.png"));
                    }
                    else if (recinto.getOrientacion() == 'E'){
                        walle = new Image(getClass().getResourceAsStream("Wall-EE.png"));
                    }
                    else if (recinto.getOrientacion() == 'S'){
                        walle = new Image(getClass().getResourceAsStream("Wall-E.png"));
                    }
                    else if (recinto.getOrientacion() == 'O'){
                        walle = new Image(getClass().getResourceAsStream("Wall-EO.png"));
                    }
                    imagen.setImage(walle);
                    imagen.setFitWidth(700 / columnas);
                    imagen.setFitHeight(500 / filas);
                    Label label2 = new Label();
                    label2.setGraphic(imagen);
                    grid.add(label2, j, i);
                }
            }
        }
        ocultarElementos();
        todoListo();
        Borderpane.setCenter(grid);
        textMensajes.setVisible(false);
        Platform.runLater(() -> Borderpane.requestFocus());
    }

    private void reanudarWallE(){
        WallE wallESerializado = WallE.getWallE();
        if (wallESerializado != null){
            posicionPlanta = wallESerializado.getpDPlanta();
            numPlanta--;
            if (!recinto.isPlantaAlcanzada()){
                ImageView imagen1 = new ImageView();
                imagen1.setImage(planta);
                imagen1.setFitWidth(700 / columnas);
                imagen1.setFitHeight(500 / filas);
                Label label = new Label();
                label.setGraphic(imagen1);
                grid.add(label, posicionPlanta.getpColumna(), posicionPlanta.getpFila());
            }
            posicionZS = wallESerializado.getpDZonaSegura();
            ImageView imagen2 = new ImageView();
            imagen2.setImage(EVA);
            imagen2.setFitWidth(700 / columnas);
            imagen2.setFitHeight(500 / filas);
            Label label2 = new Label();
            label2.setGraphic(imagen2);
            grid.add(label2, posicionZS.getpColumna(), posicionZS.getpFila());
            numZonaSegura--;
        }
        juegoComenzado = true;
        ocultarElementos();
        todoListo();
        textMensajes.setVisible(false);
        Platform.runLater(() -> Borderpane.requestFocus());
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
        textMensajes.setVisible(false);
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
        listaRutas.setMouseTransparent(true);
        listaRutas.setFocusTraversable(false);
        juegoComenzado = true;
        listaRutas.setOnKeyPressed(null);
        listaRutas.setOnKeyReleased(null);
        /*
        settearRutaRandom();
        settearRutaRapida();
        settearAllRutas();*/
        botonRecalcular.setDisable(false);
        fechaInicio = LocalTime.now();
        recinto.setInicioRecorrido(fechaInicio);
        Platform.runLater(() -> Borderpane.requestFocus());
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
        recinto.setpActual(posicionWallE);
        WallE wallE = WallE.getWallE();
    }

    @FXML
    public void movimiento(KeyEvent ke) throws FileNotFoundException {
        if (todoListo() && !falloGeneral) {
            switch (ke.getCode()) {
                case SPACE:
                    if (listaObs != null && listaObs.size() != 0) {
                        if (listaObs.get(0) == 'A') {
                            listaObs.remove(0);
                            avanzar();
                            if (listaObs.size() == 0) {
                                flag = 2;
                                recinto.setPlantaAlcanzada(true);
                                permitirRecalcular();
                            }
                        } else {
                            avanzar();
                            permitirRecalcular();
                        }
                    } else {
                        avanzar();
                        permitirRecalcular();
                    }
                    break;
                case LEFT:
                    if (listaObs != null && listaObs.size() != 0) {
                        if (listaObs.get(0) == 'I') {
                            listaObs.remove(0);
                            izquierda();
                            if (listaObs.size() == 0) {
                                flag = 2;
                                recinto.setPlantaAlcanzada(true);
                                permitirRecalcular();
                            }
                        } else {
                            izquierda();
                            permitirRecalcular();
                        }
                    } else {
                        izquierda();
                        permitirRecalcular();
                    }
                    break;
                case RIGHT:
                    if (listaObs != null && listaObs.size() != 0) {
                        if (listaObs.get(0) == 'D') {
                            listaObs.remove(0);
                            derecha();
                            if (listaObs.size() == 0) {
                                flag = 2;
                                recinto.setPlantaAlcanzada(true);
                                permitirRecalcular();
                            }
                        } else {
                            derecha();
                            permitirRecalcular();
                        }
                    } else {
                        derecha();
                        permitirRecalcular();
                    }
                    break;
            }
        }
    }
    @FXML
    private void permitirRecalcular(){
        alertaRecalcular.setVisible(true);
        botonRecalcular.setDisable(false);
        if (listaObs != null && !listaObs.isEmpty()){
            listaObs.clear();
        }
    }
    @FXML
    private void recalcular() {
        settearRutaRandom();
        settearRutaRapida();
        clearAllRutas();
        settearAllRutas();
        Platform.runLater(() -> Borderpane.requestFocus());
        alertaRecalcular.setVisible(false);
        botonRecalcular.setDisable(true);
    }

    private void clearAllRutas() {
        if (botonAllRutas.getItems().size() != 0){
            botonAllRutas.getItems().clear();
        }

        if (todaslasRutas != null && !todaslasRutas.isEmpty()){
            todaslasRutas.clear();
        }
    }

    public void avanzar() throws FileNotFoundException {
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

        if (recinto.esSeguro(recinto.getRecintoCompleto(), recinto.getpActual().getpFila(), recinto.getpActual().getpColumna())
                && !recinto.esMuroBomba(recinto, recinto.getpActual().getpFila(), recinto.getpActual().getpColumna())){
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
            recinto.getRecintoCompleto()[posicionActual.getpFila()][posicionActual.getpColumna()] = 0;
            if (posicionActual.getpFila() == posicionZS.getpFila() && posicionActual.getpColumna() == posicionZS.getpColumna()){
                recinto.getRecintoCompleto()[posicionActual.getpFila()][posicionActual.getpColumna()] = 4;
            }
            recinto.getRecintoCompleto()[recinto.getpActual().getpFila()][recinto.getpActual().getpColumna()] = 3;
            if (recinto.getpActual().getpFila() == posicionPlanta.getpFila() &&
                    recinto.getpActual().getpColumna() == posicionPlanta.getpColumna()){
                flag = 2;
                recinto.setPlantaAlcanzada(true);
                textMensajes.setFill(Color.GREEN);
                textMensajes.setText("¡Has llegado a la planta!. Si lo deseas, presiona en Calcular para encontrar la ruta" +
                        " hacia EVA");
                textMensajes.setVisible(true);
            }
            if (recinto.getpActual().getpFila() == posicionZS.getpFila() &&
                    recinto.getpActual().getpColumna() == posicionZS.getpColumna() && flag == 2){
                System.out.println("VICTORIA!!!!");

                LocalTime fechaFinal = LocalTime.now();
                long segundosEntre = ChronoUnit.SECONDS.between(fechaInicio,fechaFinal);
                int minutos = 0;
                int segundos = 0;
                do {
                    if (segundosEntre-60 >= 0){
                        segundosEntre = segundosEntre-60;
                        minutos++;
                    }
                    if (segundosEntre-60 < 60){
                        segundos = (int)segundosEntre;
                    }
                }while(segundosEntre >= 60);
                textMensajes.setFill(Color.GREEN);
                textMensajes.setText("¡Has llegado sano y salvo con la planta!. Ahora le espera un futuro más verde a la Tierra." +
                        "Te demoraste "+minutos+" minuto(s) y "+segundos+" segundo(s)");
                textMensajes.setVisible(true);

            }
            serializador.serializar(recinto);
        }
        else{
            textMensajes.setFill(Color.RED);
            textMensajes.setText("¡Has muerto!. La tierra está condenada sin ti Wall-E. Reinicia el juego para salvar a la Tierra");
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

    public void izquierda() throws FileNotFoundException {
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
        serializador.serializar(recinto);
    }

    public void derecha() throws FileNotFoundException {
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
        serializador.serializar(recinto);
    }
}

package com.example.finalproject218;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;

public class WarAndPeaceGenerator extends Application {
    private ListView<String> filesListView;
    private ObservableList<String> filesList;

    private HashMap<String, List<String>> wordMap;
    private TextArea outputTextArea;
    private TextField startingWordField;
    private TextField wordCountField;
    private Stage primaryStage;
    private FileChooser fileChooser;

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("War and Peace Generator");

        // Set the initial size of the stage
        primaryStage.setWidth(1200);  // or whatever size you prefer
        primaryStage.setHeight(800);  // or whatever size you prefer

        wordMap = new HashMap<>();
        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Image image = new Image("https://www.google.com/imgres?imgurl=https%3A%2F%2Fwww.simplilearn.com%2Fice9%2Ffree_resources_article_thumb%2FAdvantages_and_Disadvantages_of_artificial_intelligence.jpg&tbnid=1S7rw4OQRYv59M&vet=12ahUKEwjlk5mb4NT-AhVgEVkFHSEnAx0QMygDegUIARDsAQ..i&imgrefurl=https%3A%2F%2Fwww.simplilearn.com%2Fadvantages-and-disadvantages-of-artificial-intelligence-article&docid=IHV3F21l1cTuwM&w=848&h=477&itg=1&q=ai%20picture&client=safari&ved=2ahUKEwjlk5mb4NT-AhVgEVkFHSEnAx0QMygDegUIARDsAQ");



        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);




        outputTextArea = new TextArea();
        outputTextArea.setBackground(background);
        VBox.setVgrow(outputTextArea, Priority.ALWAYS);
        root.getChildren().add(outputTextArea);
        outputTextArea.setBackground(background);

        outputTextArea.setStyle("-fx-text-fill: #00AAFF; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-color: #333; -fx-opacity: 0.8;");

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), outputTextArea);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();//end
        HBox bottomPanel = new HBox(10);
//

        // Create zoom buttons.
        Button zoomInButton = new Button("Zoom In");
        zoomInButton.setOnAction(e -> {
            double fontSize = outputTextArea.getFont().getSize() + 2;
            outputTextArea.setStyle("-fx-font-size: " + fontSize + "px;");
        });

        Button zoomOutButton = new Button("Zoom Out");
        zoomOutButton.setOnAction(e -> {
            double fontSize = outputTextArea.getFont().getSize() - 2;
            if (fontSize > 8) {
                outputTextArea.setStyle("-fx-font-size: " + fontSize + "px;");
            }
        });



        startingWordField = new TextField();
        startingWordField.setPromptText("Starting word");
        startingWordField.setStyle("-fx-text-fill: lightgreen;"); // Set color to light green
        bottomPanel.getChildren().add(startingWordField);

        wordCountField = new TextField();
        wordCountField.setPromptText("Word count");
        wordCountField.setStyle("-fx-text-fill: lightgreen;"); // Set color to light green
        bottomPanel.getChildren().add(wordCountField);

        startingWordField.setStyle("-fx-prompt-text-fill: LIGHTGREEN;");
        wordCountField.setStyle("-fx-prompt-text-fill: LIGHTGREEN;");
        startingWordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                animateTextField(startingWordField, 1.2);
            } else {
                animateTextField(startingWordField, 1.0);
            }
        });

        wordCountField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                animateTextField(wordCountField, 1.2);
            } else {
                animateTextField(wordCountField, 1.0);
            }
        });


        Button generateButton = new Button("Generate");
        generateButton.setStyle("-fx-base: #00AAFF; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;");
        generateButton.setOnMouseEntered(e -> generateButton.setStyle("-fx-base: #0077CC; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;"));
        generateButton.setOnMouseExited(e -> generateButton.setStyle("-fx-base: #00AAFF; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;"));

        generateButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> generateOutput());

        bottomPanel.getChildren().add(generateButton);

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-base: #00AAFF; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;");
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> saveOutput());
        bottomPanel.getChildren().add(saveButton);
        saveButton.setOnMouseEntered(e -> saveButton.setStyle("-fx-base: #0077CC; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;"));
        saveButton.setOnMouseExited(e -> saveButton.setStyle("-fx-base: #00AAFF; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5;"));


        // Add zoom buttons to the bottom panel.
        bottomPanel.getChildren().addAll(zoomInButton, zoomOutButton);

        root.getChildren().add(bottomPanel);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

       MenuItem openItem = new MenuItem("Open");
        openItem.setOnAction(e -> openFile());
        fileMenu.getItems().add(openItem);

        MenuItem newItem = new MenuItem("New");
        newItem.setOnAction(e -> clearTextArea());
        fileMenu.getItems().add(newItem);

        MenuItem closeItem = new MenuItem("Exit");
        closeItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(closeItem);

        menuBar.getMenus().add(fileMenu);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(menuBar);


        // Initialize the filesListView and add it to the SplitPane
        filesList = FXCollections.observableArrayList();
        filesListView = new ListView<>(filesList);

        SplitPane splitPane = new SplitPane();
        splitPane.getItems().addAll(filesListView, root);
        SplitPane.setResizableWithParent(filesListView, false);
        splitPane.setDividerPositions(0.15);

        borderPane.setCenter(splitPane);

        filesListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    loadFile(newValue);
                    TranslateTransition transition = new TranslateTransition(Duration.millis(300), filesListView);
                    transition.setFromX(-200);
                    transition.setToX(0);
                    transition.play();
                }
        );


        saveLastFilePath("");
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));

        filesListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> loadFile(newValue)
        );
        File outputDir = new File("output");
        if (outputDir.exists() && outputDir.isDirectory()) {
            File[] files = outputDir.listFiles((dir, name) -> name.endsWith(".txt"));
            if (files != null) {
                for (File file : files) {
                    filesList.add(file.getName());
                }
            }
        }
        Scene scene = new Scene(borderPane);
        scene.getStylesheets().add(getClass().getResource("/dark-theme.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    private void animateTextField(TextField textField, double scale) {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(textField.scaleXProperty(), scale, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.millis(100), kv);
        timeline.getKeyFrames().add(kf);

        KeyValue kv2 = new KeyValue(textField.scaleYProperty(), scale, Interpolator.EASE_BOTH);
        KeyFrame kf2 = new KeyFrame(Duration.millis(100), kv2);
        timeline.getKeyFrames().add(kf2);

        timeline.play();
    }


    private void clearTextArea() {
        outputTextArea.clear();
        wordMap.clear();
    }


    private void loadFile(String fileName) {
        File file = new File("output/" + fileName);
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
            outputTextArea.setText(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openFile() {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            readFile(selectedFile);
        }
    }

    private void readFile(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
            br.close();

            // Display a dialog after processing all words
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Data loaded successfully!");
            alert.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void processLine(String line) {
        String[] words = line.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            wordMap.computeIfAbsent(words[i], k -> new ArrayList<>()).add(words[i + 1]);
        }
    }

    private void generateOutput() {
        String startingWord = startingWordField.getText();
        if (startingWord.isEmpty()) {
            showAlert("Error", "Please enter a starting word.");
            return;
        }

        // Check if the starting word is known
        if (!wordMap.containsKey(startingWord)) {
            showAlert("Error", "Unknown starting word. Please enter a known word.");
            return;
        }

        int wordCount;
        try {
            wordCount = Integer.parseInt(wordCountField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid word count. Please enter a valid number.");
            return;
        }

        StringBuilder output = new StringBuilder();
        String currentWord = startingWord;
        Random random = new Random();

        for (int i = 0; i < wordCount; i++) {
            output.append(currentWord).append(" ");

            if ((i + 1) % 20 == 0) {
                output.append("\n");
            }

            List<String> nextWords = wordMap.get(currentWord);
            if (nextWords == null || nextWords.isEmpty()) {
                // Fallback: get a random word from wordMap as the next word
                List<String> keys = new ArrayList<>(wordMap.keySet());
                currentWord = keys.get(random.nextInt(keys.size()));
            } else {
                currentWord = nextWords.get(random.nextInt(nextWords.size()));
            }
        }

        outputTextArea.setText(output.toString());
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveOutput() {
        try {
            File outputDir = new File("output");
            if (!outputDir.exists()) {
                outputDir.mkdir();
            }

            // Get the last saved file path from the configuration file
            Properties props = new Properties();
            File configFile = new File("config.properties");
            if (configFile.exists()) {
                FileInputStream fis = new FileInputStream(configFile);
                props.load(fis);
                fis.close();
            }

            String lastFilePath = props.getProperty("lastFilePath");
            if (lastFilePath == null || lastFilePath.isEmpty()) {
                // Generate a filename based on the starting word and word count
                String startingWord = startingWordField.getText();
                int wordCount = Integer.parseInt(wordCountField.getText());
                String filename = startingWord + "_" + wordCount + ".txt";
                lastFilePath = "output/" + filename;
            }

            File outputFile = new File(lastFilePath);
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
            bw.write(outputTextArea.getText());
            bw.close();

            // Add the file name to the list
            filesList.add(outputFile.getName());

            // Save the last saved file path to the configuration file
            props.setProperty("lastFilePath", outputFile.getAbsolutePath());
            FileOutputStream fos = new FileOutputStream(configFile);
            props.store(fos, null);
            fos.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Output saved to '" + lastFilePath + "'");
            alert.showAndWait();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void saveLastFilePath(String filePath) {
        try {
            Properties props = new Properties();
            File configFile = new File("config.properties");
            if (configFile.exists()) {
                FileInputStream fis = new FileInputStream(configFile);
                props.load(fis);
                fis.close();
            }

            props.setProperty("lastFilePath", filePath);
            FileOutputStream fos = new FileOutputStream(configFile);
            props.store(fos, null);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

package vdm.controllers;

import dorkbox.util.Desktop;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

import vdm.*;
import vdm.helpers.*;
import vdm.models.*;



public class NewDownloadController implements Initializable{

    private static final String APP_DATA_DIRECTORY = DataHandler.getAppDataDirectory();
    private static final String TEMP_DIRECTORY = System.getProperty("java.io.tmpdir").replaceAll("[/\\\\]$", "") + System.getProperty("file.separator") + "vdm";
    private static final String YTDL_PATH = APP_DATA_DIRECTORY + System.getProperty("file.separator") + "youtube-dl";


    @FXML private BorderPane newDownloadPane;
    @FXML private ToolBar toolbar;
    @FXML private Button startBtn;
    @FXML private Button scheduleBtn;
    @FXML private VBox scrollPaneVBox;
    @FXML private ImageView thumbnailImageView;
    @FXML private Label titleLabel;
    @FXML private Hyperlink urlLabel;
    @FXML private Label descriptionLabel;
    @FXML private TitledPane artifactsTitledPane;
    @FXML private HBox artifactsSaveLocationHBox;
    @FXML private TextField locationTextField;
    @FXML private Button browseBtn;
    @FXML private CheckBox customNameChkBox;
    @FXML private TextField customNameTextField;
    @FXML private TitledPane qualityTitledPane;
    @FXML private ChoiceBox<Quality> videoQualityChoiceBox;
    @FXML private ChoiceBox<Quality> audioQualityChoiceBox;
    @FXML private ChoiceBox<String> formatChoiceBox;
    @FXML private ChoiceBox<String> convertChoiceBox;
    @FXML private TitledPane websiteTitledPane;
    @FXML private CheckBox embeddedSubtitleChkBox;
    @FXML private CheckBox autoGenSubtitleChkBox;
    @FXML private HBox subtitleLanguagePane;
    @FXML private ChoiceBox<String> subtitleLanguageChoiceBox;
    @FXML private TitledPane playlistTitledPane;
    @FXML private CheckBox isPlaylistChkBox;
    @FXML private VBox playlistPane;
    @FXML private RadioButton allItemsRadioBtn;
    @FXML private RadioButton indexRangeRadioBtn;
    @FXML private TextField startIndexTextField;
    @FXML private TextField endIndexTextField;
    @FXML private RadioButton specificItemsRadioBtn;
    @FXML private TextField playlistItemsTextField;
    @FXML private Spinner<Integer> intervalSpinner;
    @FXML private TitledPane authenticationTitledPane;
    @FXML private CheckBox needLoginCheckBox;
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordTextField;
    @FXML private TitledPane othersTitledPane;
    @FXML private Spinner<Integer> limitSpinner;
    @FXML private CheckBox shutdownCheckBox;

    private boolean isQueueBtnSelected;
    private Stage appStage;
    private Stage urlDialogStage;
    private List<String> playlistTypes = new ArrayList<>(Arrays.asList("playlist", "list", "set", "album",
            "course", "user", "chanel", "group"));


    public BorderPane getNewDownloadPane() {
        return newDownloadPane;
    }

    public ToolBar getToolbar() {
        return toolbar;
    }

    public Button getStartBtn() {
        return startBtn;
    }

    public Button getScheduleBtn() {
        return scheduleBtn;
    }

    public VBox getScrollPaneVBox() {
        return scrollPaneVBox;
    }

    public ImageView getThumbnailImageView() {
        return thumbnailImageView;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Hyperlink getUrlLabel() {
        return urlLabel;
    }

    public Label getDescriptionLabel() {
        return descriptionLabel;
    }

    public TitledPane getArtifactsTitledPane() {
        return artifactsTitledPane;
    }

    public HBox getArtifactsSaveLocationHBox() {
        return artifactsSaveLocationHBox;
    }

    public TextField getLocationTextField() {
        return locationTextField;
    }

    public Button getBrowseBtn() {
        return browseBtn;
    }

    public CheckBox getCustomNameChkBox() {
        return customNameChkBox;
    }

    public TextField getCustomNameTextField() {
        return customNameTextField;
    }

    public TitledPane getQualityTitledPane() {
        return qualityTitledPane;
    }

    public ChoiceBox<Quality> getVideoQualityChoiceBox() {
        return videoQualityChoiceBox;
    }

    public ChoiceBox<Quality> getAudioQualityChoiceBox() {
        return audioQualityChoiceBox;
    }

    public ChoiceBox<String> getFormatChoiceBox() {
        return formatChoiceBox;
    }

    public ChoiceBox<String> getConvertChoiceBox() {
        return convertChoiceBox;
    }

    public TitledPane getWebsiteTitledPane() {
        return websiteTitledPane;
    }

    public CheckBox getEmbeddedSubtitleChkBox() {
        return embeddedSubtitleChkBox;
    }

    public CheckBox getAutoGenSubtitleChkBox() {
        return autoGenSubtitleChkBox;
    }

    public HBox getSubtitleLanguagePane() {
        return subtitleLanguagePane;
    }

    public ChoiceBox<String> getSubtitleLanguageChoiceBox() {
        return subtitleLanguageChoiceBox;
    }

    public TitledPane getPlaylistTitledPane() {
        return playlistTitledPane;
    }

    public CheckBox getIsPlaylistChkBox() {
        return isPlaylistChkBox;
    }

    public VBox getPlaylistPane() {
        return playlistPane;
    }

    public RadioButton getAllItemsRadioBtn() {
        return allItemsRadioBtn;
    }

    public RadioButton getIndexRangeRadioBtn() {
        return indexRangeRadioBtn;
    }

    public TextField getStartIndexTextField() {
        return startIndexTextField;
    }

    public TextField getEndIndexTextField() {
        return endIndexTextField;
    }

    public RadioButton getSpecificItemsRadioBtn() {
        return specificItemsRadioBtn;
    }

    public TextField getPlaylistItemsTextField() {
        return playlistItemsTextField;
    }

    public Spinner<Integer> getIntervalSpinner() {
        return intervalSpinner;
    }

    public TitledPane getAuthenticationTitledPane() {
        return authenticationTitledPane;
    }

    public CheckBox getNeedLoginCheckBox() {
        return needLoginCheckBox;
    }

    public TextField getUserNameTextField() {
        return userNameTextField;
    }

    public TextField getPasswordTextField() {
        return passwordTextField;
    }

    public TitledPane getOthersTitledPane() {
        return othersTitledPane;
    }

    public Spinner<Integer> getLimitSpinner() {
        return limitSpinner;
    }

    public CheckBox getShutdownCheckBox() {
        return shutdownCheckBox;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // stores a reference to the stage
        appStage = Main.getAppStage();

        // specify if the download item is a playlist or not
        descriptionLabel.textProperty().addListener((obs, oldText, newText) -> {
            for(String type : playlistTypes) {
                if(newText.contains(type)) {
                    isPlaylistChkBox.setSelected(true);
                    break;
                }
            }
        });

        if(System.getProperty("os.name").toLowerCase().contains("win"))
            locationTextField.setText(System.getProperty("user.home") + "\\Downloads");
        else
            locationTextField.setText(System.getProperty("user.home") + "/Downloads");

        subtitleLanguageChoiceBox.setItems(FXCollections.observableArrayList("Arabic", "English", "French", "Italian", "spanish", "German", "Russian"));
        subtitleLanguageChoiceBox.setValue("English");
        embeddedSubtitleChkBox.selectedProperty().not().and(autoGenSubtitleChkBox.selectedProperty().not()).addListener((observable, oldValue, newValue) -> subtitleLanguagePane.setDisable(newValue));

        newDownloadPane.setOnKeyPressed((KeyEvent keyEvent) -> {
            if(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN).match(keyEvent)) {
                if(startBtn.isVisible())
                    startBtnAction();
                keyEvent.consume();
            } else if(new KeyCodeCombination(KeyCode.Q, KeyCombination.SHORTCUT_DOWN).match(keyEvent)) {
                if(scheduleBtn.isVisible())
                    scheduleBtnAction();
                keyEvent.consume();
            } else if(new KeyCodeCombination(KeyCode.ESCAPE).match(keyEvent)) {
                cancelBtnAction();
                keyEvent.consume();
            }
        });

        // center the urlDialogStage in the appStage
        appStage.xProperty().addListener((observableValue, oldValue, newValue) -> {
            if(urlDialogStage != null && urlDialogStage.isShowing())
                urlDialogStage.setX(newValue.doubleValue() + appStage.getWidth()/2d - urlDialogStage.getWidth()/2d);
        });
        appStage.yProperty().addListener((observableValue, oldValue, newValue) -> {
            if(urlDialogStage != null && urlDialogStage.isShowing())
                urlDialogStage.setY(newValue.doubleValue() + appStage.getHeight()/2d - urlDialogStage.getHeight()/2d);
        });

    }

    public void setQueueBtnSelected(boolean selected) {
        isQueueBtnSelected = selected;
    }

    public void showUrlDialog() {

        final double URL_DIALOG_WIDTH = 650;
        final double URL_DIALOG_HEIGHT = 370;

        Effect blurEffect = new BoxBlur(10, 10, 3);
        newDownloadPane.setEffect(blurEffect);

        try {

            String urlRegex = "(https?:\\/\\/)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
            Clipboard systemClipboard = Clipboard.getSystemClipboard();
            String clipboardText = systemClipboard.getString();

            FXMLLoader urlDialogLoader = new FXMLLoader(getClass().getResource("/windows/URLDialog.fxml"));
            VBox vBox = urlDialogLoader.load();
            Scene urlDialogScene = new Scene(vBox, URL_DIALOG_WIDTH, URL_DIALOG_HEIGHT, Color.TRANSPARENT);
            urlDialogStage = new Stage();
            urlDialogStage.setScene(urlDialogScene);

            TextField urlTextFieldOnUrlDialog = (TextField) urlDialogLoader.getNamespace().get("urlTextFieldOnUrlDialog");
            Button addBtnOnUrlDialog = (Button) urlDialogLoader.getNamespace().get("addBtnOnUrlDialog");
            Button cancelBtnOnUrlDialog = (Button) urlDialogLoader.getNamespace().get("cancelBtnOnUrlDialog");
            CheckBox needLoginCheckBoxOnUrlDialog = (CheckBox) urlDialogLoader.getNamespace().get("needLoginCheckBoxOnUrlDialog");
            TextField userNameTextFieldOnUrlDialog = (TextField) urlDialogLoader.getNamespace().get("userNameTextFieldOnUrlDialog");
            TextField passwordTextFieldOnUrlDialog = (TextField) urlDialogLoader.getNamespace().get("passwordTextFieldOnUrlDialog");

            if(clipboardText != null && clipboardText.matches(urlRegex))
                urlTextFieldOnUrlDialog.setText(clipboardText);

            String[] containerFormats = {"mp4", "mkv", "webm", "flv", "ogg"};
            String[] convertToVideoFormats = {"mp4", "mkv", "avi", "webm", "flv", "ogg"};
            String[] convertToAudioFormats = {"mp3", "m4a", "wav", "aac"};
            List<Quality> audioQualities = new ArrayList<>();
            List<Quality> videoQualities = new ArrayList<>();

            addBtnOnUrlDialog.setOnAction((ActionEvent actionEvent) -> {

                boolean validUserInputs = true;

                if(urlTextFieldOnUrlDialog.getText().matches(urlRegex)) {
                    urlTextFieldOnUrlDialog.getStyleClass().removeAll(Collections.singleton("text-field-error"));
                    urlLabel.setText(urlTextFieldOnUrlDialog.getText());
                } else {
                    urlTextFieldOnUrlDialog.getStyleClass().add("text-field-error");
                    validUserInputs = false;
                }

                if(needLoginCheckBoxOnUrlDialog.isSelected()) {

                    needLoginCheckBox.setSelected(true);

                    if(userNameTextFieldOnUrlDialog.getText().equals("")) {
                        userNameTextFieldOnUrlDialog.getStyleClass().add("text-field-error");
                        validUserInputs = false;
                    } else {
                        userNameTextFieldOnUrlDialog.getStyleClass().removeAll(Collections.singleton("text-field-error"));
                        userNameTextField.setText(userNameTextFieldOnUrlDialog.getText());
                    }

                    if(passwordTextFieldOnUrlDialog.getText().equals("")) {
                        passwordTextFieldOnUrlDialog.getStyleClass().add("text-field-error");
                        validUserInputs = false;
                    } else {
                        passwordTextFieldOnUrlDialog.getStyleClass().removeAll(Collections.singleton("text-field-error"));
                        passwordTextField.setText(passwordTextFieldOnUrlDialog.getText());
                    }

                }

                if(! validUserInputs)
                    return;

                vBox.getChildren().clear();
                vBox.setAlignment(Pos.CENTER);
                vBox.setStyle("-fx-effect: none; -fx-background-color: transparent;");
                ProgressIndicator progressIndicator = new ProgressIndicator();
                progressIndicator.setPrefHeight(48);
                progressIndicator.setPrefWidth(48);
                vBox.getChildren().add(progressIndicator);
                urlDialogStage.setWidth(50);
                urlDialogStage.setHeight(50);
                urlDialogStage.setX(appStage.getX() + appStage.getWidth()/2d - urlDialogStage.getWidth()/2d);
                urlDialogStage.setY(appStage.getY() + appStage.getHeight()/2d - urlDialogStage.getHeight()/2d);

                List<String> argsList = new ArrayList<>();
                argsList.add("python");
                argsList.add(YTDL_PATH);
                if(needLoginCheckBox.isSelected()) {
                    argsList.add("-u");
                    argsList.add(userNameTextField.getText());
                    argsList.add("-p");
                    argsList.add(passwordTextField.getText());
                }
                CountDownLatch latch = new CountDownLatch(3);
                Timeline timeline = new Timeline();

                try {

                    // for parsing the download thumbnail image
                    List<String> thumbnailCmd = new ArrayList<>(argsList);
                    thumbnailCmd.add("--get-thumbnail");
                    thumbnailCmd.add(urlLabel.getText());
                    Process thumbnailProcess = new ProcessBuilder(thumbnailCmd).redirectErrorStream(true).start();

                    Task<Void> thumbnailLoader = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {

                            InputStream inputStream = thumbnailProcess.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            String line = null;
                            while(thumbnailProcess.isAlive() && line == null) {
                                line = bufferedReader.readLine();
                            }
                            thumbnailProcess.destroy();
                            bufferedReader.close();
                            inputStream.close();
                            String thumbnailUrl = line;
                            if(thumbnailUrl!= null && thumbnailUrl.matches(urlRegex)) {
                                Platform.runLater(() -> {
                                    thumbnailImageView.setImage(new Image(thumbnailUrl, true));
                                    thumbnailImageView.setAccessibleText(thumbnailUrl);
                                });
                            }

                            synchronized (latch) {
                                latch.countDown();
                                if(latch.getCount() == 0) {
                                    Platform.runLater(() -> closeUrlDialog());
                                    timeline.stop();
                                }
                            }

                            return null;
                        }

                    };

                    Thread thumbnailLoaderThread = new Thread(thumbnailLoader);
                    thumbnailLoaderThread.start();


                    // for parsing the download title and description
                    List<String> titleCmd = new ArrayList<>(argsList);
                    titleCmd.add("-o");
                    titleCmd.add(TEMP_DIRECTORY + System.getProperty("file.separator") + "%(title)s");
                    titleCmd.add(urlLabel.getText());
                    Process titleProcess = new ProcessBuilder(titleCmd).redirectErrorStream(true).start();

                    Task<Void> titleParser = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {

                            InputStream inputStream = titleProcess.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                            // parse the download description
                            String line = bufferedReader.readLine();
                            if(line.matches("\\[.+\\].+")) {
                                String description = line.split("\\[")[1].split("\\]")[0].replace(':', ' ');
                                Platform.runLater(() -> descriptionLabel.setText(description));
                            }

                            //parse the download title
                            while(titleProcess.isAlive() && (line = bufferedReader.readLine()) != null) {

                                if(line.startsWith("[download]") && line.contains(":")) {
                                    titleProcess.destroy();
                                    bufferedReader.close();
                                    inputStream.close();
                                    String title = line.replace(TEMP_DIRECTORY + System.getProperty("file.separator"), "").split(":")[1].split("\\.f\\d{1,4}")[0];
                                    Platform.runLater(() -> titleLabel.setText(title));
                                    break;
                                }

                            }

                            synchronized (latch) {
                                latch.countDown();
                                if(latch.getCount() == 0) {
                                    Platform.runLater(() -> closeUrlDialog());
                                    timeline.stop();
                                }
                            }

                            return null;
                        }

                    };

                    Thread titleParserThread = new Thread(titleParser);
                    titleParserThread.start();


                    // for parsing the download qualities
                    List<String> qualityCmd = new ArrayList<>(argsList);
                    qualityCmd.add("-F");
                    qualityCmd.add(urlLabel.getText());
                    Process qualityProcess = new ProcessBuilder(qualityCmd).start();

                    Task<Void> qualityParser = new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {

                            InputStream inputStream = qualityProcess.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                            String line = bufferedReader.readLine();

                            while(qualityProcess.isAlive() && line != null && !line.startsWith("format code")) {
                                line = bufferedReader.readLine();
                            }

                            StringBuilder qualityLines = new StringBuilder();
                            int c = bufferedReader.read();
                            while(qualityProcess.isAlive() && c != -1 && c != (int)'[') {
                                qualityLines.append((char) c);
                                c = bufferedReader.read();
                            }

                            qualityProcess.destroy();
                            bufferedReader.close();
                            inputStream.close();

                            String[] qualityLinesArray = qualityLines.toString().split("[\n\r]+");
                            for(String qualityLine : qualityLinesArray) {
                                Quality quality = Quality.parseAndCreate(qualityLine);
                                if(quality != null) {
                                    if (quality.getType() == Quality.Type.AUDIO_ONLY) {
                                        audioQualities.add(quality);
                                    } else {
                                        videoQualities.add(quality);
                                    }
                                }
                            }


                            Platform.runLater(() -> {

                                videoQualityChoiceBox.getItems().addAll(videoQualities);
                                audioQualityChoiceBox.getItems().addAll(audioQualities);
                                convertChoiceBox.getItems().add(0, "None");

                                if(videoQualities.size() > 0) {
                                    videoQualityChoiceBox.getItems().add(0, new Quality("Default"));
                                    audioQualityChoiceBox.getItems().add(0, new Quality("Default"));
                                    convertChoiceBox.getItems().addAll(convertToVideoFormats);
                                    if(audioQualities.size() > 0) {
                                        videoQualityChoiceBox.getItems().add(new Quality("None"));
                                        audioQualityChoiceBox.getItems().add(new Quality("None"));
                                        convertChoiceBox.getItems().addAll(convertToAudioFormats);
                                    }
                                } else {
                                    if(audioQualities.size() > 0) {
                                        videoQualityChoiceBox.getItems().add(new Quality("None"));
                                        convertChoiceBox.getItems().addAll(convertToAudioFormats);
                                    } else {
                                        videoQualityChoiceBox.getItems().add(0, new Quality("Default"));
                                        convertChoiceBox.getItems().addAll(convertToVideoFormats);
                                        convertChoiceBox.getItems().addAll(convertToAudioFormats);
                                    }
                                    audioQualityChoiceBox.getItems().add(0, new Quality("Default"));
                                }

                                videoQualityChoiceBox.getSelectionModel().select(0);
                                audioQualityChoiceBox.getSelectionModel().select(0);
                                convertChoiceBox.getSelectionModel().select(0);

                            });

                            synchronized (latch) {
                                latch.countDown();
                                if(latch.getCount() == 0) {
                                    Platform.runLater(() -> closeUrlDialog());
                                    timeline.stop();
                                }
                            }

                            return null;
                        }

                    };

                    Thread qualityParserThread = new Thread(qualityParser);
                    qualityParserThread.start();


                    timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(60), ae -> {
                        if(thumbnailProcess.isAlive())
                            thumbnailProcess.destroy();
                        if(titleProcess.isAlive())
                            titleProcess.destroy();
                        if(qualityProcess.isAlive())
                            qualityProcess.destroy();

                        Platform.runLater(() -> {
                            if(videoQualityChoiceBox.getItems().size() == 0 && audioQualityChoiceBox.getItems().size() == 0) {
                                videoQualityChoiceBox.getItems().add(0, new Quality("Default"));
                                audioQualityChoiceBox.getItems().add(0, new Quality("Default"));
                                convertChoiceBox.getItems().add(0, "None");
                                convertChoiceBox.getItems().addAll(convertToVideoFormats);
                                convertChoiceBox.getItems().addAll(convertToAudioFormats);
                                videoQualityChoiceBox.getSelectionModel().select(0);
                                audioQualityChoiceBox.getSelectionModel().select(0);
                                convertChoiceBox.getSelectionModel().select(0);
                            }
                        });

                        closeUrlDialog();
                        System.out.println("Request timeout...");
                    }));
                    timeline.play();

                } catch (Exception e) {
                    new MessageDialog("Error getting the download info\n" +
                            "Try again later or report this issue", MessageDialog.Type.ERROR,
                            MessageDialog.Buttons.CLOSE).show();
                }

            });
            cancelBtnOnUrlDialog.setOnAction(actionEvent -> {
                urlDialogStage.close();
                cancelBtnAction();
            });

            formatChoiceBox.getItems().add(0, "Default");
            formatChoiceBox.getSelectionModel().select(0);

            videoQualityChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, wasSelectedQuality, nowSelectedQuality) -> {
                if(videoQualities.size() > 0) {

                    if(nowSelectedQuality != null) {

                        if(nowSelectedQuality.getText().equals("Default")) {
                            audioQualityChoiceBox.getItems().clear();
                            audioQualityChoiceBox.getItems().add(0, new Quality("Default"));
                            audioQualityChoiceBox.getSelectionModel().select(0);
                            formatChoiceBox.getItems().clear();
                            formatChoiceBox.getItems().add(0, "Default");
                            formatChoiceBox.getSelectionModel().select(0);
                            convertChoiceBox.getItems().clear();
                            convertChoiceBox.getItems().addAll(convertToVideoFormats);
                            convertChoiceBox.getItems().addAll(convertToAudioFormats);
                            convertChoiceBox.getItems().add(0, "None");
                            convertChoiceBox.getSelectionModel().select(0);
                        } else if(nowSelectedQuality.getType() == Quality.Type.FULL_VIDEO) {
                            audioQualityChoiceBox.getItems().clear();
                            audioQualityChoiceBox.getItems().addAll(audioQualities);
                            audioQualityChoiceBox.getItems().add(0, new Quality("Default"));
                            audioQualityChoiceBox.getSelectionModel().select(0);
                            formatChoiceBox.getItems().clear();
                            formatChoiceBox.getItems().add(nowSelectedQuality.getExtension());
                            formatChoiceBox.getSelectionModel().select(0);
                            convertChoiceBox.getItems().clear();
                            convertChoiceBox.getItems().addAll(convertToVideoFormats);
                            convertChoiceBox.getItems().addAll(convertToAudioFormats);
                            convertChoiceBox.getItems().add(0, "None");
                            convertChoiceBox.getSelectionModel().select(0);
                        } else if(nowSelectedQuality.getType() == Quality.Type.VIDEO_ONLY) {
                            audioQualityChoiceBox.getItems().clear();
                            if(audioQualities.size() > 0) {
                                audioQualityChoiceBox.getItems().addAll(audioQualities);
                                formatChoiceBox.getItems().clear();
                                formatChoiceBox.getItems().addAll(containerFormats);
                                formatChoiceBox.getSelectionModel().select(0);
                                convertChoiceBox.getItems().clear();
                                convertChoiceBox.getItems().addAll(convertToVideoFormats);
                                convertChoiceBox.getItems().addAll(convertToAudioFormats);
                                convertChoiceBox.getItems().add(0, "None");
                                convertChoiceBox.getSelectionModel().select(0);
                            } else {
                                formatChoiceBox.getItems().clear();
                                formatChoiceBox.getItems().add(nowSelectedQuality.getExtension());
                                formatChoiceBox.getSelectionModel().select(0);
                                convertChoiceBox.getItems().clear();
                                convertChoiceBox.getItems().addAll(convertToVideoFormats);
                                convertChoiceBox.getItems().add(0, "None");
                                convertChoiceBox.getSelectionModel().select(0);
                            }
                            audioQualityChoiceBox.getItems().add(new Quality("None"));
                            audioQualityChoiceBox.getSelectionModel().select(0);
                        } else if(nowSelectedQuality.getText().equals("None") && audioQualities.size() > 0) {
                            audioQualityChoiceBox.getItems().clear();
                            audioQualityChoiceBox.getItems().addAll(audioQualities);
                            audioQualityChoiceBox.getSelectionModel().select(0);
                            convertChoiceBox.getItems().clear();
                            convertChoiceBox.getItems().addAll(convertToAudioFormats);
                            convertChoiceBox.getItems().add(0, "None");
                            convertChoiceBox.getSelectionModel().select(0);
                        }

                    }
                }

            });
            audioQualityChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, wasSelectedQuality, nowSelectedQuality) -> {

                if(nowSelectedQuality != null) {

                    if(nowSelectedQuality.getText().equals("Default")) {
                        formatChoiceBox.getItems().clear();
                        if(videoQualityChoiceBox.getSelectionModel().getSelectedItem().getType() == Quality.Type.FULL_VIDEO)
                            formatChoiceBox.getItems().add(videoQualityChoiceBox.getSelectionModel().getSelectedItem().getExtension());
                        else
                            formatChoiceBox.getItems().add(0, "Default");
                        formatChoiceBox.getSelectionModel().select(0);
                    } else if(nowSelectedQuality.getText().equals("None")) {
                        formatChoiceBox.getItems().clear();
                        formatChoiceBox.getItems().add(videoQualityChoiceBox.getSelectionModel().getSelectedItem().getExtension());
                        formatChoiceBox.getSelectionModel().select(0);
                        convertChoiceBox.getItems().clear();
                        convertChoiceBox.getItems().addAll(convertToVideoFormats);
                        convertChoiceBox.getItems().add(0, "None");
                        convertChoiceBox.getSelectionModel().select(0);
                    } else if(nowSelectedQuality.getType() == Quality.Type.AUDIO_ONLY) {
                        if(videoQualityChoiceBox.getSelectionModel().getSelectedItem().getText().equals("None")) {
                            formatChoiceBox.getItems().clear();
                            formatChoiceBox.getItems().add(nowSelectedQuality.getExtension());
                            formatChoiceBox.getSelectionModel().select(0);
                            convertChoiceBox.getItems().clear();
                            convertChoiceBox.getItems().addAll(convertToAudioFormats);
                            convertChoiceBox.getItems().add(0, "None");
                            convertChoiceBox.getSelectionModel().select(0);
                        } else if(videoQualityChoiceBox.getSelectionModel().getSelectedItem().getType() == Quality.Type.VIDEO_ONLY
                                || videoQualityChoiceBox.getSelectionModel().getSelectedItem().getType() == Quality.Type.FULL_VIDEO) {
                            formatChoiceBox.getItems().clear();
                            formatChoiceBox.getItems().addAll(containerFormats);
                            formatChoiceBox.getSelectionModel().select(0);
                            convertChoiceBox.getItems().clear();
                            convertChoiceBox.getItems().addAll(convertToVideoFormats);
                            convertChoiceBox.getItems().addAll(convertToAudioFormats);
                            convertChoiceBox.getItems().add(0, "None");
                            convertChoiceBox.getSelectionModel().select(0);
                        }
                    }

                }

            });
            formatChoiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, wasSelectedFormat, nowSelectedFormat) -> {
                if (nowSelectedFormat != null && ! nowSelectedFormat.equals("Default") && ! nowSelectedFormat.equals("None")) {
                    convertChoiceBox.getItems().remove(nowSelectedFormat);
                }
            });

            urlDialogStage.setResizable(false);
            urlDialogStage.initStyle(StageStyle.TRANSPARENT);
            urlDialogStage.initModality(Modality.WINDOW_MODAL);
            urlDialogStage.initOwner(appStage);
            urlDialogStage.getIcons().add(0, new Image(getClass().getResource("/icon/icon.png").toString()));
            urlDialogStage.setOnCloseRequest(Event::consume);
            urlDialogStage.setOpacity(0.75);
            urlDialogStage.setX(appStage.getX() + appStage.getWidth()/2d - URL_DIALOG_WIDTH/2d);
            urlDialogStage.setY(appStage.getY() + appStage.getHeight()/2d - URL_DIALOG_HEIGHT/2d);
            urlDialogStage.show();
            urlDialogStage.toFront();

        } catch (Exception ex) {
            new MessageDialog("Error Loading the Home Window! \n" +
                    "Try again later or report this issue", MessageDialog.Type.ERROR,
                    MessageDialog.Buttons.CLOSE).createErrorDialog(ex.getStackTrace()).showAndWait();
        }

    }

    public void closeUrlDialog() {

        if(urlDialogStage != null) {
            urlDialogStage.close();
            newDownloadPane.setEffect(null);
        }

    }


    @FXML
    private void urlLabelAction() {
        try {
            Desktop.browseURL(urlLabel.getText());
        } catch (IOException e) {
            new MessageDialog("Error opening the default web browser" +
                    "Try again later or report this issue", MessageDialog.Type.ERROR, MessageDialog.Buttons.CLOSE).show();
        }
    }

    @FXML
    private void browseBtnAction() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose save location");
        File initDirectory = new File(locationTextField.getText());
        if(initDirectory.exists() && initDirectory.isDirectory())
            directoryChooser.setInitialDirectory(initDirectory);

        File selectedDirectory = directoryChooser.showDialog(newDownloadPane.getScene().getWindow());
        if(selectedDirectory != null)
            locationTextField.setText(selectedDirectory.getPath());

    }

    @FXML
    private void startBtnAction() {

        if(isValidInfo()) {
            Item item = createItem();
            item.setIsAddedToQueue(false);
            HomeController.getItemList().add(item);
            DataHandler.save(item);
            item.startDownload();
            cancelAndSetQueueBtn(false);
        }

    }

    @FXML
    private void scheduleBtnAction() {

        if(isValidInfo()) {
            Item item = createItem();
            item.setIsAddedToQueue(true);

            if(queueIsRunningBefore(item))
                item.setStatus("Waiting");
            else
                item.setStatus("Stopped");

            HomeController.getQueueItemList().add(item);
            DataHandler.save(item);
            cancelAndSetQueueBtn(true);
        }

    }

    @FXML
    private void cancelBtnAction() {
        cancelAndSetQueueBtn(isQueueBtnSelected);
    }

    private boolean isValidInfo() {

        boolean result = true;

        // Check if the save location is not empty
        if(locationTextField.getText().equals("")) {
            locationTextField.getStyleClass().add("text-field-error");
            result = false;
        } else {
            locationTextField.getStyleClass().removeAll(Collections.singleton("text-field-error"));
        }

        // Check if the custom name is not empty
        if(customNameChkBox.isSelected()) {
            if(customNameTextField.getText().equals("")) {
                customNameTextField.getStyleClass().add("text-field-error");
                result = false;
            } else {
                customNameTextField.getStyleClass().removeAll(Collections.singleton("text-field-error"));
            }
        }

        // Check if the specific playlist items are written correctly
        if(isPlaylistChkBox.isSelected()) {
            if(indexRangeRadioBtn.isSelected()) {
                if(startIndexTextField.getText().matches("[0-9]*")) {
                    startIndexTextField.getStyleClass().removeAll(Collections.singleton("text-field-error"));
                } else {
                    startIndexTextField.getStyleClass().add("text-field-error");
                    result = false;
                }

                if(endIndexTextField.getText().matches("[0-9]*")) {
                    endIndexTextField.getStyleClass().removeAll(Collections.singleton("text-field-error"));
                } else {
                    endIndexTextField.getStyleClass().add("text-field-error");
                    result = false;
                }
            }

            if(specificItemsRadioBtn.isSelected()) {
                if(playlistItemsTextField.getText().replaceAll("\\s","").matches("[-,0-9]+")) {
                    playlistItemsTextField.getStyleClass().removeAll(Collections.singleton("text-field-error"));
                } else {
                    playlistItemsTextField.getStyleClass().add("text-field-error");
                    result = false;
                }
            }

            // Check if the interval is valid
            if(intervalSpinner.getEditor().getText().matches("[0-9]+")) {
                intervalSpinner.getStyleClass().removeAll(Collections.singleton("spinner-error"));
            } else {
                intervalSpinner.getStyleClass().add("spinner-error");
                result = false;
            }
        }

        // Check if the username and password are not empty
        if(needLoginCheckBox.isSelected()) {
            if(userNameTextField.getText().equals("")) {
                userNameTextField.getStyleClass().add("text-field-error");
                result = false;
            } else {
                userNameTextField.getStyleClass().removeAll(Collections.singleton("text-field-error"));
            }

            if(passwordTextField.getText().equals("")) {
                passwordTextField.getStyleClass().add("text-field-error");
                result = false;
            } else {
                passwordTextField.getStyleClass().removeAll(Collections.singleton("text-field-error"));
            }
        }

        // Check if the speed limit is valid
        if(limitSpinner.getEditor().getText().matches("[0-9]+")) {
            limitSpinner.getStyleClass().removeAll(Collections.singleton("spinner-error"));
        } else {
            limitSpinner.getStyleClass().add("spinner-error");
            result = false;
        }

        return result;
    }

    private Item createItem() {

        Item item = new Item();

        item.setId(DataHandler.getNextId());
        item.setUrl(urlLabel.getText());
        item.setTitle(titleLabel.getText());
        item.setDescription(descriptionLabel.getText());
        item.setThumbnailUrl(thumbnailImageView.getAccessibleText());
        item.setLocation(locationTextField.getText().replaceAll("[/\\\\]$",""));
        if(customNameChkBox.isSelected())
            item.setCustomName(customNameTextField.getText());

        if(isPlaylistChkBox.isSelected()) {
            item.setIsPlaylist(true);
            item.setNeedAllPlaylistItems(false);
            if(allItemsRadioBtn.isSelected()) {
                item.setNeedAllPlaylistItems(true);
            } else if(indexRangeRadioBtn.isSelected()) {

                if(startIndexTextField.getText().equals("")) {
                    item.setPlaylistStartIndex(0);
                } else {
                    item.setPlaylistStartIndex(Integer.parseInt(startIndexTextField.getText()));
                }

                if(endIndexTextField.getText().equals("")) {
                    item.setPlaylistEndIndex(-1);
                } else {
                    item.setPlaylistEndIndex(Integer.parseInt(endIndexTextField.getText()));
                }

            } else {
                item.setPlaylistItems(playlistItemsTextField.getText().replaceAll("\\s",""));
            }
            item.setIntervalBetweenItems(Integer.parseInt(limitSpinner.getEditor().getText()));
        }

        item.setVideoQuality(videoQualityChoiceBox.getSelectionModel().getSelectedItem());
        item.setAudioQuality(audioQualityChoiceBox.getSelectionModel().getSelectedItem());
        if(item.getVideoQuality().getText().equals("None"))
            item.setIsVideo(false);
        else
            item.setIsVideo(true);
        item.setConvertTo(convertChoiceBox.getSelectionModel().getSelectedItem());
        item.setFormat(formatChoiceBox.getSelectionModel().getSelectedItem());

        item.setNeedEmbeddedSubtitle(embeddedSubtitleChkBox.isSelected());
        item.setSubtitleLanguage(subtitleLanguageChoiceBox.getValue());
        item.setNeedAutoGeneratedSubtitle(autoGenSubtitleChkBox.isSelected());

        if(needLoginCheckBox.isSelected()) {
            item.setUserName(userNameTextField.getText());
            item.setPassword(AES.encrypt(passwordTextField.getText()));
        }

        item.setSpeedLimit(Integer.parseInt(limitSpinner.getEditor().getText()));
        item.setShutdownAfterFinish(shutdownCheckBox.isSelected());

        return item;
    }

    private void cancelAndSetQueueBtn(boolean queueBtnSelected) {

        try {

            FXMLLoader homeWindowLoader = new FXMLLoader(getClass().getResource("/windows/HomeWindow.fxml"));
            homeWindowLoader.load();
            ((HomeController) homeWindowLoader.getController()).getQueueBtn().setSelected(queueBtnSelected);
            Parent root = homeWindowLoader.getRoot();
            newDownloadPane.getScene().setRoot(root);

        } catch (Exception e) {
            newDownloadPane.setOpacity(0.30);
            new MessageDialog("Error Loading the Home Window! \n" +
                    "Try again later or report this issue", MessageDialog.Type.ERROR,
                    MessageDialog.Buttons.CLOSE).createErrorDialog(e.getStackTrace()).showAndWait();
            newDownloadPane.setOpacity(1);
        }

    }

    private boolean queueIsRunningBefore(Item currentItem) {
        for(Item item : HomeController.getQueueItemList()) {
            if(item.getStatus().equals("Starting") || item.getStatus().equals("Running"))
                return true;
        }
        return false;
    }

}

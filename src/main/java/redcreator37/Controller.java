package redcreator37;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.Component;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Controller class for Dialog Box Generator
 */
public class Controller extends Component {

    // initialize controls
    public TextField titleField, headerField, countField, buttonsField;
    public TextArea contentBox;
    public CheckBox topMostCBox, randomPosCBox, autoHideCBox;
    public ComboBox<String> typeCombo;

    /**
     * Add items to the dialog typo combo box
     */
    public void addItemsToCombo() {
        String[] items = {"Information", "Warning", "Error", "Confirmation", "Generic"};
        ObservableList<String> comboItems = FXCollections.observableArrayList(items);
        typeCombo.setItems(comboItems);
    }

    /**
     * Main process (display the dialog boxes)
     */
    public void start() {
        String title, header, text, count, type;
        title  = titleField.getText();
        header = headerField.getText();
        text   = contentBox.getText();
        count  = countField.getText();
        type   = typeCombo.getSelectionModel().getSelectedItem();
        String[] buttonText = buttonsField.getText().split(" ");
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

        if (type == null) { // to avoid null pointers
            typeCombo.requestFocus();
            return;
        }

        ButtonType[] buttons = new ButtonType[buttonText.length];
        for (int i = 0; i < buttonText.length; i++) {   // first button will be the default one
            if (i == 0) buttons[i] = new ButtonType(buttonText[i], ButtonBar.ButtonData.OK_DONE);
            else buttons[i] = new ButtonType(buttonText[i], ButtonBar.ButtonData.CANCEL_CLOSE);
        }

        // display the dialogs
        try {
            for (int i = Integer.parseInt(count); i > 0; i--)
                 Platform.runLater(() -> {
                     Alert alert = new Alert(extractAlertType(type));
                     alert.initModality(Modality.NONE);
                     alert.setTitle(title);
                     alert.setHeaderText(header);
                     alert.setContentText(text);
                     alert.getDialogPane().getButtonTypes().setAll(buttons);

                     if (randomPosCBox.isSelected()) {
                         alert.setX(ThreadLocalRandom.current().nextInt((int) bounds.getMaxX() + 1));
                         alert.setY(ThreadLocalRandom.current().nextInt((int) bounds.getMaxY() + 1));
                     }
                     alert.showAndWait();
                 });
        } catch (NumberFormatException | NullPointerException e) {
            // caused by invalid input so ignore it
        }

        if (autoHideCBox.isSelected()) hideMainWin();
        System.gc(); // free up some memory
    }

    /**
     * Return the correct alert type based on the text inserted.
     *
     * @param str the input string
     * @return AlertType based on the input string or "none" if no
     * valid one is specified
     */
    private static Alert.AlertType extractAlertType(String str) {
        switch (str.toUpperCase()) {
            case "CONFIRMATION":
                return Alert.AlertType.CONFIRMATION;
            case "WARNING":
                return Alert.AlertType.WARNING;
            case "ERROR":
                return Alert.AlertType.ERROR;
            case "INFORMATION":
                return Alert.AlertType.INFORMATION;
            default:    // return generic type by default
                return Alert.AlertType.NONE;
        }
    }

    /**
     * Set the main window as top-most if "keep on top" is selected
     */
    public void setTopMost() {
        Stage stage = (Stage) topMostCBox.getScene().getWindow();
        stage.setAlwaysOnTop(topMostCBox.isSelected());
    }

    /**
     * Hide the main window but don't close the dialog boxes
     */
    public void hideMainWin() {
        topMostCBox.getScene().getWindow().hide();
    }

    /**
     * Close the program
     */
    public void close() {
        System.exit(0);
    }

}

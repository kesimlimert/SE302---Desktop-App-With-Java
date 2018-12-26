package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class Controller {

    public static ObservableList<String> observableCollectionList = FXCollections.observableArrayList(); //Collectionların listede tutulması için
    public DatabaseOperations databaseOperations = new DatabaseOperations();
    @FXML
    private BorderPane mainPanel;
    @FXML
    private ListView<String> collectionListView;

    @FXML
    public void initialize(){
        observableCollectionList.setAll(databaseOperations.takeAllTableName());
        collectionListView.setItems(observableCollectionList);
    }
    @FXML
    public void showAddCollectionDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Add new Collection");
        dialog.setHeaderText(null);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("collectionDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

        } else {
            System.out.println("Cancel Pressed");
        }

    }

    @FXML
    public void showEditCollectionDialog() {

        /*
        Seçilen koleksiyonun üzerinde edit yapılacak (ekstra bir koleksiyon oluşturmadan).
        Aşağıdaki kodlar showAddCollection methoduyla aynı bunu edite göre düzenleyin!
         */

        System.out.println("Collection eklemeye ait Dialog Pane ayarlanacak");
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPanel.getScene().getWindow());
        dialog.setTitle("Edit collection");
        dialog.setHeaderText(null);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("collectionDialog.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            /*
            Editlemeye ait kodlar
             */

        } else {
            System.out.println("Cancel Pressed");
        }

    }

    @FXML
    public void showAddItemDialog() {
        /*
        Koleksiyon tarafında aldığımız fieldlar burada doldurulacak.
        Item eklemeye ait dialog pane açılacak textfielda yazılan değerler alınacak
        Buralar tamamen databaseden yapılacak
         */

        System.out.println("Item eklemeye ait Dialog Pane ayarlanacak");
        if(collectionListView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Select a collection!");
            alert.setContentText("Something went wrong");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));
            alert.showAndWait();
        }else{


            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.initOwner(mainPanel.getScene().getWindow());
            DialogPane itemDialogPane = new DialogPane();
            dialog.setTitle("Add item");

            //Bize burdan liste gelmesi gerekiyor (databaseden seçili collectiona göre) column nameleri mesela (Book'a ait BookName, BookPageNumber, BookAuthor bunların
            //bir listede gelmesi lazım
            ArrayList<String> sample = new ArrayList<>();
/*
            sample.add("Name");
            sample.add("Age");
            sample.add("page number");
            sample.add("year");
*/
            VBox vBox = new VBox();
            TextField[] textFields = new TextField[sample.size()];
            Label[] labelList = new Label[sample.size()];

            for(int i=0; i<sample.size(); i++){
                labelList[i] = new Label(sample.get(i));
                textFields[i] = new TextField();

                vBox.getChildren().addAll(labelList[i], textFields[i]);
            }

            Button addItem = new Button("Add Item");
            vBox.getChildren().add(addItem);
            vBox.setSpacing(10);

            ArrayList<String> userInputs = new ArrayList<>(); //User inputlarını bir yerde tutuyoruz
            //Userdan inputları alıyoruz


            try {
                addItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        for (int i = 0; i < sample.size(); i++) {
                            userInputs.add(textFields[i].getText());

                        }

                        // insert sql methodu çağırılacak ve userInputs List parametre olarak gönderilecek. Kolon sırasına göre inputlar insert edilecek.
                        //methodsql(userInputs); //bu sınıf yazılırken içinde try catch kullanılmayacak hata bu sınıfı çağıran sınafa throw edilecek

                           /* for (int j =0;j<sample.size();j++){
                                System.out.println(sample.get(j)+" = " +userInputs.get(j));
                            }*/
                    }

                });

            } catch (Exception e) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("Look, a Warning Dialog");
                alert.setContentText("Something went wrong!");
                Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(this.getClass().getResource("/icons/warning.png").toString()));

                alert.showAndWait();

            }

            itemDialogPane.setContent(vBox);
            dialog.getDialogPane().setContent(itemDialogPane);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            dialog.showAndWait();

        }


    }


    @FXML
    public void showEditItemDialog () {
        System.out.println("Edit aşaması");
        /*
        Seçilen iteme ait editleme (Database tarafı)
         */
        }
    }


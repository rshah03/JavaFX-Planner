import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class itemsTodo extends Application {

    private TableView<listItems> dataTable = new TableView<listItems>();
    final HBox sceneLayout = new HBox(); //Lays out elements in horizontal format
    private final ObservableList<listItems> taskData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args); //Initiate JavaFX
    }

    //Override the start function
    @Override
    public void start(Stage stage) {
        Scene mainScene = new Scene(new Group());
        stage.setTitle("Planne/ToDo List");
        //Set stage dimensions here -- adding scene via getRoot in Group later
        stage.setWidth(400);
        stage.setHeight(600);

        dataTable.setEditable(true); //FOR TESTING PURPOSES -- SET TO FALSE LATER

        //CREATE COLUMNS -- 1 PER SIMPLESTRINGPROPERTY IN NESTED STATIC CLASS CONSTRUCTOR

        TableColumn itemNameCol = new TableColumn("Task");
        itemNameCol.setMinWidth(300);
        //ASSOCIATE CELL VALUE WITH PROP FROM listItems
        itemNameCol.setCellValueFactory(new PropertyValueFactory<listItems, String>("itemName"));

        TableColumn itemDueDateCol = new TableColumn("Due Date");
        itemDueDateCol.setMinWidth(100);
        itemDueDateCol.setCellValueFactory(new PropertyValueFactory<listItems, String>("itemDueDate"));

        //CREATE COLUMN FOR itemID

        //PLACE ALL COMPONENTS INTO THE TABLE
        dataTable.setItems(taskData);
        dataTable.getColumns().addAll(itemNameCol, itemDueDateCol);

        //CREATE COMPONENTS TO ADD/REMOVE THE ENTRIES IN TABLE
        //Have the TextFields display some placeholder text -- prior to user input -- use function setPromptText()
        final TextField addItemName = new TextField();
        addItemName.setPromptText("Input task...");
        addItemName.setMaxWidth(itemNameCol.getPrefWidth());

        final TextField addItemDueDate = new TextField();
        addItemDueDate.setPromptText("Due on/by...");
        addItemDueDate.setMaxWidth(itemDueDateCol.getPrefWidth());

        final Button addButton = new Button("Add Task");
        addButton.setOnAction(new EventHandler<ActionEvent>() { //Anonymous inner class for add button
            //Debug -- Error somewhere here: is an check if override is required
            @Override
            public void handle(ActionEvent e) {
                taskData.add(new listItems(
                        addItemName.getText(),
                        addItemDueDate.getText()));
                //CLEAR VALUES IN TEXTFIELDS
                addItemName.clear();
                addItemDueDate.clear();
            }
        });

        final Button removeButton = new Button("Remove Task");
        removeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                taskData.remove(new listItems(
                        addItemName.getText(),
                        addItemDueDate.getText()));
                addItemName.clear();
                addItemDueDate.clear();
            }
        });

        sceneLayout.getChildren().addAll(addItemName, addItemDueDate, addButton, removeButton);
        //SET ARBITRARY SPACING FOR THE LAYOUT
        sceneLayout.setSpacing(3);

        //CREATE A LAYOUT FOR THE ENTIRE STAGE
        //Use a similar format? -- either stick to HBox or use VBox -- mess around with the layouts for a bit
        final VBox mainLayout = new VBox();
        mainLayout.setSpacing(5);
        //Look up how to stop the layout from hittin the window edges --> function setPadding(new Insets(w,x,y,z));
        mainLayout.setPadding(new Insets(7, 0, 0, 7));
        mainLayout.getChildren().addAll(dataTable, sceneLayout);

        //ADD THE SCENE TO THE STAGE
        //mainScene = new Scene(mainLayout, 400, 600);
        //stage.setScene(mainScene);

        ((Group) mainScene.getRoot()).getChildren().addAll(mainLayout);

        stage.setScene(mainScene);
        stage.show();

    }

    //To remove data from the table use data.remove(...) in the anonymous inner class for the remove button

    //Create the nested static class for the table items
    public static class listItems {
        private final SimpleStringProperty itemName;
        private final SimpleStringProperty itemDueDate;
        //private final SimpleIntegerProperty itemID;

        //Constructor
        protected listItems(String item, String dueDate) {
            //Init the SimpleStringProps from above
            this.itemName = new SimpleStringProperty(item);
            this.itemDueDate = new SimpleStringProperty(dueDate);
            //this.itemID = new SimpleIntegerProperty(iID);
        }

        //Getter/Setter methods
        public String getItem() {
            return itemName.get();
        }

        public void setItemName(String item) {
            itemName.set(item);
        }

        public String getDueDate() {
            return itemDueDate.get();
        }

        public void setItemDueDate(String dueDate) {
            itemDueDate.set(dueDate);
        }

        // TODO: CREATE THE GET/SET METHODS FOR itemID

    }//End of nested static class
}

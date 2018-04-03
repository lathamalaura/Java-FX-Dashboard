package coursework3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class Coursework3Controller implements Initializable {
    private static String markup;
    private static List<String> strings;
    private static List<Sales> sales;
    private static List<String> qtr;
    private static List<String> quantity;
    private static List<String> region;
    private static List<String> vehicle;
    private static List<String> years;
    //
    private static List<String> regionsveh;
    
    
    private DashService service;

    private CheckBox[] checkBoxes;

    @FXML
    private AnchorPane AnchorPane1;

    @FXML
    private HBox HBox1;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private BarChart<?, ?> BarChart1;
    
    @FXML
    private TableView<Sales> TableView1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new DashService();
        service.setAddress("http://glynserver.cms.livjm.ac.uk/DashService/SalesGetSales");
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
           
            @Override
            public void handle(WorkerStateEvent e) {
                markup = e.getSource().getValue().toString();
                
               // sales = (new Gson()).fromJson(markup, new TypeToken<LinkedList<Sales>>() {}.getType());
                sales = (new com.google.gson.Gson()).fromJson(markup, new com.google.gson.reflect.TypeToken<LinkedList<Sales>>() {}.getType());
              //  recruits = (new Gson()).fromJson(markup, new TypeToken<LinkedList<Recruits>>() {}.getType());
               // sales.add(new Sales(2, 45, "Asia", "Elise", 2014)); // NOTE : Insert Cheeky Data ! */
                
                qtr = sales.stream().map(o -> String.valueOf(o.getQTR())).distinct().collect(Collectors.toList());
                quantity = sales.stream().map(o -> String.valueOf(o.getQuantity())).distinct().collect(Collectors.toList());
                region = sales.stream().map(o -> o.getRegion()).distinct().collect(Collectors.toList());
                vehicle = sales.stream().map(o -> o.getVehicle()).distinct().collect(Collectors.toList());
                years = sales.stream().map(o -> String.valueOf(o.getYear())).distinct().collect(Collectors.toList());
                
              // constructObsevableList();

        System.out.println(markup);
                   
                constructCheckBoxes();
                constructTableView();
            }
        });

        service.start();
    }     
    
    /*    private void constructObsevableList() {
        List<String> legacyList = new LinkedList<String>();

        ObservableList<String> observableList = FXCollections.observableList(legacyList);
        observableList.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        System.out.println("Insert Detected !");
                        System.out.println("Size : " + c.getAddedSize());
                        

                        for (Object o : c.getAddedSubList()) {
                            System.out.println("Value : " + o.toString());
                        }
                    }
                    
                    // ToDo : Detect Alternative Operations i.e. Modify, Remove !
                }
            }
        });

        observableList.add("Oracle");
        observableList.addAll("JavaFX", "JavaDB");

        legacyList.add("GlassFish");        
        System.out.println("Complete Size : " + observableList.size());
    }*/

    private void constructCheckBoxes() { 
        checkBoxes = new CheckBox[years.size()];

        for (byte index = 0; index < years.size(); index++) {
            checkBoxes[index] = new CheckBox(years.get(index));
            checkBoxes[index].setSelected(true);
            checkBoxes[index].addEventFilter(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.out.println("Firstly, Event Filters !");
                }
            });
            checkBoxes[index].addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    System.out.println("Secondly, Event Handlers !");
                }
            });
            checkBoxes[index].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {                    
                    System.out.println("Thirdly, Convenience Methods !");

                    constructSeries();
                }
            });

            HBox1.getChildren().add(checkBoxes[index]);
        }

        // NOTE : More Controls Visible !
        AnchorPane1.getScene().getWindow().sizeToScene();

        constructSeries();
    }

    private void constructSeries() {
        BarChart1.getData().clear();

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                XYChart.Series series = new XYChart.Series();
                series.setName(checkBox.getText());

                // NOTE : Java SE 7 Code !
             //   for (Sales sales : sales) {
             //       if (sales.getIntake().equals(checkBox.getText())) {
             //           series.getData().add(new XYChart.Data<>(sales.getQtr(), sales.getQuantity(), sales.getRegion(), sales.getVehicle(), sales.getYear()));
             //       }
             //   }

                // NOTE : Java SE 8 Code !
                
               //----->>>>> sales.stream().filter(o -> o.getQtr().equals(checkBox.getText())).forEach(o -> {
               //    ---->>>>>  series.getData().add(new XYChart.Data<>(String.valueOf(o.getQuantity()), o.getVehicle()));
                    
                
               // ----->>>>>> });
                

                BarChart1.getData().add(series);
            }
        }
    }
    
   /* public ObservableList<Sales> getSales(){
        ObservableList<Sales> data = FXCollections.observableArrayList();
        return data;
    }*/
    
    private void constructTableView() {
        TableView1.getItems().clear();
        TableView1.getColumns().clear();
 
        TableColumn TableColumn1 = new TableColumn("QTR");
        TableColumn1.setMinWidth(75);
        TableColumn1.setCellValueFactory(new PropertyValueFactory("QTR"));
 
        TableColumn TableColumn2 = new TableColumn("Quantity");
        TableColumn2.setMinWidth(75);
        TableColumn2.setCellValueFactory(new PropertyValueFactory("Quantity"));
 
        TableColumn TableColumn3 = new TableColumn("Region");
        TableColumn3.setMinWidth(75);
        TableColumn3.setCellValueFactory(new PropertyValueFactory("Region"));
        
        TableColumn TableColumn4 = new TableColumn("Vehicle");
        TableColumn4.setMinWidth(75);
        TableColumn4.setCellValueFactory(new PropertyValueFactory("Vehicle"));
        
        TableColumn TableColumn5 = new TableColumn("Year");
        TableColumn5.setMinWidth(75);
        TableColumn5.setCellValueFactory(new PropertyValueFactory("Year"));
 
        TableView1.getColumns().addAll(TableColumn1, TableColumn2, TableColumn3, TableColumn4, TableColumn5);
        TableView1.setItems(FXCollections.observableArrayList(sales));
    }
            

    private static class DashService extends Service<String> {
        private StringProperty address = new SimpleStringProperty();

        public final void setAddress(String address) {
            this.address.set(address);
        }

        public final String getAddress() {
            return address.get();
        }

        public final StringProperty addressProperty() {
           return address;
        }

        @Override
        protected Task<String> createTask() {
            return new Task<String>() {
                private URL url;
                private HttpURLConnection connect;
                private String markup = "";

                @Override
                protected String call() {
                    try {
                        url = new URL(getAddress());
                        connect = (HttpURLConnection)url.openConnection();
                        connect.setRequestMethod("GET");
                        connect.setRequestProperty("Accept", "application/json");
                        connect.setRequestProperty("Content-Type", "application/json");                        

                        markup = (new BufferedReader(new InputStreamReader(connect.getInputStream()))).readLine();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    finally {
                        if (connect != null) {
                            connect.disconnect();
                        }
                    }

                    return markup;
                }
            };
        }
    }

    private static class TypeToken<T> {

        public TypeToken() {
        }
    }

    private static class Gson {

        public Gson() {
        }
    }
}
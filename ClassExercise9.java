package classexercise9;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.*;

import javax.swing.JOptionPane;

/**
 *
 * @author Danny
 */
public class ClassExercise9 extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Create layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Create labels
        Label lblID = new Label("ID:");
        Label lblLastName = new Label("Last Name:");
        Label lblFirstName = new Label("First Name:");
        Label lblAge = new Label("Age:");
        Label lblGender = new Label("Gender:");
        
        Label lblAddress = new Label("Address:");
        Label lblCity = new Label("City:");
        Label lblZip = new Label("Zip:");
        Label lblState = new Label("State");
        
        ComboBox<String> stateBox = new ComboBox<>();
        
        
        stateBox.getItems().addAll("CA", "AZ", "NY");

        // Create text fields
        TextField txtID = new TextField();
        TextField txtLastname = new TextField();
        TextField txtFirstname = new TextField();
        TextField txtAge = new TextField();
        
        TextField txtAddress = new TextField();
        TextField txtCity = new TextField();
        TextField txtZip = new TextField();

        // Create radio buttons for gender
        RadioButton rdoMale = new RadioButton("Male");
        RadioButton rdoFemale = new RadioButton("Female");
        ToggleGroup genderGroup = new ToggleGroup();
        rdoMale.setToggleGroup(genderGroup);
        rdoFemale.setToggleGroup(genderGroup);

        // Create buttons
        Button btnSubmit = new Button("Submit");
        Button btnUpdate = new Button("Update");
        Button btnDelete = new Button("Delete");

        // Create ListView to display customer data
        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(200);

        // Add components to gridPane
        gridPane.add(lblID, 0, 0);
        gridPane.add(txtID, 1, 0);
        gridPane.add(lblLastName, 0, 1);
        gridPane.add(txtLastname, 1, 1);
        gridPane.add(lblFirstName, 0, 2);
        gridPane.add(txtFirstname, 1, 2);
        gridPane.add(lblAge, 0, 3);
        gridPane.add(txtAge, 1, 3);
        gridPane.add(lblAddress, 0, 4);
        gridPane.add(txtAddress, 1, 4);
        gridPane.add(lblCity, 0, 5);
        gridPane.add(txtCity, 1, 5);
        gridPane.add(lblZip, 0, 6);
        gridPane.add(txtZip, 1, 6);
        
        gridPane.add(lblState, 0, 7);
        gridPane.add(stateBox, 1, 7);
        
        
        gridPane.add(lblGender, 0, 8);
        gridPane.add(rdoMale, 1, 8);
        gridPane.add(rdoFemale, 1, 9);
        gridPane.add(btnSubmit, 0, 10);
        gridPane.add(btnUpdate, 1, 10);
        gridPane.add(btnDelete, 2, 10);
        
        
        gridPane.add(listView, 0, 11, 3, 1); // Span across 3 columns

        // Scene setup
        Scene scene = new Scene(gridPane, 600, 600);
        primaryStage.setTitle("JavaFX with SQLite");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load customer data into ListView
        loadCustomerData(listView);

        // Submit button action
        btnSubmit.setOnAction(e -> {
            String id = txtID.getText();
            String lastname = txtLastname.getText();
            String firstname = txtFirstname.getText();
            int age = Integer.parseInt(txtAge.getText());
            String gender = ((RadioButton) genderGroup.getSelectedToggle()).getText();
            
            String address = txtAddress.getText();
            String city = txtCity.getText();
            String state = stateBox.getValue();
            String zip = txtZip.getText();

            insertData(id, lastname, firstname, age, gender, address, city, state, zip);
            loadCustomerData(listView); // Refresh ListView
        });

        // Update button action
        btnUpdate.setOnAction(e -> {
            String id = txtID.getText();
            String lastname = txtLastname.getText();
            updateData(id, lastname);
            loadCustomerData(listView);
        });

        // Delete button action
        btnDelete.setOnAction(e -> {
            String id = txtID.getText();
            deleteData(id);
            loadCustomerData(listView); // Refresh ListView
        });
    }

    private Connection connect() {
    String url = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/ClassExercise9/Customer.db";
    Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void insertData(String id, String lastname, String firstname, int age, String gender,
            String address, String city, String state, String zip) {
        String sql = "INSERT INTO Customer (id, lastname, firstname, age, gender, address, city, state, zip) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, lastname);
            pstmt.setString(3, firstname);
            pstmt.setInt(4, age);
            pstmt.setString(5, gender);
            
            pstmt.setString(6, address);
            pstmt.setString(7, city);
            pstmt.setString(8, state);
            pstmt.setString(9, zip);
            
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data inserted successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateData(String id, String lastname) {
        String sql = "UPDATE Customer SET lastname = ? WHERE ID = ? ";
        
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, lastname); //set lastname
            pstmt.setString(2, id); //set id for WHERE, clause
            int rowsAffected = pstmt.executeUpdate();
            
            if(rowsAffected > 0){
             JOptionPane.showMessageDialog(null, "Data updated successfully.");   
            } else
            {
             JOptionPane.showMessageDialog(null, "No Record found for ID");   
            }
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());   
            
        }
    }

    private void deleteData(String id) {
        String sql = "DELETE FROM Customer WHERE ID = ?";
        
        try(Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data deleted successfully");
            
                
        } catch(SQLException e){
            
            System.out.println(e.getMessage());   
        }
    }

    private void loadCustomerData(ListView<String> listView) {
        String sql = "SELECT * FROM Customer";
        //clear the listview before loading new data
        listView.getItems().clear();
        
        try(Connection conn = this.connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            
            while(rs.next())
            {
             String row = "ID: " + rs.getString("id") + 
                     ", Lastname: " + rs.getString("lastname") + 
                     ", Firstname: " + rs.getString("firstname") + 
                     ", Age: " + rs.getInt("age") + 
                     ", Gender: " + rs.getString("gender") +
                     ", Address: " + rs.getString("address") + 
                     ", City: " + rs.getString("city") + 
                     ", State: " + rs.getString("state") + 
                     ", Zip: " + rs.getString("zip");
              
             //add row to listview 
             listView.getItems().add(row);
        }
    } catch(SQLException e)
    {
        System.out.println(e.getMessage());   
    }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

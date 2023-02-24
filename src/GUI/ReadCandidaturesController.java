/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Candidature;
import entities.Offre;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import services.ServiceCandidature;
import services.ServiceOffre;
import utils.DbConnect;

/**
 * FXML Controller class
 *
 * @author Karim
 */
public class ReadCandidaturesController implements Initializable {

    @FXML
    private TableView<Candidature> candidaturesTable;
    @FXML
    private TableColumn<Candidature, String> idCol;
    @FXML
    private TableColumn<Candidature, String> offreCol;
    @FXML
    private TableColumn<Candidature, String> recruteurCol;
    @FXML
    private TableColumn<Candidature, String> candidatCol;
    @FXML
    private TableColumn<Candidature, String> statusCol;
    @FXML
    private TableColumn<Candidature, String > informationCol;
    @FXML
    private TableColumn<Candidature, String> datePostulationCol;
    @FXML
    private TableColumn<Candidature, String> dateModificationCol;
    @FXML
    private TableColumn<Candidature, String> buttonsCol;

    Candidature candidature = null ;
    
    ObservableList<Candidature>  candidaturesList = FXCollections.observableArrayList();
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                fillTableView();
    }    

    @FXML
    private void btnAjoutOffre(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("ReadOffres.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ReadOffresController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    @FXML
    private void btnClose(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void btnViewOffre() {
        candidaturesList.clear();                 
                            ServiceCandidature s = new ServiceCandidature();
                            s.afficherV2(candidaturesList, candidaturesTable);
    }


    
    
    
    
        Connection connection = null ;
    private void fillTableView() {
        
                //add cell of button edit 
        Callback<TableColumn<Candidature, String>, TableCell<Candidature, String>> cellFoctory = (TableColumn<Candidature, String> param) -> {
            // cell button
            final TableCell<Candidature, String> cell = new TableCell<Candidature, String>() {
                //fill
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //if row empty
                    if (empty) {setGraphic(null);setText(null);}
                    //if row has data
                    else { 
                        
                        
                        
                        
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        deleteIcon.setStyle(" -fx-cursor: hand ;"+ "-glyph-size:25px;"+ "-fx-fill:#744124;"); 
                        
                        //deletus
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            candidature = candidaturesTable.getSelectionModel().getSelectedItem();
                            ServiceCandidature s = new ServiceCandidature();
                            s.supprimer(new Candidature(candidature.getId()));    
                            JOptionPane.showMessageDialog(null, "Candidature pour l'offre '"+candidature.getOffre()+"' supprimée.");
                        }); 
                         
                        
                        
                        
                        
                        
                        
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        editIcon.setStyle(" -fx-cursor: hand ;"+ "-glyph-size:25px;"+ "-fx-fill:#BF833A;"); 
                        
                        //mod
                        editIcon.setOnMouseClicked((MouseEvent event) -> { 
                            candidature = candidaturesTable.getSelectionModel().getSelectedItem();
                            
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("CandidatureEdit.fxml"));
                            try {                                loader.load();                            } catch (IOException ex) {                                Logger.getLogger(ReadOffresController.class.getName()).log(Level.SEVERE, null, ex);                            }
                            CandidatureEditController eoc = loader.getController();
                            eoc.setTextField(candidature.getId(), candidature.getOffre(),candidature.getRecruteur(), candidature.getCandidat(), 
                                    candidature.getStatus(),candidature.getInformations(),
 candidature.getDatePostulation(), candidature.getDateModification());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();    
                        });
        
                                setGraphic(new HBox(editIcon , deleteIcon));
 

                    }
                }

            };

            return cell;
        };
        
     
        connection = DbConnect.getConnect();
          btnViewOffre();
        offreCol.setCellValueFactory(new PropertyValueFactory<>("offre"));
        recruteurCol.setCellValueFactory(new PropertyValueFactory<>("recruteur"));
        candidatCol.setCellValueFactory(new PropertyValueFactory<>("candidat"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        informationCol.setCellValueFactory(new PropertyValueFactory<>("informations"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        datePostulationCol.setCellValueFactory(new PropertyValueFactory<>("datePostulation"));
        dateModificationCol.setCellValueFactory(new PropertyValueFactory<>("dateModification")); 
         buttonsCol.setCellFactory(cellFoctory);
        candidaturesTable.setItems(candidaturesList);
    }
    
    
}

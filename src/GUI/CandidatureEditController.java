/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import entities.Candidature; 
import entities.Offre;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;
import services.ServiceCandidature;
import services.ServiceOffre;

/**
 * FXML Controller class
 *
 * @author Karim
 */
public class CandidatureEditController implements Initializable {
    
    @FXML
    private Label lbId;
    @FXML
    private Label lbTitreOffre;
    @FXML
    private Label lbNomRecruteur;
    @FXML
    private Label lbNomCandidat;
    @FXML
    private Label lbDatePostulation;
    @FXML
    private Label lbDateModification;
    @FXML
    private ChoiceBox<String> cbStatus;
    private String[] status_candidatures ={"Pending","Acceptée","Rejetée","Annulée"};
  
    @FXML
    private TextArea tfInformation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbStatus.getItems().addAll(status_candidatures);
    }    

    
        void setTextField(int id, String offre, String recruteur, String candidat, String status, String informations, String datePostulation, String dateModification)   {
        lbId.setText(Integer.toString(id));
        lbTitreOffre.setText(offre); 
        lbNomRecruteur.setText(recruteur); 
        lbNomCandidat.setText(candidat);  
        lbDatePostulation.setText(datePostulation);     
        lbDateModification.setText(dateModification); 
    } 
 
    
    @FXML
    private void btnConfirmerModif(ActionEvent event) {
     ServiceCandidature s = new ServiceCandidature();
     String d = LocalDate.now().toString()   ;
      s.modifier(new Candidature(Integer.parseInt(lbId.getText()),lbTitreOffre.getText(),lbNomRecruteur.getText(),lbNomCandidat.getText(),
        cbStatus.getValue(),tfInformation.getText(),lbDatePostulation.getText(),d));
           JOptionPane.showMessageDialog(null, "Candidature modifiée avec succès.");
  
    
        
         
    }
    
}

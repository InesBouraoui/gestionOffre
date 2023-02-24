/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package GUI;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Offre;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import javax.imageio.ImageIO;
import services.ServiceOffre;
import utils.DbConnect;





import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
 
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import jdk.nashorn.internal.objects.annotations.Property;
 

/**
 * FXML Controller class
 *
 * @author BOURAOUI
 */
public class ReadOffresController implements Initializable {

    @FXML
    private TableColumn<Offre, String> titreCol;
    @FXML
    private TableColumn<Offre, String> descCol;
    @FXML
    private TableColumn<Offre, String> postCol;
    @FXML
    private TableColumn<Offre, Number> salaireCol;
    @FXML
    private TableColumn<Offre, String> lieuCol;
    @FXML
    private TableColumn<Offre, String> contratCol;
    @FXML
    private TableColumn<Offre, Number> dureeCol;
    @FXML
    private TableColumn<Offre, String> statusCol;
    @FXML
    private TableColumn<Offre, String> domaineCol;
    @FXML
    private TableColumn<Offre, String> nomRecruteurCol;
    @FXML
    private TableColumn<Offre, String> emailRecruteurCol;
    @FXML
    private TableColumn<Offre, String> buttonsCol;
    
    @FXML
    private TableColumn<Offre, String> idCol;
    @FXML
    private TableView<Offre> offresTable;

    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Offre offre = null ;
    
    ObservableList<Offre>  offresList = FXCollections.observableArrayList();

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fillTableView();
    }    
    
    @FXML
    private void btnClose(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void btnAjoutOffre(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("CreateOffres.fxml"));
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
    private void btnViewCandidatures() {       
         try {
            Parent parent = FXMLLoader.load(getClass().getResource("ReadCandidatures.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ReadOffresController.class.getName()).log(Level.SEVERE, null, ex);
        }}
    
    @FXML
    private void btnViewOffre() {         
        offresList.clear();                 
                            ServiceOffre s = new ServiceOffre();
                            s.afficherV2(offresList, offresTable);
    }
    
    private void fillTableView() {
        
        //add cell of button edit 
        Callback<TableColumn<Offre, String>, TableCell<Offre, String>> cellFoctory = (TableColumn<Offre, String> param) -> {
            // cell button
            final TableCell<Offre, String> cell = new TableCell<Offre, String>() {
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
                            offre = offresTable.getSelectionModel().getSelectedItem();
                            ServiceOffre s = new ServiceOffre();
                            s.supprimer(new Offre(offre.getId()));    
                            JOptionPane.showMessageDialog(null, "Offre "+offre.getTitre()+" By "+offre.getNomRecruteur()+" Supprimé.");
                        }); 
                         
                        
                        
                        
                        
                        
                        
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        editIcon.setStyle(" -fx-cursor: hand ;"+ "-glyph-size:25px;"+ "-fx-fill:#BF833A;"); 
                        
                        //mod
                        editIcon.setOnMouseClicked((MouseEvent event) -> { 
                            offre = offresTable.getSelectionModel().getSelectedItem();
                            
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("EditOffre.fxml"));
                            try {                                loader.load();                            } catch (IOException ex) {                                Logger.getLogger(ReadOffresController.class.getName()).log(Level.SEVERE, null, ex);                            }
                            EditOffreController eoc = loader.getController();
                            eoc.setTextField(offre.getId(), offre.getTitre(),offre.getDescription(), offre.getPost(), offre.getSalaire(),offre.getLieu(),
 offre.getTypeContrat(), offre.getDuree(),offre.getStatus(), offre.getDomaineOffre(), offre.getNomRecruteur(),offre.getEmailRecruteur());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();    
                        });
                        
                        
                        FontAwesomeIconView postulerIcon = new FontAwesomeIconView(FontAwesomeIcon.HEART);
                        postulerIcon.setStyle(" -fx-cursor: hand ;"+ "-glyph-size:25px;"+ "-fx-fill:#0C2D40;"); 
                        
                        postulerIcon.setOnMouseClicked((MouseEvent event) -> { 
                            offre = offresTable.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("CandidaturePostuler.fxml"));
                            try {                                loader.load();                            } catch (IOException ex) {                                Logger.getLogger(ReadOffresController.class.getName()).log(Level.SEVERE, null, ex);                            }
                            CandidaturePostulerController eoc = loader.getController();
                                 String d = LocalDate.now().toString()   ;
                            eoc.setTextField(offre.getId(), offre.getTitre(),offre.getNomRecruteur(), "Candidat", "Pending",
                                    "Pas de changement pour le moment",d,"Pas de modifications");
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();    
                        });
 
                        setGraphic(new HBox(postulerIcon, editIcon , deleteIcon));
 

                    }
                }

            };

            return cell;
        };
        
        
        connection = DbConnect.getConnect();
        btnViewOffre();
        titreCol.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        postCol.setCellValueFactory(new PropertyValueFactory<>("post"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaire"));
        lieuCol.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        contratCol.setCellValueFactory(new PropertyValueFactory<>("typeContrat"));
        dureeCol.setCellValueFactory(new PropertyValueFactory<>("duree"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        domaineCol.setCellValueFactory(new PropertyValueFactory<>("domaineOffre"));
        nomRecruteurCol.setCellValueFactory(new PropertyValueFactory<>("nomRecruteur"));
        emailRecruteurCol.setCellValueFactory(new PropertyValueFactory<>("emailRecruteur"));
        buttonsCol.setCellFactory(cellFoctory);
        offresTable.setItems(offresList);
    }
    
}
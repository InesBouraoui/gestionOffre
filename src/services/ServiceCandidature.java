/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.Candidature; 
import entities.Offre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import utils.DbConnect;

/**
 *
 * @author Karim
 */
public class ServiceCandidature implements IService<Candidature> {
    private Connection cnx = DbConnect.getInstance().getConnection();
    
    
    @Override
    public void ajouter(Candidature c) {
        String req = "INSERT INTO candidature(offre, recruteur, candidat, status, informations, datePostulation, dateModification) VALUES(?, ?, ?, ?, ?, ?, ?);";
        try {
            //Statement st = cnx.createStatement();
            PreparedStatement pst = cnx.prepareStatement(req);
            pst.setString(1, c.getOffre());
            pst.setString(2, c.getRecruteur());
            pst.setString(3, c.getCandidat());
            pst.setString(4, c.getStatus());
            pst.setString(5, c.getInformations());       
            pst.setString(6, c.getDatePostulation());
            pst.setString(7, c.getDateModification()); 
            pst.executeUpdate();
            System.out.println("Candidature ajoutée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
 
    @Override
    public void modifier(Candidature c) { 
       String req = "UPDATE candidature SET id='"+c.getId()+"', offre= '"+c.getOffre()+"', recruteur= '"+c.getRecruteur()+"', candidat= '"+c.getCandidat()
               +"', status= '"+c.getStatus()+"', informations= '"+c.getInformations()+"', datePostulation= '"+c.getDatePostulation()+"', dateModification= '"+c.getDateModification()
               +"' WHERE id="+c.getId()+"";
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Candidature modifiée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } 
    }
    
    @Override
    public void supprimer(Candidature c) {
        String req = "DELETE FROM candidature WHERE id="+c.getId();
        try {
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("Candidature supprimée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
  @Override
    public List<Candidature> afficher() {
        List<Candidature> list = new ArrayList<>();
        
        String req = "SELECT * FROM candidature";
        try {
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);
            while(result.next()) { 
                list.add(new Candidature(result.getInt("id"),
                        result.getString("offre"),
                        result.getString("recruteur"),
                        result.getString("candidat"),
                        result.getString("status"),
                        result.getString("informations"), 
                        result.getString("datePostulation"),
                        result.getString("dateModification")));
            }
            System.out.println("Candidature récupérée !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return list;
    }
    
       public ObservableList<Candidature> afficherV2(ObservableList<Candidature> listee,TableView<Candidature> tableee) {
    //    List<Offre> list = new ArrayList<>();
        
       try { 
            String query = "SELECT * FROM `candidature`";
            Statement st = cnx.createStatement();
            ResultSet resultSet = st.executeQuery(query);; 
            
            while (resultSet.next()){
                listee.add(new Candidature(resultSet.getInt("id"),
                        resultSet.getString("offre"),
                        resultSet.getString("recruteur"),
                        resultSet.getString("candidat"),
                        resultSet.getString("status"),
                        resultSet.getString("informations"), 
                        resultSet.getString("datePostulation"),
                        resultSet.getString("dateModification")));
                                tableee.setItems(listee);      
            }}
         catch (SQLException ex) {
               System.out.println(ex.getMessage());
        }
        return listee;
    }
     
    
    
}
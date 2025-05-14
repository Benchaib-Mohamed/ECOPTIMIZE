package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

public class EcoptimizeService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        SELECT_ALL_PRODUITS("SELECT t.IdP, t.Nom, t.Poids, t.IG, t.Bio, t.Origine, t.IdC, t.IdA, t.NbRecherche FROM produits t"),
        DELETE_PRODUIT("DELETE FROM produits WHERE Nom = ?"),
        INSERT_PRODUIT("INSERT INTO produits (idP, nom, poids, ig, bio, origine, idC, idA, NbRecherche) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        //SELECT_PRODUIT_NOM("SELECT t.IdP, t.Nom, t.Poids, t.IG, t.Bio, t.Origine, t.IdC, t.IdA, t.NbRecherche FROM produits t WHERE t.Nom = ?");
        SELECT_PRODUIT_NOM("SELECT t.IdA, t.Nom, t.Poids, t.IG, t.Bio, t.Origine, 0, 0, 0 FROM aleternatives t, produits p WHERE p.Nom = ? AND p.IdA = t.IdA"),
        UPDATE_PRODUIT_NBRECHERCHE("UPDATE produits SET NbRecherche = ? WHERE Nom = ?"),
        SELECT_PRODUIT_NOMP("SELECT t.IdP,t.Nom,t.Poids,t.IG,t.Bio,t.Origine,t.IdC ,t.IdA ,t.NbRecherche , t.EmpreinteC FROM produits t WHERE t.Nom=?");
        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }

    public static EcoptimizeService inst = null;
    public static final EcoptimizeService getInstance()  {
        if(inst == null) {
            inst = new EcoptimizeService();
        }
        return inst;
    }

    private EcoptimizeService() {

    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;
        String s=request.getNom();

        final Queries queryEnum = Enum.valueOf(Queries.class, request.getRequestOrder());//request.get... =ordre de requete qui est recu de ProduitService
        switch(queryEnum) {
            case SELECT_ALL_PRODUITS:
                response = SelectAllProduits(request, connection);
                break;
            case DELETE_PRODUIT:
                DeleteProduitNom(request, connection);
                break;
            case INSERT_PRODUIT:
                response = InsertProduit(request, connection);
                break;
            case SELECT_PRODUIT_NOM:
            
                response = SelectProduitNom(request, connection,s);
                break;
            
            case  UPDATE_PRODUIT_NBRECHERCHE :
                 UpdateProduitNom(request, connection);
                 break;
            case SELECT_PRODUIT_NOMP :
                response=SelectProduitNomP(request, connection, s);
                break;
                
                
                default:
                break;
        }

        return response;
    }

    private Response InsertProduit(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Produit produit = objectMapper.readValue(request.getRequestBody(), Produit.class); // On instancie un produit grâce aux infos de la requet sous format JSON
    
       
        final PreparedStatement stmnt = connection.prepareStatement(Queries.INSERT_PRODUIT.query);
       
        
        stmnt.setInt(1, produit.getIdP());  
        stmnt.setString(2, produit.getNom()); 
        stmnt.setInt(3, produit.getPoids());  
        stmnt.setInt(4, produit.getIg()); 
        stmnt.setBoolean(5, produit.getBio()); 
        stmnt.setString(6, produit.getOrigine());
        stmnt.setInt(7, produit.getIdC()); 
        stmnt.setInt(8, produit.getIdA());  
        stmnt.setInt(9, produit.getNbRecherche());
        stmnt.executeUpdate();  
        
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(produit));
    }
    

    private Response SelectAllProduits(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_PRODUITS.query);
        Produits produits = new Produits();
    
        while (res.next()) {
            Produit produit = new Produit();
    
            // Récupération des valeurs 
            produit.setIdP(res.getInt(1));  
            produit.setNom(res.getString(2));
            produit.setPoids(res.getInt(3));  
            produit.setIg(res.getInt(4));  
            produit.setBio(res.getBoolean(5));  
            produit.setOrigine(res.getString(6));
            produit.setIdC(res.getInt(7));  
            produit.setIdA(res.getInt(8)); 
            produit.setNbRecherche(res.getInt(9));
    
            produits.add(produit);
        }
    
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(produits));
    }

    private Response SelectProduitNom(final Request request, final Connection connection, String s) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final PreparedStatement stmt = connection.prepareStatement(Queries.SELECT_PRODUIT_NOM.query);
        stmt.setString(1,s);
        final ResultSet res = stmt.executeQuery();
        if (res.next()) {
            Produit produit = new Produit();
            produit.setIdP(res.getInt(1));  
            produit.setNom(res.getString(2));
            produit.setPoids(res.getInt(3));  
            produit.setIg(res.getInt(4));  
            produit.setBio(res.getBoolean(5));  
            produit.setOrigine(res.getString(6));
            produit.setIdC(res.getInt(7));  
            produit.setIdA(res.getInt(8)); 
            produit.setNbRecherche(res.getInt(9));
        
            return new Response(request.getRequestId(), objectMapper.writeValueAsString(produit));
        } else {
            logger.warn("Aucun produit trouvé avec le nom : {}", s);
            return new Response(request.getRequestId(), "Produit non trouvé");
}
            
        }

    
        
    

    private void UpdateProduitNom(final Request request, final Connection connection) throws SQLException, JsonProcessingException,IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        
        final Produit produit = objectMapper.readValue(request.getRequestBody(), Produit.class);
        
        
        final PreparedStatement stmt = connection.prepareStatement(Queries.UPDATE_PRODUIT_NBRECHERCHE.query);
        stmt.setInt(1,produit.getNbRecherche());
        stmt.setString(2, produit.getNom());
        
        int rowsUpdated = stmt.executeUpdate();
        if (rowsUpdated > 0) {
            logger.info("Le nombre de recherches pour le produit '{}' a été mis à jour avec succès.", produit.getNom());
        } else {
            logger.error("Aucun produit trouvé avec le nom '{}'. Aucune mise à jour effectuée.", produit.getNom());
        }
    }

    private void DeleteProduitNom(final Request request, final Connection connection) throws SQLException, JsonProcessingException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();

        final Produit produit = objectMapper.readValue(request.getRequestBody(), Produit.class);
    
        final PreparedStatement stmt = connection.prepareStatement(Queries.DELETE_PRODUIT.query);
        stmt.setString(1, produit.getNom());
    
        int rowsDeleted = stmt.executeUpdate();
        if (rowsDeleted > 0) {
            logger.info("Le produit '{}' a été supprimé avec succès.", produit.getNom());
        } else {
            logger.error("Aucun produit trouvé avec le nom '{}'. Aucune suppression effectuée.", produit.getNom());
        }
    }
        
    private Response SelectProduitNomP(final Request request, final Connection connection, String s) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final PreparedStatement stmt = connection.prepareStatement(Queries.SELECT_PRODUIT_NOMP.query);
        stmt.setString(1,s);
        final ResultSet res = stmt.executeQuery();
        if (res.next()){

        Produit produit = new Produit();
       
            // Récupération des valeurs 
            produit.setIdP(res.getInt(1));  
            produit.setNom(res.getString(2));
            produit.setPoids(res.getInt(3));  
            produit.setIg(res.getInt(4));  
            produit.setBio(res.getBoolean(5));  
            produit.setOrigine(res.getString(6));
            produit.setIdC(res.getInt(7));  
            produit.setIdA(res.getInt(8)); 
            produit.setNbRecherche(res.getInt(9));
            produit.setEmpreinteC(res.getInt(10));

    
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(produit));}
        else {
            logger.warn("Aucun produit trouvé avec le nom : {}", s);
            return new Response(request.getRequestId(), "Produit non trouvé");}
    }


        

       



         





    }



    
    

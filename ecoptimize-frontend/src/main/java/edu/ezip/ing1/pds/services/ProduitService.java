package edu.ezip.ing1.pds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.InsertStudentsClientRequest;
import edu.ezip.ing1.pds.requests.SelectAllStudentsClientRequest;
import edu.ezip.ing1.pds.requests.SelectStudentNomClientRequest;
import edu.ezip.ing1.pds.requests.UpadateProduitClientRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

import javax.swing.JOptionPane;

public class ProduitService {
    private final static String LoggingLabel = "FrontEnd - StudentService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    //private final static String produitsToBeInserted = "produits-to-be-inserted.yaml";

    final String insertRequestOrder = "INSERT_PRODUIT";
    final String selectRequestOrder = "SELECT_ALL_PRODUITS";
    final String selectNomRequestOrder = "SELECT_PRODUIT_NOM";
    final String updateRequestOrder="UPDATE_PRODUIT_NBRECHERCHE";

    private final NetworkConfig networkConfig;

    public ProduitService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public void insertProduits() throws InterruptedException, IOException {
        


        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        Produit prod = new Produit();

        int idP;
        while (true) {
            try {
                idP = Integer.parseInt(JOptionPane.showInputDialog("Veuillez entrer l'id du produit (non existant)"));
                break; 
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer un nombre valide pour l'id du produit.");
            }
        }
        prod.setIdP(idP);

       
        String nom;
        while (true) {
            nom = JOptionPane.showInputDialog("Veuillez entrer le nom du produit à insérer");
            if (nom != null && !nom.trim().isEmpty()) { //trim() pour suppr les espaces et refuser les espaces comme seuls carac
                break; 
            }
            JOptionPane.showMessageDialog(null, "Erreur : Le nom du produit ne peut pas être vide.");
        }
        prod.setNom(nom);

        
        int poids;
        while (true) {
            try {
                poids = Integer.parseInt(JOptionPane.showInputDialog("Veuillez entrer le poids du produit (par unité en g)"));
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer un nombre valide pour le poids.");
            }
        }
        prod.setPoids(poids);

        
        int ig;
        while (true) {
            try {
                ig = Integer.parseInt(JOptionPane.showInputDialog("Veuillez entrer l'indice glycémique du produit"));
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer un nombre valide pour l'indice glycémique.");
            }
        }
        prod.setIg(ig);

       
        boolean bio;
        while (true) {
            String bioInput = JOptionPane.showInputDialog("Ce produit est-il bio (true/false) ?");
            if (bioInput.equalsIgnoreCase("true") || bioInput.equalsIgnoreCase("false")) {
                bio = Boolean.parseBoolean(bioInput);
                break;
            }
            JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer 'true' ou 'false'.");
        }
        prod.setBio(bio);

        
        String origine;
        while (true) {
            origine = JOptionPane.showInputDialog("Veuillez entrer l'origine du produit");
            if (origine != null && !origine.trim().isEmpty()) {
                break;
            }
            JOptionPane.showMessageDialog(null, "Erreur : L'origine du produit ne peut pas être vide.");
        }
        prod.setOrigine(origine);

        
        int cate;
        while (true) {
            try {
                cate = Integer.parseInt(JOptionPane.showInputDialog("Veuillez entrer l'id de la catégorie du produit (1=Gâteau, 2=Boisson)"));
                if (cate == 1 || cate == 2) {
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer 1 pour Gâteau ou 2 pour Boisson.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer un nombre valide (1 ou 2) pour la catégorie.");
            }
        }


        prod.setIdC(cate);
        prod.setIdA(cate);
        prod.setNbRecherche(0);
        int birthdate = 0;
        
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(prod);
            logger.trace("Student with its JSON face : {}", jsonifiedGuy);
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(insertRequestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertStudentsClientRequest clientRequest = new InsertStudentsClientRequest(
                    networkConfig,
                    birthdate++, request, prod, requestBytes);
            clientRequests.push(clientRequest);
        

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest.join();
            final Produit prod2 = (Produit)clientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} {} {} {} {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    prod2.getIdP(), prod2.getNom(), prod2.getPoids(), prod2.getIg(), prod2.getBio(), prod2.getOrigine() ,prod2.getIdC(), prod2.getIdA(),prod2.getNbRecherche(),
                    clientRequest2.getResult());
        }
    }

    public Produits selectProduits() throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(selectRequestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllStudentsClientRequest clientRequest = new SelectAllStudentsClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes); 
        clientRequests.push(clientRequest);

        if(!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (Produits) joinedClientRequest.getResult();
        }
        else {
            logger.error("No students found");
            return null;
        }
    }


    public Produit selectProduitNom(String nom) throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setNom(nom);
        request.setRequestId(requestId);
        request.setRequestOrder(selectNomRequestOrder);  // est récupéré dans EcoptimizeService.dispatch
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectStudentNomClientRequest clientRequest = new SelectStudentNomClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes); 
        clientRequests.push(clientRequest);
    
        if(!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            Produit P= (Produit) joinedClientRequest.getResult();
            if(P!= null){
                int NbrechercheIncrement=P.getNbRecherche()+1;
                P.setNbRecherche(NbrechercheIncrement);
            }
            
            final String updateRequestId = UUID.randomUUID().toString();
            Request request2=new Request();
            request2.setNom(nom);
            request2.setRequestId(updateRequestId);
            request2.setRequestOrder(updateRequestOrder);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestByte = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request2);
            LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestByte);
            UpadateProduitClientRequest updateRequest=new UpadateProduitClientRequest(networkConfig, birthdate, request2, null, requestByte);
            return P;
    
            
    
            
        }
        else {
            logger.error("No students found");
            return null;
        }
    }
    }
    



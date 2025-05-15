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
import edu.ezip.ing1.pds.requests.DeleteProduitClientRequest;
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

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class ProduitService {
    private final static String LoggingLabel = "FrontEnd - StudentService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    //private final static String produitsToBeInserted = "produits-to-be-inserted.yaml";

    final String insertRequestOrder = "INSERT_PRODUIT";
    final String deleteOrder="DELETE_PRODUIT";
    final String selectRequestOrder = "SELECT_ALL_PRODUITS";
    final String selectNomRequestOrder = "SELECT_PRODUIT_NOM";
    final String updateRequestOrder="UPDATE_PRODUIT_NBRECHERCHE";
    final String selectNomPRequestOrder="SELECT_PRODUIT_NOMP";

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
        if (nom != null && !nom.trim().isEmpty()) {
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

    int EmpreinteC;
    while (true) {
        try {
            EmpreinteC = Integer.parseInt(JOptionPane.showInputDialog("Veuillez entrer l'Empreinte carbonne du produit "));
            break;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erreur : Veuillez entrer un nombre valide pour le poids.");
        }
    }
    prod.setEmpreinteC(EmpreinteC);

    String[] bioOptions = {"Oui", "Non"};
    JComboBox<String> bioBox = new JComboBox<>(bioOptions);
    int bioResult = JOptionPane.showConfirmDialog(null, bioBox, "Ce produit est-il bio ?", JOptionPane.OK_CANCEL_OPTION);
    boolean bio;
    if (bioResult == JOptionPane.OK_OPTION) {
        String selectedBio = (String) bioBox.getSelectedItem();
        bio = selectedBio.equals("Oui");
    } else {
        JOptionPane.showMessageDialog(null, "Opération annulée.");
        return;
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

    
    String[] categories = {"Gâteau", "Boisson"};
    JComboBox<String> comboBox = new JComboBox<>(categories);
    int result = JOptionPane.showConfirmDialog(null, comboBox, "Choisissez la catégorie du produit", JOptionPane.OK_CANCEL_OPTION);
    int cate;
    if (result == JOptionPane.OK_OPTION) {
        String selected = (String) comboBox.getSelectedItem();
        cate = selected.equals("Gâteau") ? 1 : 2;
    } else {
        JOptionPane.showMessageDialog(null, "Opération annulée.");
        return; 
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
    final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

    final InsertStudentsClientRequest clientRequest = new InsertStudentsClientRequest(
            networkConfig,
            birthdate++, request, prod, requestBytes);
    clientRequests.push(clientRequest);

    while (!clientRequests.isEmpty()) {
        final ClientRequest clientRequest2 = clientRequests.pop();
        clientRequest.join();
        final Produit prod2 = (Produit) clientRequest.getInfo();
        logger.debug("Thread {} complete : {} {} {} {} {} {} {} {} {} --> {}",
                clientRequest2.getThreadName(),
                prod2.getIdP(), prod2.getNom(), prod2.getPoids(), prod2.getIg(), prod2.getBio(), prod2.getOrigine(), prod2.getIdC(), prod2.getIdA(), prod2.getNbRecherche(),prod2.getEmpreinteC(),
                clientRequest2.getResult());
    }
}



public void deleteProduit() throws InterruptedException, IOException {
    final Deque<ClientRequest> clientRequests = new ArrayDeque<>();
    Produit prod = new Produit();

    String nom;
    while (true) {
        nom = JOptionPane.showInputDialog("Veuillez entrer le nom du produit à supprimer");
        if (nom != null && !nom.trim().isEmpty()) {
            break;
        }
        JOptionPane.showMessageDialog(null, "Erreur : Le nom du produit ne peut pas être vide.");
    }
    prod.setNom(nom);

    final ObjectMapper objectMapper = new ObjectMapper();
    final String jsonifiedProduit = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(prod);
    logger.trace("Produit à supprimer (JSON) : {}", jsonifiedProduit);

    final String requestId = UUID.randomUUID().toString();
    final Request request = new Request();
    request.setRequestId(requestId);
    request.setRequestOrder(deleteOrder); 
    request.setRequestContent(jsonifiedProduit);
    objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

    final DeleteProduitClientRequest clientRequest = new DeleteProduitClientRequest(
            networkConfig, 0, request, prod, requestBytes);
    clientRequests.push(clientRequest);

    while (!clientRequests.isEmpty()) {
        final ClientRequest clientRequest2 = clientRequests.pop();
        clientRequest2.join(); 
        final Produit deletedProd = (Produit) clientRequest2.getInfo();
        logger.debug("Thread {} terminé : suppression du produit '{}' --> {}",
                clientRequest2.getThreadName(),
                deletedProd.getNom(),
                clientRequest2.getResult());
    }
}







    public Produits selectProduits(int i) throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        
        request.setRequestId(requestId);
        request.setRequestOrder(selectRequestOrder);
        request.setIdC(i);

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

             return P;

           

    
            
    
            
        }
        else {
            logger.error("No Produit found");
            return null;
        }
    }
   

    public Produit selectProduitPrincipal(String nom) throws InterruptedException, IOException {
        int birthdate=0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setNom(nom);
        request.setRequestId(requestId);
        request.setRequestOrder(selectNomPRequestOrder);  
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
    
        final SelectStudentNomClientRequest clientRequest = new SelectStudentNomClientRequest(
                networkConfig, 0, request, null, requestBytes);
        
        clientRequests.push(clientRequest);
    
        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            Produit P= (Produit) joinedClientRequest.getResult();
        
            logger.debug("Before update: NbRecherche = {}", P.getNbRecherche());
        int newNbRecherche = P.getNbRecherche() + 1;
            P.setNbRecherche(newNbRecherche);
            logger.debug("After update: NbRecherche = {}", P.getNbRecherche());
            final String updateRequestId = UUID.randomUUID().toString();
            Request request2=new Request();
            
            request2.setRequestId(updateRequestId);
            request2.setRequestOrder(updateRequestOrder);
            request2.setNom(nom);
            String updatedProduit =objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(P);
            request2.setRequestContent(updatedProduit);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            
            final byte []  requestByte = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request2);
            LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestByte);
            UpadateProduitClientRequest updateRequest=new UpadateProduitClientRequest(networkConfig, birthdate, request2, null, requestByte);
            clientRequests.push(updateRequest);
            final ClientRequest updateJoinedRequest= clientRequests.pop();
            updateJoinedRequest.join();
            logger.debug("Update complete for product: {}", P.getNom());
            return P;
        }
            
        
        
        
        
        
        
        
        
        
        
        
        else {
            logger.error("No Produit found");
            return null;
        }
    }
    public Produit selectProduitPrincipalSansUpdate(String nom) throws InterruptedException, IOException {
        int birthdate=0;
        final ObjectMapper objectMapper = new ObjectMapper();
        
        final Deque<ClientRequest> clientRequests=new ArrayDeque<ClientRequest>();
        final String requestID=UUID.randomUUID().toString();
        final Request request=new Request();
        request.setNom(nom);
        request.setRequestId(requestID);
        request.setRequestOrder(selectNomPRequestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
    
        final SelectStudentNomClientRequest clientRequest = new SelectStudentNomClientRequest(
                networkConfig, 0, request, null, requestBytes);
        
        clientRequests.push(clientRequest);
    
        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            Produit P= (Produit) joinedClientRequest.getResult();
            return P;


       



}
else {
    logger.error("No Produit found");
    return null;

}
}  







}
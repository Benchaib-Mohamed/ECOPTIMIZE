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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class ProduitService {
    private final static String LoggingLabel = "FrontEnd - StudentService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String produitsToBeInserted = "produits-to-be-inserted.yaml";

    final String insertRequestOrder = "INSERT_PRODUIT";
    final String selectRequestOrder = "SELECT_ALL_PRODUITS";
    final String selectNomRequestOrder = "SELECT_PRODUIT_NOM";

    private final NetworkConfig networkConfig;

    public ProduitService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public void insertProduits() throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final Produits guys = ConfigLoader.loadConfig(Produits.class, produitsToBeInserted);

        int birthdate = 0;
        for(final Produit guy : guys.getProduits()) {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(guy);
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
                    birthdate++, request, guy, requestBytes);
            clientRequests.push(clientRequest);
        }

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest = clientRequests.pop();
            clientRequest.join();
            final Produit prod = (Produit)clientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} {} {} {} {} {}  --> {}",
                    clientRequest.getThreadName(),
                    //prod.getFirstname(), prod.getName(), prod.getGroup(),
                    prod.getIdP(), prod.getNom(), prod.getPoids(), prod.getIg(), prod.getBio(), prod.getOrigine() ,prod.getIdC(), prod.getIdA(),
                    clientRequest.getResult());
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



public Produit selectProduitNom() throws InterruptedException, IOException {
    int birthdate = 0;
    final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    final ObjectMapper objectMapper = new ObjectMapper();
    final String requestId = UUID.randomUUID().toString();
    final Request request = new Request();
    request.setRequestId(requestId);
    request.setRequestOrder(selectNomRequestOrder);
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
        return (Produit) joinedClientRequest.getResult();
    }
    else {
        logger.error("No students found");
        return null;
    }
}
}

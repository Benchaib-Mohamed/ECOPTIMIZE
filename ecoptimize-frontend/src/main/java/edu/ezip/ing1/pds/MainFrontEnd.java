package edu.ezip.ing1.pds;

import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.*;
import javax.swing.JFrame;

public class MainFrontEnd {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public static void main(String[] args) throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        final ProduitService produitService = new ProduitService(networkConfig);
        //produitService.insertProduits();
        //Produits produits = produitService.selectProduits();
        final AsciiTable asciiTable = new AsciiTable();
        /*for (final Produit produit : produits.getProduits()) { 
            asciiTable.addRule();
            asciiTable.addRow(produit.getIdP(), produit.getNom(), produit.getPoids(), produit.getIg(), produit.getBio(), produit.getOrigine(), produit.getIdC(), produit.getIdA());
        }
        asciiTable.addRule();
        logger.debug("\n{}\n", asciiTable.render());
        */
        String s= "Granola";
        Produit p=produitService.selectProduitNom(s);
        asciiTable.addRule();
        asciiTable.addRow(p.getIdP(), p.getNom(), p.getPoids(), p.getIg(), p.getBio(), p.getOrigine(), p.getIdC(), p.getIdA());
        asciiTable.addRule();
        logger.debug("\n{}\n", asciiTable.render());
        //new FenetrePrincipale();
    }
}




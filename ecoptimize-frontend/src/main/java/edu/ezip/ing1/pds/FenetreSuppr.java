package edu.ezip.ing1.pds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.client.commons.ClientRequest;



import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.ProduitService;


import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FenetreSuppr extends JFrame implements ActionListener {
    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    

    final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final ProduitService produitService = new ProduitService(networkConfig);

    
    JButton suppr = new JButton("Cliquez pour commencer la suppression d'un produit");
    
    public FenetreSuppr() throws IOException, InterruptedException{

        
        this.setTitle("Suppression de produit");
        this.setSize(600,600);
        suppr.setBackground(new Color(204, 255, 204));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // Rendre le bouton transparent sauf le texte
        suppr.setContentAreaFilled(false);  
        suppr.setBorderPainted(false);      
        suppr.setFocusPainted(false);      
        suppr.addActionListener(this);
        this.getContentPane().setBackground(new Color(204, 255, 204));
        this.getContentPane().add(suppr);

        this.setVisible(true);

        
    }
    public void actionPerformed(ActionEvent e){
        
        try {
            try {
                
                    produitService.deleteProduit();
                    JOptionPane.showMessageDialog(this, "Le produit a bien été supprimé");
                
            } catch (IOException io) {
                io.printStackTrace();
            } 
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        
    }
}    
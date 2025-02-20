package edu.ezip.ing1.pds;

import javax.swing.JFrame;

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

public class FenetreInser extends JFrame implements ActionListener {
    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    

    final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final ProduitService produitService = new ProduitService(networkConfig);

    
    JButton inser = new JButton("Cliquez pour commencer l'insertion d'un produit");

    public FenetreInser() throws IOException, InterruptedException{

        
        this.setTitle("Insertion de produit");
        this.setSize(600,600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        
        inser.addActionListener(this);
        
        this.getContentPane().add(inser);
        this.setVisible(true);

        
    }
    public void actionPerformed(ActionEvent e){
        
        try {
            try {
                
                    produitService.insertProduits();
                    JOptionPane.showMessageDialog(this, "Le produit à bien était inserer");
                
            } catch (IOException io) {
                io.printStackTrace();
            } 
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        
    }
}    
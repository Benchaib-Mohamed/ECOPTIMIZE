package edu.ezip.ing1.pds;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.client.commons.ClientRequest;


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
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FenetreAlt extends JFrame implements ActionListener {
    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    

    final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final ProduitService produitService = new ProduitService(networkConfig);

    JLabel l = new JLabel("Veuillez entrer le nom du produit à chercher");
    JTextField entrerProd = new JTextField();
    JLabel carac1 = new JLabel("Caractéristiques du produit choisit :");
    JLabel carac2=new JLabel();
    JButton rech= new JButton("rechercher");

    public FenetreAlt() throws IOException, InterruptedException{

        


        this.setTitle("Recherche d'alternatives");
        this.setSize(600,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new GridLayout(3,2));
        rech.addActionListener(this);
        this.getContentPane().add(l);  
        this.getContentPane().add(entrerProd);
        this.getContentPane().add(rech);
        this.setVisible(true);
        
        



        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        
        try {
            try {
                String s= entrerProd.getText();
                Produit p=produitService.selectProduitNom(s);
                carac2.setText(p.toString());
                this.getContentPane().add(carac1);
                this.getContentPane().add(carac2);
                this.setVisible(true);
            } catch (IOException io) {
                io.printStackTrace();
            } 
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        
    }
}

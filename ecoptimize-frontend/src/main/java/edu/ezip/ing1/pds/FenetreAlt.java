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

public class FenetreAlt extends JFrame implements ActionListener {
    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    

    final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final ProduitService produitService = new ProduitService(networkConfig);

    JLabel l = new JLabel("Veuillez entrer le nom du produit à remplacer");
    JTextField entrerProd = new JTextField();
    JLabel carac1 = new JLabel("Ecoptimize vous recommande :");
    JLabel carac2=new JLabel();
    JButton rech= new JButton("Rechercher");
    JPanel pNord= new JPanel();
    JPanel pSud = new JPanel();

    public FenetreAlt() throws IOException, InterruptedException{

        
        this.setTitle("Recherche d'alternatives");
        this.setSize(600,600);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(BorderLayout.SOUTH,pSud);
        this.getContentPane().add(pNord);
        pNord.setLayout(new GridLayout(3,2));
        pSud.setLayout(new BorderLayout());
        rech.addActionListener(this);
        pNord.add(l);  
        pNord.add(entrerProd);
        pNord.add(rech);
        this.setVisible(true);

        
    }
    public void actionPerformed(ActionEvent e) {
    try {
        String s = entrerProd.getText();
        Produit p = null;
        try {
            p = produitService.selectProduitNom(s);
        } catch (IOException er) {
            er.printStackTrace();
        } catch (InterruptedException er) {
            er.printStackTrace();
        }

        carac2.setPreferredSize(new Dimension(70, 150));
        carac1.setPreferredSize(new Dimension(35, 75));

        if (p != null) {
            carac2.setText(p.getNom() + " , avec " + p.getPoids() + "g par portion, un IG de " + p.getIg() + " ." +
                    " BIO : " + p.getBio() + ", origine : " + p.getOrigine());
        } else {
            carac2.setText("Produit introuvable"); 
        }

        pSud.add(BorderLayout.NORTH, carac1);
        pSud.add(carac2);
        this.setVisible(true);

    } catch (Exception ex) { 
        ex.printStackTrace();
        carac2.setText("Erreur lors de la recherche du produit");
    }
}
}

package edu.ezip.ing1.pds;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.ProduitService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;



public class FenetreInfo extends JFrame{
     private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    
     JTextField F=new JTextField();
     JPanel P2;
     final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final ProduitService produitS = new ProduitService(networkConfig);

    public FenetreInfo(String s)  throws InterruptedException, IOException{
        setSize(600,600);
        setLocationRelativeTo(null);
        
        
        
        Produit P =produitS.selectProduitPrincipalSansUpdate(s);
        if (P != null) {
                P2 = new JPanel(new BorderLayout());
                JLabel Nom = new JLabel(P.getNom());
                Nom.setFont(new Font("Arial", Font.BOLD, 35));
                Nom.setOpaque(true);  
                Nom.setBackground(new Color(204, 255, 204));
                Nom.setHorizontalAlignment(JLabel.CENTER);
                P2.add(Nom, BorderLayout.NORTH);
    
                JPanel P3 = new JPanel(new GridLayout(5, 2)); 
                P3.setBackground(new Color(245, 245, 245));
    
                P3.add(createLabel("Nombre de recherches d'alt effectuées :"));
                P3.add(createValueLabel(String.valueOf(P.getNbRecherche())));
                P3.add(createLabel("BIO:"));
            if(P.getBio()==true){    
                P3.add(createValueLabel(String.valueOf("✅")));}
                else{P3.add(createValueLabel("❌"));}
                P3.add(createLabel("Indice glycémique du produit:"));
             if(P.getIg()<40) {  P3.add(createValueLabel(String.valueOf(P.getIg()+" 🟢")));}
            else if(40>=P.getIg() || P.getIg()<=60){P3.add(createValueLabel(String.valueOf(P.getIg()+ "🟠")));}
            else if(P.getIg()>60){P3.add(createValueLabel(String.valueOf(P.getIg()+" 🔴")));}
             
                P3.add(createLabel("Origine du produit:"));
         
                if(P.getOrigine().equals("France"))   {    P3.add(createValueLabel(P.getOrigine()+ " 🐓"));}
                else if(P.getOrigine().equals("Etats Unis"))   {    P3.add(createValueLabel(P.getOrigine()+ " 🦅"));}
                else{P3.add(createValueLabel(P.getOrigine()));}
                P3.add(createLabel("Empreinte Carbonne du produit "));
                if(P.getEmpreinteC()>=20){ P3.add(createValueLabel(P.getEmpreinteC() + "🔴"));}
                else if(P.getEmpreinteC()<20 && P.getEmpreinteC()>=10){P3.add(createValueLabel(P.getEmpreinteC()+"🟠"));}
                else { P3.add(createValueLabel(P.getEmpreinteC()+"🟢"));}


    
                P2.add(P3, BorderLayout.CENTER);
                this.getContentPane().add(P2,BorderLayout.CENTER);
                this.setVisible(true);
            }

        else{JOptionPane.showMessageDialog(this, "Produit non trouver");}


        }
 private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(100, 100, 100)); 
        return label;
    }
    
    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setForeground(new Color(30, 30, 30)); 
        return label;
    }
}
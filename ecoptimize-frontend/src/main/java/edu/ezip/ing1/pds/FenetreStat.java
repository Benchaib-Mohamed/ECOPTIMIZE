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

public class FenetreStat extends JFrame implements ActionListener{
    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    
     JTextField F=new JTextField();
     final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final ProduitService produitS = new ProduitService(networkConfig);
    JLabel L2=new JLabel();
    JPanel Panels;
    JPanel P2;
    CardLayout C=new CardLayout();
    
    
    
    
    
    
    public FenetreStat(){
        setTitle("Information");
        setSize(600,600);
        setLocationRelativeTo(null);
        Panels=new JPanel(C);
        JPanel P1=new JPanel(new GridLayout(3,1,10,10));
        P1.setBackground(new Color(245,245,245));
        
        Panels.add(P1, "FirstPanel");
       
        JLabel SP=new JLabel("Selectionner votre produit");
        SP.setFont(new Font("Arial", Font.BOLD, 18));
        JButton B1=new JButton("Valider");
        B1.setFont(new Font("Arial", Font.BOLD, 16));
        F.setFont(new Font("Arial", Font.PLAIN, 16));

        P1.add(SP);
        P1.add(F);
        P1.add(B1);
        B1.addActionListener(this);
        add(Panels);
        setVisible(true);
       
        



    }
    
    public void actionPerformed(ActionEvent e)  {
     String s = F.getText();
        try {
            Produit P = produitS.selectProduitPrincipalSansUpdate(s);
            if (P != null) {
                P2 = new JPanel(new BorderLayout());
                JLabel Nom = new JLabel(P.getNom());
                Nom.setFont(new Font("Arial", Font.BOLD, 35));
                Nom.setHorizontalAlignment(JLabel.CENTER);
                P2.add(Nom, BorderLayout.NORTH);
    
                JPanel P3 = new JPanel(new GridLayout(4, 2)); // Simplification pour deux colonnes
                P3.setBackground(new Color(245, 245, 245)); // Couleur de fond claire
    
                // Labels et valeurs avec amélioration visuelle
                P3.add(createLabel("Nombre de recherches effectuées :"));
                P3.add(createValueLabel(String.valueOf(P.getNbRecherche())));
                P3.add(createLabel("BIO:"));
                P3.add(createValueLabel(String.valueOf(P.getBio())));
                P3.add(createLabel("Indice glycémique du produit:"));
                P3.add(createValueLabel(String.valueOf(P.getIg())));
                P3.add(createLabel("Origine du produit:"));
                P3.add(createValueLabel(P.getOrigine()));
    
                P2.add(P3, BorderLayout.CENTER);
                Panels.add(P2, "Panneau 2");
                C.show(Panels, "Panneau 2");
            } else {
                JOptionPane.showMessageDialog(this, "Produit introuvable");
            }
        } catch (IOException | NullPointerException | InterruptedException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche du produit");
            
        }
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
    
    
    
    
    
    
    


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
        SP.setOpaque(true);  
        SP.setBackground(new Color(204, 255, 204));
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
        new FenetreInfo(s);
    } catch (InterruptedException e1) {
        
        e1.printStackTrace();
    } catch (IOException e1) {
        
        e1.printStackTrace();
    }
        



        
    }}
    
    
    
    
    
    
    


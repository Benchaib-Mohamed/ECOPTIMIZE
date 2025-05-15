package edu.ezip.ing1.pds;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.ProduitService;

public class FenetreStat2 extends JFrame implements ActionListener{
     private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private List<Produit> ProduitList;
   
    final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final ProduitService produitS = new ProduitService(networkConfig);
    JButton B;
    JButton B1;
    JButton B2;
    JButton B3;
    JButton B4;
    public FenetreStat2(){
        this.setTitle("Fenetre Stat");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
       
        B=new JButton("Gateaux");
        JPanel P=new JPanel();
        P.setLayout(new GridLayout(2,2));
        
        B1=new JButton("Boissons"+ " 🧉");
         B2=new JButton("🔍");
        B3=new JButton("Bonbons 🍬");
        B4=new JButton("Snack 🍿");
         Color bleuPastel = new Color(100, 149, 237);
        Color vertMenthe = new Color(152, 251, 152);
        Color grisClair = new Color(230, 230, 250);
         
        B.setBackground(bleuPastel);
        B.setForeground(Color.WHITE);
        B.setFocusPainted(false);

        B1.setBackground(vertMenthe);
        B1.setForeground(Color.BLACK);
        B1.setFocusPainted(false);

        B2.setBackground(grisClair);
        B2.setForeground(Color.DARK_GRAY);
        B2.setFocusPainted(false);

        B3.setBackground(vertMenthe);
        B3.setForeground(Color.WHITE);
        B3.setFocusPainted(false);

        B4.setBackground(bleuPastel);
        B4.setForeground(Color.BLACK);
        B4.setFocusPainted(false);



       
        P.add(B);
        P.add(B1);
        P.add(B3);
        P.add(B4);
        getContentPane().add(B2, BorderLayout.NORTH);
        getContentPane().add(P, BorderLayout.CENTER);
        
        B2.addActionListener(this);
        B1.addActionListener(this);
        B.addActionListener(this);
        B3.addActionListener(this);
        B4.addActionListener(this);
        this.setVisible(true);

    }
    
    public void actionPerformed(ActionEvent e) {
       if(e.getSource().equals(B2)){
        dispose();
        new FenetreStat();
       }
       else if(e.getSource().equals(B)){
        try {
            
            new FenetreProduit(1);
        } catch (InterruptedException e1) {
            
            e1.printStackTrace();
        } catch (IOException e1) {
           
            e1.printStackTrace();
        }
        
        




       }
    else if(e.getSource().equals(B1)){
        try {
            new FenetreProduit(2);
        } catch (InterruptedException e1) {
            
            e1.printStackTrace();
        } catch (IOException e1) {
          
            e1.printStackTrace();
        }
    }
    else if(e.getSource().equals(B3)){
        try {
            new FenetreProduit(3);
        } catch (InterruptedException e1) {
            
            e1.printStackTrace();
        } catch (IOException e1) {
            
            e1.printStackTrace();
        }
    }
    else if(e.getSource().equals(B4)){
        try {
            new FenetreProduit(4);
        } catch (InterruptedException e1) {
            
            e1.printStackTrace();
        } catch (IOException e1) {
            
            e1.printStackTrace();
        }
    }
}
}

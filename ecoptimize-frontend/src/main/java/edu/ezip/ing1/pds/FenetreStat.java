package edu.ezip.ing1.pds;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    
    
    
    
    
    
    public FenetreStat(){
        setTitle("Statistique du produit");
        setSize(600,600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2,2));
        JLabel L=new JLabel("Selectionné votre produit ");
        
        

        
        
        JButton B1=new JButton("Valider");
        B1.addActionListener(this);
        getContentPane().add(L);
        getContentPane().add(F);
        getContentPane().add(B1);
        setVisible(true);
        



    }
    
    public void actionPerformed(ActionEvent e) {
try{
    try  {
        String s=F.getText();
        Produit P=produitS.selectProduitNom(s);
        L2.setText("Ce produit a été cherché " + P.getNbRecherche()  + " fois a cette borne");
        this.getContentPane().add(L2);
        this.setVisible(true);

        




    }catch(IOException io){
        io.printStackTrace();
    }
}catch(InterruptedException ie){
    ie.printStackTrace();
}
       
       

       



        
    }
    
    }
    
    
    
    
    


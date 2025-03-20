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
        JPanel P1=new JPanel(new GridLayout(3,1));
        
        Panels.add(P1, "FirstPanel");
       
        JLabel SP=new JLabel("Selectionner votre produit");
        JButton B1=new JButton("Valider");
        P1.add(SP);
        P1.add(F);
        P1.add(B1);
        B1.addActionListener(this);
        add(Panels);
        setVisible(true);
       
        



    }
    
    public void actionPerformed(ActionEvent e) {
try{
    try  {
        String s=F.getText();
 while(true)  { try{    Produit P=produitS.selectProduitPrincipalSansUpdate(s);
        P2=new JPanel(new BorderLayout());
        JLabel Nom=new JLabel(P.getNom());
        Nom.setFont(new Font("Arial", Font.BOLD, 35));
        Nom.setHorizontalAlignment(JLabel.CENTER);
        P2.add(Nom,BorderLayout.NORTH);
        JPanel P3=new JPanel(new GridLayout(4,4));
       
        JLabel L2=new JLabel("Nombre de recherche effectuer sur ce produit ");
        JLabel NbRecherche=new JLabel(String.valueOf(P.getNbRecherche()));
        JLabel L3 =new JLabel("BIO");
        JLabel Bio=new JLabel(String.valueOf(P.getBio()));
        JLabel L4 =new JLabel("Indice glycémique du produit");
        JLabel ig=new JLabel(String.valueOf(P.getIg()));
        JLabel L5 =new JLabel("Origine du produit");
        JLabel Origine=new JLabel(P.getOrigine());
        P3.add(L2);
        P3.add(NbRecherche);
        P3.add(L3);
        P3.add(Bio);
        P3.add(L4);
        P3.add(ig);
        P3.add(L5);
        P3.add(Origine);
        P2.add(P3,BorderLayout.CENTER);
        Panels.add(P2,"Panneau 2");
        C.show(Panels, "Panneau 2");
        break;}
        catch(NullPointerException np){
            JOptionPane.showMessageDialog( this,"Produit introuvable");
            break;

        }}


        



        




    }catch(IOException io){
        io.printStackTrace();
    }
}catch(InterruptedException ie){
    ie.printStackTrace();
}
       
       

       



        
    }
    
    }
    
    
    
    
    


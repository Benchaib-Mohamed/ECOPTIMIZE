
package edu.ezip.ing1.pds;

import javax.swing.ImageIcon;
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
    
    
   public FenetreStat() {
    setTitle("Moteur de recherche");
    setSize(600, 600);
    setLocationRelativeTo(null);
    setLayout(null);

    Panels = new JPanel(null);
    Panels.setBounds(0, 0, 600, 600);
    Panels.setBackground(new Color(173, 216, 230));
    add(Panels);

    JLabel iconLabel = new JLabel(new ImageIcon("ecoptimize-frontend\\src\\main\\resources\\search.png")); 
    iconLabel.setBounds(200, 50, 200, 200);
    Panels.add(iconLabel);

    JLabel SP = new JLabel("Rechercher un produit");
    SP.setFont(new Font("Arial", Font.BOLD, 18));
    SP.setBounds(200, 230, 300, 30);
    Panels.add(SP);

    F = new JTextField();
    F.setFont(new Font("Arial", Font.PLAIN, 16));
    F.setBounds(180, 280, 250, 35);
    Panels.add(F);

    JButton B1 = new JButton("Valider");
    B1.setFont(new Font("Arial", Font.BOLD, 16));
    B1.setBounds(250, 340, 100, 40);
    Panels.add(B1);

    B1.addActionListener(this);

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
    
    
    
    
    
    
    


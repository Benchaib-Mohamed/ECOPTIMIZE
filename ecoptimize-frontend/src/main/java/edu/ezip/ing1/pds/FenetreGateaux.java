package edu.ezip.ing1.pds;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.ProduitService;

public class FenetreGateaux extends JFrame {
     private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private List<Produit> ProduitList;
   
    final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final ProduitService produitS = new ProduitService(networkConfig);
    public FenetreGateaux() throws InterruptedException, IOException{
        setSize(600,600);
        setTitle("Fenetre Gateaux");
        setLocationRelativeTo(null);
        JLabel Jl=new JLabel("Voici la liste de tout les gateaux disponible , du plus sain au moins sain");
        getContentPane().add(Jl,BorderLayout.NORTH);
    
        JPanel P=new JPanel();
        P.setLayout(new BoxLayout(P, BoxLayout.Y_AXIS));
        Produits Pr=produitS.selectProduits(1);
        ProduitList=new ArrayList<>(Pr.getProduits());
        for(int i=0;i<ProduitList.size();i++){
            JPanel Ligne=new JPanel();
            Ligne.setLayout(new BorderLayout());
            JLabel Nom=new JLabel(ProduitList.get(i).getNom());
            JButton info=new JButton("❔");
            Ligne.add(Nom,BorderLayout.CENTER);
            Ligne.add(info,BorderLayout.EAST);
            P.add(Ligne);
            info.addActionListener(e-> {
               try {
                new FenetreInfo(Nom.getText());
               } catch (InterruptedException e1) {
                
                e1.printStackTrace();
               } catch (IOException e1) {
                
                e1.printStackTrace();
               }

            });
            


        }
             getContentPane().add(P,BorderLayout.CENTER);
             this.setVisible(true);


    }
    
    
    
}

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
        ProduitList.sort((p1,p2) -> Integer.compare(noteGateau(p2), noteGateau(p1)));
        for(int i=0;i<ProduitList.size();i++){
            JPanel Ligne=new JPanel();
            Ligne.setLayout(new BorderLayout());
            JLabel Nom=new JLabel(ProduitList.get(i).getNom()+ motifG(ProduitList.get(i)));
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
    
    public  int noteGateau(Produit P){
        
        int noteE;
        int noteig;
        int notebio;
        
        int noteG;
        
        if(P.getIg()<40){
            noteig=12;
        }
        else if(40>=P.getIg() || P.getIg()<=60){
            noteig=8;
        }
        else{
            noteig=5;
        }
        if(P.getBio()==true){
            notebio=3;
        }
        else{ notebio=0;}
        if(P.getEmpreinteC()<50 ){
            noteE=3;
        }
        else if (P.getEmpreinteC()>=50 && P.getEmpreinteC()<90){
            noteE=2;
        }
        else { noteE=1;}
        noteG=noteE+noteig+notebio;
        return noteG;
}
    public String motifG(Produit P){
        if (noteGateau(P)>=15){
            return "🟢";
        }
        else if(noteGateau(P)<10){
            return "🔴";
        }
        else return "🟠";
    }
}



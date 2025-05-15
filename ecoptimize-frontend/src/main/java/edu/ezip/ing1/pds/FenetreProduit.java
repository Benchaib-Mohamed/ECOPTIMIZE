package edu.ezip.ing1.pds;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.swing.BorderFactory;
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

public class FenetreProduit extends JFrame implements ActionListener{
         private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    private List<Produit> ProduitList;
   
    final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final ProduitService produitS = new ProduitService(networkConfig);
    JButton preced;

    public FenetreProduit(int idC)  throws InterruptedException, IOException {
        setSize(600,600);
        JLabel Jl=new JLabel();
        switch (idC){
          case 1: setTitle("Fenetre Gateaux");
        Jl.setText("Voici la liste de tous les gateaux disponibles , du plus sain au moins saine");
        break;
        case 2:  setTitle("Fenetre Boisson");
        
        Jl.setText("Voici la liste de toutes les boissons disponibles , de la plus saine a la moins saine");
        break;
        case 3: setTitle("Fenetre Bonbons");
        Jl.setText("Voici la liste de toutes les bonbons disponibles , du plus sain au moins sain");
        break;
        case 4: setTitle("Fenetre Snack");
        Jl.setText("Voici la liste de toutes les snacks disponibles , du plus sain au moins sain");
        break;
        
        
           
        }
        
       
        
        
        
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 250));

        
        Jl.setForeground(new Color(33, 33, 33));
        getContentPane().add(Jl,BorderLayout.NORTH);
    
        JPanel P=new JPanel();
        P.setLayout(new BoxLayout(P, BoxLayout.Y_AXIS));
        P.setBackground(new Color(245, 245, 250));

        preced=new JButton("⬅️");

        
        System.out.println(idC);
        Produits Pr=produitS.selectProduits(idC);
        ProduitList=new ArrayList<>(Pr.getProduits());
        ProduitList.sort((p1,p2) -> Integer.compare(noteGateau(p2), noteGateau(p1)));
       
        for(int  i=0;i<ProduitList.size();i++){
            Produit p=ProduitList.get(i);
            JPanel Ligne=new JPanel();
            Ligne.setLayout(new BorderLayout());
            Ligne.setBackground(new Color(230, 240, 255));
            Ligne.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 160), 1));
            JLabel Nom=new JLabel(ProduitList.get(i).getNom().toUpperCase()+ " "+ motifG(ProduitList.get(i))+" "+noteGateau(ProduitList.get(i))+"/20");
            Nom.setFont(new Font("SansSerif", Font.BOLD, 16));

             Nom.setForeground(new Color(25, 25, 112));
            JButton info=new JButton("❔");
            info.setBackground(new Color(200, 220, 255));
             info.setForeground(new Color(25, 25, 112));
            Ligne.add(Nom,BorderLayout.CENTER);
            Ligne.add(info,BorderLayout.EAST);
            P.add(Ligne);
            info.addActionListener(e-> {
               try {
                new FenetreInfo(p.getNom());
               } catch (InterruptedException e1) {
                
                e1.printStackTrace();
               } catch (IOException e1) {
                
                e1.printStackTrace();
               }

            });
            


        }
            preced.addActionListener(this);
        getContentPane().add(P,BorderLayout.CENTER);
             getContentPane().add(preced,BorderLayout.SOUTH);
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

    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(preced)){
            setVisible(false);
            new FenetreStat2();
        }
        
        
    }
}

    
     


    


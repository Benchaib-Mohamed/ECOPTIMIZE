package edu.ezip.ing1.pds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FenetreAdmin extends JFrame implements ActionListener {
    
    
    JButton boutonInser = new JButton("➕ Inserer un produit dans la base de données");
    JButton boutonSuppr = new JButton("➖ Supprimer un produit de la base de données");

    JLabel message= new JLabel("Bienvenue sur votre borne ECOPTIMIZE, votre chercheur d'alternatives saines !");
    JPanel titre= new JPanel();
    JPanel reste = new JPanel();
    JButton preced;
    public FenetreAdmin(){

        this.setTitle("Ecoptimize");
        
        this.setSize(1280,720);
        this.setLayout(new BorderLayout());
        message.setPreferredSize(new Dimension(50,150));
        
        boutonInser.addActionListener(this);
        boutonSuppr.addActionListener(this);
        //this.getContentPane().setBackground(Color.BLACK);
        this.getContentPane().add(BorderLayout.NORTH, titre);
        titre.setLayout(new BorderLayout());
        reste.setLayout(new FlowLayout());
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD,30));
        message.setOpaque(true);  // Rendre le fond opaque
        message.setBackground(new Color(204, 255, 204));
        titre.add(message);
        
        boutonInser.setPreferredSize(new Dimension(350,350));
        boutonSuppr.setPreferredSize(new Dimension(350,350));

        preced=new JButton("⬅️");
        preced.addActionListener(this);
        reste.add(boutonInser);
        reste.add(boutonSuppr);
        this.getContentPane().add(BorderLayout.CENTER, reste);
        getContentPane().add(preced,BorderLayout.SOUTH);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        try {
            try{    
                
                if(e.getSource() == boutonInser){
                    new FenetreInser();
                }
                if(e.getSource() == boutonSuppr){
                    new FenetreSuppr();
                }
                if(e.getSource().equals(preced)){
                    this.dispose();
                    new FenetrePrincipale2();
                }
                
            }catch(IOException ex){
                ex.printStackTrace();
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        
    }
    
}


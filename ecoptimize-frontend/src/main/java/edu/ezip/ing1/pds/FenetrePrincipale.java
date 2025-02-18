package edu.ezip.ing1.pds;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetrePrincipale extends JFrame implements ActionListener {
    JButton boutonAlt = new JButton("Rechercher une alternative à un produit");
    JButton boutonStat = new JButton("Consulter les statistiques relatives à un produit");
    JLabel message= new JLabel("Bienvenue sur votre borne ECOPTIMIZE, votre chercheur d'alternatives saines !");
    public FenetrePrincipale(){
        this.setTitle("Ecoptimize");
        this.setLocationRelativeTo(null);
        this.setSize(600,600);
        this.setLayout(new BorderLayout());
        boutonAlt.addActionListener(this);
        boutonStat.addActionListener(this);
        this.getContentPane().add(BorderLayout.NORTH, message);
        this.getContentPane().add(BorderLayout.WEST, boutonAlt);
        this.getContentPane().add(BorderLayout.EAST, boutonStat);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == boutonAlt){
            new FenetreAlt();
        }
        if(e.getSource() == boutonStat){
            //new FenetreAlt();
        }
    }
    
}

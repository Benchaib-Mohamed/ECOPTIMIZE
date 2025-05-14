package edu.ezip.ing1.pds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class FenetreUser extends JFrame implements ActionListener {
    
    JButton boutonAlt = new JButton("🔎Rechercher une alternative à un produit");
    JButton boutonStat = new JButton("ℹ️Consulter les informations relatives à un produit");
    
    JLabel message= new JLabel("Bienvenue sur votre borne ECOPTIMIZE, votre chercheur d'alternatives saines !");
    JPanel titre= new JPanel();
    JPanel reste = new JPanel();
    public FenetreUser(){

        this.setTitle("Ecoptimize");
        
        this.setSize(1280,720);
        this.setLayout(new BorderLayout());
        message.setPreferredSize(new Dimension(50,150));
        boutonAlt.addActionListener(this);
        boutonStat.addActionListener(this);
        
        //this.getContentPane().setBackground(Color.BLACK);
        this.getContentPane().add(BorderLayout.NORTH, titre);
        titre.setLayout(new BorderLayout());
        reste.setLayout(new FlowLayout());
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setFont(new Font("Arial", Font.BOLD,30));
        message.setOpaque(true);  // Rendre le fond opaque
        message.setBackground(new Color(204, 255, 204));
        titre.add(message);
        boutonAlt.setPreferredSize(new Dimension(350,350));
        boutonStat.setPreferredSize(new Dimension(350,350));
        
        reste.add(boutonAlt);
        reste.add(boutonStat);
        
        this.getContentPane().add(BorderLayout.CENTER, reste);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        try {
            try{    
                if(e.getSource() == boutonAlt){
                    new FenetreAlt();
                }
                if(e.getSource() == boutonStat){
                    new FenetreStat2();
                }
                
                
            }catch(IOException ex){
                ex.printStackTrace();
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        
    }
    
}

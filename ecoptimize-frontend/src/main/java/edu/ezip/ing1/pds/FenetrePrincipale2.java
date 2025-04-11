package edu.ezip.ing1.pds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FenetrePrincipale2 extends JFrame implements ActionListener {
    
    JButton boutonUser = new JButton("Espace Utilisateur");
    JButton boutonAdmin = new JButton("Espace Adminstrateur");
    JLabel message= new JLabel("Bienvenue sur votre borne ECOPTIMIZE, votre chercheur d'alternatives saines ! ");
    JPanel titre= new JPanel();
    JPanel reste = new JPanel();
    public FenetrePrincipale2(){

        this.setTitle("Ecoptimize");
        
        this.setSize(1280,720);
        this.setLayout(new BorderLayout());
        message.setPreferredSize(new Dimension(1050,150));
        boutonUser.addActionListener(this);
        boutonAdmin.addActionListener(this);
        
        //this.getContentPane().setBackground(Color.BLACK);
        this.getContentPane().add(BorderLayout.NORTH, titre);
        titre.setLayout(new BorderLayout());
        reste.setLayout(new FlowLayout());
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setFont(new Font("SansSerif", Font.BOLD, 30));
        message.setOpaque(true);  // Rendre le fond opaque
        message.setBackground(new Color(204, 255, 204));
        titre.add(message, BorderLayout.CENTER);
        boutonUser.setPreferredSize(new Dimension(350,350));
        boutonAdmin.setPreferredSize(new Dimension(350,350));
        
        reste.add(boutonUser);
        reste.add(boutonAdmin);
        
        this.getContentPane().add(BorderLayout.CENTER, reste);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
          
                if(e.getSource() == boutonUser){
                    new FenetreUser();
                }
                if(e.getSource() == boutonAdmin){
                    new FenetreAdmin();
                }
                
                
            
        
    }
    
}

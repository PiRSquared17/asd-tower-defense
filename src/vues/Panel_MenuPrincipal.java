package vues;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import models.jeu.Jeu;
import models.terrains.Desert;
import models.terrains.ElementTD;
import models.terrains.Spiral;
import models.terrains.WaterWorld;

public class Panel_MenuPrincipal extends JPanel implements ActionListener
{
    // constantes statiques
    private final int MARGES_PANEL = 40;
    private static final long serialVersionUID = 1L;
    private static final ImageIcon IMAGE_MENU = new ImageIcon(
            "img/logos/principal.png");
    private static final Color COULEUR_TEXTE_VERSION = new Color(200, 200, 200);

    private static final Image IMAGE_DE_FOND = Toolkit.getDefaultToolkit().getImage("img/interfaces/menuPrincipal.png");
    
    // elements du formulaire
    private JLabel version;

    private JButton bPartieSolo = new JButton("Partie Solo");
    private JButton bRejoindrePartieMulti = new JButton("Rejoindre");
    private JButton bCreerPartieMulti = new JButton("Créer");
    private JButton bQuitter = new JButton("Quitter");

    private JFrame parent;

    /**
     * Constructeur de la fenetre du menu principal
     */
    public Panel_MenuPrincipal(JFrame parent)
    {
        super(new BorderLayout());
        this.parent = parent;

        // -------------------------------
        // -- preferances de le fenetre --
        // -------------------------------
        parent.setTitle("Menu principal - ASD Tower Defense");

        setBorder(new EmptyBorder(new Insets(MARGES_PANEL, MARGES_PANEL,
                MARGES_PANEL, MARGES_PANEL)));
        
        
        // attent que toutes les images soit complementements chargees
        MediaTracker tracker = new MediaTracker(this);
        tracker.addImage(IMAGE_DE_FOND, 0);

        try { 
            tracker.waitForAll(); 
        } 
        catch (InterruptedException e){ 
            e.printStackTrace(); 
        }
        
        // ---------------------------
        // -- element du formulaire --
        // ---------------------------

        setBackground(LookInterface.COULEUR_DE_FOND);
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(1,280));
        
        add(p, BorderLayout.NORTH);

        
        JPanel pAbsolu = new JPanel(null); // layout absolu
        pAbsolu.setPreferredSize(new Dimension(0, 160));
        pAbsolu.setOpaque(false);

        // partie solo
        bPartieSolo.addActionListener(this);
        bPartieSolo.setBounds(50, 0, 100, 50);
        pAbsolu.add(bPartieSolo);

        // partie multijoueurs
        bRejoindrePartieMulti.setBounds(50, 100, 100, 50);
        bRejoindrePartieMulti.addActionListener(this);
        pAbsolu.add(bRejoindrePartieMulti);

        bCreerPartieMulti.setBounds(160, 100, 100, 50);
        bCreerPartieMulti.addActionListener(this);
        pAbsolu.add(bCreerPartieMulti);

        // quitter
        bQuitter.addActionListener(this);
        bQuitter.setBounds(555, 100, 100, 50);
        pAbsolu.add(bQuitter);

        add(pAbsolu, BorderLayout.CENTER);

        version = new JLabel(Jeu.getVersion());
        version.setForeground(COULEUR_TEXTE_VERSION);
        add(version, BorderLayout.SOUTH);
    }
    
    public void paintComponent(Graphics g)
    {
        g.setColor(LookInterface.COULEUR_DE_FOND);
        g.fillRect(0, 0, 800, 600);
        
        g.drawImage(IMAGE_DE_FOND, 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object source = ae.getSource();

        // quitter
        if (source == bQuitter)
            System.exit(0); // Fermeture correcte du logiciel

        else if (source == bPartieSolo)
        {
            parent.getContentPane().removeAll();
            parent.getContentPane().add(new Panel_ModeSolo(parent),
                    BorderLayout.CENTER);
            parent.getContentPane().validate();
        } else if (source == bRejoindrePartieMulti)
        {
            parent.getContentPane().removeAll();
            parent.getContentPane().add(new Panel_RejoindrePartieMulti(parent),
                    BorderLayout.CENTER);
            parent.getContentPane().validate();
        } else if (source == bCreerPartieMulti)
        {
            parent.getContentPane().removeAll();
            parent.getContentPane().add(new Panel_CreerPartieMulti(parent),
                    BorderLayout.CENTER);
            parent.getContentPane().validate();
        }
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.metier.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jliermann
 */
@Entity
public class Livraison implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Temporal(TemporalType.TIME)
    private Date heureLivraison;
    
    @Temporal(TemporalType.TIME)
    private Date dureeEstimee;
    
    @Temporal(TemporalType.DATE)
    private Date instantPassageCmd;
    
    @ManyToMany
    List<Produit> commande = new LinkedList<>();

    @ManyToOne
    Client client;
    
    @ManyToOne
    Restaurant resto;
    
    @ManyToOne
    Livreur livreur;
    
    public Livraison() {
    }

    public Livraison(Date instantPassageCmd, Client client, Restaurant resto) {
        this.instantPassageCmd = instantPassageCmd;
        this.client = client;
        this.resto = resto;
    }


    public Long getId() {
        return id;
    }

    public Date getHeureLivraison() {
        return heureLivraison;
    }

    public void setHeureLivraison(Date heureLivraison) {
        this.heureLivraison = heureLivraison;
    }

    public Date getDureeEstimee() {
        return dureeEstimee;
    }

    public Livreur getLivreur() {
        return livreur;
    }

    public void setLivreur(Livreur livreur) {
        this.livreur = livreur;
    }
    
    public void setDureeEstimee(Date dureeEstimee) {
        this.dureeEstimee = dureeEstimee;
    }

    public Date getInstantPassageCmd() {
        return instantPassageCmd;
    }

    public void setInstantPassageCmd(Date instantPassageCmd) {
        this.instantPassageCmd = instantPassageCmd;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Restaurant getResto() {
        return resto;
    }

    public void setResto(Restaurant resto) {
        this.resto = resto;
    }

    public List<Produit> getFullCommande() {
        return commande;
    }
    
    public void addProduit(Produit p)
    {
        commande.add(p);
    }
    
    public void removeProduit(Produit p)
    {
        commande.remove(p);
    }
    
    public float getTotalPoids()
    {
        float renvoi = 0;
        for (Produit commande1 : commande)
        {
            renvoi += commande1.getPoids();
        }
        return renvoi;
    }
    
    public float getTotalPrix()
    {
        float renvoi = 0;
        for (Produit commande1 : commande)
        {
            renvoi += commande1.getPrix();
        }
        return renvoi;
    }

    public void setCommande(List<Produit> commande) {
        this.commande = commande;
    }

    @Override
    public String toString() {
        return "Livraison{" 
                + "id=" + id 
                + ", heureLivraison=" + heureLivraison 
                + ", dureeEstimee=" + dureeEstimee 
                + ", instantPassageCmd=" + instantPassageCmd 
                + ", commande=" + commande 
                + ", client=" + client 
                + ", resto=" + resto 
                + ", livreur=" + livreur + '}';
    } 
        
}

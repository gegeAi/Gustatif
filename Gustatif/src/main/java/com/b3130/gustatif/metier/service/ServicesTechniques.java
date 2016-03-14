/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.metier.service;

import com.b3130.gustatif.dao.ClientDao;
import com.b3130.gustatif.dao.JpaUtil;
import com.b3130.gustatif.dao.LivraisonDao;
import com.b3130.gustatif.dao.LivreurDao;
import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.modele.Livraison;
import com.b3130.gustatif.metier.modele.Livreur;
import com.b3130.gustatif.metier.modele.Produit;
import com.b3130.gustatif.util.GeoTest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jliermann
 */
public class ServicesTechniques {
        
    public String inscription(Client c)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        ClientDao dao = new ClientDao();
        try {
            dao.create(c);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
            return "Bonjour " + c.getPrenom() + ",\nVotre inscription au service GUSTAT’IF a malencontreusement échoué... Merci de recommencer ultérieurement.";
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return "Bonjour " + c.getPrenom() + ",\nNous vous confirmons votre inscription au service GUSTAT’IF. Votre numéro de client est : " + c.getId() + ".";
        
    }
          
    public boolean affecteLivreur(Livraison l)
    // Choisi un livreur pour une livraison, et bloque son etat comme indisponible 
    // jusqu'à livraison de la commande ou annulation de celle-ci
    {
        LivreurDao dao = new LivreurDao();
        Livreur renvoi = null;
        Double trajetCourt = -1d;
        try {
            
            
            List<Livreur> livreurs = new ServicesMetier().listAllDeliveryMan(); 
            
            
            for (Livreur livreur : livreurs) {
                
                if (livreur.isDisponible() && l.getTotalPoids() < livreur.getCapacite()) {
                    Double candidat;
                    if(livreur.getVitesseMoyenne() == null)
                        candidat = GeoTest.getTripDurationByBicycleInMinute(GeoTest.getLatLng(livreur.getAdresse()), GeoTest.getLatLng(l.getResto().getAdresse()));
                    else
                        candidat = GeoTest.getFlightDistanceInKm(GeoTest.getLatLng(livreur.getAdresse()), GeoTest.getLatLng(l.getResto().getAdresse())) / livreur.getVitesseMoyenne();
                    if (trajetCourt > candidat || trajetCourt == -1d) {    
                        renvoi = livreur;
                        trajetCourt=candidat;
                    } 
                }
            }
            
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            
            if(renvoi != null)
            {
                l.setLivreur(renvoi);
                l.setDureeEstimee(new Date((long)trajetCourt.doubleValue()));
                renvoi.setDisponible(false);
                dao.update(renvoi);
            }        
            
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
            
        } catch (Throwable ex) {
            Logger.getLogger(ServicesTechniques.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return renvoi != null;
    }
    
    public void validerLivraison(Livraison l)
    // Termine une livraison, replace le livreur en etat libre et met a jour
   // son adresse actuelle avec la derniere adresse connue (celle du client)
    {
        LivreurDao dao = new LivreurDao();
        LivraisonDao ldao = new LivraisonDao();
       
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            
            l.setHeureLivraison(new Date());
            ldao.update(l);
            l.getLivreur().setDisponible(true);
            l.getLivreur().setAdresse(l.getClient().getAdresse());
            dao.update(l.getLivreur());
            
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
            
        } catch (Throwable ex) {
            Logger.getLogger(ServicesTechniques.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String mailLivreur(Livraison l)
    // Valide la commande et renvoie le contenu d'un mail a envoyer au livreur
    {
        String renvoi = "Bonjour " + l.getLivreur().getNom() + ",\nMerci d'effectuer cette livraison dès maintenant, tout en respectant le code de la route;-)\n\t Le Chef";
        renvoi += "\n\nDétails de la Livraison \n\tDate/heure : ";
        renvoi += l.getInstantPassageCmd().toString();
        renvoi += "\n\tLivreur : ";
        renvoi += l.getLivreur().getNom();
        renvoi += "(n°";
        renvoi += l.getLivreur().getId();
        renvoi += ")\n\tRestaurant : ";
        renvoi += l.getResto().getDenomination();
        renvoi += "\n\t Client :\n\t\t";
        renvoi += l.getClient().getPrenom() + " "+ l.getClient().getNom();
        renvoi += "\n\t\t" + l.getClient().getAdresse();
        renvoi += "\n\t\t" + l.getClient().getMail();
        renvoi +="\n\nCommande : \n";
        List<Produit> plats = l.getFullCommande();
        HashMap<Produit, Integer> reduction;
        reduction = new HashMap<>();
        for (Produit plat : plats) {
            if(reduction.containsKey(plat))
            {
                reduction.put(plat, reduction.get(plat)+1);
                plats.remove(plat);
            }
            else
            {
                reduction.put(plat, 1);
            }
        }
        
        for(Produit plat : plats)
        {
            renvoi += reduction.get(plat);
            renvoi += plat.getDenomination();
            renvoi += ": ";
            renvoi += reduction.get(plat);
            renvoi += " X ";
            renvoi += plat.getPrix();
            renvoi += " euros\n";
        }
        
        renvoi += "\nTOTAL : " + l.getTotalPrix() + " euros";
        
        try {
            
            LivraisonDao ldao = new LivraisonDao();
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            
            ldao.create(l);
            
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
            
        } catch (Throwable ex) {
            Logger.getLogger(ServicesTechniques.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return renvoi;
    }
    
    public void annuleCommande(Livraison l)
    // annule une commande non terminee et libere le livreur
    {
        try {
            
            LivreurDao dao = new LivreurDao();
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            
            if(l.getLivreur() != null)
                l.getLivreur().setDisponible(true);
            dao.update(l.getLivreur());
            
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
            
        } catch (Throwable ex) {
            Logger.getLogger(ServicesTechniques.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<Livraison> trouveActuellesCommandes()
    // renvoie une listes des commandes non abouties (en cours)
    {
        List<Livraison> livraisonsActuelles = new ServicesMetier().listAllDelivery();
        for (Livraison livraisonsActuelle : livraisonsActuelles) 
        {
            if(livraisonsActuelle.getHeureLivraison() != null)
            {
                livraisonsActuelles.remove(livraisonsActuelle);
            }
        }
        return livraisonsActuelles;
    }
    
}

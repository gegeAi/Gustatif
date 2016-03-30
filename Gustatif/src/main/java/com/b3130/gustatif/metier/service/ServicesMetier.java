/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.metier.service;

import com.b3130.gustatif.dao.ClientDao;
import com.b3130.gustatif.dao.JpaUtil;
import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.modele.Livraison;
import com.b3130.gustatif.metier.modele.Livreur;
import com.b3130.gustatif.metier.modele.Produit;
import com.b3130.gustatif.metier.modele.Restaurant;
import com.b3130.gustatif.util.GeoTest;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Samuel Toko
 */
public class ServicesMetier {
    
    ServicesTechniques st = new ServicesTechniques();
    
    public String inscription(Client c)
    // inscrit un client dans la base
    // et renvoie le mail de succes / echec
    {
        if(!st.createClient(c))
        {
        return "Bonjour " + c.getPrenom() + ",\nVotre inscription au service GUSTAT’IF a malencontreusement échoué... Merci de recommencer ultérieurement.";
        }
        else
        {
        return "Bonjour " + c.getPrenom() + ",\nNous vous confirmons votre inscription au service GUSTAT’IF. Votre numéro de client est : " + c.getId() + ".";
        }
    }
    
    public Client connexionClient(String mail)
    // connecte un client selon son adresse mail
    // renvoie le client si trouvé, null sinon
    {
        Client clientFound = null;
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            clientFound = new ClientDao().connectClient(mail);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
            return clientFound;  
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return clientFound;  
    }
    
    public boolean creerCommande(Client c, List<Produit> p, List<Integer> qte, Restaurant r)
    {
        try {
            Livraison l = new Livraison(new Date(), c, r);
            for(int i = 0 ; i< p.size(); i++)
            {
                for(int j = 0; j < qte.get(i); j ++)
                {
                    addDish(l, p.get(i));
                }
            }
            affecteLivreur(l);
            String mail = mailLivreur(l);
            st.createLivraisons(l);
            System.out.println(mail);
        } catch (Throwable ex) {
            System.out.println(ex);
  
            return false;
        }
        
        return true;
    }
    
    public boolean affecteLivreur(Livraison l)
    // Choisi un livreur pour une livraison, et bloque son etat comme indisponible 
    // jusqu'à livraison de la commande ou annulation de celle-ci
    {
        Livreur renvoi = null;
        Double trajetCourt = -1d;
        
        List<Livreur> livreurs = new ServicesTechniques().listAllDeliveryMan(); 

        for (Livreur livreur : livreurs) {
            
            if (livreur.isDisponible() && l.getTotalPoids() < livreur.getCapacite()) {
                Double candidat;
                if(livreur.getVitesseMoyenne() == null)
                    candidat = GeoTest.getTripDurationByBicycleInMinute(GeoTest.getLatLng(livreur.getAdresse()), GeoTest.getLatLng(l.getResto().getAdresse()));
                else
                    candidat = GeoTest.getFlightDistanceInKm(GeoTest.getLatLng(livreur.getAdresse()), GeoTest.getLatLng(l.getResto().getAdresse())) / livreur.getVitesseMoyenne();
                
                System.out.println("Trajet court : " + trajetCourt + " candidat : " + candidat);
                if (trajetCourt > candidat || trajetCourt == -1d) { 
                    System.out.println("Trajet court : " + trajetCourt + " candidat : " + candidat);
                    renvoi = livreur;
                    trajetCourt=candidat;
                } 
            }
        }
            
        if(renvoi != null)
        {
            l.setLivreur(renvoi);
            l.setDureeEstimee(new Date((long)trajetCourt.doubleValue()*60000));
            renvoi.setDisponible(false);
            st.updateLivreur(renvoi);
        }        
        
        return renvoi != null;
    }
    
    public void validerLivraison(Livraison l)
    // Termine une livraison, replace le livreur en etat libre et met a jour
   // son adresse actuelle avec la derniere adresse connue (celle du client)
    {
        l.setHeureLivraison(new Date());
        st.updateLivraisons(l);
        l.getLivreur().setDisponible(true);
        l.getLivreur().setAdresse(l.getClient().getAdresse());
        st.updateLivreur(l.getLivreur());

        
    }
    
    public String mailLivreur(Livraison l)
    // Valide la commande et renvoie le contenu d'un mail a envoyer au livreur
    {
        System.out.println(l);
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
        
   
       return renvoi;
    }
    
    public void annuleCommande(Livraison l)
    // annule une commande non terminee et libere le livreur
    {
        l.getLivreur().setDisponible(true);
        st.updateLivreur(l.getLivreur());   
    }
    
    public List<Livraison> trouveActuellesCommandes()
    // renvoie une listes des commandes non abouties (en cours)
    {
        List<Livraison> livraisonsActuelles = st.listAllDelivery();
        List<Livraison> renvoi = new LinkedList<>();
        for (Livraison livraisonsActuelle : livraisonsActuelles) 
        {
            if(livraisonsActuelle.getHeureLivraison() == null)
            {
                renvoi.add(livraisonsActuelle);
            }
        }
        return renvoi;
    }
    
    public void addDish(Livraison l, Produit p)
    // ajoute un plat a une livraison en attente de validation
    // en verifiant la disponibilite par rapport au restaurant choisi
    {
        boolean existe = false;
        for (Produit produit : l.getResto().getProduits()) {
        
            if(existe = Objects.equals(p.getId(), produit.getId()))
            {
                break;
            }
        }
        if(existe)
        {
            l.addProduit(p);
        }
            
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.vue;

import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.service.ServicesTechniques;
import com.b3130.gustatif.metier.modele.Livraison;
import com.b3130.gustatif.metier.modele.Produit;
import com.b3130.gustatif.metier.modele.Restaurant;
import com.b3130.gustatif.metier.service.ServicesMetier;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jerome
 */
public class MainLivraisonDur {
    
    public static void main(String args[])
    {
        ServicesTechniques st = new ServicesTechniques();
        ServicesMetier sm = new ServicesMetier();
        
        List<Integer> qte = new LinkedList<>();
        List<Produit> prods = st.listAllRestaurants().get(0).getProduits();
        
        for(Produit p : prods)
            qte.add(1);
        
        sm.creerCommande(st.listAllClients().get(0), prods ,qte, st.listAllRestaurants().get(0));
       /* Livraison l = new Livraison(new Date(), st.listAllClients().get(0), st.listAllRestaurants().get(0));
        Restaurant r = l.getResto();
        
        System.out.println(" ----- Nous sommes ici ----- ");
        List<Produit> prod = r.getProduits();*/
        
        /*
        sm.addDish(l, r.getProduits().get(0));
        System.out.println(sm.affecteLivreur(l));
        String mail = sm.mailLivreur(l);
        st.createLivraisons(l);
        System.out.println(mail);
        
        
        System.out.println("\n\n*******VALIDATION********\n\n");
        Livraison aValider;
        System.out.println(sm.trouveActuellesCommandes().get(0));
        sm.validerLivraison(aValider = sm.trouveActuellesCommandes().get(0));
        System.out.println(aValider);*/
        
    }
}

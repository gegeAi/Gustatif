/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.vue;

import com.b3130.gustatif.metier.service.ServicesTechniques;
import com.b3130.gustatif.metier.modele.Produit;
import com.b3130.gustatif.metier.service.ServicesMetier;
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
        
        System.out.println(sm.creerCommande(st.listAllClients().get(0), prods ,qte, st.listAllRestaurants().get(0)));
               
    }
}

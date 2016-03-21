/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.vue;

import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.modele.Livraison;
import com.b3130.gustatif.metier.modele.Produit;
import com.b3130.gustatif.metier.modele.Restaurant;
import com.b3130.gustatif.metier.service.ServicesTechniques;

/**
 *
 * @author jliermann
 */
public class Main {
    public static void main(String args[])
    {
        Restaurant r = new ServicesTechniques().listAllRestaurants().get(0);
        System.out.println(r);
        for (Produit produit : r.getProduits()) {
            System.out.println(produit);
        }
    }
}

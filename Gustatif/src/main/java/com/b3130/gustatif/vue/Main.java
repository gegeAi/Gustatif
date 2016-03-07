/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.vue;

import com.b3130.gustatif.dao.ClientDao;
import com.b3130.gustatif.dao.JpaUtil;
import com.b3130.gustatif.dao.ProduitDao;
import com.b3130.gustatif.dao.RestaurantDao;
import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.modele.Produit;
import com.b3130.gustatif.metier.modele.Restaurant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jliermann
 */
public class Main {
    
    public static void main(String args[])
    {
        Produit p = new Produit("grosse bertha", "le kiff des bosch !", 50f,95f);
        ProduitDao daop = new ProduitDao();
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        } catch (Throwable ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.vue;

import com.b3130.gustatif.dao.JpaUtil;
import com.b3130.gustatif.dao.LivreurDao;
import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.modele.Livreur;
import com.b3130.gustatif.metier.service.ServicesTechniques;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jliermann
 */
public class Main {
    
    public static void main(String args[])
    {
        Livreur l = new Livreur("livreur", "20 avenue Albert Einstein 69100 Villeurbanne", 10d,10d);
        Livreur l2 = new Livreur("livreursss", "12 impasse Paul Cézanne 57730 Folschviller", 10d,25d);
        LivreurDao dao = new LivreurDao();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            dao.create(l2);
            dao.create(l);
        } catch (Throwable ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
        ServicesTechniques st = new ServicesTechniques();
        
    }
}

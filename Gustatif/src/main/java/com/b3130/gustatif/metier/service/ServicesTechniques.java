/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.metier.service;

import com.b3130.gustatif.dao.ClientDao;
import com.b3130.gustatif.dao.JpaUtil;
import com.b3130.gustatif.metier.modele.Client;

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
    
    
}

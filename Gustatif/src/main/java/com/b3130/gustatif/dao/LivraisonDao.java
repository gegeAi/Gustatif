/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.dao;

import com.b3130.gustatif.metier.modele.Livraison;
import com.b3130.gustatif.metier.modele.Produit;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author jliermann
 */
public class LivraisonDao {
    
    public void create(Livraison livraison) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(livraison);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Livraison update(Livraison livraison) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            livraison = em.merge(livraison);
        }
        catch(Exception e){
            throw e;
        }
        return livraison;
    }
    
    public Livraison findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Livraison livraison = null;
        try {
            livraison = em.find(Livraison.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return livraison;
    }
    
    public List<Livraison> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Livraison> livraisons = null;
        try {
            Query q = em.createQuery("SELECT l FROM Livraison l ORDER BY l.instantPassageCmd");
            livraisons = (List<Livraison>) q.getResultList();
            
            
        }
        catch(Exception e) {
            throw e;
        }     
        return livraisons;
    }
    
    public void addDish(Long id, Produit p) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Livraison livraisons = null;
        try {
            Query q = em.createQuery("SELECT l FROM Livraison l WHERE l.id=:id");
            q.setParameter("id", id);
            livraisons = (Livraison) q.getSingleResult();
            livraisons.addProduit(p);
            em.merge(livraisons);
        }
        catch(Exception e) {
            throw e;
        }  
        
    }
    
}

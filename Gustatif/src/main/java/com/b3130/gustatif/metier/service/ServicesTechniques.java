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
import com.b3130.gustatif.dao.ProduitDao;
import com.b3130.gustatif.dao.RestaurantDao;
import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.modele.Livraison;
import com.b3130.gustatif.metier.modele.Livreur;
import com.b3130.gustatif.metier.modele.Produit;
import com.b3130.gustatif.metier.modele.Restaurant;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jliermann
 */
public class ServicesTechniques {
        
    ClientDao daoClient = new ClientDao();
    RestaurantDao daoRestaurant = new RestaurantDao();
    ProduitDao daoProduit = new ProduitDao();
    LivreurDao daoLivreur = new LivreurDao();
    LivraisonDao daoLivraison = new LivraisonDao();
    
    public boolean createClient(Client c)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoClient.create(c);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
            return false;
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return true;
        
    }
    
    public boolean updateClient(Client c)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoClient.update(c);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
            return false;
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return true;
        
    }
    
    public boolean createLivreur(Livreur l)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoLivreur.create(l);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
            return false;
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return true;
        
    }
    
    public boolean updateLivreur(Livreur l)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoLivreur.update(l);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
            return false;
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return true;
        
    }
    
    public void createRestaurant(Restaurant r)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoRestaurant.create(r);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager(); 
    }
    
    public void updateRestaurant(Restaurant r)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoRestaurant.update(r);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager(); 
    }
    
    public void createProducts(Produit p)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoProduit.create(p);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager(); 
    }
    
    public void updateProducts(Produit p)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoProduit.update(p);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager(); 
    }
    
    public void createLivraisons(Livraison l)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoLivraison.create(l);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager(); 
    }
    
    public void updateLivraisons(Livraison l)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        try {
            daoLivraison.update(l);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager(); 
    }
    
    public List<Client> listAllClients()
    {
        List<Client> allClient = new LinkedList<>();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            allClient = daoClient.findAll();
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager(); 
        return allClient;
    }
    
    public List<Restaurant> listAllRestaurants()
    {
        List<Restaurant> allRest = new LinkedList<>();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            allRest = daoRestaurant.findAll();
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager(); 
        return allRest;
    }
    
    public List<Produit> listAllProducts(Long restId)
    {
        List<Produit> allProducts = new LinkedList<>();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            allProducts = daoProduit.findAll();
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return allProducts;
    }
    
    public List<Livreur> listAllDeliveryMan()
    {
        List<Livreur> allLivreur = new LinkedList<>();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            allLivreur = daoLivreur.findAll();
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return allLivreur;
    }
    
    public List<Livraison> listAllDelivery()
    {
        List<Livraison> allLivraison = new LinkedList<>();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            allLivraison = daoLivraison.findAll();
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return allLivraison;
    }
        
    public Restaurant findRestaurantByName(String name)
    {
        Restaurant restaurantFound = null;
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            restaurantFound = daoRestaurant.findByName(name);
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return restaurantFound; 
    }
    
    public List<Produit> listAllProductsForARestaurant(Long restId)
    {
        List<Produit> allProducts = new LinkedList<>();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        try {
            allProducts = daoRestaurant.findById(restId).getProduits();
        } catch (Throwable ex) {
            JpaUtil.fermerEntityManager();
        }
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        return allProducts;
    }
            
}

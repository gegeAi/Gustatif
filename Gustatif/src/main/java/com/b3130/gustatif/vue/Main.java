/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.vue;

import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.modele.Produit;
import com.b3130.gustatif.metier.modele.Restaurant;
import com.b3130.gustatif.metier.modele.Livreur;
import com.b3130.gustatif.metier.service.ServicesTechniques;
import com.b3130.gustatif.metier.service.ServicesMetier;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jliermann
 */
public class Main {
    
    public static ServicesMetier serviceM = new ServicesMetier();
    public static ServicesTechniques serviceT = new ServicesTechniques();
    public static Scanner sc = new Scanner(System.in);
    public static void main(String args[])
    {
        //initDB();
        
        boolean run = true;
        while(run)
        {
            System.out.println("*****Bienvenue à GustatIF*****\n");
            System.out.println("Choisissez votre action\n");
            System.out.println("1) Creer un client\n");
            System.out.println("2) Creer commande client\n");
            System.out.println("3) Lister les restaurants\n");
            System.out.println("4) Lister les clients\n");
            System.out.println("5) Lister les produits d'un restaurant\n");
            System.out.println("6) Valider commande\n");
            System.out.println("7) Quitter appli\n");

            int choix =  Integer.parseInt(sc.nextLine());

            switch(choix)
            {
                case 1:
                    Client client = new Client(requete("Nom : "),
                    requete("Prenom : "),
                    requete("adresse : "),
                    requete("adresse Mail : "));
                    if(serviceT.createClient(client) == true)
                    {
                        System.out.println("Client créér\n");
                    }
                    else
                    {
                        System.out.println("Erreur création du client\n");
                    }
                    break;
                    
                case 2:
                    Client connecte = serviceM.connexionClient(
                            requete("Entrez votre adresse mail"));
                    if(connecte == null)
                    {
                        System.out.println("Erreur lors de la connexion\n");
                    }
                    else
                    {
                        System.out.println(
                                ""+connecte.getMail()+" est connecte\n");
                        System.out.println("Entrez id resto\n");
                        choix =  Integer.parseInt(sc.nextLine());
                        List<Produit> pr = serviceT.listAllProductsForARestaurant((long) choix);
                        List<Integer> qte = new LinkedList<>();
                        for(Produit p : pr)
                             qte.add(1);
                        System.out.println(serviceM.creerCommande(connecte, pr ,qte, serviceT.findRestaurantById((long) choix)));
                        
                    }
                    break;
                    
                case 3:
                    List<Restaurant> r = serviceT.listAllRestaurants();
                    for(int j = 0; j < r.size(); j ++)
                    {
                        System.out.println(r.get(j).toString()+"\n");
                        
                    }
                    break;
                    
                case 4:
                    List<Client> c = serviceT.listAllClients();
                    for(int j = 0; j < c.size(); j ++)
                    {
                        System.out.println(c.get(j).toString()+"\n");
                    }
                    
                    break;
                    
                case 5:
                    System.out.println("Entre l'id d'un resto\n");
                    choix =  Integer.parseInt(sc.nextLine());
                    List<Produit> p = 
                            serviceT.listAllProductsForARestaurant((long)choix);
                    for(int j = 0; j < p.size(); j ++)
                    {
                        System.out.println(p.get(j).toString()+"\n");
                        
                    }
                    break; 
                    
                    
                case 6:
                    
                    break;      
                    
                case 7:
                    run = false;
                    break;
            }
        }
                
    }
    
    public static void initDB()
    {
        /*
        //Creation des produits
        String plats [] = {"Viande","Poisson","Vegan"};
        for(int i=0; i < 1 ; i++)
        {
            Produit p = new Produit();
            p.setDenomination(plats[i]);
            p.setDescription("Trop bon");
            p.setPoids((float) i + 5);
            p.setPrix((float) i + 10);
            serviceT.createProducts(p);
        }
        
        //Creation restaurants
        String restos [] = {"Au bon dodu","Mac do","Vegan palace"};
        for(int i=0; i < 1 ; i++)
        {
            Restaurant r = new Restaurant();
            r.setAdresse(""+(i+1)+" avenue roger salengro");
            r.setDenomination(restos[i]);
            r.setDescription("On mange bien");
            List<Produit> prod = new LinkedList<Produit>();
            prod = serviceT.listAllProducts(r.getId());
            for(int j=0; j<prod.size() ; j++)
            {
               r.addProduit(prod.get(j)); 
            }
            serviceT.createRestaurant(r);
        }
        
        //Creation clients
        for(int i=0; i < 1 ; i++)
        {
            Client c = new Client();
            c.setNom("Jhon"+(i+1));
            c.setPrenom("Doe"+(i+1));
            c.setAdresse(""+(i+1)+"avenue albert einstein");
            c.setMail(""+(i+1)+"@gustatif.fr");
            serviceT.createClient(c);
        }
        */
        //Creation Livreur
        for(int i=0; i < 10 ; i++)
        {
            Livreur l = new Livreur();
            l.setNom("Will"+(i+1));
            l.setCapacite((double) i+1500000);
            l.setAdresse(""+(i+1)+"avenue jean france");
            l.setVitesseMoyenne((double) i+20);
            l.setDisponible(true);
            serviceT.createLivreur(l);
        }
    }
    
    public static String requete(String req)
    {
        System.out.println(req);
        String str = sc.nextLine();
        return str;
    }   
    
}


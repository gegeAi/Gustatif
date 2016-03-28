/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.vue;

import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.modele.Livraison;
import com.b3130.gustatif.metier.service.ServicesTechniques;
import com.b3130.gustatif.metier.service.ServicesMetier;
import static com.b3130.gustatif.vue.Main.requete;
import static com.b3130.gustatif.vue.Main.sc;
import static com.b3130.gustatif.vue.Main.serviceM;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jerome
 */
public class MainFinLivraison {
    
    public static void main(String args[])
    {
        boolean continu = true;
        ServicesTechniques st = new ServicesTechniques();
        ServicesMetier sm = new ServicesMetier();
        Long id;
        while(continu)
        {
            System.out.println("*****Bienvenue à GustatIF*****\n");
            System.out.println("Choisissez votre action\n");
            System.out.println("1) Fin par idLivraison\n");
            System.out.println("2) Fin par idLivreur\n");
            System.out.println("3) Quitter\n");

            int choix =  Integer.parseInt(sc.nextLine());
            List<Livraison> list = sm.trouveActuellesCommandes();
            switch(choix)
            {
                case 1:
                    System.out.println("id :");
                    id = Long.parseLong(sc.nextLine());
                    for (Livraison list1 : list) {
                        if(Objects.equals(list1.getId(), id))
                        {
                            sm.validerLivraison(list1);
                            break;
                        }
                    }
                    break;
                    
                case 2:
                    System.out.println("id :");
                    id = Long.parseLong(sc.nextLine());
                    for (Livraison list1 : list) {
                        if(Objects.equals(list1.getLivreur().getId(), id))
                        {
                            sm.validerLivraison(list1);
                            break;
                        }
                    }
                    break;
                    
                case 3:
                    continu = false;
                    break;
            }
        }
    }
    
}

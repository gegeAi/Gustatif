/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.vue;

import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.service.ServicesMetier;
import com.b3130.gustatif.metier.service.ServicesTechniques;
import java.util.Scanner;

/**
 *
 * @author jliermann
 */
public class Main {
    
    public static void main(String args[])
    {
        
        
        ServicesMetier services = new ServicesMetier();
        Client c = new Client("jean", "paul", "12 rue des begonias", "adressemailbidon@");
        ServicesTechniques st = new ServicesTechniques();
        String s = st.inscription(c);
        System.out.println(s);
        int choix = 1;
        
        Scanner lectureClavier = new Scanner(System.in);
        
        
        while(choix != 0)
        {
            System.out.println("Choix \n"
                    + "1. Créer client \n"
                    + "2. Connexion client"
                    + "3.");
            choix = lectureClavier.nextInt();
            switch(choix){
                case 1 :
                    String mail = lectureClavier.next();
                    services.connexionClient(mail);
                    
                
            }
            
        }
        
    }
}

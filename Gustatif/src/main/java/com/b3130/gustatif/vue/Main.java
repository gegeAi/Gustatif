/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.vue;

import com.b3130.gustatif.metier.modele.Client;
import com.b3130.gustatif.metier.service.ServicesTechniques;

/**
 *
 * @author jliermann
 */
public class Main {
    
    public static void main(String args[])
    {
        Client c = new Client("jean", "paul", "12 rue des begonias", "adressemailbidon@");
        ServicesTechniques st = new ServicesTechniques();
        String s = st.inscription(c);
        System.out.println(s);
        
    }
}

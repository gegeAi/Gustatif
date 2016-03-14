/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.b3130.gustatif.metier.modele;

import com.b3130.gustatif.util.GeoTest;
import com.google.maps.model.LatLng;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author jliermann
 */
@Entity
public class Livreur implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String adresse;
    private Double longitude;
    private Double latitude;
    private Double capacite;
    private Double vitesseMoyenne;
    private boolean disponible;

    public Livreur() {
    }
      
    public Livreur(String nom, String adresse, Double capacite) {
        this.nom = nom;
        this.adresse = adresse;
        this.capacite = capacite;
        LatLng latlng = GeoTest.getLatLng(adresse);
        latitude = latlng.lat;
        longitude = latlng.lng;
        disponible = true;
    }
    
    public Livreur(String nom, String adresse, Double capacite, Double vitesseMoyenne) {
        this(nom, adresse, capacite);
        this.vitesseMoyenne = vitesseMoyenne;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
        LatLng latlng = GeoTest.getLatLng(adresse);
        latitude = latlng.lat;
        longitude = latlng.lng;
    }

    public Double getCapacite() {
        return capacite;
    }

    public void setCapacite(Double capacite) {
        this.capacite = capacite;
    }

    public Double getVitesseMoyenne() {
        return vitesseMoyenne;
    }

    public void setVitesseMoyenne(Double vitesseMoyenne) {
        this.vitesseMoyenne = vitesseMoyenne;
    }
        
    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
    
    public LatLng getLatLng(String adresse) {
        return new LatLng(latitude, longitude);     
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Long getId() {
        return id;
    }
       
       
    @Override
    public String toString() {
        return "Livreur{" + "id=" + id + ", nom=" + nom + ", adresse=" + adresse + ", longitude=" + longitude + ", latitude=" + latitude + ", capacite=" + capacite + ", vitesseMoyenne=" + vitesseMoyenne + '}';
    }

}

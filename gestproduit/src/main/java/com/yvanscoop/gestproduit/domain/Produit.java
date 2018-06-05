/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yvanscoop.gestproduit.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author yvan
 */
@Entity
@Table(name = "produit")
public class Produit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private long id;

    @Column(name = "code")
    private String code;
    @Column(name = "designation")
    private String designation;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prix")
    private double prix;
    @Column(name = "quantite")
    private int quantite;
    @Column(name = "qrcode")
    private String qrcode;

    public Produit() {
    }

    public Produit(String code, String designation, double prix, int quantite) {
        this.code = code;
        this.designation = designation;
        this.prix = prix;
        this.quantite = quantite;
    }

    public String getCode() {
        return code;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }


    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
}

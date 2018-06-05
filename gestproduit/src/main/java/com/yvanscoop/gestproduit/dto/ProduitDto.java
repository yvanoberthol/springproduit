package com.yvanscoop.gestproduit.dto;

import com.yvanscoop.gestproduit.domain.Produit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by medilox on 3/10/17.
 */
public class ProduitDto {

    private Long id;
    private String designation;
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getCode() {

        return code;
    }

    public String getQrcode() {
        return qrcode;
    }

    private String qrcode;
    private Double prix;
    private Integer quantite;


    public Long getId() {
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

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public ProduitDto createDTO(Produit produit) {
        ProduitDto produitDto = new ProduitDto();

        if(produit != null){
            produitDto.setId(produit.getId());
            produitDto.setDesignation(produit.getDesignation());
            produitDto.setPrix(produit.getPrix());
            produitDto.setQuantite(produit.getQuantite());
            produitDto.setCode(produit.getCode());
            produitDto.setQrcode(produit.getQrcode());

            /*List<RoleDto> roleDtos = new ArrayList<RoleDto>();
            if (produit.getRoles() != null) {
                for (Role role : produit.getRoles())
                    roleDtos.add(new RoleDto().createDTO(role));
            }
            produitDto.setRoles(roleDtos);*/
        }
        return produitDto;
    }
}

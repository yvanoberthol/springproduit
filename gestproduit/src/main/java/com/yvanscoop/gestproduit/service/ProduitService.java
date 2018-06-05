package com.yvanscoop.gestproduit.service;


import com.yvanscoop.gestproduit.domain.Produit;
import com.yvanscoop.gestproduit.dto.ProduitDto;
import com.yvanscoop.gestproduit.repository.ProduitRepository ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

//import com.pdv.security.SecurityUtils;

/**
 * Service Implementation for managing Produit.
 */
@Service
@Transactional
public class ProduitService {

  private static final Logger log = LoggerFactory.getLogger(ProduitService.class);

  //@Autowired
  //private ProduitRepository produitRepository;

  @Autowired
  private ProduitRepository produitRepository;


  /**
   * Save a produit.
   *
   * @param produit the entity to save
   * @return the persisted entity
   */
  public ProduitDto save(Produit produit){
      log.debug("Request to save Produit : {}", produit);
      Produit result = produitRepository.save(produit);
      return new ProduitDto().createDTO(produit);
  }

  public ProduitDto update(Produit produit) {
    log.debug("Request to update Produit : {}", produit);
    Produit p = new Produit();
    p = produitRepository.findOne(produit.getId());
    p.setDesignation(produit.getDesignation());
    p.setPrix(produit.getPrix());
    p.setQuantite(produit.getQuantite());
    Produit result = produitRepository.save(p);

    return new ProduitDto().createDTO(p);
  }

  public int getLast() {
    log.debug("Request to get last id of Produit");
    int result = produitRepository.findLast();
    return result;
  }

  /**
   *  Get one produit by id.
   *
   *  @param id the id of the entity
   *  @return the entity
   */
  @Transactional(readOnly = true)
  public ProduitDto findOne(Long id) {
    log.debug("Request to get Produit : {}", id);
    Produit produit = produitRepository.findOne(id);

    return new ProduitDto().createDTO(produit);
  }

  @Transactional(readOnly = true)
  public Produit findByCode(String code) {
    log.debug("Request to get Produit : {}", code);
    Produit produit = produitRepository.findByCode(code);
    return produit;
  }

  /**
   *  Delete the  produit by id.
   *
   *  @param id the id of the entity
   */
  public void delete(Long id) {
    log.debug("Request to delete Produit : {}", id);
    Produit produit = produitRepository.findOne(id);

    produitRepository.delete(id);
  }


  @Transactional(readOnly = true)
  public Page<Produit> findAll(Pageable pageable) {
    log.debug("Request to get all Categories");
   // Sort triparnom = new Sort(Sort.Direction.DESC,"designation");
    //List<Produit> produits = new ArrayList<>();
    Page<Produit> produits = produitRepository.findAll(pageable);
    return produits;
  }

}

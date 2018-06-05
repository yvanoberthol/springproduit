package com.yvanscoop.gestproduit.repository;

import com.yvanscoop.gestproduit.domain.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit,Long> {
    @Query("select max(p.id)from Produit p")
    int findLast();

    @Query("select p from Produit p where p.code = :x")
    Produit findByCode(@Param("x") String code);

    @Override
    Page<Produit> findAll(Pageable pageable);
}

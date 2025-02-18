package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;

public class Produits {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("produits")
    private  Set<Produit> produits = new LinkedHashSet<Produit>();

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    public final Produits add (final Produit produit) {
        produits.add(produit);
        return this;
    }

    @Override
    public String toString() {
        return "Produits{" +
                "produits =" + produits +
                '}';
    }
}

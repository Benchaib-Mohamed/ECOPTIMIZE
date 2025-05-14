package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Produit {

    private int idP; 

    private String nom;

    private int poids; 

    private int ig; 

    private boolean bio; 

    private String origine;

    private int idC; 

    private int idA;
    
    private int nbRecherche;
    
    private int empreinteC;

    public Produit() {
    }

    public final Produit build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResultSet(resultSet, "idP", "nom", "poids", "ig", "bio", "origine", "idC", "idA", "nbRecherche","empreinteC");
        return this;
    }

    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, idP, nom, poids, ig, bio, origine, idC, idA,nbRecherche,empreinteC);
    }
    
    // Getters et setters
    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public int getIg() {
        return ig;
    }

    public void setIg(int ig) {
        this.ig = ig;
    }

    public boolean getBio() {
        return bio;
    }

    public void setBio(boolean bio) {
        this.bio = bio;
    }

    public String getOrigine() {
        return origine;
    }

    public void setOrigine(String origine) {
        this.origine = origine;
    }

    public int getIdC() {
        return idC;
    }

    public void setIdC(int idC) {
        this.idC = idC;
    }
    public void setEmpreinteC(int E){
        this.empreinteC=E;

    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }
    public int getEmpreinteC(){
        return empreinteC;
    }

    private void setFieldsFromResultSet(final ResultSet resultSet, final String... fieldNames)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for (final String fieldName : fieldNames) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }

    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final Object... fieldValues)
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for (final Object fieldValue : fieldValues) {
            if (fieldValue instanceof Integer) {
                preparedStatement.setInt(++ix, (Integer) fieldValue);
            } else if (fieldValue instanceof String) {
                preparedStatement.setString(++ix, (String) fieldValue);
            } else if (fieldValue instanceof Boolean) {
                preparedStatement.setBoolean(++ix, (Boolean) fieldValue);
            }
        }
        return preparedStatement;
    }

    

    @Override
    public String toString() {
        return "Produit [idP=" + idP + ", nom=" + nom + ", poids=" + poids + ", ig=" + ig + ", bio=" + bio
                + ", origine=" + origine + ", idC=" + idC + ", idA=" + idA + ", NbRecherche=" + nbRecherche + "]" + "empreinteCarb" + empreinteC;
    }

    public int getNbRecherche() {
        return nbRecherche;
    }

    public void setNbRecherche(int nbRecherche) {
        this.nbRecherche = nbRecherche;
    }
}





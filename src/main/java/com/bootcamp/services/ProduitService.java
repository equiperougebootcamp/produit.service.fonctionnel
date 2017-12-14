package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.crud.ProduitCRUD;
import com.bootcamp.entities.Produit;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * @author leger
 */
@Component
public class ProduitService implements DatabaseConstants {

    public Produit create(Produit produit) throws SQLException {
        ProduitCRUD.create(produit);
        return produit;
    }
    
    public List<Produit> findAll() throws SQLException {
        return ProduitCRUD.read();
    }
    
    public Produit read(int id) throws SQLException {
        Criterias criterias = new Criterias();
        criterias.addCriteria(new Criteria("id", "=", id));
        Produit produit = ProduitCRUD.read(criterias).get(0);
        return produit;
    }
    
    public boolean delete(int id) throws Exception{
        Produit produit = read(id);
        return ProduitCRUD.delete(produit);
    }

    public boolean update(Produit produit) throws Exception{
        return ProduitCRUD.update(produit);
    }

}

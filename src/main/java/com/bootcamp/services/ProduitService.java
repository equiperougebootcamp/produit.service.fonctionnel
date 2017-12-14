package com.bootcamp.services;

import com.bootcamp.commons.constants.DatabaseConstants;
import com.bootcamp.commons.exceptions.DatabaseException;
import com.bootcamp.commons.models.Criteria;
import com.bootcamp.commons.models.Criterias;
import com.bootcamp.commons.ws.utils.RequestParser;
import com.bootcamp.crud.ProduitCRUD;
import com.bootcamp.entities.Post;
import com.bootcamp.entities.Produit;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
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


    public List<Produit> read(HttpServletRequest request) throws SQLException, IllegalAccessException, DatabaseException, InvocationTargetException {
        Criterias criterias = RequestParser.getCriterias(request);
        List<String> fields = RequestParser.getFields(request);
        List<Produit> produits = null;
        if(criterias == null && fields == null)
            produits =  ProduitCRUD.read();
        else if(criterias!= null && fields==null)
            produits = ProduitCRUD.read(criterias);
        else if(criterias== null && fields!=null)
            produits = ProduitCRUD.read(fields);
        else
            produits = ProduitCRUD.read(criterias, fields);

        return produits;
    }


    public boolean exist(int id) throws Exception{
        if(read(id)!=null)
            return true;
        return false;
    }

}

package com.bootcamp.controllers;

import com.bootcamp.entities.Produit;
import com.bootcamp.services.ProduitService;
import com.bootcamp.version.ApiVersions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController("ProduitController")
@RequestMapping("/produits")
@CrossOrigin(origins = "*")
@Api(value = "Produit API", description = "Produit API")
public class ProduitController {

    @Autowired
    ProduitService produitService;
    
    @RequestMapping(method = RequestMethod.POST)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Create a new product", notes = "Create a new product")
    public ResponseEntity<Produit> create(@RequestBody @Valid Produit produit) {

        HttpStatus httpStatus = null;
        
        try {
            produit = produitService.create(produit);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
//            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Produit>(produit, httpStatus);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "update a new product", notes = "Update a new product")
    public ResponseEntity<Boolean> update(@RequestBody @Valid Produit produit) throws Exception {

        HttpStatus httpStatus = null;
            boolean done = produitService.update(produit);
            httpStatus = HttpStatus.OK;
            return new ResponseEntity<>(done, httpStatus);
    }

    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "delete a new product", notes = "delete a new product")
    public ResponseEntity<Boolean> update(@PathVariable  int id) throws Exception {

        HttpStatus httpStatus = null;
        boolean done = produitService.delete(id);
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<>(done, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Get list of products", notes = "Get list of products")
    public ResponseEntity<List<Produit>> findAll() throws SQLException {
        HttpStatus httpStatus = null;
        List<Produit> produits = produitService.findAll();
        httpStatus = HttpStatus.OK;
        return new ResponseEntity<List<Produit>>(produits, httpStatus);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ApiVersions({"1.0"})
    @ApiOperation(value = "Find a produit", notes = "Find a produit")
    public ResponseEntity<Produit> read(@PathVariable int id) {

        Produit produit = new Produit();
        HttpStatus httpStatus = null;

        try {
            produit = produitService.read(id);
            httpStatus = HttpStatus.OK;
        } catch (SQLException ex) {
//            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<Produit>(produit, httpStatus);
    }
    

}

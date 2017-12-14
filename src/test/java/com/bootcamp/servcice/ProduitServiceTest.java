package com.bootcamp.servcice;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.crud.ProduitCRUD;
import com.bootcamp.entities.Produit;
import com.bootcamp.services.ProduitService;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;


/**
 * Created by Ibrahim on 12/9/17.
 */

@RunWith(PowerMockRunner.class)
@WebMvcTest(value = ProduitService.class, secure = false)
@ContextConfiguration(classes = {Application.class})
@PrepareForTest(ProduitCRUD.class)
@PowerMockRunnerDelegate(SpringRunner.class)
public class ProduitServiceTest {


    @InjectMocks
    private ProduitService produitService;

    @Test
    public void getAllProduit() throws Exception {
        List<Produit> produits = loadDataProduitFromJsonFile();
        PowerMockito.mockStatic(ProduitCRUD.class);
        Mockito.
                when(ProduitCRUD.read()).thenReturn(produits);
        List<Produit> resultProduits = produitService.findAll();
        Assert.assertEquals(produits.size(), resultProduits.size());

    }


    @Test
    public void create() throws Exception{
        List<Produit> produits = loadDataProduitFromJsonFile();
        Produit produit = produits.get(1);

        PowerMockito.mockStatic(ProduitCRUD.class);
        Mockito.
                when(ProduitCRUD.create(produit)).thenReturn(true);
    }

    @Test
    public void delete() throws Exception{
        List<Produit> produits = loadDataProduitFromJsonFile();
        Produit produit = produits.get(1);

        PowerMockito.mockStatic(ProduitCRUD.class);
        Mockito.
                when(ProduitCRUD.delete(produit)).thenReturn(true);
    }

    @Test
    public void update() throws Exception{
        List<Produit> produits = loadDataProduitFromJsonFile();
        Produit produit = produits.get(1);

        PowerMockito.mockStatic(ProduitCRUD.class);
        Mockito.
                when(ProduitCRUD.update(produit)).thenReturn(true);
    }


    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if (!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    public List<Produit> loadDataProduitFromJsonFile() throws Exception {
        //TestUtils testUtils = new TestUtils();
        File dataFile = getFile( "data-json" + File.separator + "produits.json");

        String text = Files.toString(new File(dataFile.getAbsolutePath()), Charsets.UTF_8);

        Type typeOfObjectsListNew = new TypeToken<List<Produit>>() {
        }.getType();
        List<Produit> produits = GsonUtils.getObjectFromJson(text, typeOfObjectsListNew);

        return produits;
    }



    private Produit getDebatById(int id) throws Exception {
        List<Produit> produits = loadDataProduitFromJsonFile();
        Produit produit = produits.stream().filter(item -> item.getId() == id).findFirst().get();

        return produit;
    }

}
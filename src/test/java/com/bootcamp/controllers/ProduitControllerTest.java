package com.bootcamp.controllers;

import com.bootcamp.application.Application;
import com.bootcamp.commons.utils.GsonUtils;
import com.bootcamp.entities.Produit;
import com.bootcamp.services.ProduitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/*
import org.junit.runner.RunWith;
*/
/*
 *
 * Created by darextossa on 12/5/17.
 */

//@TestExecutionListeners(MockitoTestExecutionListener.class)
//@ActiveProfiles({"controller-unit-test"})
//@SpringBootApplication(scanBasePackages={"com.bootcamp"})
//@ContextConfiguration(classes = ControllerUnitTestConfig.class)
//@WebMvcTest(value = PilierController.class, secure = false, excludeAutoConfiguration = {HealthIndicatorAutoConfiguration.class, HibernateJpaAutoConfiguration.class, FlywayAutoConfiguration.class, DataSourceAutoConfiguration.class})

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProduitController.class, secure = false)
@ContextConfiguration(classes={Application.class})
public class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProduitService produitService;


    @Test
    public void getAllProduit() throws Exception{
        List<Produit> produits =  loadDataProduitFromJsonFile();
        //Mockito.mock(ProduitCRUD.class);
        when(produitService.findAll()).thenReturn(produits);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/produits")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());
        System.out.println("*********************************Test for get all pilar  in secteur controller done *******************");


    }

    @Test
    public void getProduitById() throws Exception{
        int id = 1;
        Produit produit = getProduitById(id);
        when(produitService.read(id)).thenReturn(produit);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/produits/{id}",id)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        System.out.println(response.getContentAsString());
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for get pilar by id in pilar controller done *******************");

    }


    @Test
    public void createProduit() throws Exception{
        List<Produit> produits =  loadDataProduitFromJsonFile();
        Produit produit = getProduitById( 1 );

        when(produitService.exist(1)).thenReturn(false);
                when(produitService.create(produit)).thenReturn(produit);

        RequestBuilder requestBuilder =
                post("/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(produit));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        mockMvc.perform(requestBuilder).andExpect(status().isOk());

        System.out.println(response.getContentAsString());

        System.out.println("*********************************Test for create produit in produit controller done *******************");
    }

/*
    @Test
    public void updateProduit() throws Exception{
        List<Produit> produits =  loadDataProduitFromJsonFile();
        Produit produit = getProduitById( 1 );
        when(produitService.exist(produit.getId())).thenReturn(true);
        when(produitService.update(produit)).thenReturn(true);

        RequestBuilder requestBuilder =
                put("/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(produit));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for update produit in produit controller done *******************");


    }*/

    @Test
    public void deleteProduit() throws Exception{
        int id = 1;
        when(produitService.exist(id)).thenReturn(true);
              when(produitService.delete(id)).thenReturn(true);

        RequestBuilder requestBuilder =
                delete("/produits/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        System.out.println(response.getContentAsString());

        mockMvc.perform(requestBuilder).andExpect(status().isOk());
        System.out.println("*********************************Test for delete produit in produit controller done *******************");


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

    private Produit getProduitById(int id) throws Exception {
        List<Produit> produits = loadDataProduitFromJsonFile();
        Produit produit = produits.stream().filter(item -> item.getId() == id).findFirst().get();

        return produit;
    }



    public File getFile(String relativePath) throws Exception {

        File file = new File(getClass().getClassLoader().getResource(relativePath).toURI());

        if(!file.exists()) {
            throw new FileNotFoundException("File:" + relativePath);
        }

        return file;
    }

    private static String objectToJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

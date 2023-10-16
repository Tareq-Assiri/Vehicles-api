package com.udacity.pricing.api;

import com.udacity.pricing.domain.price.Price;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Implements testing of the PriceController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PriceControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Price> json;
    

    /**
     * Creates pre-requisites for testing, such as an example price.
     */
    @Before
    public void setup() {
        Price price = getPrice();
    }

    /**
     * Tests for successful creation of new price in the system
     * @throws Exception when price creation fails in the system
     */
    @Test
    public void createPrice() throws Exception {
        Price price = getPrice();
        mvc.perform(
                post(new URI("/prices"))
                        .content(json.write(price).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    /**
     * Tests if the read operation appropriately returns a list of vehicles.
     * @throws Exception if the read operation of the vehicle list fails
     */
    @Test
    public void listPrices() throws Exception {
        mvc.perform(
                        get(new URI("/prices"))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("_embedded.prices[0].vehicleId",is(1)));
    }

    /**
     * Tests the read operation for a single price by ID.
     * @throws Exception if the read operation for a single price fails
     */
    @Test
    public void findPrice() throws Exception {
        mvc.perform(
                        get(new URI("/prices/1"))
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("vehicleId",is(1)));
    }


    /**
     * Creates an example Price object for use in testing.
     * @return an example Price object
     */
    private Price getPrice() {
        Price price = new Price("$",new BigDecimal(1000),1L);
        return price;
    }
}
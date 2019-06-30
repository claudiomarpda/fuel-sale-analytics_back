package com.mz.fuel_sale_analytics_back.util;

import com.mz.fuel_sale_analytics_back.model.*;
import com.mz.fuel_sale_analytics_back.util.file.*;
import com.mz.fuel_sale_analytics_back.repository.StateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static com.mz.fuel_sale_analytics_back.util.file.FileRecords.COUNTIES_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileHelperTest {

    private List<State> states;
    private List<County> counties;
    private List<Region> regions;
    private List<Product> products;
    private List<Banner> banners;

    @Autowired
    private StateRepository stateRepository;

    @Before
    public void setUp() throws IOException {
        states = new StatesReader().read(FileRecords.STATES_PATH);
        counties = CountyDtoUtil.getFromDto(new CountiesReader().read(COUNTIES_PATH), stateRepository);
        regions = new RegionsReader().read(FileRecords.REGIONS_PATH);
        products = new ProductsReader().read(FileRecords.PRODUCTS_PATH);
        banners = new BannersReader().read(FileRecords.BANNERS_PATH);
    }

    @Test
    public void numberOfStatesMatches() {
        assertEquals(FileRecords.STATES, states.size());
    }

    @Test
    public void firstStateShouldNotMatch() {
        State first = states.get(0);
        assertNotEquals(99, first.getUfCode());
        assertNotEquals("invalid", first.getUf());
        assertNotEquals("invalid", first.getUfName());
    }

    /**
     * 11,RO,Rondônia
     */
    @Test
    public void firstStateMustBeSameAsDescribedAbove() {
        State first = states.get(0);
        assertEquals(11, first.getUfCode());
        assertEquals("RO", first.getUf());
        assertEquals("Rondônia", first.getUfName());
    }

    @Test
    public void numberOfCountiesMatches() {
        assertEquals(FileRecords.COUNTIES, counties.size());
    }

    @Test
    public void firstCountyShouldNotMatch() {
        County first = counties.get(0);
        assertNotEquals(99, first.getIbgeCode());
        assertNotEquals("invalid", first.getName());
        assertNotEquals("invalid", first.getState().getUfCode());
    }

    /**
     * 5200050,Abadia de Goiás,-16.7573,-49.4412,0,52
     */
    @Test
    public void firstCountyMustBeSameAsDescribedAbove() {
        County first = counties.get(0);
        assertEquals(5200050, first.getIbgeCode());
        assertEquals("Abadia de Goiás", first.getName());
        assertEquals(52, first.getState().getUfCode());
    }

    @Test
    public void firstRegionShouldNotMatch() {
        Region first = regions.get(0);
        assertNotEquals("invalid", first.getCode());
        assertNotEquals("invalid", first.getName());
    }

    /**
     * CO,Centro-Oeste
     */
    @Test
    public void firstRegionMustBeSameAsDescribedAbove() {
        Region first = regions.get(0);
        assertEquals("CO", first.getCode());
        System.out.println(first.getName());
        assertEquals("Centro-Oeste", first.getName());
    }

    @Test
    public void firstProductShouldNotMatch() {
        Product first = products.get(0);
        assertNotEquals("invalid", first.getName());
    }

    /**
     * Diesel
     */
    @Test
    public void firstProductMustBeSameAsDescribedAbove() {
        Product first = products.get(0);
        assertEquals("DIESEL", first.getName());
    }

    @Test
    public void firstBannerShouldNotMatch() {
        Banner first = banners.get(0);
        assertNotEquals("invalid", first.getName());
    }

    /**
     * IPIRANGA
     */
    @Test
    public void firstBannerMustBeSameAsDescribedAbove() {
        Banner first = banners.get(0);
        assertEquals("IPIRANGA", first.getName());
    }

}

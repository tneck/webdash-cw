package com.dr_alan_turing.webdash_cw.web.rest;

import com.dr_alan_turing.webdash_cw.WebDashCwApp;
import com.dr_alan_turing.webdash_cw.domain.Dashboard;
import com.dr_alan_turing.webdash_cw.repository.DashboardRepository;
import com.dr_alan_turing.webdash_cw.service.DashboardService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the DashboardResource REST controller.
 *
 * @see DashboardResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebDashCwApp.class)
@WebAppConfiguration
@IntegrationTest
public class DashboardResourceIntTest {

    private static final String DEFAULT_OPTIONS = "AAAAA";
    private static final String UPDATED_OPTIONS = "BBBBB";
    private static final String DEFAULT_DATA_1 = "AAAAA";
    private static final String UPDATED_DATA_1 = "BBBBB";
    private static final String DEFAULT_DATA_2 = "AAAAA";
    private static final String UPDATED_DATA_2 = "BBBBB";

    @Inject
    private DashboardRepository dashboardRepository;

    @Inject
    private DashboardService dashboardService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restDashboardMockMvc;

    private Dashboard dashboard;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DashboardResource dashboardResource = new DashboardResource();
        ReflectionTestUtils.setField(dashboardResource, "dashboardService", dashboardService);
        this.restDashboardMockMvc = MockMvcBuilders.standaloneSetup(dashboardResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        dashboard = new Dashboard();
        dashboard.setOptions(DEFAULT_OPTIONS);
        dashboard.setData1(DEFAULT_DATA_1);
        dashboard.setData2(DEFAULT_DATA_2);
    }

    @Test
    @Transactional
    public void createDashboard() throws Exception {
        int databaseSizeBeforeCreate = dashboardRepository.findAll().size();

        // Create the Dashboard

        restDashboardMockMvc.perform(post("/api/dashboards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(dashboard)))
                .andExpect(status().isCreated());

        // Validate the Dashboard in the database
        List<Dashboard> dashboards = dashboardRepository.findAll();
        assertThat(dashboards).hasSize(databaseSizeBeforeCreate + 1);
        Dashboard testDashboard = dashboards.get(dashboards.size() - 1);
        assertThat(testDashboard.getOptions()).isEqualTo(DEFAULT_OPTIONS);
        assertThat(testDashboard.getData1()).isEqualTo(DEFAULT_DATA_1);
        assertThat(testDashboard.getData2()).isEqualTo(DEFAULT_DATA_2);
    }

    @Test
    @Transactional
    public void getAllDashboards() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        // Get all the dashboards
        restDashboardMockMvc.perform(get("/api/dashboards?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(dashboard.getId().intValue())))
                .andExpect(jsonPath("$.[*].options").value(hasItem(DEFAULT_OPTIONS.toString())))
                .andExpect(jsonPath("$.[*].data1").value(hasItem(DEFAULT_DATA_1.toString())))
                .andExpect(jsonPath("$.[*].data2").value(hasItem(DEFAULT_DATA_2.toString())));
    }

    @Test
    @Transactional
    public void getDashboard() throws Exception {
        // Initialize the database
        dashboardRepository.saveAndFlush(dashboard);

        // Get the dashboard
        restDashboardMockMvc.perform(get("/api/dashboards/{id}", dashboard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(dashboard.getId().intValue()))
            .andExpect(jsonPath("$.options").value(DEFAULT_OPTIONS.toString()))
            .andExpect(jsonPath("$.data1").value(DEFAULT_DATA_1.toString()))
            .andExpect(jsonPath("$.data2").value(DEFAULT_DATA_2.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDashboard() throws Exception {
        // Get the dashboard
        restDashboardMockMvc.perform(get("/api/dashboards/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDashboard() throws Exception {
        // Initialize the database
        dashboardService.save(dashboard);

        int databaseSizeBeforeUpdate = dashboardRepository.findAll().size();

        // Update the dashboard
        Dashboard updatedDashboard = new Dashboard();
        updatedDashboard.setId(dashboard.getId());
        updatedDashboard.setOptions(UPDATED_OPTIONS);
        updatedDashboard.setData1(UPDATED_DATA_1);
        updatedDashboard.setData2(UPDATED_DATA_2);

        restDashboardMockMvc.perform(put("/api/dashboards")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedDashboard)))
                .andExpect(status().isOk());

        // Validate the Dashboard in the database
        List<Dashboard> dashboards = dashboardRepository.findAll();
        assertThat(dashboards).hasSize(databaseSizeBeforeUpdate);
        Dashboard testDashboard = dashboards.get(dashboards.size() - 1);
        assertThat(testDashboard.getOptions()).isEqualTo(UPDATED_OPTIONS);
        assertThat(testDashboard.getData1()).isEqualTo(UPDATED_DATA_1);
        assertThat(testDashboard.getData2()).isEqualTo(UPDATED_DATA_2);
    }

    @Test
    @Transactional
    public void deleteDashboard() throws Exception {
        // Initialize the database
        dashboardService.save(dashboard);

        int databaseSizeBeforeDelete = dashboardRepository.findAll().size();

        // Get the dashboard
        restDashboardMockMvc.perform(delete("/api/dashboards/{id}", dashboard.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Dashboard> dashboards = dashboardRepository.findAll();
        assertThat(dashboards).hasSize(databaseSizeBeforeDelete - 1);
    }
}

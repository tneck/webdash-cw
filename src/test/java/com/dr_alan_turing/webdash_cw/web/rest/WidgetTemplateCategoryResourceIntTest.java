package com.dr_alan_turing.webdash_cw.web.rest;

import com.dr_alan_turing.webdash_cw.WebDashCwApp;
import com.dr_alan_turing.webdash_cw.domain.WidgetTemplateCategory;
import com.dr_alan_turing.webdash_cw.repository.WidgetTemplateCategoryRepository;
import com.dr_alan_turing.webdash_cw.service.WidgetTemplateCategoryService;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the WidgetTemplateCategoryResource REST controller.
 *
 * @see WidgetTemplateCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebDashCwApp.class)
@WebAppConfiguration
@IntegrationTest
public class WidgetTemplateCategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Inject
    private WidgetTemplateCategoryRepository widgetTemplateCategoryRepository;

    @Inject
    private WidgetTemplateCategoryService widgetTemplateCategoryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restWidgetTemplateCategoryMockMvc;

    private WidgetTemplateCategory widgetTemplateCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WidgetTemplateCategoryResource widgetTemplateCategoryResource = new WidgetTemplateCategoryResource();
        ReflectionTestUtils.setField(widgetTemplateCategoryResource, "widgetTemplateCategoryService", widgetTemplateCategoryService);
        this.restWidgetTemplateCategoryMockMvc = MockMvcBuilders.standaloneSetup(widgetTemplateCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        widgetTemplateCategory = new WidgetTemplateCategory();
        widgetTemplateCategory.setName(DEFAULT_NAME);
        widgetTemplateCategory.setDescription(DEFAULT_DESCRIPTION);
        widgetTemplateCategory.setImage(DEFAULT_IMAGE);
        widgetTemplateCategory.setImageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createWidgetTemplateCategory() throws Exception {
        int databaseSizeBeforeCreate = widgetTemplateCategoryRepository.findAll().size();

        // Create the WidgetTemplateCategory

        restWidgetTemplateCategoryMockMvc.perform(post("/api/widget-template-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(widgetTemplateCategory)))
                .andExpect(status().isCreated());

        // Validate the WidgetTemplateCategory in the database
        List<WidgetTemplateCategory> widgetTemplateCategories = widgetTemplateCategoryRepository.findAll();
        assertThat(widgetTemplateCategories).hasSize(databaseSizeBeforeCreate + 1);
        WidgetTemplateCategory testWidgetTemplateCategory = widgetTemplateCategories.get(widgetTemplateCategories.size() - 1);
        assertThat(testWidgetTemplateCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWidgetTemplateCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWidgetTemplateCategory.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testWidgetTemplateCategory.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = widgetTemplateCategoryRepository.findAll().size();
        // set the field null
        widgetTemplateCategory.setName(null);

        // Create the WidgetTemplateCategory, which fails.

        restWidgetTemplateCategoryMockMvc.perform(post("/api/widget-template-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(widgetTemplateCategory)))
                .andExpect(status().isBadRequest());

        List<WidgetTemplateCategory> widgetTemplateCategories = widgetTemplateCategoryRepository.findAll();
        assertThat(widgetTemplateCategories).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWidgetTemplateCategories() throws Exception {
        // Initialize the database
        widgetTemplateCategoryRepository.saveAndFlush(widgetTemplateCategory);

        // Get all the widgetTemplateCategories
        restWidgetTemplateCategoryMockMvc.perform(get("/api/widget-template-categories?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(widgetTemplateCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    public void getWidgetTemplateCategory() throws Exception {
        // Initialize the database
        widgetTemplateCategoryRepository.saveAndFlush(widgetTemplateCategory);

        // Get the widgetTemplateCategory
        restWidgetTemplateCategoryMockMvc.perform(get("/api/widget-template-categories/{id}", widgetTemplateCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(widgetTemplateCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingWidgetTemplateCategory() throws Exception {
        // Get the widgetTemplateCategory
        restWidgetTemplateCategoryMockMvc.perform(get("/api/widget-template-categories/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWidgetTemplateCategory() throws Exception {
        // Initialize the database
        widgetTemplateCategoryService.save(widgetTemplateCategory);

        int databaseSizeBeforeUpdate = widgetTemplateCategoryRepository.findAll().size();

        // Update the widgetTemplateCategory
        WidgetTemplateCategory updatedWidgetTemplateCategory = new WidgetTemplateCategory();
        updatedWidgetTemplateCategory.setId(widgetTemplateCategory.getId());
        updatedWidgetTemplateCategory.setName(UPDATED_NAME);
        updatedWidgetTemplateCategory.setDescription(UPDATED_DESCRIPTION);
        updatedWidgetTemplateCategory.setImage(UPDATED_IMAGE);
        updatedWidgetTemplateCategory.setImageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restWidgetTemplateCategoryMockMvc.perform(put("/api/widget-template-categories")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedWidgetTemplateCategory)))
                .andExpect(status().isOk());

        // Validate the WidgetTemplateCategory in the database
        List<WidgetTemplateCategory> widgetTemplateCategories = widgetTemplateCategoryRepository.findAll();
        assertThat(widgetTemplateCategories).hasSize(databaseSizeBeforeUpdate);
        WidgetTemplateCategory testWidgetTemplateCategory = widgetTemplateCategories.get(widgetTemplateCategories.size() - 1);
        assertThat(testWidgetTemplateCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWidgetTemplateCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWidgetTemplateCategory.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testWidgetTemplateCategory.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteWidgetTemplateCategory() throws Exception {
        // Initialize the database
        widgetTemplateCategoryService.save(widgetTemplateCategory);

        int databaseSizeBeforeDelete = widgetTemplateCategoryRepository.findAll().size();

        // Get the widgetTemplateCategory
        restWidgetTemplateCategoryMockMvc.perform(delete("/api/widget-template-categories/{id}", widgetTemplateCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<WidgetTemplateCategory> widgetTemplateCategories = widgetTemplateCategoryRepository.findAll();
        assertThat(widgetTemplateCategories).hasSize(databaseSizeBeforeDelete - 1);
    }
}

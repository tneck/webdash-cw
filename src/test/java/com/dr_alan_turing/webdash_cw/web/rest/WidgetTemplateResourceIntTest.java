package com.dr_alan_turing.webdash_cw.web.rest;

import com.dr_alan_turing.webdash_cw.WebDashCwApp;
import com.dr_alan_turing.webdash_cw.domain.WidgetTemplate;
import com.dr_alan_turing.webdash_cw.repository.WidgetTemplateRepository;
import com.dr_alan_turing.webdash_cw.service.WidgetTemplateService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dr_alan_turing.webdash_cw.domain.enumeration.WidgetAccess;

/**
 * Test class for the WidgetTemplateResource REST controller.
 *
 * @see WidgetTemplateResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebDashCwApp.class)
@WebAppConfiguration
@IntegrationTest
public class WidgetTemplateResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_CREATED_STR = dateTimeFormatter.format(DEFAULT_DATE_CREATED);

    private static final ZonedDateTime DEFAULT_DATE_LAST_MODIFIED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_DATE_LAST_MODIFIED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_DATE_LAST_MODIFIED_STR = dateTimeFormatter.format(DEFAULT_DATE_LAST_MODIFIED);
    private static final String DEFAULT_CURRENT_VERSION = "AAAAA";
    private static final String UPDATED_CURRENT_VERSION = "BBBBB";

    private static final WidgetAccess DEFAULT_ACCESS = WidgetAccess.PRIVATE;
    private static final WidgetAccess UPDATED_ACCESS = WidgetAccess.PRIVATE_SHARED;
    private static final String DEFAULT_SHARE_CODE = "AAAAA";
    private static final String UPDATED_SHARE_CODE = "BBBBB";

    private static final byte[] DEFAULT_IMAGE_PREVIEW = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_PREVIEW = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_PREVIEW_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_PREVIEW_CONTENT_TYPE = "image/png";
    private static final String DEFAULT_OPTIONS = "AAAAA";
    private static final String UPDATED_OPTIONS = "BBBBB";
    private static final String DEFAULT_CONTENT_URL = "AAAAA";
    private static final String UPDATED_CONTENT_URL = "BBBBB";
    private static final String DEFAULT_DATA_LIBRARIES = "AAAAA";
    private static final String UPDATED_DATA_LIBRARIES = "BBBBB";
    private static final String DEFAULT_DATA_HTML = "AAAAA";
    private static final String UPDATED_DATA_HTML = "BBBBB";
    private static final String DEFAULT_DATA_CSS = "AAAAA";
    private static final String UPDATED_DATA_CSS = "BBBBB";
    private static final String DEFAULT_DATA_JAVASCRIPT = "AAAAA";
    private static final String UPDATED_DATA_JAVASCRIPT = "BBBBB";
    private static final String DEFAULT_DATA_INPUT_VARIABLES = "AAAAA";
    private static final String UPDATED_DATA_INPUT_VARIABLES = "BBBBB";

    @Inject
    private WidgetTemplateRepository widgetTemplateRepository;

    @Inject
    private WidgetTemplateService widgetTemplateService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restWidgetTemplateMockMvc;

    private WidgetTemplate widgetTemplate;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        WidgetTemplateResource widgetTemplateResource = new WidgetTemplateResource();
        ReflectionTestUtils.setField(widgetTemplateResource, "widgetTemplateService", widgetTemplateService);
        this.restWidgetTemplateMockMvc = MockMvcBuilders.standaloneSetup(widgetTemplateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        widgetTemplate = new WidgetTemplate();
        widgetTemplate.setName(DEFAULT_NAME);
        widgetTemplate.setDescription(DEFAULT_DESCRIPTION);
        widgetTemplate.setDateCreated(DEFAULT_DATE_CREATED);
        widgetTemplate.setDateLastModified(DEFAULT_DATE_LAST_MODIFIED);
        widgetTemplate.setCurrentVersion(DEFAULT_CURRENT_VERSION);
        widgetTemplate.setAccess(DEFAULT_ACCESS);
        widgetTemplate.setShareCode(DEFAULT_SHARE_CODE);
        widgetTemplate.setImagePreview(DEFAULT_IMAGE_PREVIEW);
        widgetTemplate.setImagePreviewContentType(DEFAULT_IMAGE_PREVIEW_CONTENT_TYPE);
        widgetTemplate.setOptions(DEFAULT_OPTIONS);
        widgetTemplate.setContentUrl(DEFAULT_CONTENT_URL);
        widgetTemplate.setDataLibraries(DEFAULT_DATA_LIBRARIES);
        widgetTemplate.setDataHtml(DEFAULT_DATA_HTML);
        widgetTemplate.setDataCss(DEFAULT_DATA_CSS);
        widgetTemplate.setDataJavascript(DEFAULT_DATA_JAVASCRIPT);
        widgetTemplate.setDataInputVariables(DEFAULT_DATA_INPUT_VARIABLES);
    }

    @Test
    @Transactional
    public void createWidgetTemplate() throws Exception {
        int databaseSizeBeforeCreate = widgetTemplateRepository.findAll().size();

        // Create the WidgetTemplate

        restWidgetTemplateMockMvc.perform(post("/api/widget-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(widgetTemplate)))
                .andExpect(status().isCreated());

        // Validate the WidgetTemplate in the database
        List<WidgetTemplate> widgetTemplates = widgetTemplateRepository.findAll();
        assertThat(widgetTemplates).hasSize(databaseSizeBeforeCreate + 1);
        WidgetTemplate testWidgetTemplate = widgetTemplates.get(widgetTemplates.size() - 1);
        assertThat(testWidgetTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWidgetTemplate.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWidgetTemplate.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
        assertThat(testWidgetTemplate.getDateLastModified()).isEqualTo(DEFAULT_DATE_LAST_MODIFIED);
        assertThat(testWidgetTemplate.getCurrentVersion()).isEqualTo(DEFAULT_CURRENT_VERSION);
        assertThat(testWidgetTemplate.getAccess()).isEqualTo(DEFAULT_ACCESS);
        assertThat(testWidgetTemplate.getShareCode()).isEqualTo(DEFAULT_SHARE_CODE);
        assertThat(testWidgetTemplate.getImagePreview()).isEqualTo(DEFAULT_IMAGE_PREVIEW);
        assertThat(testWidgetTemplate.getImagePreviewContentType()).isEqualTo(DEFAULT_IMAGE_PREVIEW_CONTENT_TYPE);
        assertThat(testWidgetTemplate.getOptions()).isEqualTo(DEFAULT_OPTIONS);
        assertThat(testWidgetTemplate.getContentUrl()).isEqualTo(DEFAULT_CONTENT_URL);
        assertThat(testWidgetTemplate.getDataLibraries()).isEqualTo(DEFAULT_DATA_LIBRARIES);
        assertThat(testWidgetTemplate.getDataHtml()).isEqualTo(DEFAULT_DATA_HTML);
        assertThat(testWidgetTemplate.getDataCss()).isEqualTo(DEFAULT_DATA_CSS);
        assertThat(testWidgetTemplate.getDataJavascript()).isEqualTo(DEFAULT_DATA_JAVASCRIPT);
        assertThat(testWidgetTemplate.getDataInputVariables()).isEqualTo(DEFAULT_DATA_INPUT_VARIABLES);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = widgetTemplateRepository.findAll().size();
        // set the field null
        widgetTemplate.setName(null);

        // Create the WidgetTemplate, which fails.

        restWidgetTemplateMockMvc.perform(post("/api/widget-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(widgetTemplate)))
                .andExpect(status().isBadRequest());

        List<WidgetTemplate> widgetTemplates = widgetTemplateRepository.findAll();
        assertThat(widgetTemplates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = widgetTemplateRepository.findAll().size();
        // set the field null
        widgetTemplate.setDescription(null);

        // Create the WidgetTemplate, which fails.

        restWidgetTemplateMockMvc.perform(post("/api/widget-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(widgetTemplate)))
                .andExpect(status().isBadRequest());

        List<WidgetTemplate> widgetTemplates = widgetTemplateRepository.findAll();
        assertThat(widgetTemplates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = widgetTemplateRepository.findAll().size();
        // set the field null
        widgetTemplate.setDateCreated(null);

        // Create the WidgetTemplate, which fails.

        restWidgetTemplateMockMvc.perform(post("/api/widget-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(widgetTemplate)))
                .andExpect(status().isBadRequest());

        List<WidgetTemplate> widgetTemplates = widgetTemplateRepository.findAll();
        assertThat(widgetTemplates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentVersionIsRequired() throws Exception {
        int databaseSizeBeforeTest = widgetTemplateRepository.findAll().size();
        // set the field null
        widgetTemplate.setCurrentVersion(null);

        // Create the WidgetTemplate, which fails.

        restWidgetTemplateMockMvc.perform(post("/api/widget-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(widgetTemplate)))
                .andExpect(status().isBadRequest());

        List<WidgetTemplate> widgetTemplates = widgetTemplateRepository.findAll();
        assertThat(widgetTemplates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccessIsRequired() throws Exception {
        int databaseSizeBeforeTest = widgetTemplateRepository.findAll().size();
        // set the field null
        widgetTemplate.setAccess(null);

        // Create the WidgetTemplate, which fails.

        restWidgetTemplateMockMvc.perform(post("/api/widget-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(widgetTemplate)))
                .andExpect(status().isBadRequest());

        List<WidgetTemplate> widgetTemplates = widgetTemplateRepository.findAll();
        assertThat(widgetTemplates).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWidgetTemplates() throws Exception {
        // Initialize the database
        widgetTemplateRepository.saveAndFlush(widgetTemplate);

        // Get all the widgetTemplates
        restWidgetTemplateMockMvc.perform(get("/api/widget-templates?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(widgetTemplate.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(DEFAULT_DATE_CREATED_STR)))
                .andExpect(jsonPath("$.[*].dateLastModified").value(hasItem(DEFAULT_DATE_LAST_MODIFIED_STR)))
                .andExpect(jsonPath("$.[*].currentVersion").value(hasItem(DEFAULT_CURRENT_VERSION.toString())))
                .andExpect(jsonPath("$.[*].access").value(hasItem(DEFAULT_ACCESS.toString())))
                .andExpect(jsonPath("$.[*].shareCode").value(hasItem(DEFAULT_SHARE_CODE.toString())))
                .andExpect(jsonPath("$.[*].imagePreviewContentType").value(hasItem(DEFAULT_IMAGE_PREVIEW_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].imagePreview").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_PREVIEW))))
                .andExpect(jsonPath("$.[*].options").value(hasItem(DEFAULT_OPTIONS.toString())))
                .andExpect(jsonPath("$.[*].contentUrl").value(hasItem(DEFAULT_CONTENT_URL.toString())))
                .andExpect(jsonPath("$.[*].dataLibraries").value(hasItem(DEFAULT_DATA_LIBRARIES.toString())))
                .andExpect(jsonPath("$.[*].dataHtml").value(hasItem(DEFAULT_DATA_HTML.toString())))
                .andExpect(jsonPath("$.[*].dataCss").value(hasItem(DEFAULT_DATA_CSS.toString())))
                .andExpect(jsonPath("$.[*].dataJavascript").value(hasItem(DEFAULT_DATA_JAVASCRIPT.toString())))
                .andExpect(jsonPath("$.[*].dataInputVariables").value(hasItem(DEFAULT_DATA_INPUT_VARIABLES.toString())));
    }

    @Test
    @Transactional
    public void getWidgetTemplate() throws Exception {
        // Initialize the database
        widgetTemplateRepository.saveAndFlush(widgetTemplate);

        // Get the widgetTemplate
        restWidgetTemplateMockMvc.perform(get("/api/widget-templates/{id}", widgetTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(widgetTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.dateCreated").value(DEFAULT_DATE_CREATED_STR))
            .andExpect(jsonPath("$.dateLastModified").value(DEFAULT_DATE_LAST_MODIFIED_STR))
            .andExpect(jsonPath("$.currentVersion").value(DEFAULT_CURRENT_VERSION.toString()))
            .andExpect(jsonPath("$.access").value(DEFAULT_ACCESS.toString()))
            .andExpect(jsonPath("$.shareCode").value(DEFAULT_SHARE_CODE.toString()))
            .andExpect(jsonPath("$.imagePreviewContentType").value(DEFAULT_IMAGE_PREVIEW_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagePreview").value(Base64Utils.encodeToString(DEFAULT_IMAGE_PREVIEW)))
            .andExpect(jsonPath("$.options").value(DEFAULT_OPTIONS.toString()))
            .andExpect(jsonPath("$.contentUrl").value(DEFAULT_CONTENT_URL.toString()))
            .andExpect(jsonPath("$.dataLibraries").value(DEFAULT_DATA_LIBRARIES.toString()))
            .andExpect(jsonPath("$.dataHtml").value(DEFAULT_DATA_HTML.toString()))
            .andExpect(jsonPath("$.dataCss").value(DEFAULT_DATA_CSS.toString()))
            .andExpect(jsonPath("$.dataJavascript").value(DEFAULT_DATA_JAVASCRIPT.toString()))
            .andExpect(jsonPath("$.dataInputVariables").value(DEFAULT_DATA_INPUT_VARIABLES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWidgetTemplate() throws Exception {
        // Get the widgetTemplate
        restWidgetTemplateMockMvc.perform(get("/api/widget-templates/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWidgetTemplate() throws Exception {
        // Initialize the database
        widgetTemplateService.save(widgetTemplate);

        int databaseSizeBeforeUpdate = widgetTemplateRepository.findAll().size();

        // Update the widgetTemplate
        WidgetTemplate updatedWidgetTemplate = new WidgetTemplate();
        updatedWidgetTemplate.setId(widgetTemplate.getId());
        updatedWidgetTemplate.setName(UPDATED_NAME);
        updatedWidgetTemplate.setDescription(UPDATED_DESCRIPTION);
        updatedWidgetTemplate.setDateCreated(UPDATED_DATE_CREATED);
        updatedWidgetTemplate.setDateLastModified(UPDATED_DATE_LAST_MODIFIED);
        updatedWidgetTemplate.setCurrentVersion(UPDATED_CURRENT_VERSION);
        updatedWidgetTemplate.setAccess(UPDATED_ACCESS);
        updatedWidgetTemplate.setShareCode(UPDATED_SHARE_CODE);
        updatedWidgetTemplate.setImagePreview(UPDATED_IMAGE_PREVIEW);
        updatedWidgetTemplate.setImagePreviewContentType(UPDATED_IMAGE_PREVIEW_CONTENT_TYPE);
        updatedWidgetTemplate.setOptions(UPDATED_OPTIONS);
        updatedWidgetTemplate.setContentUrl(UPDATED_CONTENT_URL);
        updatedWidgetTemplate.setDataLibraries(UPDATED_DATA_LIBRARIES);
        updatedWidgetTemplate.setDataHtml(UPDATED_DATA_HTML);
        updatedWidgetTemplate.setDataCss(UPDATED_DATA_CSS);
        updatedWidgetTemplate.setDataJavascript(UPDATED_DATA_JAVASCRIPT);
        updatedWidgetTemplate.setDataInputVariables(UPDATED_DATA_INPUT_VARIABLES);

        restWidgetTemplateMockMvc.perform(put("/api/widget-templates")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedWidgetTemplate)))
                .andExpect(status().isOk());

        // Validate the WidgetTemplate in the database
        List<WidgetTemplate> widgetTemplates = widgetTemplateRepository.findAll();
        assertThat(widgetTemplates).hasSize(databaseSizeBeforeUpdate);
        WidgetTemplate testWidgetTemplate = widgetTemplates.get(widgetTemplates.size() - 1);
        assertThat(testWidgetTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWidgetTemplate.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWidgetTemplate.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
        assertThat(testWidgetTemplate.getDateLastModified()).isEqualTo(UPDATED_DATE_LAST_MODIFIED);
        assertThat(testWidgetTemplate.getCurrentVersion()).isEqualTo(UPDATED_CURRENT_VERSION);
        assertThat(testWidgetTemplate.getAccess()).isEqualTo(UPDATED_ACCESS);
        assertThat(testWidgetTemplate.getShareCode()).isEqualTo(UPDATED_SHARE_CODE);
        assertThat(testWidgetTemplate.getImagePreview()).isEqualTo(UPDATED_IMAGE_PREVIEW);
        assertThat(testWidgetTemplate.getImagePreviewContentType()).isEqualTo(UPDATED_IMAGE_PREVIEW_CONTENT_TYPE);
        assertThat(testWidgetTemplate.getOptions()).isEqualTo(UPDATED_OPTIONS);
        assertThat(testWidgetTemplate.getContentUrl()).isEqualTo(UPDATED_CONTENT_URL);
        assertThat(testWidgetTemplate.getDataLibraries()).isEqualTo(UPDATED_DATA_LIBRARIES);
        assertThat(testWidgetTemplate.getDataHtml()).isEqualTo(UPDATED_DATA_HTML);
        assertThat(testWidgetTemplate.getDataCss()).isEqualTo(UPDATED_DATA_CSS);
        assertThat(testWidgetTemplate.getDataJavascript()).isEqualTo(UPDATED_DATA_JAVASCRIPT);
        assertThat(testWidgetTemplate.getDataInputVariables()).isEqualTo(UPDATED_DATA_INPUT_VARIABLES);
    }

    @Test
    @Transactional
    public void deleteWidgetTemplate() throws Exception {
        // Initialize the database
        widgetTemplateService.save(widgetTemplate);

        int databaseSizeBeforeDelete = widgetTemplateRepository.findAll().size();

        // Get the widgetTemplate
        restWidgetTemplateMockMvc.perform(delete("/api/widget-templates/{id}", widgetTemplate.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<WidgetTemplate> widgetTemplates = widgetTemplateRepository.findAll();
        assertThat(widgetTemplates).hasSize(databaseSizeBeforeDelete - 1);
    }
}

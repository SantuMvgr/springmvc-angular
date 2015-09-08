package tutorial.mvc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.BlogEntryService;
import tutorial.rest.mvc.BlogEntryController;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Created by Sandy on 8/26/2015.
 */
public class BlogEntryControllerTest {
    @InjectMocks
    private BlogEntryController controller;

    @Mock
    private BlogEntryService service;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getExistingBlogEntry() throws Exception {
        BlogEntry blogEntry = new BlogEntry();
        blogEntry.setId(1L);
        blogEntry.setTitle("This is a Test");

        when(service.findBlogEntry(1L)).thenReturn(blogEntry);

        mockMvc.perform(get("/rest/blog-entries/1"))
                .andExpect(jsonPath("$.title", is(blogEntry.getTitle())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blog-entries/1"))))
                .andExpect(status().isOk());

    }

    @Test
    public void getNotExistingBlogEntry() throws Exception {
        when(service.findBlogEntry(1L)).thenReturn(null);

        mockMvc.perform(get("/rest/blog-entries/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteExistingEntry() throws Exception {
        BlogEntry deletedBlogEntry = new BlogEntry();
        deletedBlogEntry.setId(1L);
        deletedBlogEntry.setTitle("Test title");

        when(service.deleteBlogEntry(1L)).thenReturn(deletedBlogEntry);

        mockMvc.perform(delete("/rest/blog-entries/1"))
                .andExpect(jsonPath("$.title", is(deletedBlogEntry.getTitle())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blog-entries/1"))))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNotExistingEntry() throws Exception {
        when(service.deleteBlogEntry(1L)).thenReturn(null);

        mockMvc.perform(delete("/rest/blod-entries/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateExistingEntry() throws Exception {
        BlogEntry updatedBlogEntry = new BlogEntry();
        updatedBlogEntry.setId(1L);
        updatedBlogEntry.setTitle("Test title");

        when(service.updateBlogEntry(eq(1L), any(BlogEntry.class))).thenReturn(updatedBlogEntry);

        mockMvc.perform(put("/rest/blog-entries/1")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(updatedBlogEntry.getTitle())))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blog-entries/1"))))
                .andExpect(status().isOk());
    }

    @Test
    public void updateNotExistingEntry() throws Exception {
        when(service.updateBlogEntry(eq(1L), any(BlogEntry.class))).thenReturn(null);

        mockMvc.perform(put("/rest/blod-entries/1")
                .content("{\"title\":\"Test Title\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}

package tutorial.mvc;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;
import tutorial.core.services.AccountService;
import tutorial.core.services.exceptions.AccountDoesNotExistException;
import tutorial.core.services.exceptions.AccountExistsException;
import tutorial.rest.mvc.AccountController;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.endsWith;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Sandy on 8/30/2015.
 */
public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    public void createBlogExistingAccount() throws Exception {
        Blog blog = new Blog();
        blog.setId(1L);
        blog.setTitle("Test Blog title");

        when(accountService.createBlog(eq(1L), any(Blog.class))).thenReturn(blog);

        mockMvc.perform(post("/rest/accounts/1/blogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Blog title\"}"))
                .andDo(print())
                .andExpect(jsonPath("$.title", is("Test Blog title")))
                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blogs/1"))))
                .andExpect(header().string("location", endsWith("/blogs/1")))
                .andExpect(status().isCreated());
    }

    @Test
    public void createBlogNonExistingAccount() throws Exception {
        when(accountService.createBlog(eq(1L), any(Blog.class))).thenThrow(new AccountDoesNotExistException());

        mockMvc.perform(post("/rest/accounts/1/blogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Test Blog title\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createAccountNonExistingUsername() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setName("Test user");
        createdAccount.setPassword("Test password");

        when(accountService.createAccount(any(Account.class))).thenReturn(createdAccount);

        mockMvc.perform(post("/rest/accounts")
                        .content("{\"name\":\"Test user\", \"password\":\"Test password\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", org.hamcrest.Matchers.endsWith("/rest/accounts/1")))
                .andExpect(jsonPath("$.name", is(createdAccount.getName())))
                .andExpect(status().isCreated());

    }

    @Test
    public void createAccountExistingUsername() throws Exception {
        Account createdAccount = new Account();
        createdAccount.setId(1L);
        createdAccount.setPassword("test");
        createdAccount.setName("test");

        when(accountService.createAccount(any(Account.class))).thenThrow(new AccountExistsException());

        mockMvc.perform(post("/rest/accounts")
                .content("{\"name\":\"test\",\"password\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    public void getExistingAccount() throws Exception {
        Account foundAccount = new Account();
        foundAccount.setId(1L);
        foundAccount.setPassword("test");
        foundAccount.setName("test");

        when(accountService.findAccount(1L)).thenReturn(foundAccount);

        mockMvc.perform(get("/rest/accounts/1"))
                .andDo(print())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.name", is(foundAccount.getName())))
                .andExpect(status().isOk());
    }

    @Test
    public void getNonExistingAccount() throws Exception {
        when(accountService.findAccount(1L)).thenReturn(null);

        mockMvc.perform(get("/rest/accounts/1"))
                .andExpect(status().isNotFound());
    }

}

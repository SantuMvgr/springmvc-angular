package tutorial.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.BlogService;
import tutorial.core.services.exceptions.BlogNotFoundException;
import tutorial.core.services.util.BlogEntryList;
import tutorial.core.services.util.BlogList;
import tutorial.rest.exceptions.NotFoundException;
import tutorial.rest.resources.BlogEntryListResource;
import tutorial.rest.resources.BlogEntryResource;
import tutorial.rest.resources.BlogListResource;
import tutorial.rest.resources.BlogResource;
import tutorial.rest.resources.asm.BlogEntryListResourceAsm;
import tutorial.rest.resources.asm.BlogEntryResourceAsm;
import tutorial.rest.resources.asm.BlogListResourceAsm;
import tutorial.rest.resources.asm.BlogResourceAsm;

import java.net.URI;

/**
 * Created by Sandy on 8/30/2015.
 */
@Controller
@RequestMapping("/rest/blogs")
public class BlogController {
    private BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BlogListResource> findAllBlogs() {
        BlogList blogList = blogService.findAllBlogs();
        BlogListResource res = new BlogListResourceAsm().toResource(blogList);
        return new ResponseEntity<BlogListResource>(res, HttpStatus.OK);
    }

    @RequestMapping(value = "/{blogId}", method = RequestMethod.GET)
    public ResponseEntity<BlogResource> getBlog(@PathVariable Long blogId) {
        Blog blog = blogService.findBlog(blogId);
        BlogResource blogResource = new BlogResourceAsm().toResource(blog);
        return new ResponseEntity<BlogResource>(blogResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{blogId}/blog-entries", method = RequestMethod.POST)
    public ResponseEntity<BlogEntryResource> createBlogEntry(@PathVariable Long blogId,
                                                             @RequestBody BlogEntryResource blogEntryResource) {
        BlogEntry blogEntry = null;
        try {
            blogEntry = blogService.createBlogEntry(blogId, blogEntryResource.toBlogEntry());
            BlogEntryResource res = new BlogEntryResourceAsm().toResource(blogEntry);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(URI.create(res.getLink("self").getHref()));
            return new ResponseEntity<BlogEntryResource>(res, httpHeaders, HttpStatus.CREATED);
        } catch (BlogNotFoundException exception) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(value = "{blogId}/blog-entries", method = RequestMethod.GET)
    public ResponseEntity<BlogEntryListResource> findAllBlogEntries(@PathVariable Long blogId) {
        try {
            BlogEntryList blogEntryList = blogService.findAllBlogEntries(blogId);
            BlogEntryListResource res = new BlogEntryListResourceAsm().toResource(blogEntryList);
            return new ResponseEntity<BlogEntryListResource>(res, HttpStatus.OK);
        } catch (BlogNotFoundException exception) {
            throw new NotFoundException(exception);
        }

    }
}

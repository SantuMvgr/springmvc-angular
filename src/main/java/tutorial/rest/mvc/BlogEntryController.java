package tutorial.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.BlogEntryService;
import tutorial.rest.resources.BlogEntryResource;
import tutorial.rest.resources.asm.BlogEntryResourceAsm;

/**
 * Created by Sandy on 8/26/2015.
 */

@Controller
@RequestMapping(value="/rest/blog-entries")
public class BlogEntryController {
    private BlogEntryService blogEntryService;

    @Autowired
    public BlogEntryController(BlogEntryService service) {
        this.blogEntryService = service;
    }

    @RequestMapping(value="/{blogEntryId}",
                method=RequestMethod.GET)
    public ResponseEntity<BlogEntryResource> getBlogEntry(@PathVariable Long blogEntryId) {
        BlogEntry blogEntry = blogEntryService.findBlogEntry(blogEntryId);
        if(blogEntry != null) {
            BlogEntryResource res = new BlogEntryResourceAsm().toResource(blogEntry);
            return new ResponseEntity<BlogEntryResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{blogEntryId}",
            method=RequestMethod.DELETE)
    public ResponseEntity<BlogEntryResource> deleteBlogEntry(@PathVariable Long blogEntryId) {
        BlogEntry blogEntry = blogEntryService.deleteBlogEntry(blogEntryId);
        if(blogEntry != null) {
            BlogEntryResource res = new BlogEntryResourceAsm().toResource(blogEntry);
            return new ResponseEntity<BlogEntryResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{blogEntryId}",
        method=RequestMethod.PUT)
    public ResponseEntity<BlogEntryResource> updateBlogEntry(@PathVariable Long blogEntryId,
                                                             @RequestBody BlogEntryResource sentBlogEntry) {
        BlogEntry blogEntry = blogEntryService.updateBlogEntry(blogEntryId, sentBlogEntry.toBlogEntry());
        if(blogEntry != null) {
            BlogEntryResource res = new BlogEntryResourceAsm().toResource(blogEntry);
            return new ResponseEntity<BlogEntryResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
        }
    }

}

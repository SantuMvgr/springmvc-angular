package tutorial.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import tutorial.core.services.util.BlogList;
import tutorial.rest.mvc.BlogController;
import tutorial.rest.resources.BlogListResource;

/**
 * Created by Sandy on 8/30/2015.
 */
public class BlogListResourceAsm extends ResourceAssemblerSupport<BlogList, BlogListResource> {
    public BlogListResourceAsm()
    {
        super(BlogController.class, BlogListResource.class);
    }

    @Override
    public BlogListResource toResource(BlogList blogList) {
        BlogListResource res = new BlogListResource();
        res.setBlogs(new BlogResourceAsm().toResources(blogList.getBlogs()));
        return res;
    }
}

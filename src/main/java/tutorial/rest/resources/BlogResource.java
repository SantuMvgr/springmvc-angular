package tutorial.rest.resources;

import org.springframework.hateoas.ResourceSupport;
import tutorial.core.entities.Blog;

/**
 * Created by Sandy on 8/29/2015.
 */
public class BlogResource extends ResourceSupport {
    private String title;
    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Blog toBlog() {
        Blog blog = new Blog();
        blog.setTitle(title);
        return blog;
    }
}

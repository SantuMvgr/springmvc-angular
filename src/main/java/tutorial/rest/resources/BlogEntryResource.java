package tutorial.rest.resources;

import org.springframework.hateoas.ResourceSupport;
import tutorial.core.entities.BlogEntry;

/**
 * Created by Sandy on 8/27/2015.
 */
public class BlogEntryResource extends ResourceSupport {
    private String title;
    private String content;
    private Long rid;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public BlogEntry toBlogEntry() {
        BlogEntry entry = new BlogEntry();
        entry.setTitle(title);
        entry.setContent(content);
        return entry;
    }
}

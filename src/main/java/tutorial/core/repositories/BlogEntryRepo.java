package tutorial.core.repositories;

import tutorial.core.entities.BlogEntry;

import java.util.List;

/**
 * Created by Sandy on 9/6/2015.
 */
public interface BlogEntryRepo {
    public BlogEntry findBlogEntry(Long id);
    public BlogEntry deleteBlogEntry(Long id);
    public BlogEntry updateBlogEntry(Long id, BlogEntry data);

    public BlogEntry createBlogEntry(BlogEntry data);
    public List<BlogEntry> findByBlogId(Long blogId);
}

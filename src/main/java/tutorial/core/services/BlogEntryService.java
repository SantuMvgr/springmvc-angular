package tutorial.core.services;

import tutorial.core.entities.BlogEntry;

/**
 * Created by Sandy on 8/27/2015.
 */
public interface BlogEntryService {
    public BlogEntry findBlogEntry(Long id);
    public BlogEntry deleteBlogEntry(Long id);
    public BlogEntry updateBlogEntry(Long id, BlogEntry data);
}

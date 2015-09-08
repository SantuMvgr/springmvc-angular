package tutorial.core.services;

import tutorial.core.entities.Blog;
import tutorial.core.entities.BlogEntry;
import tutorial.core.services.util.BlogEntryList;
import tutorial.core.services.util.BlogList;

/**
 * Created by Sandy on 8/29/2015.
 */
public interface BlogService {
    public BlogEntry createBlogEntry(Long blogId, BlogEntry data);
    public BlogList findAllBlogs();
    public BlogEntryList findAllBlogEntries(Long blogId); // findBlog all associated blog entries
    public Blog findBlog(Long eq);
}

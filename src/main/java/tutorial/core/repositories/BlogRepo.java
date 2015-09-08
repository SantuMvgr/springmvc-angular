package tutorial.core.repositories;

import tutorial.core.entities.Blog;
import java.util.List;

/**
 * Created by Sandy on 9/6/2015.
 */
public interface BlogRepo {
    public Blog createBlog(Blog data);
    public List<Blog> findAllBlogs();
    public Blog findBlog(Long id);
    public Blog findBlogByTitle(String blogTitle);
    public List<Blog> findBlogsByAccount(Long accountId);
}

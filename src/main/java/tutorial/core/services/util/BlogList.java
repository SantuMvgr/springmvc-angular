package tutorial.core.services.util;

import tutorial.core.entities.Blog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandy on 8/29/2015.
 */
public class BlogList {
    private List<Blog> blogs = new ArrayList<Blog>();

    public BlogList(List list) {
        blogs = list;
    }
    public List<Blog> getBlogs() {

        return blogs;
    }
    public void setBlogs(List<Blog> blogs) {

        this.blogs = blogs;
    }
}

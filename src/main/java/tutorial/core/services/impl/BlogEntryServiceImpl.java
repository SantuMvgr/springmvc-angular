package tutorial.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tutorial.core.entities.BlogEntry;
import tutorial.core.repositories.BlogEntryRepo;
import tutorial.core.services.BlogEntryService;

import javax.transaction.Transactional;

/**
 * Created by Sandy on 9/7/2015.
 */
@Service
@Transactional
public class BlogEntryServiceImpl implements BlogEntryService {

    @Autowired
    private BlogEntryRepo blogEntryRepo;

    @Override
    public BlogEntry findBlogEntry(Long id) {
        return blogEntryRepo.findBlogEntry(id);
    }

    @Override
    public BlogEntry deleteBlogEntry(Long id) {
        return blogEntryRepo.deleteBlogEntry(id);
    }

    @Override
    public BlogEntry updateBlogEntry(Long id, BlogEntry data) {
        return blogEntryRepo.updateBlogEntry(id, data);
    }
}

package tutorial.core.services;

import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;
import tutorial.core.services.util.AccountList;
import tutorial.core.services.util.BlogList;

/**
 * Created by Sandy on 8/29/2015.
 */
public interface AccountService {
    public Account findAccount(Long id);
    public Account createAccount(Account data);
    public Blog createBlog(Long accountId, Blog data);
    public BlogList findBlogsByAccount(Long accountId);
    public AccountList findAllAccounts();
    public Account findByAccountName(String name);
}

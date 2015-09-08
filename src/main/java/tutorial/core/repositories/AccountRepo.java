package tutorial.core.repositories;

import tutorial.core.entities.Account;
import tutorial.core.entities.Blog;

import java.util.List;

/**
 * Created by Sandy on 8/31/2015.
 */
public interface AccountRepo {
    public List<Account> findAllAccounts();
    public Account findAccount(Long id);
    public Account findAccountByName(String name);
    public Account createAccount(Account data);
}

package tutorial.core.services.util;

import tutorial.core.entities.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandy on 9/7/2015.
 */
public class AccountList {
    private List<Account> accounts = new ArrayList<Account>();

    public AccountList(List<Account> list) {
        this.accounts = list;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}

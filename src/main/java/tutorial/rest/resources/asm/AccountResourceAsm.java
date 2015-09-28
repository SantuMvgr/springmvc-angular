package tutorial.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import tutorial.core.entities.Account;
import tutorial.rest.mvc.AccountController;
import tutorial.rest.resources.AccountResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by Sandy on 8/29/2015.
 */
public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource> {
    public AccountResourceAsm() {
        super(AccountController.class, AccountResource.class);
    }

    @Override
    public AccountResource toResource(Account account) {
        AccountResource accountResource = new AccountResource();
        accountResource.setName(account.getName());
        accountResource.setPassword(account.getPassword());
        accountResource.setRid(account.getId());
        accountResource.add(linkTo(methodOn(AccountController.class).getAccount(account.getId())).withSelfRel());
        accountResource.add(linkTo(methodOn(AccountController.class).findAllBlogs(account.getId())).withRel("blogs"));
        return accountResource;
    }
}

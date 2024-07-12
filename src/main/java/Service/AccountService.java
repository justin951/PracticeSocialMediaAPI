package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // TODO #1 COMPLETE: account creation
    public Account createAccount(Account account) {
        return accountDAO.createAccount(account);
    }

    // TODO #2 COMPLETE: account login
    public Account loginAccount(Account account) {
        return accountDAO.loginAccount(account);
    }

}

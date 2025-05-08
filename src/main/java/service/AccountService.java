package service;

import dao.AccountDao;
import model.Account;

import java.sql.Connection;
import java.util.List;

public class AccountService {
    private final AccountDao accountDao;
    private final Connection conn;

    public AccountService(Connection conn) {
        this.conn = conn;
        this.accountDao = new AccountDao();
    }

    public List<Account> getAccounts(int id) {
        return accountDao.getAccounts(conn, id);
    }

    public Account getAccount(int id) {
        return accountDao.getAccount(conn, id);
    }
}

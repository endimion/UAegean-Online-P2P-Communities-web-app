/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teem.loginapp.utils;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teem.loginapp.model.dmo.AccountBuilder;
import teem.loginapp.service.SwellrtAccountService;

/**
 *
 * @author nikos
 */
public class AccountUtils {

    private static final Logger log = LoggerFactory.getLogger(AccountUtils.class);

    public static AccountBuilder.SwellrtAccountMngDMO updateAccountId(AccountBuilder.SwellrtAccountMngDMO account, SwellrtAccountService accountServ) {

        log.info("QUERYING!!! by:::=> " + account.getEngCurrentGivenName() + " " + account.getEngCurrentFamilyName());

        List<AccountBuilder.SwellrtAccountMngDMO> matches = accountServ.findByFirstAndLast(account.getEngCurrentGivenName(), account.getEngCurrentFamilyName());
        int matchesInc = matches.size() + 1;
        log.info("MATCH!!! " + matchesInc);

        boolean eIDFound = false;

        for (AccountBuilder.SwellrtAccountMngDMO acc : matches) {
            if (acc.getEid().equals(account.getEid())) {
                log.info("Match found " + acc.getEid());
                eIDFound = true;
            }
        }

        if ((!eIDFound) && matches.size() > 0) {
            log.info("will update teem id " + matchesInc);
            String name = account.getId().split("@")[0];
            account.setId(name + matchesInc + "@local.net");
        }
        return account;
    }
}

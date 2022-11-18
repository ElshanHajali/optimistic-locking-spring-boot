package az.company.accounts.controller;

import az.company.accounts.dao.entity.AccountsEntity;
import az.company.accounts.dto.request.AccountsCriteriaRequest;
import az.company.accounts.dto.request.AccountsRequest;
import az.company.accounts.dto.response.AccountsResponse;
import az.company.accounts.dto.response.PageableAccountsResponse;
import az.company.accounts.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("v1/accounts")
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;

    ///// Fetch /////
    @GetMapping
    public PageableAccountsResponse getAccounts(
            int page, int count,
            AccountsCriteriaRequest criteriaRequest) {
        return accountsService.getAccounts(page, count, criteriaRequest);
    }

    @GetMapping("{id}")
    public AccountsResponse getAccount(@PathVariable Long id) {
        return accountsService.getAccount(id);
    }

    ///// Management /////
    @PostMapping
    @ResponseStatus(CREATED)
    public void saveAccount(@RequestBody AccountsRequest request) {
        accountsService.saveAccount(request);
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateAccount(@PathVariable Long id,
                              @RequestBody AccountsRequest request) {
        accountsService.updateAccount(id, request);
    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void makeTransaction(@PathVariable Long id, @RequestParam BigDecimal value) {
        accountsService.makeTransaction(id, value);
    }


    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountsService.deleteAccount(id);
    }
}

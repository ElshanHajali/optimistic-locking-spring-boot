package az.company.accounts.service;

import az.company.accounts.dao.entity.AccountsEntity;
import az.company.accounts.dao.repository.AccountsRepository;
import az.company.accounts.dto.request.AccountsCriteriaRequest;
import az.company.accounts.dto.request.AccountsRequest;
import az.company.accounts.dto.response.AccountsResponse;
import az.company.accounts.dto.response.PageableAccountsResponse;
import az.company.accounts.exception.NotFoundException;
import az.company.accounts.exception.constant.NotFoundConstants;
import az.company.accounts.service.specification.AccountsSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static az.company.accounts.mapper.AccountsMapper.MAP;
import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountsService {
    private final AccountsRepository accountsRepository;

    ////// Fetch //////
    public PageableAccountsResponse getAccounts(int page, int count, AccountsCriteriaRequest criteriaRequest) {
        log.info("ActionLog.getAccounts.start");
        var pageable =
                PageRequest.of(page, count, Sort.by(ASC, "balance"));

        var pageableAccount =
                accountsRepository.findAll(
                        new AccountsSpecification(criteriaRequest), pageable);

        var accounts = pageableAccount.getContent()
                .stream().map(MAP::entityToResponse)
                .collect(Collectors.toList());

        var pageableAccountResponse = MAP.responseToPageableResponse(accounts, pageableAccount);
        log.info("ActionLog.getAccounts.success");
        return pageableAccountResponse;
    }

    public AccountsResponse getAccount(Long id) {
        log.info("ActionLog.getAccount.start");
        return MAP.entityToResponse(fetchAccountById(id));
    }

    /////// Management ///////

    @Async
    public void saveAccount(AccountsRequest request) {
        log.info("ActionLog.saveAccount.start");
//        AccountsEntity account = MAP.requestToEntity(request);
//        account.setAccountType(account.getAccountType().concat(" Account"));

        String[] accountTypes = {"None", "Real", "Personal", "Nominal"};

        for (int i = 1; i <= 1000; i++) {
            AccountsEntity entity = AccountsEntity.builder()
                    .number(accountTypes[(int) (Math.ceil(Math.random() * 3))])
                    .balance(BigDecimal.valueOf(Math.random() * 20000 + Math.random()))
                    .build();

            accountsRepository.save(entity);
        }
//        accountsRepository.save(account);
        log.info("ActionLog.saveAccounts.success");
    }

    public void updateAccount(Long id, AccountsRequest request) {
        log.info("ActionLog.updateAccount.start");
        var accountFromDB = fetchAccountById(id);

        accountFromDB.setNumber(request.getNumber());
        accountFromDB.setBalance(request.getBalance());

        accountsRepository.save(accountFromDB);
        log.info("ActionLog.updateAccount.start");
    }

    public void makeTransaction(Long id, BigDecimal value) {
        AccountsEntity account = fetchAccountById(id);
        BigDecimal balance = account.getBalance();
        account.setBalance(account.getBalance().subtract(value)); // 2253.81 - 10
        accountsRepository.save(account);
        log.info("ActionLog.makeTransaction.start balance: {}", balance);
        log.info("ActionLog.makeTransaction.success balance: {}", account.getBalance());

//        account.setBalance(account.getBalance().add(BigDecimal.valueOf(1000))); 2253.81 + 1000 cancelled!
//        accountsRepository.save(account);
//        log.info("ActionLog.makeTransaction.success balance: {}", account.getBalance());
    }

    @PostConstruct
    public void makeTransactionFakeRequests() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Long id = 2L;

        executor.execute(() -> {
            makeTransaction(id, BigDecimal.valueOf(15));
            log.info("Transaction -15 applied");
        });

        executor.execute(() -> {
            makeTransaction(id, BigDecimal.valueOf(200));
            log.info("Transaction -200 applied");
        });

        executor.execute(() -> {
            makeTransaction(id, BigDecimal.valueOf(500));
            log.info("Transaction -500 applied");
        });

        executor.execute(() -> {
            makeTransaction(id, BigDecimal.valueOf(1000));
            log.info("Transaction -1000 applied");
        });

        executor.execute(() -> {
            makeTransaction(id, BigDecimal.valueOf(2300));
            log.info("Transaction -2300 applied");
        });
    }

    public void deleteAccount(Long id) {
        log.info("ActionLog.deleteAccount.start");

        accountsRepository.deleteById(id);

        log.info("ActionLog.deleteAccount.success");
    }

    private AccountsEntity fetchAccountById(Long id) {
        return accountsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(
                                NotFoundConstants.NOT_FOUND_EXCEPTION_MESSAGE, id),
                        NotFoundConstants.NOT_FOUND_EXCEPTION_CODE
                ));
    }
}

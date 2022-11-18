package az.company.accounts.dao.repository;

import az.company.accounts.dao.entity.AccountsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountsRepository extends
        JpaRepository<AccountsEntity, Long>,
        JpaSpecificationExecutor<AccountsEntity> {

}

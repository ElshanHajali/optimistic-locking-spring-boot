package az.company.accounts.mapper;

import az.company.accounts.dao.entity.AccountsEntity;
import az.company.accounts.dto.request.AccountsRequest;
import az.company.accounts.dto.response.AccountsResponse;
import az.company.accounts.dto.response.PageableAccountsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface AccountsMapper {
    AccountsMapper MAP = Mappers.getMapper(AccountsMapper.class);

    default AccountsEntity requestToEntity(AccountsRequest request) {
        return AccountsEntity.builder()
                .balance(request.getBalance())
                .number(request.getNumber())
                .build();
    }

    default AccountsResponse entityToResponse(AccountsEntity account) {
        return AccountsResponse.builder()
                .id(account.getId())
                .balance(account.getBalance())
                .number(account.getNumber())
                .version(account.getVersion())
                .build();
    }

    default PageableAccountsResponse responseToPageableResponse(
            List<AccountsResponse> accounts, Page<AccountsEntity> pageableAccount) {
        return PageableAccountsResponse.builder()
                .totalPages(pageableAccount.getTotalPages())
                .totalElements(pageableAccount.getTotalElements())
                .accounts(accounts)
                .hasNextPage(pageableAccount.hasNext())
                .build();
    }

}

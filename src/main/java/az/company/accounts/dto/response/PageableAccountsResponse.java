package az.company.accounts.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageableAccountsResponse {
    private List<AccountsResponse> accounts;
    private long totalElements;
    private int totalPages;
    private boolean hasNextPage;
}

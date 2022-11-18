package az.company.accounts.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountsCriteriaRequest {
    private BigDecimal balanceFrom;
    private BigDecimal balanceTo;
    private String number;
}

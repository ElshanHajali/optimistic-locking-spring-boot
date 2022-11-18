package az.company.accounts.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsResponse {

    private Long id;

    private String number;
    private BigDecimal balance;

    private Long version;

}

package az.company.accounts.dto.request;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountsRequest {

    private String number;
    private BigDecimal balance;

//{
//    "number" : "",
//    "balance" :
//}
}

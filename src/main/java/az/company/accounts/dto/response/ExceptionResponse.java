package az.company.accounts.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private String code;
    private String message;
}

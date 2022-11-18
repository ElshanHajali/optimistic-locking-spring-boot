package az.company.accounts.service.specification;

import az.company.accounts.dao.entity.AccountsEntity;
import az.company.accounts.dto.request.AccountsCriteriaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class AccountsSpecification implements Specification<AccountsEntity> {
    private final AccountsCriteriaRequest criteriaRequest;

    private static final String BALANCE = "balance";
    private static final String NUMBER = "number";

    @Override
    public Predicate toPredicate(Root<AccountsEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (criteriaRequest != null) {
            if (criteriaRequest.getBalanceFrom() != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get(BALANCE), criteriaRequest.getBalanceFrom()
                        )
                );
            }

            if (criteriaRequest.getBalanceTo() != null) {
                predicates.add(
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get(BALANCE), criteriaRequest.getBalanceTo()
                        )
                );
            }

            if (StringUtils.hasText(criteriaRequest.getNumber())) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get(NUMBER),
                                "%" +
                                        criteriaRequest.getNumber().replace(
                                                criteriaRequest.getNumber().charAt(0),
                                                Character.toUpperCase(
                                                        criteriaRequest
                                                                .getNumber()
                                                                .charAt(0)
                                                )
                                        )
                                + "%"
                        )
                );
            }
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

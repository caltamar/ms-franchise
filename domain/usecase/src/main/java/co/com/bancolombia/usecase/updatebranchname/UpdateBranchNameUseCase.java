package co.com.bancolombia.usecase.updatebranchname;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateBranchNameUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(
            String franchiseId,
            String branchId,
            String name) {

        return repository.findById(franchiseId)
                .switchIfEmpty(
                        Mono.error(
                                new BusinessException(
                                        BusinessErrorMessage.FRANCHISE_NOT_FOUND
                                )
                        )
                )
                .map(franchise -> {

                    Branch branch = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() ->
                                    new BusinessException(
                                            BusinessErrorMessage.BRANCH_NOT_FOUND
                                    ));

                    branch.setName(name);

                    return franchise;
                })
                .flatMap(repository::save);
    }
}

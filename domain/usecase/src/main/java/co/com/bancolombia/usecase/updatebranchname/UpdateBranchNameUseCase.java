package co.com.bancolombia.usecase.updatebranchname;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
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
                .flatMap(franchise ->
                        Flux.fromIterable(franchise.getBranches())
                                .filter(branch -> branch.getId().equals(branchId))
                                .next()
                                .switchIfEmpty(
                                        Mono.error(
                                                new BusinessException(
                                                        BusinessErrorMessage.BRANCH_NOT_FOUND
                                                )
                                        )
                                )
                                .map(branch -> {
                                    branch.setName(name);
                                    return franchise;
                                })
                )
                .flatMap(repository::save);
    }
}

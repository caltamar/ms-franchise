package co.com.bancolombia.usecase.updatefranchisename;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateFranchiseNameUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(
            String franchiseId,
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
                    franchise.setName(name);
                    return franchise;
                })
                .flatMap(repository::save);
    }
}

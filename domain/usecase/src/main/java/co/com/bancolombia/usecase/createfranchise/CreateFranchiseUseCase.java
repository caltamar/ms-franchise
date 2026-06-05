package co.com.bancolombia.usecase.createfranchise;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateFranchiseUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(Franchise franchise) {

        return repository.existsByName(franchise.getName())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(
                                new BusinessException(
                                        BusinessErrorMessage.FRANCHISE_ALREADY_EXISTS
                                )
                        );
                    }
                    franchise.setId(UUID.randomUUID().toString());
                    if (franchise.getBranches() == null) {
                        franchise.setBranches(new ArrayList<>());
                    }
                    return repository.save(franchise);
                });
    }
}

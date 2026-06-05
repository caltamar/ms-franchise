package co.com.bancolombia.usecase.addbranch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
public class AddBranchUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(String franchiseId, Branch branch) {

        return repository.findById(franchiseId)
                .switchIfEmpty(
                        Mono.error(
                                new BusinessException(
                                        BusinessErrorMessage.FRANCHISE_NOT_FOUND
                                )
                        )
                )
                .map(franchise -> {
                    branch.setId(UUID.randomUUID().toString());

                    if (branch.getProducts() == null) {
                        branch.setProducts(new ArrayList<>());
                    }
                    if (franchise.getBranches() == null) {
                        franchise.setBranches(new ArrayList<>());
                    }
                    franchise.getBranches().add(branch);
                    return franchise;
                })
                .flatMap(repository::save);
    }
}

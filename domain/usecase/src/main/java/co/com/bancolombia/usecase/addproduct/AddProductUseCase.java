package co.com.bancolombia.usecase.addproduct;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@RequiredArgsConstructor
public class AddProductUseCase {

    private final FranchiseRepository repository;

    public Mono<Franchise> execute(
            String franchiseId,
            String branchId,
            Product product) {

        return repository.findById(franchiseId)
                .switchIfEmpty(Mono.error(
                        new BusinessException(
                                BusinessErrorMessage.FRANCHISE_NOT_FOUND
                        )
                ))
                .map(franchise -> {

                    Branch branch = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() ->
                                    new BusinessException(
                                            BusinessErrorMessage.BRANCH_NOT_FOUND
                                    ));

                    product.setId(UUID.randomUUID().toString());

                    if (branch.getProducts() == null) {
                        branch.setProducts(new ArrayList<>());
                    }

                    branch.getProducts().add(product);

                    return franchise;
                })
                .flatMap(repository::save);
    }
}

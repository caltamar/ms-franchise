package co.com.bancolombia.usecase.gettopstockproducts;

import co.com.bancolombia.model.exception.BusinessException;
import co.com.bancolombia.model.exception.message.BusinessErrorMessage;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.TopStockProduct;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class GetTopStockProductsUseCase {

    private final FranchiseRepository repository;

    public Flux<TopStockProduct> execute(String franchiseId) {

        return repository.findById(franchiseId)
                .switchIfEmpty(
                        Mono.error(
                                new BusinessException(
                                        BusinessErrorMessage.FRANCHISE_NOT_FOUND)))
                .flatMapIterable(Franchise::getBranches)
                .flatMap(branch ->
                        Flux.fromIterable(branch.getProducts())
                                .reduce((p1, p2) ->
                                        p1.getStock() >= p2.getStock() ? p1 : p2)
                                .map(product ->
                                        TopStockProduct.builder()
                                                .branchName(branch.getName())
                                                .productName(product.getName())
                                                .stock(product.getStock())
                                                .build()
                                )
                );
    }
}

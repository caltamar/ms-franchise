package co.com.bancolombia.usecase.addproduct;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
import co.com.bancolombia.model.product.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AddProductUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private AddProductUseCase useCase;

    @Test
    void shouldAddProductSuccessfully() {

        Branch branch = Branch.builder()
                .id("B1")
                .name("Sucursal Cali")
                .products(new ArrayList<>())
                .build();

        Franchise franchise = Franchise.builder()
                .id("F1")
                .branches(List.of(branch))
                .build();

        Product product = Product.builder()
                .name("Helado")
                .stock(10)
                .build();

        when(repository.findById("F1"))
                .thenReturn(Mono.just(franchise));

        when(repository.save(any(Franchise.class)))
                .thenAnswer(invocation ->
                        Mono.just(invocation.getArgument(0)));

        StepVerifier.create(
                        useCase.execute(
                                "F1",
                                "B1",
                                product))
                .assertNext(result -> {

                    Product saved =
                            result.getBranches()
                                    .getFirst()
                                    .getProducts()
                                    .getFirst();

                    assertNotNull(saved.getId());

                    assertEquals(
                            "Helado",
                            saved.getName());

                    assertEquals(
                            10,
                            saved.getStock());
                })
                .verifyComplete();
    }
}

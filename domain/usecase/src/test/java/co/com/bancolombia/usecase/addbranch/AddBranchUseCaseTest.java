package co.com.bancolombia.usecase.addbranch;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.franchise.gateways.FranchiseRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AddBranchUseCaseTest {

    @Mock
    private FranchiseRepository repository;

    @InjectMocks
    private AddBranchUseCase useCase;

    @Test
    void shouldAddBranchSuccessfully() {

        Franchise franchise = Franchise.builder()
                .id("F1")
                .name("Burger King")
                .branches(new ArrayList<>())
                .build();

        Branch branch = Branch.builder()
                .name("Sucursal Cali")
                .build();

        when(repository.findById("F1"))
                .thenReturn(Mono.just(franchise));

        when(repository.save(any(Franchise.class)))
                .thenAnswer(invocation ->
                        Mono.just(invocation.getArgument(0)));

        StepVerifier.create(
                        useCase.execute("F1", branch))
                .assertNext(result -> {
                    assertEquals(1,
                            result.getBranches().size());

                    assertEquals(
                            "Sucursal Cali",
                            result.getBranches()
                                    .getFirst()
                                    .getName());

                    assertNotNull(
                            result.getBranches()
                                    .getFirst()
                                    .getId());
                })
                .verifyComplete();
    }
}

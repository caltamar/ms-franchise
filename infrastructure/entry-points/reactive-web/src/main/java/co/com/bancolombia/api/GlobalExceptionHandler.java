package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.ErrorResponse;
import co.com.bancolombia.model.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(
            ServerWebExchange exchange,
            Throwable ex) {

        if (ex instanceof BusinessException businessException) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.NOT_FOUND);

            exchange.getResponse()
                    .getHeaders()
                    .setContentType(MediaType.APPLICATION_JSON);

            String response = String.format(
                    """
                    {
                      "code": "%s",
                      "message": "%s"
                    }
                    """,
                    businessException.getError().getCode(),
                    businessException.getError().getMessage()
            );

            return exchange.getResponse()
                    .writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(
                                                    response.getBytes(
                                                            StandardCharsets.UTF_8
                                                    )
                                            )
                            )
                    );
        }

        log.error("Unexpected error", ex);

        exchange.getResponse()
                .setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);

        exchange.getResponse()
                .getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        String response = """
                {
                  "code": "INTERNAL_ERROR",
                  "message": "Unexpected error"
                }
                """;

        return exchange.getResponse()
                .writeWith(
                        Mono.just(
                                exchange.getResponse()
                                        .bufferFactory()
                                        .wrap(
                                                response.getBytes(
                                                        StandardCharsets.UTF_8
                                                )
                                        )
                        )
                );
    }
}

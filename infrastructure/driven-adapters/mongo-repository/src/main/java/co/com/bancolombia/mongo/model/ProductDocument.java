package co.com.bancolombia.mongo.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductDocument {

    private String id;

    private String name;

    private Integer stock;
}

package co.com.bancolombia.mongo.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BranchDocument {

    private String id;

    private String name;

    private List<ProductDocument> products;
}

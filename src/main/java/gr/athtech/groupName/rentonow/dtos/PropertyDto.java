package gr.athtech.groupName.rentonow.dtos;

import java.math.BigDecimal;

import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.models.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PropertyDto {
    private Long id;
    private BigDecimal price;
    private String details;
    private String telephone;
    private String email;
    private String address;
    private String host;

    public static PropertyDto fromProperty(Property property) {
        var builder = PropertyDto.builder();
        User host = property.getHost();

        if (host != null) {
            builder.host(host.getName());
        }

        return builder.id(property.getId()).email(property.getEmail()).details(property.getDetails())
                .address(property.getAddress()).price(property.getPrice()).build();
    }
}

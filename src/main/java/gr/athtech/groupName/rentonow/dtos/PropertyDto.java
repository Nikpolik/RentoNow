package gr.athtech.groupName.rentonow.dtos;

import java.math.BigDecimal;
import java.util.List;

import gr.athtech.groupName.rentonow.models.Image;
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
    private Long host;
    private List<Image> images;

    public static PropertyDto fromProperty(Property property) {
        var builder = PropertyDto.builder();
        User host = property.getHost();

        if (host != null) {
            builder.host(host.getId());
        }

        return builder.id(property.getId()).email(property.getEmail()).details(property.getDetails())
                .address(property.getAddress()).price(property.getPrice()).telephone(property.getTelephone()).images(property.getImages()).build();
    }
}

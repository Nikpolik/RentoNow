package gr.athtech.groupName.rentonow.dto;

import java.math.BigDecimal;

import gr.athtech.groupName.rentonow.model.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreatePropertyDto {
    private BigDecimal price;
    private Float longitude;
    private Float latitude;
    private String details;
    private String telephone;
    private String email;

    public static CreatePropertyDto toDto(Property property) {
        return CreatePropertyDto.builder()
        .longitude(property.getLongitude())
        .latitude(property.getLatitude())
        .price(property.getPrice())
        .details(property.getDetails())
        .telephone(property.getTelephone())
        .details(property.getDetails())
        .email(property.getEmail())
        .build();
    }

    public static Property toProperty(CreatePropertyDto propertyDto) {
        Property property = new Property();
        property.setLatitude(propertyDto.getLatitude());
        property.setLongitude(propertyDto.getLongitude());
        property.setTelephone(propertyDto.getTelephone());
        property.setDetails(propertyDto.getDetails());
        property.setEmail(propertyDto.getEmail());
        return property;
    }
}

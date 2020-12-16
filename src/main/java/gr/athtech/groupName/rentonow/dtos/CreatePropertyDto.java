package gr.athtech.groupName.rentonow.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import gr.athtech.groupName.rentonow.models.Property;

@Getter
@Builder
@AllArgsConstructor
public class CreatePropertyDto {
    private BigDecimal price;
    private String details;
    private String telephone;
    private String email;
    private String address;
    private Double longitude;
    private Double latitude;
    
    public static Property toProperty(CreatePropertyDto createDto) {
        Property property = new Property();
        Point location = coordinatesToPoint(createDto.getLongitude(), createDto.getLatitude()); 
        property.setPrice(createDto.getPrice());
        property.setAddress(createDto.getAddress());
        property.setLocation(location);
        property.setTelephone(createDto.getTelephone());
        property.setDetails(createDto.getDetails());
        property.setEmail(createDto.getEmail());
        return property;
    }

    private static Point coordinatesToPoint(Double longitude, Double latitude) {
        Coordinate coordinate = new Coordinate(longitude, latitude);
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(coordinate);
    }
    
}

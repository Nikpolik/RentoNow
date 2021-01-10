package gr.athtech.groupName.rentonow.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import gr.athtech.groupName.rentonow.models.Property;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class CreatePropertyDto {

    private BigDecimal price;
    private String details;
    private String telephone;
    private String email;
    private String address;
    private Double longitude;
    private Double latitude;
    private Long host;

    public static Property toProperty(CreatePropertyDto createDto) {
        Property property = new Property();
        property.setPrice(createDto.getPrice());
        property.setAddress(createDto.getAddress());
        property.setLocation(createDto.getLocation());
        property.setTelephone(createDto.getTelephone());
        property.setDetails(createDto.getDetails());
        property.setEmail(createDto.getEmail());
        return property;
    }

    public Point getLocation(){
        if(this.latitude == null || this.longitude == null) {
            return null;
        }
        return coordinatesToPoint(this.longitude, this.latitude);
    }

    private static Point coordinatesToPoint(Double longitude, Double latitude) {
        Coordinate coordinate = new Coordinate(longitude, latitude);
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(coordinate);
    }
}

package gr.athtech.groupName.rentonow.dtos;

import java.math.BigDecimal;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import gr.athtech.groupName.rentonow.models.Property;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
public class UpdatePropertyDto {

  private Long id;
  private BigDecimal price;
  private String details;
  private String telephone;
  private String email;
  private String address;
  private Double longitude;
  private Double latitude;
  private Long host;

  public static Property toProperty(UpdatePropertyDto updateDto) {
    Property property = new Property();
    property.setPrice(updateDto.getPrice());
    property.setAddress(updateDto.getAddress());
    property.setLocation(updateDto.getLocation());
    property.setTelephone(updateDto.getTelephone());
    property.setDetails(updateDto.getDetails());
    property.setEmail(updateDto.getEmail());
    property.setId(updateDto.getId());
    return property;
  }

  public Point getLocation() {
    if (this.latitude == null || this.longitude == null) {
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

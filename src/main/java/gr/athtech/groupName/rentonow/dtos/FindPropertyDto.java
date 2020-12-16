package gr.athtech.groupName.rentonow.dtos;

import java.math.BigDecimal;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.util.GeometricShapeFactory;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindPropertyDto {
    BigDecimal minPrice;
    BigDecimal maxPrice;
    Double latitude;
    Double longitude;
    Double distance;

    // This is a very naive way of generating 
    // since it doesn't take into account the curviture of the earth
    // TODO: Read about Spherical geometry
    public Geometry generateSearchCircle() {
        if (latitude == null || longitude == null || distance == null) {
            return null;
        }

        GeometricShapeFactory factory = new GeometricShapeFactory();
         // Length in meters of 1° of latitude = always 111.32 km
        factory.setWidth(distance / 111320d);
        // Length in meters of 1° of longitude = 40075 km * cos( latitude ) / 360
        factory.setHeight(distance / (40075000 * Math.cos(Math.toRadians(latitude)) / 360));
        Coordinate center = new Coordinate(longitude, latitude);
        factory.setCentre(center);
        return factory.createCircle();
    }

    @Override
    public String toString() {
        return "Search params -> minPrice: " + minPrice + " maxPrice: " + maxPrice + " distance: " + distance;
    }
}

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

    // This is a very naive way of generating a circle
    // since it doesn't take into account the curviture of the earth
    // based on https://stackoverflow.com/questions/36481651/how-do-i-create-a-circle-with-latitude-longitude-and-radius-with-geotools/36528805
    // these values are based on https://en.wikipedia.org/wiki/Decimal_degrees
    // TODO: Read about Spherical geometry
    public Geometry generateSearchCircle() {
        if (latitude == null || longitude == null || distance == null) {
            return null;
        }

        GeometricShapeFactory factory = new GeometricShapeFactory();

        // Transform kilometers to lat + long difference
        // The earth has a circumference of 40075000
        // Therefore each degree of change  is 40075000/360 -> 111320d in kilometers
        factory.setWidth(distance * 2 / 111320d);
        // For latitude the degree of change is not constant but dependents on our current location
        // that is one of the reasons of our innacuracy
        factory.setHeight(distance * 2 / (40075000 * Math.cos(Math.toRadians(latitude)) / 360));
        Coordinate center = new Coordinate(longitude, latitude);
        factory.setCentre(center);
        return factory.createCircle();
    }

    @Override
    public String toString() {
        return "Search params -> minPrice: " + minPrice + " maxPrice: " + maxPrice + " distance: " + distance;
    }
}

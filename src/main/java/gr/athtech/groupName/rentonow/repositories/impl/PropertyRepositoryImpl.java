package gr.athtech.groupName.rentonow.repositories.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.locationtech.jts.geom.Geometry;

import gr.athtech.groupName.rentonow.dtos.FindPropertyDto;
import gr.athtech.groupName.rentonow.models.Availability;
import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.repositories.PropertyRepositoryCustom;

@Repository
public class PropertyRepositoryImpl implements PropertyRepositoryCustom {

    @Autowired
    EntityManager em;

    @Override
    public List<Property> findProperties(FindPropertyDto searchParams) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Property> query = builder.createQuery(Property.class);
        Root<Property> property = query.from(Property.class);
        List<Predicate> predicates = new ArrayList<>(3);

        // This search is not accurate and works only for small circles since
        // it is not based on spherical geometry. For a proper implementation
        // we would propably need elasticsearch or a postgresql gsis etc...
        // For a proper implementation in sql server we would need the
        // geography type but it is not supported yet in hibernate spatial
        Geometry searchCircle = searchParams.generateSearchCircle();
        ParameterExpression<Geometry> circleParam = null;
        
        if (searchCircle != null) {
            circleParam = builder.parameter(Geometry.class);
            predicates.add(
                    builder.isTrue(builder.function("within", Boolean.class, property.get("location"), circleParam)));
        }

        if (searchParams.getMaxPrice() != null) {
            predicates.add(builder.lt(property.get("price"), searchParams.getMaxPrice()));
        }

        if (searchParams.getMinPrice() != null) {
            predicates.add(builder.gt(property.get("price"), searchParams.getMinPrice()));
        }

        if (searchParams.getStartDate() != null && searchParams.getEndDate() != null) {
            // TODO validate start < end
            // sql -> union propeties availabilites 
            // sql -> property where property.id not in select availability.property where availability.startDate > searcStart and availability.endDate < searchEnd...
            Subquery<Availability> subquery = query.subquery(Availability.class);
            Root<Availability> availability = subquery.from(Availability.class);

            Predicate startCondition = builder.lessThanOrEqualTo(availability.get("endDate"), searchParams.getEndDate());
            Predicate endCondition = builder.greaterThanOrEqualTo(availability.get("startDate"), searchParams.getStartDate());

            subquery.select(availability.get("property")).where(builder.and(startCondition, endCondition));

            var betweenDates = builder.in(property.get("id")).value(subquery).not();

            predicates.add(betweenDates);
        }

        // generate query from predicates
        if (predicates.size() == 1) {
            query.where(predicates.get(0));
        } else if (predicates.size() > 1) {
            query.where(builder.and(predicates.toArray(new Predicate[0])));
        }

        // we need to use a typed query in order to set parameters
        TypedQuery<Property> tq = em.createQuery(query);
        tq.setMaxResults(searchParams.getSize()); 
        tq.setFirstResult(searchParams.getOffset());
        if (circleParam != null) {
            tq.setParameter(circleParam, searchCircle);
        }

        return tq.getResultList();
    }

}

package gr.athtech.groupName.rentonow.daos.impl;

import java.util.List;

import javax.persistence.EntityManager;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gr.athtech.groupName.rentonow.daos.StatisticsDao;
import gr.athtech.groupName.rentonow.models.QBooking;
import gr.athtech.groupName.rentonow.models.QUser;

@Component
public class StatisticsDaoImpl implements StatisticsDao {

  @Autowired
  EntityManager em;

  @Override
  public List<Tuple> bookingsPerProperty() {
    QBooking qBooking = QBooking.booking;
    JPAQueryFactory query = new JPAQueryFactory(em);
    return query.from(qBooking)
                .groupBy(qBooking.property, qBooking.property.address)
                .select(qBooking.property.id, qBooking.property.id.count(), qBooking.property.address)
                .fetch();
  }

  public List<Tuple> bookingsPerHost() {
    QBooking qBooking = QBooking.booking;
    JPAQueryFactory query = new JPAQueryFactory(em);
    QUser host = qBooking.property.host;
    return query.from(qBooking)
                .groupBy(host, host.name)
                .select(host.id, host.count(), host.name)
                .fetch();
  }

  @Override
  public List<Tuple> bookingsPerGuest() {
    QBooking qBooking = QBooking.booking;
    JPAQueryFactory query = new JPAQueryFactory(em);
    return query.from(qBooking)
                .groupBy(qBooking.guest, qBooking.guest.username)
                .select(qBooking.guest.id, qBooking.guest.id.count(), qBooking.guest.username)
                .fetch();
  }
}
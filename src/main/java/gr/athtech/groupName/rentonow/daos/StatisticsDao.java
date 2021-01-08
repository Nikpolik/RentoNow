package gr.athtech.groupName.rentonow.daos;

import java.util.List;

import com.querydsl.core.Tuple;

public interface StatisticsDao {
  List<Tuple> bookingsPerHost();
  List<Tuple> bookingsPerProperty();
  List<Tuple> bookingsPerGuest();
}

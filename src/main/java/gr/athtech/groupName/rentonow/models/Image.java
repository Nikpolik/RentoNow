package gr.athtech.groupName.rentonow.models;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@ToString
@Data
@Entity
@JsonIgnoreProperties("property")
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  String url;
  String secureUrl;
  String cloudinaryId;

  @ManyToOne
  private Property property;
}

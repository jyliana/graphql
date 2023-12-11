package com.example.graphql.datasource.problemz.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "problemz")
public class Problemz {
  @Id
  private UUID id;
  @CreationTimestamp
  private LocalDateTime creationTimestamp;
  private String title;
  private String content;
  private String tags;

  @OneToMany(mappedBy = "problemz")
  @OrderBy("creationTimestamp desc")
  private List<Solutionz> solutions;

  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false)
  private Userz createdBy;
}

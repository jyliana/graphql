package com.example.graphql.datasource.problemz.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "solutionz")
public class Solutionz {
  @Id
  private UUID id;
  @CreationTimestamp
  private LocalDateTime creationTimestamp;
  private String content;
  private String category;
  private int voteGoodCount;
  private int voteBadCount;

  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false)
  private Userz createdBy;

  @ManyToOne
  @JoinColumn(name = "problemz_id", nullable = false)
  private Problemz problemz;
}

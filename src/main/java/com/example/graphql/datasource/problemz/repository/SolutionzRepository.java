package com.example.graphql.datasource.problemz.repository;

import com.example.graphql.datasource.problemz.entity.Solutionz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SolutionzRepository extends CrudRepository<Solutionz, UUID> {
}

package com.example.graphql.exception;

public class ProblemAuthenticationException extends RuntimeException {

  public ProblemAuthenticationException() {
    super("Invalid credentials");
  }
}

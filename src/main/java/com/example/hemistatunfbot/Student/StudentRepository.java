package com.example.hemistatunfbot.Student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "student")
public interface StudentRepository extends CrudRepository<Student,Long> {
}

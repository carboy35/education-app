package com.people.repository;

import org.springframework.data.repository.CrudRepository;

import com.people.entities.Student;

public interface StudentRepository extends CrudRepository<Student,Integer> {

}

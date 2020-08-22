package com.people.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.people.entities.Course;

@Repository
public interface CourseRepository extends CrudRepository<Course,Integer> {

}

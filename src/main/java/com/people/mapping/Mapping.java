package com.people.mapping;

import javax.xml.ws.ServiceMode;

import org.springframework.stereotype.Service;

import com.people.entities.Course;
import com.people.model.Courses;
@Service
public class Mapping {

	public Courses courseEntityToModel(Course entityCourse) {
		Courses coursesModel= new Courses();
		coursesModel.setCode(entityCourse.getCode());
		coursesModel.setName(entityCourse.getName());
		coursesModel.setIdCurso(entityCourse.getIdCourse());
		return coursesModel;
	}
	
	public Course courseModelToEntity(Courses modelCourse) {
		Course courseEntity= new Course();
		courseEntity.setCode(modelCourse.getCode());
		courseEntity.setName(modelCourse.getName());
		courseEntity.setIdCourse(modelCourse.getIdCurso());
		return courseEntity;
	}
}

package com.people.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.SQLGrammarException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.people.entities.Course;
import com.people.enumerated.MensajesRespuestaRest;
import com.people.mapping.Mapping;
import com.people.model.Courses;
import com.people.model.RestResponse;
import com.people.repository.CourseRepository;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/courses")
public class CourseController {

	@Autowired
	CourseRepository courseRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	Mapping mapping;
	

	@Value("${usuarioLogin}")
	private String usuarioLogin;
	
	@Value("${passwordLogin}")
	private String passwordLogin;
	
	@Value("${urlToken}")
	private String urlToken;
	
	@GetMapping
	public ResponseEntity<RestResponse> getCourses(HttpServletRequest request){
		List<Course> courseList= new ArrayList<>();
		List<Courses> courseModelList= new ArrayList<>();
		try {
		courseList=(List<Course>)courseRepository.findAll();
		courseModelList=(List<Courses>)courseList.stream().map(e -> this.mapping.courseEntityToModel(e)).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(true,"Get sucessful courses",MensajesRespuestaRest.OK.name(),courseModelList));
		}catch (Exception e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponse(false,"Error to get courses",MensajesRespuestaRest.INTERNAL_ERROR.name(),null));
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RestResponse> getCourse(@PathVariable("id") Integer id){
		Optional<Course> course= null;
		Courses courseModel= null;
		List<Courses> courseModelList= new ArrayList<>();
		try {
		course=courseRepository.findById(id);
		if (course.isPresent()) {
			courseModel=  this.mapping.courseEntityToModel(course.get());
		}
		
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(true,"Get sucessful course",MensajesRespuestaRest.OK.name(),courseModel));
		}catch (Exception e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponse(false,"Error to get course",MensajesRespuestaRest.INTERNAL_ERROR.name(),null));
		}
	}
	
	@PostMapping
	public ResponseEntity<RestResponse> saveCourse(@RequestBody Courses course){
		
		Course courseEntity= null;
		try {
			courseEntity=courseRepository.save(this.mapping.courseModelToEntity(course));
		
			return ResponseEntity.status(HttpStatus.CREATED).body(new RestResponse(true,"Save sucessful course",MensajesRespuestaRest.CREATED.name(),courseEntity.getIdCourse()));
		}catch(DataIntegrityViolationException e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestResponse(false,"Error to save course",MensajesRespuestaRest.BAD_REQUEST.name(),null));
		}catch (InvalidDataAccessResourceUsageException e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestResponse(false,"Error to save course",MensajesRespuestaRest.BAD_REQUEST.name(),null));
		}catch (Exception e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponse(false,"Error to save course",MensajesRespuestaRest.INTERNAL_ERROR.name(),null));
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RestResponse> updateCourse(@PathVariable Integer id,@RequestBody Courses course){
		
		Course courseEntity= null;
		Optional<Course> courseEntityFind= null;
		try {
			courseEntityFind=courseRepository.findById(id);
			if (courseEntityFind.isPresent()) {
				if (course.getCode().toCharArray().length>4) {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new RestResponse(false,"El largo del código debe ser menor a 4",MensajesRespuestaRest.NOT_CREATED.name(),null));
				}else {
					course.setIdCurso(courseEntityFind.get().getIdCourse());
					courseEntity=courseRepository.save(this.mapping.courseModelToEntity(course));
				}
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(false,"Error to update course",MensajesRespuestaRest.NOT_FOUND.name(),null));
			}
		
			return ResponseEntity.status(HttpStatus.OK).body(new RestResponse(true,"Update sucessful course",MensajesRespuestaRest.OK.name(),courseEntity.getIdCourse()));
		}catch(DataIntegrityViolationException e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestResponse(false,"Error to update course",MensajesRespuestaRest.BAD_REQUEST.name(),null));
		}catch (InvalidDataAccessResourceUsageException e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestResponse(false,"Error to update course",MensajesRespuestaRest.BAD_REQUEST.name(),null));
		}catch (Exception e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponse(false,"Error to update course",MensajesRespuestaRest.INTERNAL_ERROR.name(),null));
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<RestResponse> deleteCourse(@PathVariable Integer id){
		Optional<Course> courseEntityFind= null;
		try {
			courseEntityFind=courseRepository.findById(id);
			if (courseEntityFind.isPresent()) {
				courseRepository.delete(courseEntityFind.get());
			}else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RestResponse(false,"Error to delete course",MensajesRespuestaRest.NOT_FOUND.name(),null));
			}
		
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new RestResponse(true,"Update sucessful course",MensajesRespuestaRest.NO_CONTENT.name(),true));
		}catch (InvalidDataAccessResourceUsageException e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestResponse(false,"Error to update course",MensajesRespuestaRest.BAD_REQUEST.name(),null));
		}catch (Exception e) {
			log.error("An error ocurred: ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestResponse(false,"Error to update course",MensajesRespuestaRest.INTERNAL_ERROR.name(),null));
		}
	}

	 @Bean
	    public ResourceServerTokenServices tokenService() {
	        RemoteTokenServices tokenServices = new RemoteTokenServices();
	        tokenServices.setClientId(this.usuarioLogin);
	        tokenServices.setClientSecret(this.passwordLogin);
	        tokenServices.setCheckTokenEndpointUrl(this.urlToken);
	        return tokenServices;
	    }

}

package com.devops.test.apibuilder.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devops.test.apibuilder.exceptions.IncorrectFieldContent;
import com.devops.test.apibuilder.models.Repository;
import com.devops.test.apibuilder.repository.RepositoryJPA;

@RestController
@RequestMapping("/api")
public class RepositoryController {

	@Autowired
	RepositoryJPA repositoryJPA;
	
	@GetMapping("/repositories")
	public ResponseEntity<List<Repository>> getAllRepositories() {
		try {
			List<Repository> repositories = new ArrayList<Repository>();
			repositoryJPA.findAll().forEach(repositories::add);
			
			if (repositories.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(repositories, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/repositories/{id}")
	public ResponseEntity<Repository> getRepositoryById(@PathVariable("id") long id) {
		Optional<Repository> repository = repositoryJPA.findById(id);

		if (repository.isPresent()) {
			return new ResponseEntity<>(repository.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/repositories")
	public ResponseEntity<Repository> createRepository(@RequestBody Repository repository) {
		try {
	    	Repository _repository = repositoryJPA.save(
	    			new Repository(repository.getExternalId(), repository.getName(), repository.getRepositoryPath(), repository.getVersion())
	    	);
	      return new ResponseEntity<>(_repository, HttpStatus.CREATED);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	@PutMapping("/repositories/{id}")
	public ResponseEntity<Repository> updateRepository(@PathVariable("id") long id, @RequestBody Repository repository) {
		Optional<Repository> repoData = repositoryJPA.findById(id);

	    if (repoData.isPresent()) {
	    	Repository _repository = repoData.get();
	    	try {
	  	      _repository.setExternalId(repository.getExternalId());
	  	      _repository.setName(repository.getName());
	  	      _repository.setRepositoryPath(repository.getRepositoryPath());
	  	      _repository.setVersion(repository.getVersion());

	  	      return new ResponseEntity<>(repositoryJPA.save(_repository), HttpStatus.OK);
	    	} catch (IncorrectFieldContent e) {
	    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    	}
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}

	@DeleteMapping("/repositories/{id}")
	public ResponseEntity<HttpStatus> deleteRepository(@PathVariable("id") long id) {
		try {
			repositoryJPA.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/repositories")
	public ResponseEntity<HttpStatus> deleteAllRepositories() {
		try {
			repositoryJPA.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

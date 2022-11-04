package com.devops.test.apibuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devops.test.apibuilder.models.Repository;

public interface RepositoryJPA extends JpaRepository<Repository, Long> {
	  List<Repository> findByNameContaining(String name);
}

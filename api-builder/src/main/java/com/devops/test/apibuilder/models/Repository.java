package com.devops.test.apibuilder.models;

import javax.persistence.*;

import com.devops.test.apibuilder.exceptions.IncorrectFieldContent;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "repositories")
public class Repository {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private long id;
	
	@Column(name = "externalId")
	private int externalId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "repositoryPath")
	private String repositoryPath;
	
	@Column(name = "version")
	private String version;
	
	public Repository() {}
	
	public Repository(int externalId, String name, String repositoryPath, String version) {
		this.externalId = externalId;
		this.name = name;
		this.repositoryPath = repositoryPath;
		this.version = version;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getExternalId() {
		return externalId;
	}

	public void setExternalId(int externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws IncorrectFieldContent {
		if (!name.matches("[a-zA-Z]*")) {
			throw new IncorrectFieldContent("Name field should only contain letters (A-Z or a-z)...");
		}
		this.name = name;
	}

	public String getRepositoryPath() {
		return repositoryPath;
	}

	public void setRepositoryPath(String repositoryPath) throws IncorrectFieldContent {
		if (!repositoryPath.contains("\\") && !repositoryPath.contains("/")) {
			throw new IncorrectFieldContent("Repository Path field should at least contain one slash (\\ or /)...");
		}
		this.repositoryPath = repositoryPath;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) throws IncorrectFieldContent {
		if (!version.matches("[0-9]+\\.[0-9]+\\.[0-9]+")) {
			throw new IncorrectFieldContent("Version field should be of a format M.m.f (1.12.256) ...");
		}
		this.version = version;
	}
	
	@Override
	public String toString() {
		return "Repository[id=" + getId() + "]";
	}
}

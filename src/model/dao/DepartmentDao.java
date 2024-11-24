package model.dao;	

import java.util.Set;

import model.entities.Department;

public interface DepartmentDao {

	void insert(Department obj);
	void update(Department obj);
	void deleteById(Integer id);
	Department findById(Integer id);
	Set<Department> findAll();
	
}

package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface department_repository extends JpaRepository<department, Integer> {
}

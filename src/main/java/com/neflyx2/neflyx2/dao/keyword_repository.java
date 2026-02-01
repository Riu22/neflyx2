package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface keyword_repository extends JpaRepository<keyword, Long> {
}

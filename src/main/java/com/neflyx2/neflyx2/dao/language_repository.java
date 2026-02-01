package com.neflyx2.neflyx2.dao;

import com.neflyx2.neflyx2.model.entiti.language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface language_repository extends JpaRepository<language, Long> {
}

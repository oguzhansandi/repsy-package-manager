package com.respy.repository;

import com.respy.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetadataRespository extends JpaRepository<Metadata, Long> {
}

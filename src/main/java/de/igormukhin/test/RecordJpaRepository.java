package de.igormukhin.test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordJpaRepository extends JpaRepository<Record, PK> {
	
}

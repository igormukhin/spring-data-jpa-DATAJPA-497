package de.igormukhin.test;

import org.springframework.data.repository.Repository;

public interface RecordDeclaredMethodsRepository extends Repository<Record, PK> {
	
	// self-declared method
	Record findOne(PK pk);
	
	// self-declared method
	Record save(Record rec);

}

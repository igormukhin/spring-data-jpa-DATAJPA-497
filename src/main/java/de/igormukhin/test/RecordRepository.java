package de.igormukhin.test;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;


public interface RecordRepository extends Repository<Record, PK>, QueryDslPredicateExecutor<Record> {

}

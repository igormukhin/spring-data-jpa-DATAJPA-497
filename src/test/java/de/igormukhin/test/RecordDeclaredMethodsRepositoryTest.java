package de.igormukhin.test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * demonstrates the bug https://jira.spring.io/browse/DATAJPA-498
 */
//@Transactional -- not transactional, as we want to compare the behavior of two repositories
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class RecordDeclaredMethodsRepositoryTest {

	@Autowired
	RecordJpaRepository jpaRepository;

	// It passes
	@Test
	public void executesSaveWithoutOuterTransactionInJpaRepository() {
		Record rec = new Record();
		PK pk = new PK("cvalue1", "cvalue2");
		rec.setPk(pk);
		rec.setField3("cvalue3");
		
		Record saved = jpaRepository.save(rec);
		
		Record found = jpaRepository.findOne(pk);

		assertThat(found, notNullValue());
		assertThat(found, equalTo(saved));
	}

	@Autowired
	RecordDeclaredMethodsRepository selfDeclaredMethodsRepository;

	// It fails
	@Test
	public void executesSaveWithoutOuterTransactionInSelfDeclaredMethodsRepository() {
		Record rec = new Record();
		PK pk = new PK("cvalue1", "cvalue2");
		rec.setPk(pk);
		rec.setField3("cvalue3");
		
		Record saved = selfDeclaredMethodsRepository.save(rec);
		
		Record found = selfDeclaredMethodsRepository.findOne(pk);

		assertThat(found, notNullValue());
		assertThat(found, equalTo(saved));
	}
	
}

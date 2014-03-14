package de.igormukhin.test;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
@Transactional
public class RecordRepositoryIntegrationTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	RecordRepository repository;

	@Before
	public void populateDatabase() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("data.sql"));
		DatabasePopulatorUtils.execute(populator, dataSource);
	}

	@Test
	public void executesQueryDslOrderByField3() {

		Iterable<Record> records = repository.findAll(
				QRecord.record.pk.field1.eq("value1"),
				QRecord.record.field3.asc());

		assertThat(records, Matchers.<Record>hasItem(hasProperty("field3", is("value3"))));
	}

	/**
	 * For the record: This test would pass for: 
	 *  <!-- <querydsl.version>2.8.0</querydsl.version> -->
	 *	<!-- <spring.data.jpa.version>1.3.0.RELEASE</spring.data.jpa.version> -->
	 *  <!-- <spring.version>3.2.2.RELEASE</spring.version> -->
	 *  
	 * But it will fail for:
	 * <spring.version>4.0.2.RELEASE</spring.version>
	 * <spring.data.jpa.version>1.5.0.RELEASE</spring.data.jpa.version>
	 * <querydsl.version>3.3.1</querydsl.version>
	 */
	@Test
	public void executesQueryDslOrderByPkField2() {

		Iterable<Record> records = repository.findAll(
				QRecord.record.pk.field1.eq("value1"),
				QRecord.record.pk.field2.asc());

		assertThat(records, Matchers.<Record>hasItem(hasProperty("field3", is("value3"))));
	}
}

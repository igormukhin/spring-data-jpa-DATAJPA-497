package de.igormukhin.test;

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

/**
 * demonstrates the bug https://jira.spring.io/browse/DATAJPA-497
 */
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
	public void executesQueryDslOrderByField3Asc() {

		Iterable<Record> records = repository.findAll(
				null,
				QRecord.record.field3.asc());

		assertThat(records, Matchers.<Record>iterableWithSize(2));
		assertThat(records.iterator().next().getField3(), is("avalue3"));
	}

	@Test
	public void executesQueryDslOrderByField3Desc() {

		Iterable<Record> records = repository.findAll(
				null,
				QRecord.record.field3.desc());

		assertThat(records, Matchers.<Record>iterableWithSize(2));
		assertThat(records.iterator().next().getField3(), is("bvalue3"));
	}

	@Test
	public void executesQueryDslOrderByPkField2Asc() {

		Iterable<Record> records = repository.findAll(
				null,
				QRecord.record.pk.field2.asc());

		assertThat(records, Matchers.<Record>iterableWithSize(2));
		assertThat(records.iterator().next().getField3(), is("avalue3"));
	}

	@Test
	public void executesQueryDslOrderByPkField2Desc() {

		Iterable<Record> records = repository.findAll(
				null,
				QRecord.record.pk.field2.asc());

		assertThat(records, Matchers.<Record>iterableWithSize(2));
		assertThat(records.iterator().next().getField3(), is("bvalue3"));
	}
}

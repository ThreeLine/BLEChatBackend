package com.fnx.domain.factory;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:/repo_properties.xml", "classpath:/repo_init.xml",
		"classpath:/domain_init.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class BaseDomainTestCase {

	@Autowired
	@Qualifier("basicMongoTemplate")
	protected MongoTemplate basicMongoTemplate;

	@Before
	public void setup() {
		basicMongoTemplate.getDb().dropDatabase();
	}

}

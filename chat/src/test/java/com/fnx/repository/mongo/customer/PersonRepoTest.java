package com.fnx.repository.mongo.customer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.fnx.db.entity.EntityConstants;
import com.fnx.db.entity.customer.PersonDBVO;
import com.fnx.repository.mongo.BaseRepoTestCase;


public class PersonRepoTest extends BaseRepoTestCase {

	@Autowired
	private PersonAutoRepo personAutoRepo;
	
	@Autowired
	private PersonRepo personRepo;	
	
	private String tenantId = "55eff106d4c6394ac1f87681";
	
	private void init_findPersons() {
		List<PersonDBVO> persons = new ArrayList<PersonDBVO>();
		
		PersonDBVO customerOne = new PersonDBVO();
		customerOne.setId("555ff106d4c6394ac1f87601");
		customerOne.setEmail("01@3fnx.com");
		customerOne.setMobile("13751118101");
		customerOne.setName("Tonny1");
		customerOne.setWechat("wewe1");
		customerOne.setQq("433631");
		customerOne.setSex(EntityConstants.SEX_MALE);
		customerOne.setTenantId(tenantId);
		persons.add(customerOne);
		
		PersonDBVO customerTwo = new PersonDBVO();
		customerTwo.setId("555ff106d4c6394ac1f87602");
		customerTwo.setEmail("02@3fnx.com");
		customerTwo.setMobile("13751118102");
		customerTwo.setName("Tokky2");
		customerTwo.setWechat("wewe2");
		customerTwo.setQq("433632");
		customerTwo.setSex(EntityConstants.SEX_FEMALE);	
		customerTwo.setTenantId(tenantId);
		persons.add(customerTwo);
		
		PersonDBVO customerThree = new PersonDBVO();
		customerThree.setId("555ff106d4c6394ac1f87603");
		customerThree.setEmail("03@3fnx.com");
		customerThree.setMobile("13751118103");
		customerThree.setName("kitty3");
		customerThree.setWechat("wewe3");
		customerThree.setQq("433633");
		customerThree.setSex(EntityConstants.SEX_MALE);		
		customerThree.setTenantId(tenantId);
		persons.add(customerThree);	
		
		PersonDBVO customerFour = new PersonDBVO();
		customerFour.setId("555ff106d4c6394ac1f87604");
		customerFour.setEmail("04@3fnx.com");
		customerFour.setMobile("13751118104");
		customerFour.setName("baby4");
		customerFour.setWechat("wewe4");
		customerFour.setQq("433634");
		customerFour.setSex(EntityConstants.SEX_FEMALE);		
		customerFour.setTenantId(tenantId);
		persons.add(customerFour);				
		
		personAutoRepo.insert(persons);
	}
	
	@Test
	public void findPersons() {
		init_findPersons();
		Pageable pageable = new PageRequest(0, 2, Direction.ASC, "name");
		Page<PersonDBVO> findPersons = personRepo.findPersons(tenantId, "To", "1375", pageable);
		List<PersonDBVO> content = findPersons.getContent();
		Assert.assertEquals(2, content.size());
	}
}

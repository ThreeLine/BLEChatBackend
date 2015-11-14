package com.fnx.repository.mongo;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fnx.db.entity.EntityConstants;
import com.fnx.db.entity.customer.OrganizationDBVO;
import com.fnx.db.entity.customer.PersonDBVO;
import com.fnx.db.entity.tenant.TenantDBVO;
import com.fnx.db.entity.tenant.TenantUserDBVO;
import com.fnx.db.entity.util.ObjectIdGenerator;
import com.fnx.db.vo.customer.OrganizationEmployeeVO;
import com.fnx.repository.mongo.customer.OrganizationAutoRepo;
import com.fnx.repository.mongo.customer.PersonAutoRepo;
import com.fnx.webapp.controller.BaseControllerTestCase;

@Ignore
public class InitUatData extends BaseControllerTestCase {

	@Autowired
	private PersonAutoRepo personAutoRepo;
	@Autowired
	private OrganizationAutoRepo organizationAutoRepo;
	
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
		customerTwo.setName("Tonny2");
		customerTwo.setWechat("wewe2");
		customerTwo.setQq("433632");
		customerTwo.setSex(EntityConstants.SEX_FEMALE);	
		customerTwo.setTenantId(tenantId);
		persons.add(customerTwo);
		
		PersonDBVO customerThree = new PersonDBVO();
		customerThree.setId("555ff106d4c6394ac1f87603");
		customerThree.setEmail("03@3fnx.com");
		customerThree.setMobile("13751118103");
		customerThree.setName("Tonny3");
		customerThree.setWechat("wewe3");
		customerThree.setQq("433633");
		customerThree.setSex(EntityConstants.SEX_MALE);		
		customerThree.setTenantId(tenantId);
		persons.add(customerThree);	
		
		PersonDBVO customerFour = new PersonDBVO();
		customerFour.setId("555ff106d4c6394ac1f87604");
		customerFour.setEmail("04@3fnx.com");
		customerFour.setMobile("13751118104");
		customerFour.setName("Tonny4");
		customerFour.setWechat("wewe4");
		customerFour.setQq("433634");
		customerFour.setSex(EntityConstants.SEX_FEMALE);		
		customerFour.setTenantId(tenantId);
		persons.add(customerFour);				
		
		personAutoRepo.insert(persons);
	}
	
	private void init_org() {
		OrganizationDBVO one = new OrganizationDBVO();
		one.setContacts("Jack");
		one.setContactsMobile("13751118197");
		one.setEmail("cc@3fnx.com");
		one.setName("three Line");
		one.setOther("other1");
		one.setPhone("020338423");
		one.setQq("4335");
		one.setSex(EntityConstants.SEX_FEMALE);
		one.setWebsite("http://wwww.sina.com");
		one.setWechat("oooo");
		one.setTenantId(tenantId);
		
		OrganizationEmployeeVO employeeVO = new OrganizationEmployeeVO();
		employeeVO.setId(ObjectIdGenerator.generate());
		employeeVO.setName("Allen");
		employeeVO.setSex(EntityConstants.SEX_MALE);
		employeeVO.setDepartment("IT");
		employeeVO.setPosition("manager");
		one.getEmployees().add(employeeVO);
		organizationAutoRepo.save(one);
	}
	
	
	@Test
	public void initData() {
		TenantDBVO tenant = new TenantDBVO();
		tenant.setId("55eff106d4c6394ac1f87681");
		tenant.setTenantCode("3fnx");
		basicMongoTemplate.insert(tenant);
		
		TenantUserDBVO tenantUserDBVO = new TenantUserDBVO();
		tenantUserDBVO.setEmail("admin@3fnx.com");
		tenantUserDBVO.setPassword("866a39abf9c5b724862a22453bb64c0620830aefaf8a2bad0e5fe7d8c115c029c38259af4b425190");
		tenantUserDBVO.setTenantId("55eff106d4c6394ac1f87681");
		tenantUserDBVO.setName("3fnxAdmin");
		basicMongoTemplate.insert(tenantUserDBVO);
		
		init_findPersons();
		init_org();
	}
	
}

package com.fnx.webapp.controller.customer;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fnx.db.entity.EntityConstants;
import com.fnx.db.entity.customer.OrganizationDBVO;
import com.fnx.db.entity.util.ObjectIdGenerator;
import com.fnx.db.vo.customer.OrganizationEmployeeVO;
import com.fnx.db.vo.customer.PersonSkirtVO;
import com.fnx.db.vo.customer.PersonStatureVO;
import com.fnx.db.vo.customer.PersonSuitVO;
import com.fnx.db.vo.customer.PersonTrouserVO;
import com.fnx.db.vo.customer.PersonTshirtVO;
import com.fnx.db.vo.customer.PersonVestVO;
import com.fnx.repository.mongo.customer.OrganizationAutoRepo;
import com.fnx.repository.mongo.customer.OrganizationRepo;
import com.fnx.webapp.controller.BaseController;
import com.fnx.webapp.model.ModelConstants;
import com.fnx.webapp.model.common.ResponseModel;
import com.fnx.webapp.model.common.UserDetailsModel;
import com.fnx.webapp.model.customer.EmployeeRowVO;
import com.fnx.webapp.model.customer.EmployeeTableModel;
import com.fnx.webapp.model.customer.OrganizationBasicModel;
import com.fnx.webapp.model.customer.OrganizationEmployeeBasicModel;
import com.fnx.webapp.model.customer.OrganizationSearchModel;
import com.fnx.webapp.model.customer.OrganizationSearchRowVO;
import com.fnx.webapp.model.customer.PersonSkirtModel;
import com.fnx.webapp.model.customer.PersonStatureModel;
import com.fnx.webapp.model.customer.PersonSuitModel;
import com.fnx.webapp.model.customer.PersonTrouserModel;
import com.fnx.webapp.model.customer.PersonTshirtModel;
import com.fnx.webapp.model.customer.PersonVestModel;
import com.fnx.webapp.util.WebConstants;

@RestController
@RequestMapping("/customer")
public class OrganizationController extends BaseController {

	@Autowired
	private OrganizationAutoRepo orgAutoRepo;
	@Autowired
	private OrganizationRepo organizationRepo;

	@RequestMapping(value = "/organization/basic", method = RequestMethod.POST)
	public ResponseModel createBasic(@RequestBody @Valid OrganizationBasicModel model, BindingResult result, Authentication authentication) {
		ResponseModel resModel = new ResponseModel();
		UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
		OrganizationDBVO orgDBVO = new OrganizationDBVO();
		orgDBVO.setName(model.getName());
		orgDBVO.setTenantId(userDetailsModel.getTenantId());
		orgDBVO.setPhone(model.getPhone());
		orgDBVO.setWebsite(model.getWebsite());
		orgDBVO.setContacts(model.getContacts());
		orgDBVO.setContactsMobile(model.getContactsMobile());
		orgDBVO.setWechat(model.getWechat());
		orgDBVO.setQq(model.getQq());
		orgDBVO.setEmail(model.getEmail());
		orgDBVO.setOther(model.getOther());
		if (ModelConstants.SEX_MALE.equals(model.getSex())) {
			orgDBVO.setSex(EntityConstants.SEX_MALE);
		} else if (ModelConstants.SEX_FEMALE.equals(model.getSex())) {
			orgDBVO.setSex(EntityConstants.SEX_FEMALE);
		}		
		orgAutoRepo.save(orgDBVO);
		model.setId(orgDBVO.getId());
		resModel.setData(model);
		return resModel;
	}
	
	@RequestMapping(value = "/organization/{id}/basic", method = RequestMethod.PUT)
	public ResponseModel updateBasic(@RequestBody @Valid OrganizationBasicModel model, BindingResult result,
			@PathVariable String id, Authentication authentication) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(id);
		if (orgDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		orgDBVO.setName(model.getName());
		orgDBVO.setPhone(model.getPhone());
		orgDBVO.setWebsite(model.getWebsite());
		orgDBVO.setContacts(model.getContacts());
		orgDBVO.setContactsMobile(model.getWebsite());
		orgDBVO.setWechat(model.getWechat());
		orgDBVO.setQq(model.getQq());
		orgDBVO.setEmail(model.getEmail());
		orgDBVO.setOther(model.getOther());
		this.orgAutoRepo.save(orgDBVO);		
		return resModel;
	}
	
	@RequestMapping(value = "/organization/{id}/basic", method = RequestMethod.GET)
	public ResponseModel loadBasic(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(id);
		if (orgDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;			
		}
		OrganizationBasicModel orgBasicModel = new OrganizationBasicModel();
		orgBasicModel.setId(id);
		orgBasicModel.setName(orgDBVO.getName());
		orgBasicModel.setPhone(orgDBVO.getPhone());
		orgBasicModel.setWebsite(orgDBVO.getWebsite());
		orgBasicModel.setContacts(orgDBVO.getContacts());
		orgBasicModel.setContactsMobile(orgDBVO.getContactsMobile());
		orgBasicModel.setWechat(orgDBVO.getWechat());
		orgBasicModel.setQq(orgDBVO.getQq());
		orgBasicModel.setEmail(orgDBVO.getEmail());
		orgBasicModel.setOther(orgDBVO.getOther());
		if (EntityConstants.SEX_FEMALE.equals(orgDBVO.getSex())) {
			orgBasicModel.setSex(ModelConstants.SEX_FEMALE);
		} else {
			orgBasicModel.setSex(ModelConstants.SEX_MALE);
		}
		resModel.setData(orgBasicModel);
		return resModel;
	}
	

	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/basic", method = RequestMethod.GET)
	public ResponseModel loadEmployeeBasic(@PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		if (orgDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;			
		}
		OrganizationEmployeeVO employeeVO = null;
		for (OrganizationEmployeeVO element : orgDBVO.getEmployees()) {
			if (element.getId().equals(employeeId)) {
				employeeVO = element;
				break;
			}
		}
		if (employeeVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;					
		}
		OrganizationEmployeeBasicModel basicModel = new OrganizationEmployeeBasicModel();
		basicModel.setId(employeeVO.getId());
		basicModel.setDepartment(employeeVO.getDepartment());
		basicModel.setName(employeeVO.getName());
		basicModel.setPosition(employeeVO.getPosition());
		if (employeeVO.getSex().equals(EntityConstants.SEX_FEMALE)) {
			basicModel.setSex(ModelConstants.SEX_FEMALE);
		} else {
			basicModel.setSex(ModelConstants.SEX_MALE);
		}
		resModel.setData(basicModel);
		return resModel;
	}	
	
	@RequestMapping(value = "/organization/{id}/employee/basic", method = RequestMethod.POST)
	public ResponseModel createEmployeeBasic(@RequestBody @Valid OrganizationEmployeeBasicModel model, BindingResult result, @PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(id);
		if (orgDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;			
		}	
		OrganizationEmployeeVO employeeVO = new OrganizationEmployeeVO();
		employeeVO.setId(ObjectIdGenerator.generate());
		if (model.getSex().equals(ModelConstants.SEX_FEMALE)) {
			employeeVO.setSex(EntityConstants.SEX_FEMALE);
		} else {
			employeeVO.setSex(EntityConstants.SEX_MALE);
		}
		employeeVO.setName(model.getName());
		employeeVO.setDepartment(model.getDepartment());
		employeeVO.setPosition(model.getPosition());
		orgDBVO.getEmployees().add(employeeVO);
		orgAutoRepo.save(orgDBVO);
		model.setId(employeeVO.getId());
		resModel.setData(model);
		return resModel;
	}
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/basic", method = RequestMethod.PUT)
	public ResponseModel updateEmployeeBasic(@RequestBody @Valid OrganizationEmployeeBasicModel model, BindingResult result, @PathVariable String organizationId, @PathVariable String employeeId) {	
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		if (orgDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;			
		}		
		OrganizationEmployeeVO updateEmployeeVO = null;
		for (OrganizationEmployeeVO element : orgDBVO.getEmployees()) {
			if (element.getId().equals(employeeId)) {
				updateEmployeeVO = element;
				break;
			}
		}
		if (updateEmployeeVO != null) {
			if (model.getSex().equals(ModelConstants.SEX_FEMALE)) {
				updateEmployeeVO.setSex(EntityConstants.SEX_FEMALE);
			} else {
				updateEmployeeVO.setSex(EntityConstants.SEX_MALE);
			}
			updateEmployeeVO.setName(model.getName());
			updateEmployeeVO.setDepartment(model.getDepartment());
			updateEmployeeVO.setPosition(model.getPosition());			
		}
		orgAutoRepo.save(orgDBVO);		
		return resModel;
	}
	
	@RequestMapping(value = "/organization/{id}/employees", method = RequestMethod.GET)
	public ResponseModel loadEmployees(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(id);
		if (orgDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;			
		}
		EmployeeTableModel employeeTable = new EmployeeTableModel();		
		for (OrganizationEmployeeVO employeeDBVO : orgDBVO.getEmployees()) {
			EmployeeRowVO employeeRowVO = new EmployeeRowVO();
			employeeRowVO.setDepartment(employeeDBVO.getDepartment());
			employeeRowVO.setId(employeeDBVO.getId());
			employeeRowVO.setName(employeeDBVO.getName());
			employeeRowVO.setPosition(employeeDBVO.getPosition());
			if (employeeDBVO.getSex().equals(EntityConstants.SEX_FEMALE)) {
				employeeRowVO.setSex(ModelConstants.SEX_FEMALE);
			} else {
				employeeRowVO.setSex(ModelConstants.SEX_MALE);
			}
			employeeTable.getEmployeeRows().add(employeeRowVO);
		}
		resModel.setData(employeeTable);
		return resModel;
	}

	@RequestMapping(value = "/organizations", method = RequestMethod.GET)
	public ResponseModel loadOrganizations(OrganizationSearchModel organizationSearchModel, Authentication authentication) {
		ResponseModel resModel = new ResponseModel();
		UserDetailsModel userDetailsModel = (UserDetailsModel)authentication.getPrincipal();
		String name = organizationSearchModel.getName() == null ? "" :  organizationSearchModel.getName().trim();
		String phone = organizationSearchModel.getPhone() == null ? "" : organizationSearchModel.getName().trim();
		
		Pageable page = new PageRequest(organizationSearchModel.getPageNumber(), organizationSearchModel.getPageSize(), organizationSearchModel.getSort());
		Page<OrganizationDBVO> pageObject = organizationRepo.findOrganizations(userDetailsModel.getTenantId(), name, phone, page);
		List<OrganizationDBVO> contentList = pageObject.getContent();
		organizationSearchModel.setTotal(pageObject.getTotalElements());
		for (OrganizationDBVO personDBVOItem : contentList) {
			OrganizationSearchRowVO orgRowVO = new OrganizationSearchRowVO();
			orgRowVO.setContacts(personDBVOItem.getContacts());
			orgRowVO.setContactsMobile(personDBVOItem.getContactsMobile());
			orgRowVO.setEmail(personDBVOItem.getEmail());
			orgRowVO.setId(personDBVOItem.getId());
			orgRowVO.setName(personDBVOItem.getName());
			orgRowVO.setOther(personDBVOItem.getOther());
			orgRowVO.setPhone(personDBVOItem.getPhone());
			orgRowVO.setQq(personDBVOItem.getQq());
			if (personDBVOItem.getSex().equals(EntityConstants.SEX_FEMALE)) {
				orgRowVO.setSex(ModelConstants.SEX_FEMALE);				
			} else {
				orgRowVO.setSex(ModelConstants.SEX_MALE);
			}
			orgRowVO.setWebsite(personDBVOItem.getWebsite());
			orgRowVO.setWechat(personDBVOItem.getWechat());
			organizationSearchModel.getRows().add(orgRowVO);
		}
		resModel.setData(organizationSearchModel);
		return resModel;
	}	
	
	//验证机构员工是否存在, 如果存在则返回这个员工
	private OrganizationEmployeeVO retrieveEmployeeVO(OrganizationDBVO orgDBVO, String employeeId, ResponseModel resModel) {
		if (orgDBVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return null;
		} 
		OrganizationEmployeeVO employeeVO = null;
		for (OrganizationEmployeeVO element : orgDBVO.getEmployees()) {
			if (element.getId().equals(employeeId)) {
				employeeVO = element;
				break;
			}
		}
		if (employeeVO == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return null;					
		}		
		return employeeVO;
	}
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/stature", method = RequestMethod.GET)
	public ResponseModel loadStature(@PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonStatureVO personStatureVO = employeeVO.getCustomerStatureVO();
		PersonStatureModel personStatureModel = new PersonStatureModel();
		personStatureModel.setBack(personStatureVO.getBack());
		personStatureModel.setBody(personStatureVO.getBody());
		personStatureModel.setHeight(personStatureVO.getHeight());
		personStatureModel.setShoulder(personStatureVO.getShoulder());
		personStatureModel.setTripe(personStatureVO.getTripe());
		personStatureModel.setWeight(personStatureVO.getWeight());
		resModel.setData(personStatureModel);
		return resModel;
	}	
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/stature", method = RequestMethod.PUT)
	public ResponseModel updateStature(@RequestBody PersonStatureModel model, @PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonStatureVO personStatureVO = employeeVO.getCustomerStatureVO();
		personStatureVO.setBack(model.getBack());
		personStatureVO.setBody(model.getBody());
		personStatureVO.setHeight(model.getBody());
		personStatureVO.setShoulder(model.getShoulder());
		personStatureVO.setTripe(model.getTripe());
		personStatureVO.setWeight(model.getWeight());
		orgAutoRepo.save(orgDBVO);	
		return resModel;
	}	
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/suit", method = RequestMethod.GET)
	public ResponseModel loadSuit(@PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonSuitVO personSuitVO = employeeVO.getCustomerSuitVO();
		PersonSuitModel personSuitModel = new PersonSuitModel();
		personSuitModel.setQianchang(personSuitVO.getQianchang());
		personSuitModel.setHouzhongchang(personSuitVO.getHouzhongchange());
		personSuitModel.setShangweishi(personSuitVO.getShangweishi());
		personSuitModel.setShangweipao(personSuitVO.getShangweipao());
		personSuitModel.setZhongweishi(personSuitVO.getZhongweishi());
		personSuitModel.setZhongweipao(personSuitVO.getZhongweipao());
		personSuitModel.setXiaweishi(personSuitVO.getXiaweishi());
		personSuitModel.setXiaweipao(personSuitVO.getXiaweipao());
		personSuitModel.setQianxiongkuo(personSuitVO.getQianxiongkuo());
		personSuitModel.setHoubei(personSuitVO.getHoubei());
		personSuitModel.setJiankuo(personSuitVO.getJiankuo());
		personSuitModel.setQianxiaojian(personSuitVO.getQianxiaojian());
		personSuitModel.setXiuchang(personSuitVO.getXiuchang());
		personSuitModel.setXiukou(personSuitVO.getXiukou());
		personSuitModel.setXionggao(personSuitVO.getXionggao());
		personSuitModel.setYaozhi(personSuitVO.getYaozhi());
		personSuitModel.setLingwei(personSuitVO.getLingwei());
		for (String key : personSuitVO.getCuttingSize().keySet()) {
			personSuitModel.getCuttingSize().put(key, personSuitVO.getCuttingSize().get(key));
		}
		resModel.setData(personSuitModel);
		return resModel;
	}	
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/suit", method = RequestMethod.PUT)
	public ResponseModel updateSuit(@RequestBody @Valid PersonSuitModel model, @PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonSuitVO personSuitVO = employeeVO.getCustomerSuitVO();
		personSuitVO.setQianchang(model.getQianchang());
		personSuitVO.setHouzhongchange(model.getHouzhongchang());
		personSuitVO.setShangweishi(model.getShangweishi());
		personSuitVO.setShangweipao(model.getShangweipao());
		personSuitVO.setZhongweishi(model.getZhongweishi());
		personSuitVO.setZhongweipao(model.getZhongweipao());
		personSuitVO.setXiaweishi(model.getXiaweishi());
		personSuitVO.setXiaweipao(model.getXiaweipao());
		personSuitVO.setQianxiongkuo(model.getQianxiongkuo());
		personSuitVO.setHoubei(model.getHoubei());
		personSuitVO.setJiankuo(model.getJiankuo());
		personSuitVO.setQianxiaojian(model.getQianxiaojian());
		personSuitVO.setXiuchang(model.getXiuchang());
		personSuitVO.setXiukou(model.getXiukou());
		personSuitVO.setXionggao(model.getXionggao());
		personSuitVO.setYaozhi(model.getYaozhi());
		personSuitVO.setLingwei(model.getLingwei());
		personSuitVO.getCuttingSize().clear();
		for (String key : model.getCuttingSize().keySet()) {
			personSuitVO.getCuttingSize().put(key, model.getCuttingSize().get(key));
		}
		orgAutoRepo.save(orgDBVO);
		return resModel;
	}	
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/vest", method = RequestMethod.GET)
	public ResponseModel loadVest(@PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonVestVO customerVestVO = employeeVO.getCustomerVestVO();
		PersonVestModel personVestModel = new PersonVestModel();
		personVestModel.setQianchang(customerVestVO.getQianchang());
		personVestModel.setHouzhongchang(customerVestVO.getHouzhongchang());
		personVestModel.setShangweishi(customerVestVO.getShangweishi());
		personVestModel.setShangweipao(customerVestVO.getShangweipao());
		personVestModel.setZhongweishi(customerVestVO.getZhongweishi());
		personVestModel.setZhongweipao(customerVestVO.getZhongweipao());
		personVestModel.setXiaweishi(customerVestVO.getXiaweishi());
		personVestModel.setXiaweipao(customerVestVO.getXiaweipao());
		personVestModel.setQianxiongkuo(customerVestVO.getQianxiongkuo());
		personVestModel.setHoubei(customerVestVO.getHoubei());
		personVestModel.setJiankuo(customerVestVO.getJiankuo());
		personVestModel.setQianxiaojian(customerVestVO.getQianxiaojian());
		personVestModel.setJiaquanshen(customerVestVO.getJiaquanshen());
		personVestModel.setXionggao(customerVestVO.getXionggao());
		personVestModel.setYaozhi(customerVestVO.getYaozhi());
		personVestModel.setLingwei(customerVestVO.getLingwei());
		for (String key : customerVestVO.getCuttingSize().keySet()) {
			personVestModel.getCuttingSize().put(key, customerVestVO.getCuttingSize().get(key));
		}
		resModel.setData(personVestModel);
		return resModel;
	}
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/vest", method = RequestMethod.PUT)
	public ResponseModel updateVest(@RequestBody @Valid PersonVestModel model, @PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonVestVO personVest = employeeVO.getCustomerVestVO();
		personVest.setQianchang(model.getQianchang());
		personVest.setHouzhongchang(model.getHouzhongchang());
		personVest.setShangweishi(model.getShangweishi());
		personVest.setShangweipao(model.getShangweipao());
		personVest.setZhongweishi(model.getZhongweishi());
		personVest.setZhongweipao(model.getZhongweipao());
		personVest.setXiaweishi(model.getXiaweishi());
		personVest.setXiaweipao(model.getXiaweipao());
		personVest.setQianxiongkuo(model.getQianxiongkuo());
		personVest.setHoubei(model.getHoubei());
		personVest.setJiankuo(model.getJiankuo());
		personVest.setQianxiaojian(model.getQianxiaojian());
		personVest.setJiaquanshen(model.getJiaquanshen());
		personVest.setXionggao(model.getXionggao());
		personVest.setYaozhi(model.getYaozhi());
		personVest.setLingwei(model.getLingwei());
		personVest.getCuttingSize().clear();
		for (String key : model.getCuttingSize().keySet()) {
			personVest.getCuttingSize().put(key, model.getCuttingSize().get(key));
		}
		orgAutoRepo.save(orgDBVO);
		return resModel;
	}	
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/shirt", method = RequestMethod.GET)
	public ResponseModel loadTshirt(@PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonTshirtVO customerTshirtVO = employeeVO.getCustomerTshirtVO();
		PersonTshirtModel tshirtModel = new PersonTshirtModel();
		tshirtModel.setQianchang(customerTshirtVO.getQianchang());
		tshirtModel.setHouzhongchang(customerTshirtVO.getHouzhongchang());
		tshirtModel.setShangweishi(customerTshirtVO.getShangweishi());
		tshirtModel.setShangweipao(customerTshirtVO.getShangweipao());
		tshirtModel.setZhongweishi(customerTshirtVO.getZhongweishi());
		tshirtModel.setZhongweipao(customerTshirtVO.getZhongweipao());
		tshirtModel.setXiaweishi(customerTshirtVO.getXiaweishi());
		tshirtModel.setXiaweipao(customerTshirtVO.getXiaweipao());
		tshirtModel.setQianxiongkuo(customerTshirtVO.getQianxiongkuo());
		tshirtModel.setHoubei(customerTshirtVO.getHoubei());
		tshirtModel.setJiankuo(customerTshirtVO.getJiankuo());
		tshirtModel.setQianxiaojian(customerTshirtVO.getQianxiaojian());
		tshirtModel.setXiuchang(customerTshirtVO.getXiuchang());
		tshirtModel.setXiubikuo(customerTshirtVO.getXiubikuo());
		tshirtModel.setXiukou(customerTshirtVO.getXiubikuo());
		tshirtModel.setXionggao(customerTshirtVO.getXionggao());
		tshirtModel.setYaozhi(customerTshirtVO.getYaozhi());
		tshirtModel.setLingwei(customerTshirtVO.getLingwei());
		for (String key : customerTshirtVO.getCuttingSize().keySet()) {
			tshirtModel.getCuttingSize().put(key, customerTshirtVO.getCuttingSize().get(key));
		}
		resModel.setData(tshirtModel);
		return resModel;
	}	
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/shirt", method = RequestMethod.PUT)
	public ResponseModel updateTshirt(@RequestBody @Valid PersonTshirtModel model, @PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonTshirtVO customerTshirtVO = employeeVO.getCustomerTshirtVO();
		customerTshirtVO.setQianchang(model.getQianchang());
		customerTshirtVO.setHouzhongchang(model.getHouzhongchang());
		customerTshirtVO.setShangweishi(model.getShangweishi());
		customerTshirtVO.setShangweipao(model.getShangweipao());
		customerTshirtVO.setZhongweishi(model.getZhongweishi());
		customerTshirtVO.setZhongweipao(model.getZhongweipao());
		customerTshirtVO.setXiaweishi(model.getXiaweishi());
		customerTshirtVO.setXiaweipao(model.getXiaweipao());
		customerTshirtVO.setQianxiongkuo(model.getQianxiongkuo());
		customerTshirtVO.setHoubei(model.getHoubei());
		customerTshirtVO.setJiankuo(model.getJiankuo());
		customerTshirtVO.setQianxiaojian(model.getQianxiaojian());
		customerTshirtVO.setXiuchang(model.getXiuchang());
		customerTshirtVO.setXiubikuo(model.getXiubikuo());
		customerTshirtVO.setXiukou(model.getXiukou());
		customerTshirtVO.setXionggao(model.getXionggao());
		customerTshirtVO.setYaozhi(model.getYaozhi());
		customerTshirtVO.setLingwei(model.getLingwei());
		customerTshirtVO.getCuttingSize().clear();
		for (String key : model.getCuttingSize().keySet()) {
			customerTshirtVO.getCuttingSize().put(key, model.getCuttingSize().get(key));
		}
		orgAutoRepo.save(orgDBVO);
		return resModel;
	}	
	

	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/trousers", method = RequestMethod.PUT)
	public ResponseModel updateTrousers(@RequestBody @Valid PersonTrouserModel model, @PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonTrouserVO customerTrouserVO = employeeVO.getCustomerTrouserVO();
		customerTrouserVO.setKutou(model.getKutou());
		customerTrouserVO.setKuchang(model.getKuchang());
		customerTrouserVO.setZuoweishi(model.getZuoweishi());
		customerTrouserVO.setZuoweipao(model.getZuoweipao());
		customerTrouserVO.setKubishi(model.getKubishi());
		customerTrouserVO.setKubipao(model.getKubipao());
		customerTrouserVO.setNeichang(model.getNeichang());
		customerTrouserVO.setShanglang(model.getShanglang());
		customerTrouserVO.setZonglang(model.getZonglang());
		customerTrouserVO.setZhongxi(model.getZhongxi());
		customerTrouserVO.setKujiao(model.getKujiao());
		customerTrouserVO.getCuttingSize().clear();
		for (String key : model.getCuttingSize().keySet()) {
			customerTrouserVO.getCuttingSize().put(key, model.getCuttingSize().get(key));
		}
		orgAutoRepo.save(orgDBVO);
		return resModel;
	}

	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/trousers", method = RequestMethod.GET)
	public ResponseModel loadTrousers(@PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonTrouserVO trouserVO = employeeVO.getCustomerTrouserVO();
		PersonTrouserModel trouserModel = new PersonTrouserModel();
		trouserModel.setKutou(trouserVO.getKutou());
		trouserModel.setKuchang(trouserVO.getKuchang());
		trouserModel.setZuoweishi(trouserVO.getZuoweishi());
		trouserModel.setZuoweipao(trouserVO.getZuoweipao());
		trouserModel.setKubishi(trouserVO.getKubishi());
		trouserModel.setKubipao(trouserVO.getKubipao());
		trouserModel.setNeichang(trouserVO.getNeichang());
		trouserModel.setShanglang(trouserVO.getShanglang());
		trouserModel.setZonglang(trouserVO.getZonglang());
		trouserModel.setZhongxi(trouserVO.getZhongxi());
		trouserModel.setKujiao(trouserVO.getKujiao());
		for (String key : trouserVO.getCuttingSize().keySet()) {
			trouserModel.getCuttingSize().put(key, trouserVO.getCuttingSize().get(key));
		}
		resModel.setData(trouserModel);
		return resModel;
	}	
	

	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/skirt", method = RequestMethod.PUT)
	public ResponseModel updateSkirt(@RequestBody @Valid PersonSkirtModel model, @PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonSkirtVO skirtVO = employeeVO.getCustomerSkirtVO();
		skirtVO.setQuntou(model.getQuntou());
		skirtVO.setQunchang(model.getQunchang());
		skirtVO.setZuoweishi(model.getZuoweishi());
		skirtVO.setZuoweipao(model.getZuoweipao());
		skirtVO.setQunjiao(model.getQunjiao());
		skirtVO.getCuttingSize().clear();
		for (String key : model.getCuttingSize().keySet()) {
			skirtVO.getCuttingSize().put(key, model.getCuttingSize().get(key));
		}
		orgAutoRepo.save(orgDBVO);
		return resModel;
	}

	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}/skirt", method = RequestMethod.GET)
	public ResponseModel loadSkirt(@PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		OrganizationEmployeeVO employeeVO = retrieveEmployeeVO(orgDBVO, employeeId, resModel);
		if (resModel.getCode() != WebConstants.RESPONSE_CODE_SUCCESS) {
			return resModel;
		}
		PersonSkirtVO skirtVO = employeeVO.getCustomerSkirtVO();
		PersonSkirtModel skirtModel = new PersonSkirtModel();
		skirtModel.setQuntou(skirtVO.getQuntou());
		skirtModel.setQunchang(skirtVO.getQunchang());
		skirtModel.setZuoweishi(skirtVO.getZuoweishi());
		skirtModel.setZuoweipao(skirtVO.getZuoweipao());
		skirtModel.setQunjiao(skirtVO.getQunjiao());
		for (String key : skirtVO.getCuttingSize().keySet()) {
			skirtModel.getCuttingSize().put(key, skirtVO.getCuttingSize().get(key));
		}
		resModel.setData(skirtModel);
		return resModel;
	}
	
	@RequestMapping(value = "/organization/{id}", method = RequestMethod.DELETE)
	public ResponseModel deleteOrganization(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		orgAutoRepo.delete(id);
		return resModel;
	}	
	
	@RequestMapping(value = "/organization/{organizationId}/employee/{employeeId}", method = RequestMethod.DELETE)
	public ResponseModel deleteEmployee(@PathVariable String organizationId, @PathVariable String employeeId) {
		ResponseModel resModel = new ResponseModel();
		OrganizationDBVO orgDBVO = orgAutoRepo.findOne(organizationId);
		for (OrganizationEmployeeVO employeeVO : orgDBVO.getEmployees()) {
			if (employeeId.equals(employeeVO.getId())) {
				orgDBVO.getEmployees().remove(employeeVO);
				break;
			}
		}
		orgAutoRepo.save(orgDBVO);
		return resModel;
	}		
	
}

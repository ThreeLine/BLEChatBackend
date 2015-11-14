package com.fnx.webapp.controller.customer;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.fnx.db.entity.customer.PersonDBVO;
import com.fnx.db.vo.customer.PersonSkirtVO;
import com.fnx.db.vo.customer.PersonStatureVO;
import com.fnx.db.vo.customer.PersonSuitVO;
import com.fnx.db.vo.customer.PersonTrouserVO;
import com.fnx.db.vo.customer.PersonTshirtVO;
import com.fnx.db.vo.customer.PersonVestVO;
import com.fnx.repository.mongo.customer.PersonAutoRepo;
import com.fnx.repository.mongo.customer.PersonRepo;
import com.fnx.webapp.controller.BaseController;
import com.fnx.webapp.model.ModelConstants;
import com.fnx.webapp.model.common.ResponseModel;
import com.fnx.webapp.model.common.UserDetailsModel;
import com.fnx.webapp.model.customer.PersonBasicModel;
import com.fnx.webapp.model.customer.PersonSearchModel;
import com.fnx.webapp.model.customer.PersonSearchRowVO;
import com.fnx.webapp.model.customer.PersonSkirtModel;
import com.fnx.webapp.model.customer.PersonStatureModel;
import com.fnx.webapp.model.customer.PersonSuitModel;
import com.fnx.webapp.model.customer.PersonTrouserModel;
import com.fnx.webapp.model.customer.PersonTshirtModel;
import com.fnx.webapp.model.customer.PersonVestModel;
import com.fnx.webapp.util.WebConstants;

@RestController
@RequestMapping("/customer")
public class PersonController extends BaseController {

	@Autowired
	private PersonAutoRepo customerAutoRepo;
	@Autowired
	private PersonRepo personRepo;

	@RequestMapping(value = "/person/basic", method = RequestMethod.POST)
	public ResponseModel createBasic(@RequestBody @Valid PersonBasicModel model, BindingResult result,
			Authentication authentication) {
		ResponseModel resModel = new ResponseModel();
		UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();

		if (StringUtils.isNotBlank(model.getEmail())) {
			Optional<PersonDBVO> optionalCustomer = customerAutoRepo
					.findByTenantIdAndEmail(userDetailsModel.getTenantId(), model.getEmail());
			if (optionalCustomer.isPresent()) {
				resModel.setCode(WebConstants.RESPONSE_CODE_FAILURE);
				resModel.setMsg(retrieveMessage("customer.error.email.exists"));
				return resModel;
			}
		}
		// check email exist
		Optional<PersonDBVO> mobileCustomer = customerAutoRepo.findByTenantIdAndMobile(userDetailsModel.getTenantId(),
				model.getMobile());
		if (mobileCustomer.isPresent()) {
			resModel.setCode(WebConstants.RESPONSE_CODE_FAILURE);
			resModel.setMsg(retrieveMessage("customer.error.mobile.exists"));
			return resModel;
		}

		PersonDBVO customerDBVO = new PersonDBVO();
		customerDBVO.setEmail(model.getEmail());
		customerDBVO.setMobile(model.getMobile());
		customerDBVO.setName(model.getName());
		if (ModelConstants.SEX_MALE.equals(model.getSex())) {
			customerDBVO.setSex(EntityConstants.SEX_MALE);
		} else if (ModelConstants.SEX_FEMALE.equals(model.getSex())) {
			customerDBVO.setSex(EntityConstants.SEX_FEMALE);
		}
		customerDBVO.setQq(model.getQq());
		customerDBVO.setWechat(model.getWechat());
		customerDBVO.setTenantId(userDetailsModel.getTenantId());
		customerAutoRepo.save(customerDBVO);
		model.setId(customerDBVO.getId());
		resModel.setData(model);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/basic", method = RequestMethod.PUT)
	public ResponseModel updateBasic(@RequestBody @Valid PersonBasicModel model, BindingResult result,
			@PathVariable String id, Authentication authentication) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
		if (StringUtils.isNotBlank(model.getEmail())) {
			if (!customer.getEmail().equals(model.getEmail())) {
				Optional<PersonDBVO> optionalCustomer = customerAutoRepo
						.findByTenantIdAndEmail(userDetailsModel.getTenantId(), model.getEmail());
				if (optionalCustomer.isPresent()) {
					resModel.setCode(WebConstants.RESPONSE_CODE_FAILURE);
					resModel.setMsg(retrieveMessage("customer.error.email.exists"));
					return resModel;
				}
			}
		}
		if (!customer.getMobile().equals(model.getMobile())) {
			Optional<PersonDBVO> optionalCustomer = customerAutoRepo
					.findByTenantIdAndMobile(userDetailsModel.getTenantId(), model.getMobile());
			if (optionalCustomer.isPresent()) {
				resModel.setCode(WebConstants.RESPONSE_CODE_FAILURE);
				resModel.setMsg(retrieveMessage("customer.error.mobile.exists"));
				return resModel;
			}
		}
		customer.setName(model.getName());
		customer.setMobile(model.getMobile());
		customer.setWechat(model.getWechat());
		customer.setQq(model.getQq());
		if (ModelConstants.SEX_MALE.equals(model.getSex())) {
			customer.setSex(EntityConstants.SEX_MALE);
		} else if (ModelConstants.SEX_FEMALE.equals(model.getSex())) {
			customer.setSex(EntityConstants.SEX_FEMALE);
		}
		customer.setEmail(model.getEmail());
		customerAutoRepo.save(customer);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/basic", method = RequestMethod.GET)
	public ResponseModel loadBasic(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonBasicModel personBasicModel = new PersonBasicModel();
		personBasicModel.setEmail(customer.getEmail());
		personBasicModel.setId(customer.getId());
		personBasicModel.setMobile(customer.getMobile());
		personBasicModel.setName(customer.getName());
		personBasicModel.setQq(customer.getQq());
		personBasicModel.setSex(customer.getSex());
		personBasicModel.setWechat(customer.getWechat());
		resModel.setData(personBasicModel);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}", method = RequestMethod.DELETE)
	public ResponseModel deletePerson(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		customerAutoRepo.delete(id);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/stature", method = RequestMethod.PUT)
	public ResponseModel updateStature(@RequestBody PersonStatureModel model, @PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonStatureVO customerStatureVO = customer.getCustomerStatureVO();
		customerStatureVO.setHeight(model.getHeight());
		customerStatureVO.setWeight(model.getWeight());
		customerStatureVO.setBack(model.getBack());
		customerStatureVO.setShoulder(model.getShoulder());
		customerStatureVO.setTripe(model.getTripe());
		customerStatureVO.setBody(model.getBody());
		customerAutoRepo.save(customer);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/stature", method = RequestMethod.GET)
	public ResponseModel loadStature(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonStatureVO customerStatureVO = customer.getCustomerStatureVO();
		PersonStatureModel statureModel = new PersonStatureModel();
		statureModel.setBack(customerStatureVO.getBack());
		statureModel.setBody(customerStatureVO.getBody());
		statureModel.setHeight(customerStatureVO.getHeight());
		statureModel.setShoulder(customerStatureVO.getShoulder());
		statureModel.setTripe(customerStatureVO.getTripe());
		statureModel.setWeight(customerStatureVO.getWeight());
		resModel.setData(statureModel);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/suit", method = RequestMethod.GET)
	public ResponseModel loadSuit(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonSuitVO customerSuitVO = customer.getCustomerSuitVO();
		PersonSuitModel personSuitModel = new PersonSuitModel();
		personSuitModel.setQianchang(customerSuitVO.getQianchang());
		personSuitModel.setHouzhongchang(customerSuitVO.getHouzhongchange());
		personSuitModel.setShangweishi(customerSuitVO.getShangweishi());
		personSuitModel.setShangweipao(customerSuitVO.getShangweipao());
		personSuitModel.setZhongweishi(customerSuitVO.getZhongweishi());
		personSuitModel.setZhongweipao(customerSuitVO.getZhongweishi());
		personSuitModel.setXiaweishi(customerSuitVO.getXiaweishi());
		personSuitModel.setXiaweipao(customerSuitVO.getXiaweipao());
		personSuitModel.setQianxiongkuo(customerSuitVO.getQianxiongkuo());
		personSuitModel.setHoubei(customerSuitVO.getHoubei());
		personSuitModel.setJiankuo(customerSuitVO.getJiankuo());
		personSuitModel.setQianxiaojian(customerSuitVO.getQianxiaojian());
		personSuitModel.setXiuchang(customerSuitVO.getXiuchang());
		personSuitModel.setXiukou(customerSuitVO.getXiukou());
		personSuitModel.setXionggao(customerSuitVO.getXionggao());
		personSuitModel.setYaozhi(customerSuitVO.getYaozhi());
		personSuitModel.setLingwei(customerSuitVO.getLingwei());
		for (String key : customerSuitVO.getCuttingSize().keySet()) {
			personSuitModel.getCuttingSize().put(key, customerSuitVO.getCuttingSize().get(key));
		}
		resModel.setData(personSuitModel);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/suit", method = RequestMethod.PUT)
	public ResponseModel updateSuit(@RequestBody @Valid PersonSuitModel model, @PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		customer.getCustomerSuitVO().setQianchang(model.getQianchang());
		customer.getCustomerSuitVO().setHouzhongchange(model.getHouzhongchang());
		customer.getCustomerSuitVO().setShangweishi(model.getShangweishi());
		customer.getCustomerSuitVO().setShangweipao(model.getShangweipao());
		customer.getCustomerSuitVO().setZhongweishi(model.getZhongweishi());
		customer.getCustomerSuitVO().setZhongweipao(model.getZhongweipao());
		customer.getCustomerSuitVO().setXiaweishi(model.getXiaweishi());
		customer.getCustomerSuitVO().setXiaweipao(model.getXiaweipao());
		customer.getCustomerSuitVO().setQianxiongkuo(model.getQianxiongkuo());
		customer.getCustomerSuitVO().setHoubei(model.getHoubei());
		customer.getCustomerSuitVO().setJiankuo(model.getJiankuo());
		customer.getCustomerSuitVO().setQianxiaojian(model.getQianxiaojian());
		customer.getCustomerSuitVO().setXiuchang(model.getXiuchang());
		customer.getCustomerSuitVO().setXiukou(model.getXiukou());
		customer.getCustomerSuitVO().setXionggao(model.getXionggao());
		customer.getCustomerSuitVO().setYaozhi(model.getYaozhi());
		customer.getCustomerSuitVO().setLingwei(model.getLingwei());
		customer.getCustomerSuitVO().getCuttingSize().clear();
		for (String key : model.getCuttingSize().keySet()) {
			customer.getCustomerSuitVO().getCuttingSize().put(key, model.getCuttingSize().get(key));
		}
		customerAutoRepo.save(customer);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/vest", method = RequestMethod.GET)
	public ResponseModel loadVest(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonVestVO customerVestVO = customer.getCustomerVestVO();
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

	@RequestMapping(value = "/person/{id}/vest", method = RequestMethod.PUT)
	public ResponseModel updateVest(@RequestBody @Valid PersonVestModel model, @PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonVestVO personVest = customer.getCustomerVestVO();
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
		customerAutoRepo.save(customer);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/shirt", method = RequestMethod.PUT)
	public ResponseModel updateTshirt(@RequestBody @Valid PersonTshirtModel model, @PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonTshirtVO customerTshirtVO = customer.getCustomerTshirtVO();
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
		customerAutoRepo.save(customer);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/shirt", method = RequestMethod.GET)
	public ResponseModel loadTshirt(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonTshirtVO customerTshirtVO = customer.getCustomerTshirtVO();
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

	@RequestMapping(value = "/person/{id}/trousers", method = RequestMethod.PUT)
	public ResponseModel updateTrousers(@RequestBody @Valid PersonTrouserModel model, @PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonTrouserVO customerTrouserVO = customer.getCustomerTrouserVO();
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
		customerAutoRepo.save(customer);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/trousers", method = RequestMethod.GET)
	public ResponseModel loadTrousers(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonTrouserVO trouserVO = customer.getCustomerTrouserVO();
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

	@RequestMapping(value = "/person/{id}/skirt", method = RequestMethod.PUT)
	public ResponseModel updateSkirt(@RequestBody @Valid PersonSkirtModel model, @PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonSkirtVO skirtVO = customer.getCustomerSkirtVO();
		skirtVO.setQuntou(model.getQuntou());
		skirtVO.setQunchang(model.getQunchang());
		skirtVO.setZuoweishi(model.getZuoweishi());
		skirtVO.setZuoweipao(model.getZuoweipao());
		skirtVO.setQunjiao(model.getQunjiao());
		skirtVO.getCuttingSize().clear();
		for (String key : model.getCuttingSize().keySet()) {
			skirtVO.getCuttingSize().put(key, model.getCuttingSize().get(key));
		}
		customerAutoRepo.save(customer);
		return resModel;
	}

	@RequestMapping(value = "/person/{id}/skirt", method = RequestMethod.GET)
	public ResponseModel loadSkirt(@PathVariable String id) {
		ResponseModel resModel = new ResponseModel();
		PersonDBVO customer = customerAutoRepo.findOne(id);
		if (customer == null) {
			resModel.setCode(WebConstants.RESPONSE_CODE_RESOURCE_NOT_EXIST);
			return resModel;
		}
		PersonSkirtVO skirtVO = customer.getCustomerSkirtVO();
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

	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public ResponseModel loadPersons(PersonSearchModel personSearchModel, Authentication authentication) {
		ResponseModel resModel = new ResponseModel();
		UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
		String name = personSearchModel.getName() == null ? "" : personSearchModel.getName().trim();
		String phone = personSearchModel.getMobile() == null ? "" : personSearchModel.getMobile().trim();

		Pageable page = new PageRequest(personSearchModel.getPageNumber(), personSearchModel.getPageSize(),
				personSearchModel.getSort());
		Page<PersonDBVO> pageObject = personRepo.findPersons(userDetailsModel.getTenantId(), name, phone, page);
		List<PersonDBVO> contentList = pageObject.getContent();
		personSearchModel.setTotal(pageObject.getTotalElements());
		for (PersonDBVO personDBVOItem : contentList) {
			PersonSearchRowVO personSearchRowVO = new PersonSearchRowVO();
			personSearchRowVO.setId(personDBVOItem.getId());
			personSearchRowVO.setEmail(personDBVOItem.getEmail());
			personSearchRowVO.setMobile(personDBVOItem.getMobile());
			personSearchRowVO.setName(personDBVOItem.getName());
			personSearchRowVO.setOther(personDBVOItem.getOther());
			personSearchRowVO.setQq(personDBVOItem.getQq());
			personSearchRowVO.setWechat(personDBVOItem.getWechat());
			if (personDBVOItem.getSex().equals(EntityConstants.SEX_FEMALE)) {
				personSearchRowVO.setSex(ModelConstants.SEX_FEMALE);
			} else {
				personSearchRowVO.setSex(ModelConstants.SEX_MALE);
			}
			personSearchModel.getRows().add(personSearchRowVO);
		}
		resModel.setData(personSearchModel);
		return resModel;
	}

}

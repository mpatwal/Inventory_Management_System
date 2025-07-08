package com.mona.inventoryms.controllers;

import com.mona.inventoryms.models.Country;
import com.mona.inventoryms.models.State;
import com.mona.inventoryms.services.CountryService;
import com.mona.inventoryms.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StateController {
	@Autowired private StateService stateService;
	@Autowired private CountryService countryService;

	@GetMapping("/states")
	public List<State> addModelAttribute(){
		return stateService.findAll();
	}

	@GetMapping("/state/{id}")
	public State findOne(@PathVariable("id") Integer id){
		return stateService.findById(id);
	}

	@PutMapping("/state/{id}")
	public State editState(@RequestBody State state){
		return stateService.save(state);
	}

	//Add State
	@PostMapping(value="/states")
	public State addNew(@RequestBody State state) {
		return stateService.save(state);
	}

	@RequestMapping(value="/parameters/states/delete/{id}", method = {RequestMethod.DELETE, RequestMethod.GET})
	public String delete(@PathVariable Integer id) {
		stateService.delete(id);
		return "redirect:/parameters/states";
	}

}

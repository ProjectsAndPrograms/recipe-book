package com.oranbyte.recipebook.seeder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oranbyte.recipebook.entity.Unit;
import com.oranbyte.recipebook.repository.UnitRepository;

@Component
public class UnitSeeder implements Seeder{

	@Autowired
	private UnitRepository unitRepository;
	
	@Override
	public void seed() {

	    if (unitRepository.count() == 0) {
	        List<Unit> units = new ArrayList<>();

	        units.add(Unit.builder().name("Piece").abbreviation("pcs").build());
	        units.add(Unit.builder().name("Gram").abbreviation("g").build());
	        units.add(Unit.builder().name("Milliliter").abbreviation("ml").build());
	        units.add(Unit.builder().name("Liter").abbreviation("l").build());
	        units.add(Unit.builder().name("Cup").abbreviation("cup").build());
	        units.add(Unit.builder().name("Teaspoon").abbreviation("tsp").build());
	        units.add(Unit.builder().name("Tablespoon").abbreviation("tbsp").build());
	        units.add(Unit.builder().name("Drop").abbreviation("drop").build());
	        units.add(Unit.builder().name("Bunch").abbreviation("bnch").build());
	        units.add(Unit.builder().name("Slice").abbreviation("slc").build());
	        units.add(Unit.builder().name("Can").abbreviation("can").build());
	        units.add(Unit.builder().name("Jar").abbreviation("jar").build());
	        units.add(Unit.builder().name("Bottle").abbreviation("btl").build());
	        units.add(Unit.builder().name("Box").abbreviation("box").build());
	        units.add(Unit.builder().name("Stick").abbreviation("stk").build());
	        units.add(Unit.builder().name("Head").abbreviation("hd").build());
	        units.add(Unit.builder().name("Sprig").abbreviation("sprg").build());
	        units.add(Unit.builder().name("Packet").abbreviation("pkt").build());
	        units.add(Unit.builder().name("Sheet").abbreviation("sht").build());
	        units.add(Unit.builder().name("Serving").abbreviation("srv").build());

	        unitRepository.saveAll(units);
	    }

	}


}

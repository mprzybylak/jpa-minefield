package com.mprzybylak.minefields.jpa.id;

import com.mprzybylak.minefields.jpa.id.base.IdGeneratorBaseTest;


public class IncrementGeneratorEntityTest extends IdGeneratorBaseTest<Long, IncrementGeneratorEntity>{

	@Override
	protected IncrementGeneratorEntity createInstance() {
		return new IncrementGeneratorEntity();
	}
}

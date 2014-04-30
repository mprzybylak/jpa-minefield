package com.mprzybylak.minefields.jpa.id;

import com.mprzybylak.minefields.jpa.id.base.IdGeneratorBaseTest;

public class UUIDGeneratorEntityTest extends IdGeneratorBaseTest<String, UUIDGeneratorEntity> {

	@Override
	protected UUIDGeneratorEntity createInstance() {
		return new UUIDGeneratorEntity();
	}
}

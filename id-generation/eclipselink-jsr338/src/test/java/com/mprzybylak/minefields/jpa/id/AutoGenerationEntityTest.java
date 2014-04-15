package com.mprzybylak.minefields.jpa.id;

import com.mprzybylak.minefields.jpa.id.base.IdGeneratorBaseTest;

public class AutoGenerationEntityTest extends IdGeneratorBaseTest<AutoGenerationEntity> {
	@Override
	protected AutoGenerationEntity createInstance() {
		return new AutoGenerationEntity();
	}
}

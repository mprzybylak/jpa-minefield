package com.mprzybylak.minefields.jpa.id;

import com.mprzybylak.minefields.jpa.id.base.IdGeneratorBaseTest;

public class TableGenerationEntityTest extends IdGeneratorBaseTest<Long, TableGenerationEntity> {
	@Override
	protected TableGenerationEntity createInstance() {
		return new TableGenerationEntity();
	}
}

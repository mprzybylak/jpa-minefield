package com.mprzybylak.minefields.jpa.id;

import com.mprzybylak.minefields.jpa.id.base.IdGeneratorBaseTest;

public class SequenceGenerationEntityTest extends IdGeneratorBaseTest<SequenceGenerationEntity> {
	@Override
	protected SequenceGenerationEntity createInstance() {
		return new SequenceGenerationEntity();
	}
}

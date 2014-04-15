package com.mprzybylak.minefields.jpa.id;

import com.mprzybylak.minefields.jpa.id.base.IdGeneratorBaseTest;

public class IdentityGenerationEntityTest extends IdGeneratorBaseTest<IdentityGenerationEntity> {

	@Override
	protected IdentityGenerationEntity createInstance() {
		return new IdentityGenerationEntity();
	}
}

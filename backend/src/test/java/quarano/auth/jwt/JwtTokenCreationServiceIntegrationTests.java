/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package quarano.auth.jwt;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import quarano.QuaranoIntegrationTest;
import quarano.auth.AccountRepository;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Oliver Drotbohm
 */
@QuaranoIntegrationTest
@RequiredArgsConstructor
class JwtTokenCreationServiceIntegrationTests {

	private final JwtTokenCreationService service;
	private final JwtTokenService validation;
	private final AccountRepository accounts;

	@Test
	@Transactional
	void generatesTokenForDepartmentStaff() {

		assertThat(accounts.findByUsername("agent1") //
				.map(service::generateToken)) //
						.hasValueSatisfying(it -> {
							assertThat(validation.getTrackedPersonIdFromToken(it)).isNull();
						});
	}
}

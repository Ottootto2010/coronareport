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
package quarano.tracking.web;

import static org.assertj.core.api.Assertions.*;

import lombok.RequiredArgsConstructor;
import quarano.QuaranoIntegrationTest;
import quarano.tracking.TrackedPersonDataInitializer;
import quarano.tracking.TrackedPersonRepository;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Oliver Drotbohm
 */
@QuaranoIntegrationTest
@RequiredArgsConstructor
class DiaryControllerIntegrationTests {

	private final DiaryController controller;
	private final TrackedPersonRepository repository;

	@Test
	void rendersDiaryEntryCorrectly() throws Exception {

		var person = repository.findById(TrackedPersonDataInitializer.VALID_TRACKED_PERSON3_ID_DEP2).orElseThrow();
		var entry = person.getDiary().iterator().next();

		ResponseEntity<?> entity = controller.getDiaryEntry(entry.getId(), person);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
}

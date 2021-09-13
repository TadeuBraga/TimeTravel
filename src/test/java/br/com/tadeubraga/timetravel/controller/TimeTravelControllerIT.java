package br.com.tadeubraga.timetravel.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.tadeubraga.timetravel.controller.dto.PlaceDto;
import br.com.tadeubraga.timetravel.controller.dto.TimeTravelDto;
import br.com.tadeubraga.timetravel.model.TimeTravel;
import br.com.tadeubraga.timetravel.service.TimeTravelService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TimeTravelController.class)
class TimeTravelControllerIT {

	@MockBean
	TimeTravelService timeTravelService;

	@Autowired
	MockMvc mockMvc;

	static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	TimeTravelDto clone(TimeTravelDto timeTravelDto) throws JsonMappingException, JsonProcessingException {
		return OBJECT_MAPPER.readValue(OBJECT_MAPPER.writeValueAsString(timeTravelDto), TimeTravelDto.class);
	}

	Calendar getZeroHourCalendarInstance() {
		Calendar result = Calendar.getInstance();
		result.set(Calendar.HOUR, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		result.set(Calendar.DST_OFFSET, 0);
		return result;
	}

	@Test
	void should_Save_When_AValidTimeTravelIsGiven() throws Exception {
		var personalGalaticId = "nick76";
		var date = getZeroHourCalendarInstance();
		var place = PlaceDto.builder().country("England").city("London").build();
		TimeTravelDto t = TimeTravelDto.builder().personalGalaticId(personalGalaticId).date(date).place(place).build();
		TimeTravel mockedResult = clone(t).toModel();
		mockedResult.setId(1L);
		mockedResult.getPlace().setId(1L);
		when(timeTravelService.save(any(TimeTravel.class))).thenReturn(mockedResult);

		MvcResult mvcResult = mockMvc.perform(post("/time-travels").contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(t))).andExpect(status().is(201)).andReturn();

		var result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), TimeTravel.class);
		assertThat(result, equalTo(mockedResult));
	}

}

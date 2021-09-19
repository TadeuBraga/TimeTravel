package br.com.tadeubraga.timetravel.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
import com.fasterxml.jackson.databind.type.CollectionType;

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
		TimeZone zone = TimeZone.getTimeZone("UTC");
		Calendar result = Calendar.getInstance(zone);
		result.set(Calendar.HOUR_OF_DAY, 0);
		result.set(Calendar.MINUTE, 0);
		result.set(Calendar.SECOND, 0);
		result.set(Calendar.MILLISECOND, 0);
		return result;
	}

	public TimeTravelDto getTimeTravelDto() {
		var personalGalaticId = "nick76";
		var date = getZeroHourCalendarInstance();
		var place = PlaceDto.builder().country("England").city("London").build();
		return TimeTravelDto.builder().personalGalaticId(personalGalaticId).date(date).place(place).build();
	}

	public TimeTravel getTimeTravel() throws JsonMappingException, JsonProcessingException {
		TimeTravel timeTravel = getTimeTravelDto().toModel();
		timeTravel.setId(1L);
		timeTravel.getPlace().setId(1L);
		return timeTravel;
	}

	@Test
	void should_Save_When_AValidTimeTravelIsGiven() throws Exception {
		TimeTravelDto t = getTimeTravelDto();
		TimeTravel mockedResult = getTimeTravel();
		when(timeTravelService.save(any(TimeTravel.class))).thenReturn(mockedResult);

		MvcResult mvcResult = mockMvc.perform(post("/time-travels").contentType(MediaType.APPLICATION_JSON)
				.content(OBJECT_MAPPER.writeValueAsString(t))).andExpect(status().is(201)).andReturn();

		var result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), TimeTravel.class);
		assertThat(result, equalTo(mockedResult));
	}

	@Test
	void should_FindAll_When_MethodIsCalled() throws Exception {
		var timeTravel = getTimeTravel();
		when(timeTravelService.findById(1L)).thenReturn(timeTravel);

		MvcResult mvcResult = mockMvc.perform(get("/time-travels/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andReturn();
		var result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), TimeTravelDto.class);

		assertThat(result, equalTo(TimeTravelDto.ofModel(timeTravel)));
	}

	@Test
	void should_FindById_When_MethodIsCalled() throws Exception {
		var timeTravel = getTimeTravel();
		when(timeTravelService.findAll()).thenReturn(List.of(timeTravel));

		MvcResult mvcResult = mockMvc.perform(get("/time-travels/").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200)).andReturn();
		CollectionType listType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, TimeTravelDto.class);
		List<TimeTravelDto> result = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), listType);

		assertThat(result.get(0), equalTo(TimeTravelDto.ofModel(timeTravel)));
	}
}

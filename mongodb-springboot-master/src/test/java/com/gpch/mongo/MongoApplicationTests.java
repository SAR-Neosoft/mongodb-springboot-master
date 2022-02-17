package com.gpch.mongo;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpch.mongo.controller.ReservationThymeleafController;
import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.ReservationRepository;
import com.gpch.mongo.repository.SearchRepository;
import com.gpch.mongo.service.ReservationService;
import com.gpch.mongo.service.SequenceGeneratorService;



//@SpringBootTest
@WebMvcTest(ReservationThymeleafController.class)
class MongoApplicationTests {

	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;

	@MockBean
	private  ReservationRepository reservationRepository;
	
	@MockBean
	 private ReservationService reservationService;
	
	@MockBean
	private SequenceGeneratorService sequenceGeneratorService;
	
	@MockBean
	private SearchRepository searchRepository;
	
	@Test
	void contextLoads() {
	}
	

	@Test
	public void added_Success() throws Exception {
		String str = "2016-03-04 11:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);


		Reservation r=Reservation.builder()
				.id((long) 1)
				.fname("aaa")
				.lname("bbb")
				.email("Abc@gmail.com")
				.mobile("1234567890")
				.state("karnataka")
				.city("Banglore")
				.modificationTime(dateTime)
				.date(dateTime)
				.build();
				
//		 if(reservationRepository.findById(r.getId()).isPresent()) {
//
//		Mockito.when(reservationService.saveReservation(r)).thenReturn(r);
//		 }else {
//			 r.setId(sequenceGeneratorService.generateSequence(Reservation.SEQUENCE_NAME));
		 Mockito.when( reservationService.saveReservation(r)).thenReturn(r);
//		 }
		MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/save-reservation")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(r));

		mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()));
	}
	
	  @Test
	    public void deleteReservationById_success() throws Exception {
	        
		  String str = "2016-03-04 11:30";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(str, formatter);


			Reservation r=Reservation.builder()
					.id((long) 1)
					.fname("aaa")
					.lname("bbb")
					.email("Abc@gmail.com")
					.mobile("1234567890")
					.state("karnataka")
					.city("Banglore")
					.modificationTime(dateTime)
					.date(dateTime)
					.build();
	    	
//	    	Mockito.when(userRepository.deleteById(u.getId()))
//	        .thenReturn();
	    	
	    	
	        mockMvc.perform(MockMvcRequestBuilders
	                .get("/delete-reservation/1")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk());              
	    }
	  
	  
		@Test
		public void getAllReservations_Success() throws Exception {
			String str = "2016-03-04 11:30";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime dateTime = LocalDateTime.parse(str, formatter);


			Reservation r=Reservation.builder()
					.id((long) 1)
					.fname("aaa")
					.lname("bbb")
					.email("Abc@gmail.com")
					.mobile("1234567890")
					.state("karnataka")
					.city("Banglore")
					.modificationTime(dateTime)
					.date(dateTime)
					.build();
					

	        Mockito.when(reservationService.getAllReservations())
	        				.thenReturn(Arrays.asList(r));

	        mockMvc.perform(MockMvcRequestBuilders
	                .get("/reservations-ui")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$", notNullValue()));
	    }
		
		
		  @Test
		    public void editReservationById_success() throws Exception {
		        
			  String str = "2016-03-04 11:30";
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime dateTime = LocalDateTime.parse(str, formatter);


				Reservation r=Reservation.builder()
						.id((long) 1)
						.fname("aaa")
						.lname("bbb")
						.email("Abc@gmail.com")
						.mobile("1234567890")
						.state("karnataka")
						.city("Banglore")
						.modificationTime(dateTime)
						.date(dateTime)
						.build();
				
//				 Reservation reservation = id.isPresent() ?
//			                reservationService.findReservationById(id.get()).get() : new Reservation();
				
				 Mockito.when(  reservationService.findReservationById(r.getId()))
				 .thenReturn(Optional.of(r));
		    	
		    	
		        mockMvc.perform(MockMvcRequestBuilders
		                .get("/edit-add-reservation/1")
		                .contentType(MediaType.APPLICATION_JSON))
		                .andExpect(status().isOk());              
		    }
		  
			


		

}

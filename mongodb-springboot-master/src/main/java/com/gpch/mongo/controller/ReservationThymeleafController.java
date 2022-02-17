package com.gpch.mongo.controller;

import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.SearchRepository;
import com.gpch.mongo.service.ReservationService;
import com.gpch.mongo.service.SequenceGeneratorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Controller
public class ReservationThymeleafController {
	private static final Logger log = LoggerFactory.getLogger(ReservationThymeleafController.class);


    private ReservationService reservationService;
    
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	  
		@Autowired
		private SearchRepository searchRepository;
	
    @Autowired
    public ReservationThymeleafController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations-ui")
    public String reservations(Model model) {
    	log.info("GET:/reservations-ui-->Called");
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservations";
    }

    @GetMapping("/delete-reservation/{id}")
    public String removeReservation(@PathVariable("id") Long id, Model model) {
    	log.info("User with id "+id+"deleted");
        reservationService.deleteReservationById(id);
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservations";
    }

    @GetMapping(value = {"/edit-add-reservation/{id}", "/edit-add-reservation"})
    public String editReservation(@PathVariable("id") Optional<Long> id, Model model) {
        Reservation reservation = id.isPresent() ?
                reservationService.findReservationById(id.get()).get() : new Reservation();
       
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        LocalDateTime modTime = LocalDateTime.now();

//        System.out.println(modTime);
//
//        System.out.println("2nd------"+formatter.format(modTime));
        
        model.addAttribute("modTime", formatter.format(modTime));
        
        model.addAttribute("reservation", reservation);
        return "add-edit";
    }

    @PostMapping("/save-reservation")
    public String editReservation(@ModelAttribute("reservation") @Valid Reservation reservation,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
        	
            return "add-edit";
        }
        log.info("User Saved Successfully");
        reservationService.saveReservation(reservation);
        return "redirect:reservations-ui";
    }
    
    
    @RequestMapping(value = "/search")
    public String search(Model model, @RequestParam String search) {
    	log.info("Searched "+ " "+search);
        model.addAttribute("reservations", searchRepository.searchReservations(search));
        model.addAttribute("search", search);
        return "reservations";
    }
    
}

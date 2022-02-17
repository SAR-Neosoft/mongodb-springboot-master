package com.gpch.mongo.service;


import com.gpch.mongo.model.Reservation;
import com.gpch.mongo.repository.ReservationRepository;
import com.neosoft.mongo.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    
    @Autowired
	private SequenceGeneratorService sequenceGeneratorService;


    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation saveReservation(Reservation reservation){
   	 if(reservationRepository.findById(reservation.getId()).isPresent()) {
   		return reservationRepository.save(reservation);
   	 }
   	reservation.setId(sequenceGeneratorService.generateSequence(Reservation.SEQUENCE_NAME));
         return reservationRepository.save(reservation);
   	 
         
    }
    
//    public Reservation editReservation(Reservation reservation, Long reservationId) throws ResourceNotFoundException{
//    	 
//    	reservationRepository.findById(reservationId)
//		.orElseThrow(()->new ResourceNotFoundException("Employee not found for this id::"+reservationId));
//          reservation.setId(sequenceGeneratorService.generateSequence(Reservation.SEQUENCE_NAME));
//        return reservationRepository.save(reservation);
//    }

    public Iterable<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    public void deleteAllReservations(){
        reservationRepository.deleteAll();
    }

    public void deleteReservationById(Long id){
        reservationRepository.deleteById(id);
    }

    public Optional<Reservation> findReservationById(Long id){
        return reservationRepository.findById(id);
    }
}

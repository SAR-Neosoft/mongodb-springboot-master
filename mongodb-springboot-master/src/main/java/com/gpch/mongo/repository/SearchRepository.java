package com.gpch.mongo.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gpch.mongo.model.Reservation;

@Repository
public class SearchRepository {
	// MongoTemplate basic api that provides a set of rich features for interacting with MongoDB and acts as a central class for Spring’s MongoDB support
	 @Autowired
	    MongoTemplate mongoTemplate;

	    public List<Reservation> searchReservations(String text) {
	        return mongoTemplate.find(Query.query(new Criteria()
	                        .orOperator(Criteria.where("fname").regex(text, "i"),
	                                    Criteria.where("lname").regex(text, "i"),
	                                    Criteria.where("mobile").regex(text, "i"),
	                                    Criteria.where("email").regex(text, "i"))
	                        ), Reservation.class);
	        
	        //Criteria :Central class for creating queries. It follows a fluent API style so that you can easily chain together multiple criteria
	    }

	}
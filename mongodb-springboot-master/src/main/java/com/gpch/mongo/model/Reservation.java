package com.gpch.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@Document(collection = "reservations")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
	
	//which means the field with @Transient is ignored by mapping framework and the field not mapped to any database column
		@Transient
		public static final String SEQUENCE_NAME="users_sequence";
	
    @Id
    private long id;
    
    @NotBlank(message="Please provide a name.")
    private String fname;
    
    @NotBlank(message="Please provide a name.")
    private String lname;
    
    @NotBlank(message="Please provide a email.")
    private String email;
    
    @NotBlank(message="Please provide a Mobile No.")
    private String mobile ;
    
    @NotBlank(message="Please provide a State.")
    private String state;
    
    @NotBlank(message="Please provide a city.")
    private String city;
    
 
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime modificationTime;
    
    @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
    @NotNull(message="Please provide a date whit the format dd-MM-yyyy HH:mm")
    private LocalDateTime date;
}

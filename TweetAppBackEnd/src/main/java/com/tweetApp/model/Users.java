package com.tweetApp.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Users {

	private String loginId;
	private String password;
	private String firstName;
	private String lastName;
	private String gender;
	private LocalDate dateOfBirth;
	private LocalDateTime lastSeen;
	private boolean isActive;
	private String mobileNo;
}

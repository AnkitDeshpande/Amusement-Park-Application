package com.masai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Addresses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer addressId;

	@NotBlank(message = "City is required.")
	private String city;

	@NotBlank(message = "State is required.")
	private String state;

	@NotBlank(message = "Pincode is required.")
	@Pattern(regexp = "\\d{6}", message = "Pincode must be a 6-digit number.")
	private String pincode;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}

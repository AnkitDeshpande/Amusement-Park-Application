package com.masai.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Parks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Park {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer parkId;

	@NotBlank(message = "Name is required.")
	private String name;

	@NotBlank(message = "Location is required.")
	private String location;

	@NotBlank(message = "Description is required.")
	@Size(min = 10, message = "Description must be at least 10 characters long.")
	private String description;

	@NotBlank(message = "Opening hours are required.")
	private String openingHours;

	@NotBlank(message = "Closing hours are required.")
	private String closingHours;

	private boolean isDeleted = false;

	@OneToMany(mappedBy = "park", cascade = CascadeType.ALL)
	private Set<Review> reviews = new HashSet<Review>();

	@JsonIgnore
	@OneToMany(mappedBy = "park", cascade = CascadeType.ALL)
	private Set<Ticket> tickets = new HashSet<Ticket>();

	@OneToMany(mappedBy = "park", cascade = CascadeType.ALL)
	private Set<Activity> activities = new HashSet<Activity>();

}

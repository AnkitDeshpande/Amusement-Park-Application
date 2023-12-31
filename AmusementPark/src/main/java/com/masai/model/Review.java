package com.masai.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "park_id")
	private Park park;

	@NotNull(message = "Rating is required.")
	@Min(value = 1, message = "Rating must be between 1 and 5 stars.")
	@Max(value = 5, message = "Rating must be between 1 and 5 stars.")
	private Integer rating;

	@NotNull(message = "Comment is required.")
	private String comment;

	private boolean isDeleted = false;

}

package com.insurence.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SQLDelete(sql = "UPDATE UserNotifications SET is_seen='true' WHERE id=?")
@Where(clause = "is_seen='false'")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserNotifications {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	
	private String msg;

	private Boolean isSeen;
	
	private LocalDateTime sentAt;
	
	@ManyToOne
	@JoinColumn(name = "user_Id")
	@JsonIgnore
	private User user;
}

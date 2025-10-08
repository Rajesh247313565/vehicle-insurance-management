package com.insurence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.insurence.entity.UserNotifications;

public interface NotificationRepository extends JpaRepository<UserNotifications, Long> {

}

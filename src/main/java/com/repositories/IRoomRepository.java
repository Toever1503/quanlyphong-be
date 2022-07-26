package com.repositories;

import com.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomRepository extends JpaRepository<RoomEntity, Long> {
}

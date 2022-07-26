package com.repositories;

import com.entities.RoomUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomUserRepository extends JpaRepository<RoomUserEntity, Long> {
    RoomUserEntity findByRoomIdAndUserId(Long roomId, Long userId);
}

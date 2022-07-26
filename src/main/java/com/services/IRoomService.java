package com.services;

import com.dtos.RoomDto;
import com.entities.RoomEntity;
import com.models.RoomModel;

public interface IRoomService extends IBaseService<RoomEntity, RoomModel, Long, RoomDto> {

    boolean reserveRoom(Long id);

    boolean rentRoom(Long id);
}

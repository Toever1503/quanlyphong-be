package com.dtos;

import com.entities.RoleEntity;
import com.entities.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto {

    private Long id;

    private String roomName;

    private Double roomPrice;

    private String roomStatus;
    private String roomImage;

    private String roomDescription;

    private Date createdDate;

    private Date updatedDate;
    private boolean hasRented;

    public static RoomDto toDto(RoomEntity entity) {
        if (entity == null) return null;
        return RoomDto.builder()
                .id(entity.getId())
                .roomName(entity.getRoomName())
                .roomPrice(entity.getRoomPrice())
                .roomStatus(entity.getRoomStatus())
                .roomImage(entity.getRoomImage())
                .roomDescription(entity.getRoomDescription())
                .createdDate(entity.getCreatedDate())
                .updatedDate(entity.getUpdatedDate())
                .build();
    }

}

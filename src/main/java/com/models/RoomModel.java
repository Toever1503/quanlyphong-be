package com.models;

import com.entities.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomModel {

    private Long id;

    private String roomName;

    private Double roomPrice;

    private String roomDescription;
    private MultipartFile roomImage;

    public static RoomEntity toEntity(RoomModel roomModel) {
        return RoomEntity.builder()
                .id(roomModel.getId())
                .roomName(roomModel.getRoomName())
                .roomPrice(roomModel.getRoomPrice())
                .roomDescription(roomModel.getRoomDescription())
                .build();
    }
}

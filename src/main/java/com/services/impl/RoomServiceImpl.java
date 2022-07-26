package com.services.impl;

import com.Utils.FileUtilsService;
import com.Utils.SecurityUtils;
import com.dtos.RoomDto;
import com.entities.RoomEntity;
import com.entities.RoomUserEntity;
import com.enums.ERoomStatus;
import com.models.RoomModel;
import com.repositories.IRoomRepository;
import com.repositories.IRoomUserRepository;
import com.services.IRoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService {

    private final IRoomRepository roomRepository;
    private final IRoomUserRepository roomUserRepository;

    public RoomServiceImpl(IRoomRepository roomRepository, IRoomUserRepository roomUserRepository) {
        this.roomRepository = roomRepository;
        this.roomUserRepository = roomUserRepository;
    }

    @Override
    public List<RoomDto> findAll() {
        return null;
    }

    @Override
    public Page<RoomDto> findAll(Pageable page) {
        return this.roomRepository.findAll(page).map(room -> {
            RoomDto dto = RoomDto.toDto(room);
            dto.setHasRented(this.roomUserRepository.findByRoomIdAndUserId(room.getId(), SecurityUtils.getCurrentUserId()) != null);
            return dto;
        });
    }

    @Override
    public List<RoomDto> findAll(Specification<RoomEntity> specs) {
        return null;
    }

    @Override
    public RoomDto findOne(Specification<RoomEntity> specs) {
        return null;
    }

    @Override
    public Page<RoomEntity> findAll(Pageable page, Specification<RoomEntity> specs) {
        return null;
    }

    @Override
    public RoomDto findById(Long id) {
        return null;
    }

    private RoomEntity findRoomById(Long id) {
        return this.roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @Override
    public RoomDto add(RoomModel model) {
        RoomEntity entity = RoomModel.toEntity(model);
        entity.setRoomStatus(ERoomStatus.AVAILABLE.name());
        if (model.getRoomImage() != null) {
            try {
                entity.setRoomImage(FileUtilsService.uploadFile(model.getRoomImage()));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Upload file failed");
            }
        } else throw new RuntimeException("Room image is required");
        return RoomDto.toDto(this.roomRepository.saveAndFlush(entity));
    }

    @Override
    public List<RoomDto> adds(List<RoomModel> model) {
        return null;
    }

    @Override
    public RoomDto update(RoomModel model) {
        RoomEntity originalEntity = this.findRoomById(model.getId());
        RoomEntity entity = RoomModel.toEntity(model);
        entity.setKeepingBy(originalEntity.getKeepingBy());
        entity.setRoomStatus(originalEntity.getRoomStatus());
        if (model.getRoomImage() != null) {
            FileUtilsService.deleteFile(originalEntity.getRoomImage());
            try {
                entity.setRoomImage(FileUtilsService.uploadFile(model.getRoomImage()));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Upload file failed");
            }
        }
        return RoomDto.toDto(this.roomRepository.saveAndFlush(entity));
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }

    @Override
    public boolean deleteByIds(List<Long> ids) {
        return false;
    }

    @Override
    public boolean reserveRoom(Long id) {
        RoomEntity roomEntity = this.findRoomById(id);
        if (!roomEntity.getRoomStatus().equals(ERoomStatus.AVAILABLE.name()))
            throw new RuntimeException("Room is not available");
        roomEntity.setRoomStatus(ERoomStatus.KEEPING.name());
        roomEntity.setKeepingBy(SecurityUtils.getCurrentUser().getUser());
        return this.roomRepository.saveAndFlush(roomEntity) != null;
    }

    @Override
    public boolean rentRoom(Long id) {
        RoomEntity roomEntity = this.roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        RoomUserEntity roomUserEntity = new RoomUserEntity();
        roomUserEntity.setRoomId(roomEntity.getId());
        roomUserEntity.setUserId(SecurityUtils.getCurrentUserId());
        roomUserEntity.setRentedPrice(roomEntity.getRoomPrice());

        roomEntity.setRoomStatus(ERoomStatus.RENTED.name());

        this.roomUserRepository.saveAndFlush(roomUserEntity);
        return this.roomRepository.saveAndFlush(roomEntity) != null;
    }
}

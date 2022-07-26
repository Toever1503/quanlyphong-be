package com.resources;

import com.dtos.ResponseDto;
import com.dtos.RoomDto;
import com.models.RoomModel;
import com.services.IRoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomResources {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final IRoomService roomService;

    public RoomResources(IRoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public Page<RoomDto> getAllRooms(Pageable page) {
        return this.roomService.findAll(page);
    }

    @PostMapping
    public ResponseDto createRoom(RoomModel roomModel) {
        roomModel.setId(null);
        return ResponseDto.of(roomService.add(roomModel), "Create room");
    }

    @PutMapping("{id}")
    public ResponseDto updateRoom(@PathVariable Long id, RoomModel roomModel) {
        roomModel.setId(id);
        return ResponseDto.of(roomService.update(roomModel), "Update room id: ".concat(id.toString()));
    }

    @PostMapping("reserve/{id}")
    public ResponseDto reserveRoom(@PathVariable Long id) {
        return ResponseDto.of(roomService.reserveRoom(id) == true, "Reserve room".concat(id.toString()));
    }

    @PostMapping("rent/{id}")
    public ResponseDto rentRoom(@PathVariable Long id) {
        return ResponseDto.of(roomService.rentRoom(id), "Rent room".concat(id.toString()));
    }
}

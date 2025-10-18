package com.backend.videocall;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/v1/meetings")
public class MeetingController {

    private Map<String, List<String>> meetings = new ConcurrentHashMap<>();

    @PostMapping("/create")
    public String createMeeting(@RequestParam String username) {
        String roomId = UUID.randomUUID().toString();
        meetings.put(roomId, new ArrayList<>(List.of(username)));
        return roomId;
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinMeeting(@RequestParam String roomId, @RequestParam String username) {
        List<String> users = meetings.get(roomId);
        if (users == null) {
            return ResponseEntity.status(404).body("Room not found");
        }
        users.add(username);
        return ResponseEntity.ok("Joined successfully");
    }

    @GetMapping("/{roomId}/users")
    public ResponseEntity<List<String>> getUsersInRoom(@PathVariable String roomId) {
        List<String> users = meetings.get(roomId);
        if (users == null) return ResponseEntity.status(404).build();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/all")
    public Map<String, List<String>> getAllMeetings() {
        return meetings;
    }
}

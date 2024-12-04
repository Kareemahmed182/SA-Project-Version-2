package service;

import dto.AttendeeDTO;
import repositories.AttendeeRepository;

import java.util.List;
import java.util.stream.Collectors;

public class AttendeeService {
    private final AttendeeRepository attendeeRepository;

    public AttendeeService(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    public AttendeeDTO registerAttendee(String name, String email) {
        return attendeeRepository.addAttendee(name, email);
    }

    public List<AttendeeDTO> getAllAttendees() {
        return attendeeRepository.getAllAttendees().stream()
                .map(attendee -> new AttendeeDTO(attendee.getId(), attendee.getName(), attendee.getEmail()))
                .collect(Collectors.toList());
    }
}

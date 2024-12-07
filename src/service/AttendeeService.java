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
        var newAttendee = attendeeRepository.addAttendee(name, email);
        return new AttendeeDTO(newAttendee.getId(), newAttendee.getName(), newAttendee.getEmail());
    }

    public List<AttendeeDTO> getAllAttendees() {
        return attendeeRepository.getAllAttendees().stream()
                .map(attendee -> new AttendeeDTO(attendee.getId(), attendee.getName(), attendee.getEmail()))
                .collect(Collectors.toList());
    }

    public AttendeeDTO getAttendeeByUsername(String username) {
        var attendee = attendeeRepository.getAttendeeByUsername(username);
        if (attendee != null) {
            return new AttendeeDTO(attendee.getId(), attendee.getName(), attendee.getEmail());
        }
        return null;
    }
}

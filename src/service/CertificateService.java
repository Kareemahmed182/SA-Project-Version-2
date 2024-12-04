package service;

import repositories.AttendeeRepository;

public class CertificateService {
    private final AttendeeRepository attendeeRepository;

    public CertificateService(AttendeeRepository attendeeRepository) {
        this.attendeeRepository = attendeeRepository;
    }

    public String generateCertificate(int attendeeId, String conferenceName) {
        // Implement logic to fetch attendee and generate the certificate
        return "Certificate for attendee " + attendeeId + " at " + conferenceName;
    }
}

package service;

import repositories.CertificateRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CertificateService {
    private final CertificateRepository certificateRepository;

    public CertificateService(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    public List<String> getCertificatesForAttendee(int attendeeId) {
        return certificateRepository.getCertificatesByAttendee(attendeeId).stream()
                .map(certificate -> "Certificate for " + certificate.getConferenceName())
                .collect(Collectors.toList());
    }
}

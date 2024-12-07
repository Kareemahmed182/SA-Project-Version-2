package repositories;

import data_handlers.CertificateDataHandler;
import domain.Certificate;
import java.util.stream.Collectors; // Ensure this import is present

import java.util.ArrayList;
import java.util.List;

public class CertificateRepository {
    private final List<Certificate> certificates;

    public CertificateRepository() {
        this.certificates = new ArrayList<>(CertificateDataHandler.loadCertificates());
    }

    public List<Certificate> getCertificatesByAttendee(int attendeeId) {
        return certificates.stream()
                .filter(certificate -> certificate.getAttendeeId() == attendeeId)
                .collect(Collectors.toList());
    }

    public void addCertificate(Certificate certificate) {
        certificates.add(certificate);
        saveCertificates();
    }

    private void saveCertificates() {
        CertificateDataHandler.saveCertificates(certificates);
    }
}

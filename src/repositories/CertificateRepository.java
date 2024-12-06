package repositories;

import data_handlers.CertificateDataHandler;
import domain.Certificate;

import java.util.ArrayList;
import java.util.List;

public class CertificateRepository {
    private final List<Certificate> certificates;

    public CertificateRepository() {
        this.certificates = new ArrayList<>(CertificateDataHandler.loadCertificates()); // Ensure a mutable list
    }

    public void addCertificate(Certificate certificate) {
        certificates.add(certificate);
        saveCertificates();
    }

    public List<Certificate> getAllCertificates() {
        return new ArrayList<>(certificates); // Return a mutable copy
    }

    private void saveCertificates() {
        CertificateDataHandler.saveCertificates(certificates); // Persist the list to the JSON file
    }
}

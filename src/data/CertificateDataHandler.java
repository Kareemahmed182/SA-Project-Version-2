package data;

import com.fasterxml.jackson.core.type.TypeReference;
import domain.Certificate;

import java.util.List;

public class CertificateDataHandler {
    private static final String FILE_PATH = "data/certificates.json";
    private static final DataHandler<Certificate> dataHandler = new DataHandler<>(FILE_PATH);

    public static List<Certificate> loadCertificates() {
        return dataHandler.readData(new TypeReference<>() {}); // Pass TypeReference for List<Certificate>
    }

    public static void saveCertificates(List<Certificate> certificates) {
        dataHandler.writeData(certificates);
    }
}

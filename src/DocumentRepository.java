import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentRepository {

    private final List<LegalDocument> documents = new ArrayList<>();

    public void seed() {

        documents.clear();

        documents.add(
            new LegalDocument(
                1,
                "Employment Agreement",
                "Contract",

                "The employee shall maintain confidentiality. " +
                "Any breach of contract may result in penalty and liability. " +
                "The employer may terminate this agreement after material breach. " +
                "Payment shall be made monthly."
            )
        );

        documents.add(
            new LegalDocument(
                2,
                "Technology Services Agreement",
                "Service Agreement",

                "The service provider accepts liability for breach of the agreement. " +
                "The provider shall protect confidential information and may be liable " +
                "for penalty caused by negligence."
            )
        );

        documents.add(
            new LegalDocument(
                3,
                "Commercial Lease",
                "Lease",

                "The tenant shall pay rent monthly. " +
                "Failure to pay rent constitutes a material breach. " +
                "The landlord may terminate the lease after written notice."
            )
        );

        documents.add(
            new LegalDocument(
                4,
                "NDA - Project Orion",
                "NDA",

                "The receiving party shall maintain strict confidentiality " +
                "of protected information. Unauthorized disclosure constitutes " +
                "a breach and may result in liability and damages."
            )
        );
    }

    public List<LegalDocument> all() {
        return Collections.unmodifiableList(documents);
    }

    public LegalDocument get(int id) {

        for (LegalDocument document : documents) {

            if (document.getId() == id) {
                return document;
            }
        }

        return null;
    }

    public int size() {
        return documents.size();
    }
}
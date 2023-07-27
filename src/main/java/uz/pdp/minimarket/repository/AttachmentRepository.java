package uz.pdp.minimarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.minimarket.entity.Attachment;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {
    Optional<Attachment> findByNewName(String newName);
}

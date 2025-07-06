package com.fiap.tech.challenge.global.audit;

import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static jakarta.transaction.Transactional.TxType.REQUIRES_NEW;

@Component
public class DeleteAuditorComponent {

    private final EntityManager entityManager;

    @Autowired
    public DeleteAuditorComponent(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(value = REQUIRES_NEW)
    public void deleteAudit(Audit auditEntity) {
        auditEntity.setDeletedIn(new Date());
        auditEntity.setDeletedBy(AuthUserContextHolder.getAuthUserIfExists().map(User::getName).orElse(null));
        entityManager.merge(auditEntity);
        entityManager.flush();
    }
}
package com.fiap.tech.challenge.global.audit;

import com.fiap.tech.challenge.global.bean.BeanComponent;
import jakarta.persistence.*;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

public final class AuditEntityListener implements Serializable {

    @PreRemove
    public void preRemove(Audit auditEntity) {
        preRemoveAudit(auditEntity);
    }

    @PostRemove
    public void postRemove(Audit auditEntity) {
        postRemoveAudit(auditEntity);
    }

    @PostLoad
    public void postLoad(Audit auditEntity) {
        auditEntity.saveState(SerializationUtils.clone(auditEntity));
    }

    private void preRemoveAudit(Audit auditEntity) {
        BeanComponent.getBean(DeleteAuditorComponent.class).deleteAudit(auditEntity);
    }

    private void postRemoveAudit(Audit auditEntity) {
        auditEntity.setDeleted(true);
    }
}
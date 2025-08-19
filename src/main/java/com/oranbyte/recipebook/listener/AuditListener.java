package com.oranbyte.recipebook.listener;

import java.time.LocalDateTime;

import com.oranbyte.recipebook.entity.BaseEntity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

/**
 * Listener class for auditing entity changes.
 *
 * This class provides methods annotated with
 * {@link jakarta.persistence.PrePersist} and
 * {@link jakarta.persistence.PreUpdate} to automatically update creation and
 * update timestamps on entities extending
 * {@link com.loqshop.entity.BaseEntity}.
 */
public class AuditListener {

	/**
	 * Sets creation and update timestamps before persisting a new entity.
	 *
	 * @param entity The entity being persisted.
	 */
	@PrePersist
	public void prePersist(Object entity) {
		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			LocalDateTime now = LocalDateTime.now();
			baseEntity.setCreatedAt(now);
			baseEntity.setUpdatedAt(now);
		}
	}

	/**
	 * Updates the update timestamp before updating an existing entity.
	 *
	 * @param entity The entity being updated.
	 */
	@PreUpdate
	public void preUpdate(Object entity) {
		if (entity instanceof BaseEntity) {
			BaseEntity baseEntity = (BaseEntity) entity;
			baseEntity.setUpdatedAt(LocalDateTime.now());
		}
	}
}

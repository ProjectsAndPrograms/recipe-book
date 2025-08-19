package com.oranbyte.recipebook.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.oranbyte.recipebook.listener.AuditListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * Base entity class for common audit fields.
 *
 * This class defines common fields such as creation and update timestamps.
 */
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class BaseEntity {

	/**
	 * Timestamp indicating when this entity was created.
	 */
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	/**
	 * Timestamp indicating when this entity was last updated.
	 */
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	/**
	 * Timestamp indicating when this entity was deleted, If not deleted yet it
	 * should be null.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "deleted_at")
	private Date deletedAt;

	/**
	 * Retrieves the timestamp when this entity was created.
	 *
	 * @return The creation timestamp.
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the timestamp when this entity was created.
	 *
	 * @param createdAt The creation timestamp to set.
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Retrieves the timestamp when this entity was last updated.
	 *
	 * @return The last update timestamp.
	 */
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Sets the timestamp when this entity was last updated.
	 *
	 * @param updatedAt The last update timestamp to set.
	 */
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public boolean isDeleted() {
		return deletedAt != null;
	}

	public void softDelete() {
		this.deletedAt = new Date();
	}

	public void restore() {
		this.deletedAt = null;
	}
}

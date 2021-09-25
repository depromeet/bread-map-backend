package com.depromeet.breadmapbackend.common.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public class BaseEntity {

    // 임의로 이름 지정
    @CreatedDate
    @Column(name = "createdDateTime", updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(name = "lastModifiedDateTime")
    private LocalDateTime lastModifiedDateTime;
}
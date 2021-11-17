package com.depromeet.breadmapbackend.members.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Follows  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @JoinColumn(name = "from_member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Members fromMember;

    @JoinColumn(name = "to_member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Members toMember;

    @Builder
    public Follows(Members fromMember, Members toMember) {
        this.fromMember = fromMember;
        this.toMember = toMember;
    }
}

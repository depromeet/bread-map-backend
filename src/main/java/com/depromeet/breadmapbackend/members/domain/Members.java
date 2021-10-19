package com.depromeet.breadmapbackend.members.domain;

import com.depromeet.breadmapbackend.auth.enumerate.RoleType;
import com.depromeet.breadmapbackend.bakeries.domain.Bakeries;
import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import com.depromeet.breadmapbackend.members.enumerate.MemberProvider;
import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Members extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "members")
    private List<Bakeries> bakeriesList = new ArrayList<>();

    @OneToMany(mappedBy = "members")
    private List<BakeryReviews> bakeryReviewsList = new ArrayList<>();

    @OneToMany(mappedBy = "members")
    private List<Flags> flagsList = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @Column
    private String gender;

    @Column
    private Integer breadTestResult;

    @Column(nullable = false)
    private String socialId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberProvider memberProvider;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    @Column
    private String profileImagePath;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}

package com.depromeet.breadmapbackend.members.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.breadShops.domain.BreadShops;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Members extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id") // 임의로 이름 정했습니다!
    private Long id;

    @OneToMany(mappedBy = "members")
    private List<BreadShops> breadShopsList = new ArrayList<>();

    @OneToMany(mappedBy = "members")
    private List<Flags> flagsList = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private String email;

    private Integer breadTestResult;
}

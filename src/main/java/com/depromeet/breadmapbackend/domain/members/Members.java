package com.depromeet.breadmapbackend.domain.members;

import com.depromeet.breadmapbackend.domain.BaseEntity;
import com.depromeet.breadmapbackend.domain.breadShops.BreadShops;
import lombok.Getter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Entity
public class Members extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id") // 임의로 이름 정했습니다!
    private Long id;

    @OneToMany(mappedBy = "pioneer")
    private List<BreadShops> breadShopsList = new ArrayList<>();

    private String name;
    private String encryptedPassword;
    private String email;
    private Integer breadTestResult;
}

package com.depromeet.breadmapbackend.domain.breadShops;

import com.depromeet.breadmapbackend.domain.BaseEntity;
import com.depromeet.breadmapbackend.domain.members.Members;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class BreadShops extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bread_shop_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members pioneer; // 임의 이름 지정(회원:빵집 = 1: N인데, 이러면 구조가 이상해질 것 같아서, 빵집:회원=N:1로 해서 양방향 세팅)

    private String name;
    private Double latitude;
    private Double longitude;
    private String address;

    // 이미지 경로 변수

}

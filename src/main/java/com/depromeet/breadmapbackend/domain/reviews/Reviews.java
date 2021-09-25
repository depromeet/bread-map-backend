package com.depromeet.breadmapbackend.domain.reviews;

import com.depromeet.breadmapbackend.domain.BaseEntity;
import com.depromeet.breadmapbackend.domain.breadShops.BreadShops;
import com.depromeet.breadmapbackend.domain.members.Members;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Reviews extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members member;

    @ManyToOne
    @JoinColumn(name = "bread_shop_id")
    private BreadShops breadShop;

    private String contents;
    private Integer rating;

    // 이미지 경로 변수
}

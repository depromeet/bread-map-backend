package com.depromeet.breadmapbackend.reviews.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.breadShops.domain.BreadShops;
import com.depromeet.breadmapbackend.members.domain.Members;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Reviews extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bread_shop_id")
    private BreadShops breadShop;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Integer rating;

    private List<String> reviewImgPaths = new ArrayList<>();
}

package com.depromeet.breadmapbackend.breadShops.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.common.domain.Images;
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
public class BreadShops extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bread_shop_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members members;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String address;

    @OneToMany(mappedBy = "BreadShops")
    private List<Images> exteriorImgPathList = new ArrayList<>();

    @OneToMany(mappedBy = "BreadShops")
    private List<Images> interiorImgPathList = new ArrayList<>();

    @OneToMany(mappedBy = "BreadShops")
    private List<BreadShopsMenusMap> breadShopsMenusMaps = new ArrayList<>();
}

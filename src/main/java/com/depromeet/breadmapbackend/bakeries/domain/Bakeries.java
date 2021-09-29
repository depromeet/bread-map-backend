package com.depromeet.breadmapbackend.bakeries.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.common.domain.Images;
import com.depromeet.breadmapbackend.flags.domain.Flags;
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
public class Bakeries extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bakery_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members members;

    @OneToMany(mappedBy = "bakeries")
    private List<Flags> flagsList = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String address;

    @OneToMany(mappedBy = "bakeries")
    private List<Images> exteriorImgPathList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<Images> interiorImgPathList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<BakeriesMenusMap> breadShopsMenusMaps = new ArrayList<>();
}

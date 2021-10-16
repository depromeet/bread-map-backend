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

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String address;

    private String businessHour;

    @ElementCollection
    private List<String> websiteUrlList = new ArrayList<>();

    private String telNumber;

    @ElementCollection
    private List<String> basicInfoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members members;

    @OneToMany(mappedBy = "bakeries")
    private List<Flags> flagsList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<Images> bakeryImgPathList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<BakeriesBreadCategoriesMap> bakeriesMenusMapList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<Menus> menusList = new ArrayList<>();

}

package com.depromeet.breadmapbackend.bakeries.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.common.domain.Images;
import com.depromeet.breadmapbackend.common.util.StringListConverter;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
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

    @Convert(converter = StringListConverter.class)
    private List<String> websiteUrlList = new ArrayList<>();

    private String telNumber;

    @ElementCollection
    @CollectionTable(
            joinColumns = @JoinColumn(name = "bakery_id")
    )
    @Column(name = "basic_info")
    private List<String> basicInfoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members members; // 개척자(빵집 등록한 사람)

    @OneToMany(mappedBy = "bakeries")
    private List<Flags> flagsList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<Images> imgPathList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<BakeriesBreadCategoriesMap> bakeriesMenusMapList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<Menus> menusList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<MenuReviews> menuReviewsList = new ArrayList<>();

}

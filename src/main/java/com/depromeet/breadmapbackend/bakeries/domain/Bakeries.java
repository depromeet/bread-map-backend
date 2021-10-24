package com.depromeet.breadmapbackend.bakeries.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import com.depromeet.breadmapbackend.common.enumerate.BasicInfoType;
import com.depromeet.breadmapbackend.common.util.StringListConverter;
import com.depromeet.breadmapbackend.flags.domain.Flags;
import com.depromeet.breadmapbackend.members.domain.Members;
import com.depromeet.breadmapbackend.reviews.domain.BakeryReviews;
import com.depromeet.breadmapbackend.reviews.domain.MenuReviews;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @ElementCollection(targetClass = BasicInfoType.class)
    @CollectionTable(
            joinColumns = @JoinColumn(name = "bakery_id")
    )
    @Column(name = "basic_info")
    @Enumerated(EnumType.STRING)
    private List<BasicInfoType> basicInfoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members members; // 개척자(빵집 등록한 사람)

    @OneToMany(mappedBy = "bakeries")
    private List<Flags> flagsList = new ArrayList<>();

    @Column(columnDefinition = "varchar(255) default ''")
    @Convert(converter = StringListConverter.class)
    private List<String> imgPath = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<BakeriesBreadCategoriesMap> bakeriesMenusMapList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<Menus> menusList = new ArrayList<>();

    @OneToMany(mappedBy = "bakeries")
    private List<MenuReviews> menuReviewsList = new ArrayList<>();

    @Builder
    public Bakeries(String name, Double latitude, Double longitude, String address, String businessHour, List<String> websiteUrlList, String telNumber, List<BasicInfoType> basicInfoList, Members members, List<Flags> flagsList, List<String> imgPath, List<BakeriesBreadCategoriesMap> bakeriesMenusMapList, List<Menus> menusList, List<MenuReviews> menuReviewsList) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.businessHour = businessHour;
        this.websiteUrlList = websiteUrlList;
        this.telNumber = telNumber;
        this.basicInfoList = basicInfoList;
        this.members = members;
        this.flagsList = flagsList;
        this.imgPath = imgPath;
        this.bakeriesMenusMapList = bakeriesMenusMapList;
        this.menusList = menusList;
        this.menuReviewsList = menuReviewsList;
        members.getBakeriesList().add(this);
    }
}

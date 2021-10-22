package com.depromeet.breadmapbackend.bakeries.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Menus extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bakery_id")
    private Bakeries bakeries;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bread_category_id")
    private BreadCategories breadCategories;

    @ApiModelProperty(value = "대표 이미지(첫 빵 리뷰 사진 중 택 1)")
    @Column(columnDefinition = "varchar(255) default ''")
    private String imgPath;
}

package com.depromeet.breadmapbackend.bakeries.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private String price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bread_category_id")
    private BreadCategories breadCategories;

    @OneToMany(mappedBy = "menus")
    private List<BakeriesMenusMap> bakeriesMenusMapList = new ArrayList<>();


}

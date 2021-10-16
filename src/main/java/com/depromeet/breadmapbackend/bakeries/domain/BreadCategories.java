package com.depromeet.breadmapbackend.bakeries.domain;

import com.depromeet.breadmapbackend.common.domain.BaseEntity;
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
public class BreadCategories extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bread_category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "breadCategories")
    private List<BakeriesBreadCategoriesMap> bakeriesBreadCategoriesMapList = new ArrayList<>();

    @OneToMany(mappedBy = "breadCategories")
    private List<Menus> menusList = new ArrayList<>();
}

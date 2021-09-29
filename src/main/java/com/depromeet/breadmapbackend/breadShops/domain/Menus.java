package com.depromeet.breadmapbackend.breadShops.domain;

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
    @JoinColumn(name = "bread_shop_id")
    private BreadShops breadShops;

    @OneToMany(mappedBy = "menus")
    private List<BreadShopsMenusMap> breadShopsMenusMaps = new ArrayList<>();


}

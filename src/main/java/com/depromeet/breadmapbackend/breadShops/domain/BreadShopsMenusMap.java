package com.depromeet.breadmapbackend.breadShops.domain;


import com.depromeet.breadmapbackend.common.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BreadShopsMenusMap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bread_shops_id")
    private BreadShops breadShops;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menus_id")
    private Menus menus;
}

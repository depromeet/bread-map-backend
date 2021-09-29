package com.depromeet.breadmapbackend.flags.domain;

import com.depromeet.breadmapbackend.breadShops.domain.BreadShops;
import com.depromeet.breadmapbackend.members.domain.Members;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Flags {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members members;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bread_shop_id")
    private BreadShops breadShop;

}

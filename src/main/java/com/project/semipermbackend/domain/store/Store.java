package com.project.semipermbackend.domain.store;

import com.project.semipermbackend.domain.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE store SET use_yn = false WHERE encoded_place_id = ?")
@Where(clause = "use_yn = true")
@Entity
@Table(name = "store")
public class Store extends BaseTimeEntity {
    @Id
    @Column(name = "encoded_place_id", nullable = false, unique = true)
    private String encodedPlaceId;

    // M to M -> member_zzim_store 연관관계 테이블과 M:1, 1:M으로 풀었음.
    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private List<MemberZzimStore> memberZzimStores = new ArrayList<>();

    @Column(name = "total_review_rating")
    private float totalReviewRating = 0;

    public static Store create(String encodedPlaceId) {
        Store store = new Store();
        store.encodedPlaceId = encodedPlaceId;
        return store;
    }
    public void addZzimStore(MemberZzimStore memberZzimStore) {
        this.memberZzimStores.add(memberZzimStore);
    }
}

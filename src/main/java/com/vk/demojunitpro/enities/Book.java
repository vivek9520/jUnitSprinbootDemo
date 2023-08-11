package com.vk.demojunitpro.enities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    @NonNull
    private String bookName;

    @NonNull
    private int rate;
}

package com.apress.springrest.example.quickpoll.domain;

import javax.persistence.*;


@Entity
public class Vote {
    @Id
    @GeneratedValue
    @Column(name = "VOTE_ID")
    private Long Id;

    @ManyToOne
    @JoinColumn(name="COLUMN_NAME")
    private Option option;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }
}

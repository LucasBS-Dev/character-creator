package com.rpg.character_creator.model;

import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private User master;

    public Campaign() {}

    @OneToMany
    private List<Character> characters = new ArrayList<>();

    @ManyToMany
    @JoinTable(

            name = "campaign_players",

            joinColumns =
            @JoinColumn(name = "campaign_id"),

            inverseJoinColumns =
            @JoinColumn(name = "user_id")

    )
    private List<User> players =
            new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getMaster() {
        return master;
    }

    public void setMaster(User master) {
        this.master = master;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(
            List<User> players
    ) {
        this.players = players;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}
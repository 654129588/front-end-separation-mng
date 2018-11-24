package com.wisdom.mng.entity;


import javax.persistence.*;
import java.util.List;

@Entity
public class SysRole {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(cascade = {CascadeType.REFRESH},fetch = FetchType.EAGER)
    private List<SysFunction> functions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SysFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(List<SysFunction> functions) {
        this.functions = functions;
    }
}

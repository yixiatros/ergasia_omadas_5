package com.omada5.ergasia_omadas_5.task;

public enum ProposedTechnologies {
    JAVA("Java"),
    CSHARP("C#"),
    C("C/C++"),
    PYTHON("Python"),
    HTML("Html/Css"),
    KOTLIN("Kotlin"),
    JAVASCRIPT("Javascript"),
    RUST("Rust");

    private String technology;

    ProposedTechnologies(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return technology;
    }
}

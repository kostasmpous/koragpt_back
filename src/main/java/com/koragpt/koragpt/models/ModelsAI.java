package com.koragpt.koragpt.models;

import com.koragpt.koragpt.models.enums.Models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "models_ai")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModelsAI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private String name;

    private String description;

    private String company;
}

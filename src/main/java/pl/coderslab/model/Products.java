package pl.coderslab.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=3)
    private String name;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User user;

    @NotNull
    private boolean isPublic;

    @NotNull
    @Min(0)
    private Double calories;

    @NotNull
    @Min(0)
    @Max(100)
    private Double transFat;

    @NotNull
    @Min(0)
    @Max(100)
    private Double saturatedFat;

    @NotNull
    @Min(0)
    @Max(100)
    private Double cholesterol;

    @NotNull
    @Min(0)
    @Max(100)
    private Double carbohydrate;

    @NotNull
    @Min(0)
    @Max(100)
    private Double dietaryFiber;

    @NotNull
    @Min(0)
    @Max(100)
    private Double protein;

    @NotNull
    @Min(0)
    @Max(100)
    private Double vitaminA;

    @NotNull
    @Min(0)
    @Max(100)
    private Double vitaminC;

    @NotNull
    @Min(0)
    @Max(100)
    private Double sodium;

    @NotNull
    @Min(0)
    @Max(100)
    private Double calcium;

    @NotNull
    @Min(0)
    @Max(100)
    private Double iron;


}

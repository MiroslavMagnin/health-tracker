package ru.miro.users_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "The name shouldn't be empty")
    @Size(min = 2, max = 100, message = "The length of the name should be from 2 to 100 characters")
    private String name;

    @Column(name = "birth_date")
    @NotNull(message = "The birt date shouldn't be empty")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate birthDate;

    @Column(name = "email", unique = true)
    @NotNull(message = "The email shouldn't be empty")
    @Email(message = "The email should be match the format (example: ivan_ivanov@gmail.com)")
    private String email;

    @Column(name = "password")
    @NotNull(message = "The password shouldn't be empty")
    private String password;

    @Column(name = "weight")
    @Min(value = 0, message = "The weight should be greater than 0")
    private Integer weight;

    @Column(name = "height")
    @Min(value = 0, message = "The height should be greater than 0")
    private Integer height;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<HealthData> healthDataList;

    private long createdAt;
    private long updatedAt;

}

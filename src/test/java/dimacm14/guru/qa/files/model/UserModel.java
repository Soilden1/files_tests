package dimacm14.guru.qa.files.model;

import lombok.Data;

@Data
public class UserModel {

    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String phoneNumber;
    private double weight;
    private String gender;
    private Boolean isStudent;
    private String[] hobbies;
}

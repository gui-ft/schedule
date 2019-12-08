package io.gapps.schedule.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "TB_Customer")
public class Customer implements Serializable {


//  -------------------attributes--------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCustomer;

    @NotNull
    private String name;

    @Pattern(regexp = "^\\(\\d{2,}\\) \\d{4,}\\-\\d{4}", message = "This number is in the wrong pattern")
    private String phone;

    @Email(message = "This isn't an email")
    private String email;

    private boolean status;

//  ----------------gettersandsetters-----------------

    public Long getIdCustomer() { return idCustomer; }

    public void setIdCustomer(Long idCustomer) { this.idCustomer = idCustomer; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }
}
package com.yte.intern.project.security.request;

import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20, message = "Kullanıcı adı geçersiz!")
    private String username;

    @NotBlank
    @Size(max = 50, message="E-mail geçersiz!")
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40, message="Şifre geçersiz!")
    private String password;

    @NotBlank
    @Size(min = 2, max = 40, message = "İsim geçersiz!")
    private String name;

    @NotBlank
    @Size(min = 2, max = 40, message = "Soyisim geçersiz!")
    private String surname;

    private String tcKimlikNo;

    @AssertTrue(message = "TC Kimlik numarası formatı geçersiz!")
    private boolean isTcKimlikNoValid() {
        if(tcKimlikNo.length() != 11) {
            return false;
        }

        int sumAllNumbers = 0;
        int sumOdd = 0;
        int sumEven = 0;



        Integer[] numbers = new Integer[11];
        for (int i = 0; i < 11; i++) {
            numbers[i] = Integer.parseInt(tcKimlikNo.substring(i, i + 1));
        }



        for (int i = 0; i < 9; i++) {
            sumAllNumbers = sumAllNumbers + numbers[i];
            if (i % 2 != 0) {
                sumEven = sumEven + numbers[i];
            } else {
                sumOdd = sumOdd + numbers[i];
            }
        }



        if ((sumAllNumbers + numbers[9]) % 10 != numbers[10]) {
            return false;
        }
        if ((sumOdd * 7 + sumEven * 9) % 10 != numbers[9]) {
            return false;
        }
        if (((sumOdd) * 8) % 10 != numbers[10]) {
            return false;
        }

        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTcKimlikNo() {
        return tcKimlikNo;
    }

    public void setTcKimlikNo(String tcKimlikNo) {
        this.tcKimlikNo = tcKimlikNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
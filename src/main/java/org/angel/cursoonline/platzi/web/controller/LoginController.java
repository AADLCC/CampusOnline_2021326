package org.angel.cursoonline.platzi.web.controller;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

import org.angel.cursoonline.platzi.security.AdminCredentials;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;


@Named("loginController")
@ViewScoped
@Getter
@Setter
public class LoginController implements Serializable {

    @Autowired
    private AdminCredentials adminCredentials;

    private String username;
    private String password;

    public String doLogin() {
        if (adminCredentials.validate(username, password)) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLoggedIn", true);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isAdmin", true);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Bienvenido, Administrador."));
            // Target page on successful login
            return "/dashboard.xhtml?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Credenciales incorrectas."));
            return null;
        }
    }
}

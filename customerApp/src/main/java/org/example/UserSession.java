/*
package org.example;

import javafx.scene.control.ListView;

//todo final?
public class UserSession {
    private static UserSession instance;

    private Customer customer;

*/
/**
 * https://stackoverflow.com/questions/46508098/how-to-keep-user-information-after-login-in-javafx-desktop-application
 *
 * @param customer the customer in this usersession
 * @deprecated använder inte just nu //todo ta bort?
 *//*


    private UserSession(Customer customer) {
        //todo validering att inlogg är korrekt?
        this.customer = customer;
    }

    public static UserSession setInstance(Customer customer) {
        if (instance == null) {
            instance = new UserSession(customer);
        }
        return instance;
    }

    public static UserSession getInstance() {
        return instance;
    }

    public void clearInstance() {
        instance = null;
    }

    public Customer getCustomer() {
        return customer;
    }
}

*/

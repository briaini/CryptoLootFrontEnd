package com.universityoflimerick.cryptolootfrontend.adam.User;

/**
 *  Factory to generate regular or premium users.
 */
public class UserFactory {

    public User getUser(String userType){
        if(userType == null){
            return null;
        }
        else if(userType == "regular"){
            return new RegularUser();
        }
        else if(userType == "premium") {
            return new PremiumUser();
        }

        return null;
    }
}

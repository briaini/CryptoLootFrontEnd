package com.universityoflimerick.cryptolootfrontend.Utils.factory;

import com.universityoflimerick.cryptolootfrontend.Model.User.PremiumUser;
import com.universityoflimerick.cryptolootfrontend.Model.User.RegularUser;
import com.universityoflimerick.cryptolootfrontend.Model.User.User;

/**
 *  Factory to generate regular or premium users.
 */
public class UserFactory {

    public UserFactory(){ }

    /**
     * Takes in a string of the user type. Instantiates a user of that type.
     * @param userType String of user type.
     * @return return either regular or premium user.
     */
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

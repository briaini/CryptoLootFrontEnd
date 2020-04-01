package com.universityoflimerick.cryptolootfrontend;

import com.universityoflimerick.cryptolootfrontend.adam.User.User;
import com.universityoflimerick.cryptolootfrontend.adam.User.UserFactory;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;

public class FactoryTest {

    UserFactory userFactory;
    User rUser, pUser;

    /**
     * Setup method to create User Factory for user instantiation.
     * Method then intantiates two users, one regular, one premium.
     */
    @Before
    public void setup(){
        userFactory = new UserFactory();
        rUser = userFactory.getUser("regular");
        pUser = userFactory.getUser("premium");
    }

    /**
     * Tests that regular users have a regular type.
     */
    @Test
    public void factory_regular_user_creation_test(){

        assertEquals("regular", rUser.getType());
    }

    /**
     * Tests that premium users have a premium type.
     */
    @Test
    public void factory_premium_user_creation_test(){
        assertEquals("premium", pUser.getType());
    }

    /**
     * Tests that premium users do not have a regular type.
     */
    @Test
    public void factory_premium_user_is_not_regular_test(){
        assertNotEquals("regular", pUser.getType());
    }


}

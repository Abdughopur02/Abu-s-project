package com.cydeo.steps;

import com.cydeo.pages.DashBoardPage;
import com.cydeo.pages.LoginPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class LoginStepDefs {

    LoginPage loginPage = new LoginPage();
    DashBoardPage dashBoardPage = new DashBoardPage();
    String actualUserName;
    String email;

    @Given("the user logged in  {string} and {string}")
    public void the_user_logged_in_and(String email, String password) {

        loginPage.login(email,password);
        BrowserUtil.waitFor(3);


    }
    @When("user gets username  from user fields")
    public void user_gets_username_from_user_fields() {
        String actualUserName = dashBoardPage.accountHolderName.getText();
        System.out.println("actualUserName = " + actualUserName);


    }
    @Then("the username should be same with database")
    public void the_username_should_be_same_with_database() {
        String query="select full_name from users where email = 'librarian55@library'";
        DB_Util.runQuery(query);
        //get data from database
        System.out.println("DB_Util.getFirstRowFirstColumn() = " + DB_Util.getFirstRowFirstColumn());

    }

}

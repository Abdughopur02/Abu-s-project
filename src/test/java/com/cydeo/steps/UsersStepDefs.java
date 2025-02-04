package com.cydeo.steps;

import com.cydeo.pages.UsersPage;
import com.cydeo.utility.BrowserUtil;
import com.cydeo.utility.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.Select;

import java.sql.ResultSet;

public class UsersStepDefs {
    UsersPage usersPage = new UsersPage();
    String expectedStatus;
    String email;

    @When("the user clicks Edit User button")
    public void the_user_clicks_edit_user_button() {
        BrowserUtil.waitFor(1);
        usersPage.editUser.click();
    }
    @When("the user changes user status {string} to {string}")
    public void the_user_changes_user_status_to(String active, String inactive) {
        BrowserUtil.waitFor(1);
        //we need to change status of the first user that we clicked
        Select select = new Select(usersPage.statusDropdown);
        select.selectByVisibleText(inactive);
        //save the email of the user that we changed the status
        email = usersPage.email.getAttribute("value");
        System.out.println(email);

        expectedStatus=inactive;
    }
    @When("the user clicks save changes button")
    public void the_user_clicks_save_changes_button() {
        BrowserUtil.waitFor(1);
        usersPage.saveChanges.click();
        System.out.println(email +" is deactivated.");

    }
    @Then("{string} message should appear")
    public void message_should_appear(String expectedMessage) {

        BrowserUtil.waitFor(1);
        String actualMessage = usersPage.toastMessage.getText();
        Assert.assertEquals(expectedMessage,actualMessage);

    }
    @Then("the users should see same status for related user in database")
    public void the_users_should_see_same_status_for_related_user_in_database() {
        //get info from database
        //run the query to get actualStatus
        String query ="select status from users where email='"+email+"'";

        DB_Util.runQuery(query);

        String actualStatus = DB_Util.getFirstRowFirstColumn();

        //compare with expectedStatus
        Assert.assertEquals(expectedStatus,actualStatus);


    }
    //this step only for the purpuse to switch back to default position !!!(it is not belong to the test)
    @Then("the user changes current status {string} to {string}")
    public void the_user_changes_current_status_to(String inactive, String active) {

        //THIS step for switch users pages to inactive users
        BrowserUtil.waitFor(2);
        BrowserUtil.selectByVisibleText(usersPage.userStatusDropdown,inactive);
        //we will click edit user button based on the email that we updated
        BrowserUtil.waitFor(2);
        usersPage.editUser(email).click();
        BrowserUtil.waitFor(1);
        //we update user from inactive to active
        BrowserUtil.selectByVisibleText(usersPage.statusDropdown,active);
        BrowserUtil.waitFor(1);
        usersPage.saveChanges.click();
        System.out.println(email + " is activated");

    }


}

package com.db.am.bauhaus.project.steps;

import com.db.am.bauhaus.project.SearchFor;
import com.db.am.bauhaus.project.SearchTarget;
import com.db.am.bauhaus.project.SessionVar;
import com.db.am.bauhaus.project.pages.MainSearchPage;
import com.db.am.bauhaus.project.steplib.SearchUser;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.annotations.Steps;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;
import static net.serenitybdd.screenplay.questions.WebElementQuestion.the;

/**
 * Created by ongshir on 05/10/2016.
 */
public class SearchSteps {

    @Steps
    SearchUser user;
    MainSearchPage mainSearchPage;

    @Before
    public void before() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("^John is viewing the Etsy landing page$")
    public void goto_landing_page() {
        mainSearchPage.open();
    }

    @Given("^([^\\s]+) is viewing the Etsy landing page \\(screenplay\\)$")
    public void goto_landing_page_screenplay(String theUser) {
        theActorCalled(theUser).attemptsTo(Open.browserOn().the(mainSearchPage));
    }

    @When("^he searches for a product from the input box$")
    public void search_from_input_box() {
        user.search_from_input_box();
    }

    @When("^he selects a category from drop down menu$")
    public void select_dropdown_menu() {
        user.select_menu();
    }

    @When("^he selects a product from icons$")
    public void select_random_product() {
        user.select_product_icon();
    }


    @When("^he searches for a product from the input box \\(screenplay\\)$")
    public void search_from_input_box_screenplay() {
        theActorInTheSpotlight().attemptsTo(SearchFor.randomText());
    }

    @Then("^the result should be displayed$")
    public void verify_search_result() {
        user.verify_result_for_top_categories();
        user.verify_result_for_all_categories();
    }

    @Then("^the category items should be displayed$")
    public void verify_search_heading_result() {
        user.verify_result_for_menu();
    }

    @Then("^the product should be displayed$")
    public void verify_product_name() {
        user.verify_product_name();
    }

    @Then("^all links are working fine$")
    public void check_all_links() {
        user.verify_all_links();
    }

    @Then("^the result should be displayed \\(screenplay\\)$")
    public void verify_search_result_screenplay() {
        String searchText = Serenity.sessionVariableCalled(SessionVar.SEARCH_TEXT).toString();
        theActorInTheSpotlight().should(
                seeThat("the top categories header ", the(SearchTarget.TOP_CATEGORIES_HEADER), containsText(searchText)),
                seeThat("the all categories header ", the(SearchTarget.ALL_CATEGORIES_HEADER), containsText(searchText))
        );
    }
}

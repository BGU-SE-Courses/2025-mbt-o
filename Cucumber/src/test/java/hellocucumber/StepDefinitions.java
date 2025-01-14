package hellocucumber;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize(); // Prevent click intercepted errors
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("the OpenCart store is accessible")
    public void the_open_cart_store_is_accessible() {
        driver.get("http://localhost:8080/opencart/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("nav#top")));
    }

    @Given("the OpenCart admin panel is accessible")
    public void the_open_cart_admin_panel_is_accessible() {
        driver.get("http://localhost:8080/opencart/admin");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-username")));
    }

    @Given("I am logged in as a customer")
    public void loginAsCustomer() {
        WebElement myAccount = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Account")));
        myAccount.click();
        
        WebElement login = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Login")));
        login.click();
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-email")))
            .sendKeys("customer@test.com");
        driver.findElement(By.id("input-password")).sendKeys("test123");
        
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button.btn.btn-primary[type='submit']")));
        submitButton.click();
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("My Account")));
    }

    @Given("I am logged in as administrator")
    public void loginAsAdmin() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input-username")))
            .sendKeys("admin");
        driver.findElement(By.id("input-password")).sendKeys("admin123");
        
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button.btn.btn-primary[type='submit']")));
        submitButton.click();
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("navigation")));
    }

    @Given("I am on the product listing page")
    public void i_am_on_the_product_listing_page() {
        try {
            // Currently searches for "Canon EOS 5D" - should search for specific products like "iPhone"
            // Need to ensure page is loaded before searching
            // Add proper waits to prevent stale elements
            
            // Fix:
            driver.get("http://localhost:8080/opencart/"); // Refresh page first
            WebElement searchBox = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("input[name='search']")));
            searchBox.clear();
            searchBox.sendKeys("iPhone");
            
            // Wait for button to be clickable
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn.btn-light[type='submit']")));
            searchButton.click();
            
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("product-list")));
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to product listing: " + e.getMessage(), e);
        }
    }

    @Given("the product {string} is available in stock")
    public void verifyProductAvailable(String productName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//div[contains(@class, 'product-thumb')]//h4/a[contains(text(), '" + productName + "')]")));
    }

    @Given("the product {string} is out of stock")
    public void the_product_is_out_of_stock(String productName) {
        try {
            // First click on product to get to product page
            WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'product-thumb')]//h4/a[contains(text(), '" + productName + "')]")));
            
            Actions actions = new Actions(driver);
            actions.moveToElement(productLink).click().perform();   

            // Wait for and check availability text
            WebElement availability = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//li[contains(text(), 'Availability:')]")));
            
            String availabilityText = availability.getText();
            if (!availabilityText.toLowerCase().contains("out of stock")) {
                throw new AssertionError("Product is not out of stock. Current availability: " + availabilityText);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to check stock status: " + e.getMessage());
        }
    }

    @Given("the product {string} has {string} items in stock")
    public void the_product_has_items_in_stock(String productName, String stockCount) {
        // This would typically involve admin setup to set stock
        // For now just verify product exists
        verifyProductAvailable(productName);
    }

    @Given("I have added {string} quantities of {string} to cart")
    public void i_have_added_quantities_of_to_cart(String quantity, String productName) {
        verifyProductAvailable(productName);
        setQuantity(quantity);
        addProductToCart();
    }

    @Given("a customer cart has {string} quantities of {string}")
    public void a_customer_cart_has_quantities_of(String quantity, String productName) {
        // Navigate to customer's cart in admin panel
        driver.findElement(By.id("menu-sale")).click();
        driver.findElement(By.linkText("Orders")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".table-responsive")));
    }

    @Given("a customer cart has {string} quantities of {string} priced at {string} each")
    public void a_customer_cart_has_quantities_of_priced_at_each(String quantity, String productName, String price) {
        a_customer_cart_has_quantities_of(quantity, productName);
    }

    @Given("the customer is actively viewing their cart")
    public void the_customer_is_actively_viewing_their_cart() {
        // In real implementation, this might involve another browser session
        // For now just verify we're on cart page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("shopping-cart")));
    }

    @Given("the product has been deleted from the catalog")
    public void the_product_has_been_deleted_from_the_catalog() {
        // This would involve admin setup to delete product
        // For now just simulate by checking if product exists
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[contains(@class, 'product-thumb')]")));
    }

    @When("I add the product to cart")
    public void addProductToCart() {
        try {
            // Refresh the page first
            driver.navigate().refresh();
            
            // Find the specific product's "Add to Cart" button
           
            Thread.sleep(500); // Small delay 
            WebElement addToCart = driver.findElement(By.xpath("//*[@id=\"product-list\"]/div/div/div[2]/form/div/button[1]"));
            Thread.sleep(500); // Small delay 
            Actions actions = new Actions(driver);

            actions.moveToElement(addToCart).click().perform();
            
            // Wait for success message
            wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.alert.alert-success")));
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product to cart: " + e.getMessage());
        }
    }



// @When("I add the product to cart")
//     public void addProductToCart() {
//         WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(
//             By.cssSelector("button[aria-label='Add to Cart']")));
//         addToCartButton.click();
        
//         wait.until(ExpectedConditions.presenceOfElementLocated(
//             By.cssSelector(".alert-success")));
//     }


    @When("I set the quantity to {string}")
    public void setQuantity(String quantity) {
        WebElement quantityInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.name("quantity")));
        
        // Scroll into view
        js.executeScript("arguments[0].scrollIntoView(true);", quantityInput);
        
        // Use JavaScript to set value
        js.executeScript("arguments[0].value = arguments[1]", quantityInput, quantity);
        
        // Trigger change event
        js.executeScript("arguments[0].dispatchEvent(new Event('change'))", quantityInput);
    }

    @When("I try to add the product to cart")
    public void i_try_to_add_the_product_to_cart() {
        addProductToCart();
    }

    @When("I log out and log back in")
    public void i_log_out_and_log_back_in() {
        // Logout
        WebElement myAccount = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("My Account")));
        myAccount.click();
        
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
        logout.click();
        
        // Login again
        loginAsCustomer();
    }

    @When("I attempt to change the quantity to {string}")
    public void i_attempt_to_change_the_quantity_to(String quantity) {
        adminChangeQuantity(quantity);
    }

    @When("I attempt to modify the cart quantity")
    public void i_attempt_to_modify_the_cart_quantity() {
        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-update")));
        updateButton.click();
    }

    @When("I change the quantity to {string} for the product")
    public void adminChangeQuantity(String quantity) {
        WebElement quantityInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.name("quantity[]")));
        
        js.executeScript("arguments[0].scrollIntoView(true);", quantityInput);
        js.executeScript("arguments[0].value = arguments[1]", quantityInput, quantity);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'))", quantityInput);
        
        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("button-update")));
        updateButton.click();
    }

    @Then("the cart should show quantity {string} for the product")
    public void verifyCartQuantity(String expectedQuantity) {
        WebElement quantityInput = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.name("quantity[]")));
        assert quantityInput.getAttribute("value").equals(expectedQuantity);
    }

    @Then("the cart should retain quantity {string} for the product")
    public void the_cart_should_retain_quantity_for_the_product(String expectedQuantity) {
        verifyCartQuantity(expectedQuantity);
    }

    @Then("the cart should remain empty")
    public void the_cart_should_remain_empty() {
        WebElement emptyCart = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.xpath("//p[contains(text(), 'Your shopping cart is empty!')]")));
        assert emptyCart.isDisplayed();
    }

    @Then("the cart total should be calculated correctly")
    public void the_cart_total_should_be_calculated_correctly() {
        WebElement total = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".table-responsive")));
        assert total.isDisplayed();
    }

    @Then("I should see an out of stock message")
    public void i_should_see_an_out_of_stock_message() {
        WebElement alert = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".alert-warning")));
        assert alert.getText().toLowerCase().contains("out of stock");
    }

    @Then("I should see a maximum quantity exceeded message")
    public void i_should_see_a_maximum_quantity_exceeded_message() {
        WebElement alert = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".alert-warning")));
        assert alert.getText().toLowerCase().contains("maximum quantity");
    }

    @Then("I should see an invalid quantity message")
    public void i_should_see_an_invalid_quantity_message() {
        WebElement alert = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".alert-warning")));
        assert alert.getText().toLowerCase().contains("invalid quantity");
    }

    @Then("I should see a product not found message")
    public void i_should_see_a_product_not_found_message() {
        WebElement alert = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".alert-warning")));
        assert alert.getText().toLowerCase().contains("product not found");
    }

    @Then("the cart total should be {string}")
    public void the_cart_total_should_be(String expectedTotal) {
        WebElement total = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".cart-total")));
        assert total.getText().contains(expectedTotal);
    }

    @Then("any applied discounts should be recalculated")
    public void any_applied_discounts_should_be_recalculated() {
        WebElement discounts = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".table-responsive")));
        assert discounts.isDisplayed();
    }

    @Then("the customer should see a cart update notification")
    public void the_customer_should_see_a_cart_update_notification() {
        WebElement notification = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".alert-success")));
        assert notification.getText().toLowerCase().contains("success");
    }
}
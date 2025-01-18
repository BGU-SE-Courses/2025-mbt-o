/* @provengo summon selenium */

/**
 * This story an admin opens opencart and changes quantity of iphone to 1
 */
bthread("AdminStuff", function () {
  let s = new SeleniumSession("adminStuff");
  s.start(URLadmin);
  login(s);
  navigateToProducts(s); //products list
  navigateToProduct(s); //pick iphone
  editProductQuantity(s);
  Event("adminStuffCompleted");

});

/**
 * This story a user opens opencart and adds 2 iphones to cart
 */
bthread("UserStuff", function () {
  let s = new SeleniumSession("userStuff");
  s.start(URLstore);
  searchProduct(s);  
  sync({
    waitForL: Event("adminStuffCompleted"),  // Wait for admin stuff to finish
    block: Event("adminStuffCompleted")      // Block until admin stuff is completed
  });
  addProductToCart2(s);
});





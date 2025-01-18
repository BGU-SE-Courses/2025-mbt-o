//admin actions 
function login(session) {
  session.writeText(xpaths.loginWindow.usernameBox, adminCredentials[0]);
  session.writeText(xpaths.loginWindow.passwordBox, adminCredentials[1]);
  with(session) {
    click(xpaths.loginWindow.submitLogin)
  }
}

function navigateToProducts(session) {
  with(session) {
    click(xpaths.navigationSide.openMenu)
    click(xpaths.navigationSide.catalogChoice)
    click(xpaths.navigationSide.productsChoice)
  }
}


function navigateToProduct(session) {
  with(session) {
    click(xpaths.findProduct.productLocation)
  }
}

function editProductQuantity(session) {
  with(session) {
    click(xpaths.editProduct.dataTab)
    writeText(xpaths.editProduct.quantityBox, "1", true)
    click(xpaths.editProduct.saveButton)
  }
}

function searchProduct(session) {
  with(session) {
    writeText(xpathsu.search.searchBox, "iphone", true)
    click(xpathsu.search.submitSearch)
  }
}

function addProductToCart2(session) {
  with(session) {
    click(xpathsu.searchResults.addToCartButton)
    click(xpathsu.searchResults.addToCartButton)
  }
}





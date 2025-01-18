/*
 *  This is a good place to put common test data, project-wide constants, etc.
 */

const URLstore = 'http://localhost/opencart/';
const URLadmin = 'http://localhost/opencart/admin2/';

const userCredentials = ["keren@test.com", "Quality2025"];
const adminCredentials = ["admin", "1234"];

const chosenProduct = "iPhone";

const xpaths = {
  loginWindow: {
    usernameBox:  '//input[@name="username"]',
    passwordBox:  '//input[@name="password"]',
    submitLogin:  '//button[@type="submit"]'
  },

  navigationSide: {
    openMenu: '//*[@id=\'button-menu\']/i[1]',
    catalogChoice:  '//*[@id=\'menu-catalog\']/a[1]',
    productsChoice: '//div[2]/nav[1]/ul[1]/li[2]/ul[1]/li[2]/a[1]'
  },

  findProduct: {
    productLocation: '//*[@id=\'form-product\']/div[1]/table[1]/tbody[1]/tr[6]/td[7]/div[1]/a[1]'
  },

  editProduct: {
    dataTab: '//a[@href="#tab-data"]',
    quantityBox: '//input[contains(@name, "quantity")]',
    saveButton: '//button[@form="form-product"]'
  }

}

const xpathsu = {
  loginWindow: {
    toolbox: '//i[2]',
    loginButton: '//body/div[1]/nav[1]/div[1]/div[1]/div[2]/ul[1]/li[2]/div[1]/ul[1]/li[2]/a[1]',
    emailBox:  '//input[contains(@name, "email")]',
    passwordBox:  '//input[contains(@type, "password")]',
    submitLogin:  '//*[@id=\'form-login\']/div/button[1]'
  }, 
  search: {
    searchBox: '//input[@name="search"]',
    submitSearch: '//form[1]/button[1]'
  },

  searchResults: {
    addToCartButton: '//form[1]/div[1]/button[1]'
  }


}


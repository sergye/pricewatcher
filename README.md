<h1>Price Watcher</h1>

<p>Price Watcher is a simple Spring Boot application.
It allows you to watch, filter, create, update and delete prices for different clothing brands.</p>

### Code quality checks:
<a href="https://github.com/sergye/pricewatcher/actions/workflows/main.yml"><img src="https://github.com/sergye/pricewatcher/actions/workflows/main.yml/badge.svg" alt="Java CI"/></a>
<a href="https://codeclimate.com/github/sergye/pricewatcher/maintainability"><img src="https://api.codeclimate.com/v1/badges/d38d989a02a428da6ca0/maintainability" /></a>
<a href="https://codeclimate.com/github/sergye/pricewatcher/test_coverage"><img src="https://api.codeclimate.com/v1/badges/d38d989a02a428da6ca0/test_coverage" /></a>

### Try app here: https://pricewatcher-gw1u.onrender.com

### Credentials to login:
<p>username: admin@pricewatcher.com
<p>password: admin

### Guidelines to use application by browser
<ol>
<li>Open in any browser the following link: https://pricewatcher-gw1u.onrender.com </li>
<li>Enter credentials listed above</li>
<li>Move to navigation menu on the left</li>
<li>Check price lists, dates, brands, products and prices preloaded for you </li>
<li>The table "Prices" is presented in the form of cards set for each price item </li>
<li>You can also create, edit and delete all the tables</li>
<li>In the menu point "Prices" you can use the filters at the top to select prices you need  </li>
</ol>

### Guidelines to use application by Postman
<ol>
<li>Alternatively the application API could be used by Postman</li>
<li>Open the Postman and send POST request to the following endpoint: https://pricewatcher-gw1u.onrender.com/api/login with the following JSON body:</li>
    <ul>
        <li> {
    "username": "admin@pricewatcher.com",
    "password": "admin"
}</li>
    </ul>
<li>Copy JWT token</li>
<li>Try to execute requests to any application endpoint with the token using Bearer Token authorization type</li>
</ol>

### Frameworks, databases and libraries used:
<ul>
<li>Spring Boot</li>
<li>Spring Data Jpa</li>
<li>Spring Security</li>
<li>React</li>
<li>Jackson </li>
<li>JUnit </li>
<li>H2 database </li>
<li>GitHub Actions </li>
<li>Code Climate </li>
<li>Checkstyle </li>
<li>Mapstruct mappers</li>
<li>JWT</li>
</ul>

### Some important points:
<ul>
<li>Use the following datetime format in your requests: "yyyy-MM-dd HH:mm:ss"</li>
<li>Check how the main table "Prices" is tested in PriceControllerTest.java file</li>
<li>Find the tests methods testFilter1()...testFilter5() to test main application endpoint "/api/prices"</li>
<li>Please contact me if you find bugs or have any questions: sergye@gmail.com</li>
</ul>


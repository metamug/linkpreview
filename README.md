# linkpreview
Link Preview Library, works with twitter cards, open graph tags and even wikipedia.

Support for following [strategies](src/main/java/net/metamug/scrapper/strategy)

* Article
* Audio
* Flickr
* PDF
* Product
* QnA
* Web
* Wikipedia

## Features

* Get the largest image on the page, if og:image and other image meta tags are not defined
* Support product information like price, rating for sites like flipkart.com, amazon.in and snapdeal.com
* Uses Strategy Pattern to extend the support for more sites and categories
* [Get all outbound links on the page](src/main/java/net/metamug/scrapper/util/Siblinks.java)


## Usage

```java
String url = "https://techcrunch.com/"
WebMetaData result = null;
try {
    result = MetaDataFactory.create(url);
} catch (Exception e) {
    result = new WebMetaData("Unable to read information",
            "", "", "Something is wrong with this webpage or we aren't good enough yet.",
            "https://lh3.googleusercontent.com/-MVEG-8gyeEs/WX9f1ZSbspI/AAAAAAAACi0/muowN-vTfGsEfAjK1LvrETXIa0nz6OnUwCL0BGAYYCw/h215/2017-07-31.png");

}
```

### Build

```
mvn clean install
```

```xml
<dependency>
    <groupId>net.metamug</groupId>
    <artifactId>metamug-scrapper</artifactId>
    <version>1.0.3</version>
</dependency>
```

# linkpreview
Link Preview Library, works with twitter cards, open graph tags and even wikipedia.

Support for following metadata data added

* Article
* Audio
* Flickr
* PDF
* Product
* QnA
* Web
* Wikipedia


## Usage

```
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

# Liferay External Searches
An example on how to build a configurable widget to trigger external searches along with Liferay search to Elasticsearch.

![preview](https://github.com/lgdd/doc-assets/blob/main/liferay-external-searches/preview.png?raw=true)

## Getting Started
You can either:
- Clone this repo 
- Run `./gradlew initBundle deploy`
  
Or:
- Download the modules from the [release page](https://github.com/lgdd/liferay-external-searches/releases)
- Unzip the Java 8 or 11 version.
- Move the JARs to the `deploy` folder under your Liferay home.

Once the 3 modules (`external-search-api`, `external-search-service` and `external-search-results-web`) are deployed:
- Go to the Liferay page where search results are displayed (default: `/search`)
- Add the widget `External Search Results` (under the `Search` category)
- Click on `Configure an external search engine to be used.`
- Select an `External Search Engine` from the dropdown list (default options: `Reddit`, `Stack Overflow`, `Wikipedia`)
- Click `Save`
- If the search page is a `Content Page`, click `Publish`.

Now when you run a search in Liferay, you should see results from the search engine selected in its own widget.

You can repeat this process and add more instances of the widget `External Search Results` with different configurations.

## Going Further

### Search Options
In the configuration, you can find a textarea for `Search Options`. This is a list of search options to fill as a key-value pairs (one per line). The supported options are search engine dependents, so you need to refer to each external search documentation (e.g. [Stack Exchange Search API](https://api.stackexchange.com/docs/advanced-search)).

### Display Template
This widget supports _Application Display Templates_ (ADT), also called [_Widget Templates_](https://learn.liferay.com/dxp/latest/en/site-building/creating-pages/using-widget-pages/styling-widgets/creating-a-widget-template.html).

By default, you'll find 3 templates:

- [Basic Template](modules/external-search-results-web/src/main/resources/com/github/lgdd/liferay/external/search/results/web/internal/template/dependencies/portlet_display_template_basic.ftl)
- [Reddit Template](modules/external-search-results-web/src/main/resources/com/github/lgdd/liferay/external/search/results/web/internal/template/dependencies/portlet_display_template_reddit.ftl)
- [Stack Overflow Template](modules/external-search-results-web/src/main/resources/com/github/lgdd/liferay/external/search/results/web/internal/template/dependencies/portlet_display_template_stackoverflow.ftl)

If you take a look at them, you'll notice that you can have more information than the name and URL of each search result.
A map of metadata can be associated with each type of search result. 
For example to retrieve and display the tags associated with a Stack Overflow topic.

### More Searches
If you clone this repo, you can try to add more external search sources in the [external-search-service](modules/external-search-service/src/main/java/com/github/lgdd/liferay/external/search/service) module.

The process is pretty simple and straightforward:
- Create a new class
- Annotate with `@Component`
- Add a `service` attribute to the `@Component` with the value `ExternalSearch.class`
- Add a `property` attribute to the `@Component` with the value `engine=` followed by the same of the source
- Extends `BaseExternalSearch` and implements required methods

Checkout the [RedditService](modules/external-search-service/src/main/java/com/github/lgdd/liferay/external/search/service/RedditService.java) class as an example.

In some cases, you might want to override the search methods. For example, the [WikipediaService](modules/external-search-service/src/main/java/com/github/lgdd/liferay/external/search/service/WikipediaService.java) supports localization and overriding the search methods is needed to pass the right domain prefix depending on the language.


## Feedback & Contribution
Feel free to [open issues](https://github.com/lgdd/liferay-external-searches/issues/new) if you have any question/suggestion or [pull requests](https://github.com/lgdd/liferay-external-searches/compare) if you want to contribute.

## License
[MIT](LICENSE)
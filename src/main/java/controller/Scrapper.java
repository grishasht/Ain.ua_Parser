package controller;

import model.entity.Article;
import model.entity.Category;
import model.entity.Image;
import model.service.ArticleService;
import model.service.CategoryService;
import model.service.ImageService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scrapper {
    private String url;
    private CategoryService categoryService = new CategoryService();
    private ArticleService articleService = new ArticleService();
    private ImageService imageService = new ImageService();

    Scrapper(String url) {
        this.url = url;
    }

    public void scrapAllData() {
        scrapArticles();
    }

    /**
     * Method that gets up to 5 articles from each category and writes
     * them to the data base.
     *
     * @return list of received articles
     */
    public List<Article> scrapArticles() {
        List<Article> articles = new ArrayList<>();

        for (Category category : scrapCategories()) {
            for (String href : category.getArticleRefs()) {
                try {
                    System.out.println(category.getName() + "  " + href);
                    Document document = Jsoup.connect(href).get();
                    Article article = getArticleText(document, category, href);
                    if (article != null) {
                        articleService.saveArticle(article);
                        Integer id = articleService.getArticleId(article);
                        articles.add(article);
                        getArticleImages(document, id)
                                .forEach(image -> imageService.saveImage(image));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return articles;
    }

    /**
     * Method that gets all categories from site
     *
     * @return list of received categories
     */
    public List<Category> scrapCategories() {
        List<Category> categories = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            List<String> hrefs = getHrefs(document);
            for (String href : hrefs) {
                Category category = getCategory(href);
                assert category != null;
                if (!"".equals(category.getName())) {
                    categoryService.saveCategory(category);
                    category.setId(categoryService.getCategoryId(category));
                    categories.add(category);
                }
            }
        } catch (IOException e) {
            System.out.println("Can't find " + url);
        }
        return categories;
    }

    /**
     * Method that creates article model object.
     * Gets article's name, reference and text content from the site.
     *
     * @param document — article's page
     * @param category — article's category
     * @param href     — article's hyper reference
     * @return created article object
     */
    private Article getArticleText(Document document, Category category, String href) {
        try {
            Elements elements = document.getElementById("post-content").getAllElements();
            Article article = new Article();
            article.setName(elements.select("h1").text());
            article.setHref(href);
            article.setContains(elements.select("p,em,strong,li").text());
            article.setCategoryId(category.getId());
            return article;
        } catch (NullPointerException e) {
            System.out.println("Unsupported article format! Article: " + href);
            return null;
        }
    }

    /**
     * Method that gets all images from article's page.
     *
     * @param document  — article's document
     * @param articleId — article's id. Needed as foreign key
     * @return list of received images
     */
    private List<Image> getArticleImages(Document document, Integer articleId) {
        List<Image> images = new ArrayList<>();

        Elements elements = document.getElementById("post-content").getAllElements();
        elements = elements.select("img");

        for (Element element : elements) {
            String src = element.attr("src");
            try {
                byte[] imageBytes = Jsoup.connect(src).ignoreContentType(true).execute().bodyAsBytes();
                Image image = new Image();

                image.setSrc(src);
                image.setArticle_id(articleId);
                image.setImage(imageBytes);
                images.add(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return images;
    }

    /**
     * Gets category hyper references
     *
     * @param doc — site's main page document
     * @return list of received category references
     */
    private List<String> getHrefs(Document doc) {
        List<String> hrefs = new ArrayList<>();
        Elements categoryRefs = doc.select("div.tag").select("a.look-all");

        for (Element element : categoryRefs) {
            String href = this.url + element.attr("href");
            if (!hrefs.contains(href)) {
                hrefs.add(href);
            }
        }

        return hrefs;
    }

    /**
     * Gets up to 5 articles from each category
     *
     * @param document — category's page document
     * @return list of articles hyper references
     */
    private List<String> getArticleRefs(Document document) {
        Elements byClass = document.getElementsByClass("post-link with-labels ");
        List<String> addresses = new ArrayList<>();

        int n;
        if (byClass.size() < 5) n = byClass.size();
        else n = 5;

        for (int i = 0; i < n; i++) {
            addresses.add(byClass.get(i).attr("href"));
        }

        return addresses;
    }

    /**
     * Creates category model object
     *
     * @param url — category's page url
     * @return created category object
     */
    private Category getCategory(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements byClass = doc.getElementsByClass("category-title");
            return new Category(url, byClass.text(), getArticleRefs(doc));
        } catch (IOException e) {
            System.out.println("Can't find " + url);
            return null;
        }
    }

}

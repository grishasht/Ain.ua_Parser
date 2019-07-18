package model.entity;

import java.util.List;
import java.util.Objects;

public class Category {
    private Integer id;
    private String ref;
    private String name;
    private List<String> articleRefs;

    public Category() {
    }

    public Category(String ref, String name, List<String> articleRefs) {
        this.ref = ref;
        this.name = name;
        this.articleRefs = articleRefs;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getRef() {
        return ref;
    }

    public String getName() {
        return name;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArticleRefs() {
        return articleRefs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(ref, category.ref) &&
                Objects.equals(name, category.name) &&
                Objects.equals(articleRefs, category.articleRefs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ref, name, articleRefs);
    }
}

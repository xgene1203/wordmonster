package com.xgene.wordmonster.model.entity;

import com.xgene.wordmonster.model.Category;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "word", schema = "public", catalog = "wordmonster")
//@TypeDef(name = "CategoryEnum",typeClass = CategoryEnum.class)
public class WordEntity implements Serializable {
    private long id;
    private Category category;
    private String english;
    private String chinese;
    private int freq;
    private String freqLink;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Basic
    @Column(name = "english")
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    @Basic
    @Column(name = "chinese")
    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    @Basic
    @Column(name = "freq")
    public int getFreq() {
        return freq;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    @Basic
    @Column(name = "freq_link")
    public String getFreqLink() {
        return freqLink;
    }

    public void setFreqLink(String freqLink) {
        this.freqLink = freqLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WordEntity)) return false;
        WordEntity that = (WordEntity) o;
        return id == that.id &&
                freq == that.freq &&
                category == that.category &&
                Objects.equals(english, that.english) &&
                Objects.equals(chinese, that.chinese) &&
                Objects.equals(freqLink, that.freqLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, english, chinese, freq, freqLink);
    }
}

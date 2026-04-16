package com.garden.api.lexiconfig;

import com.garden.api.lexiconfig.annotations.Lexicon;
import com.garden.api.lexiconfig.annotations.LexiconEntry;
import com.garden.api.lexiconfig.annotations.LexiconPage;
import com.garden.api.lexiconfig.classes.LexiconData;
import com.garden.api.lexiconfig.classes.LexiconPageData;

@Lexicon(name = Lexiconfig.ID)
public class LexiconfigConfig extends LexiconData {
    @LexiconPage(comment = "This is a test category.")
    public Category testCategory = new Category();

    @LexiconEntry(comment = "This is a global field.")
    public Integer globalField = 8;

    public static class Category extends LexiconPageData {
        @LexiconEntry(comment = "This is a page entry.")
        public Integer pageEntry = 8;
    }
}
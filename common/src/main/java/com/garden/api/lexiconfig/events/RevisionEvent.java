package com.garden.api.lexiconfig.events;

import com.garden.api.lexiconfig.LexiconEvent;
import com.garden.api.lexiconfig.classes.LexiconData;

/**
 * This event is called during the revision process, when all the lexicons are being reloaded, through the {@code /reload} command or other.
 * <p>
 * When using in conjunction with {@code CatalogEvent}, this class can be used in the method in place of it.
 *
 * @see com.garden.api.lexiconfig.events.CatalogEvent
 */
public class RevisionEvent extends LexiconEvent {
    public static class Lexicon extends LexiconEvent {
        public final LexiconData lexiconData;

        public Lexicon(LexiconData lexiconData) {
            this.lexiconData = lexiconData;
        }
    }
}

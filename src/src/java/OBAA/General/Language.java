package OBAA.General;

/**
 * <div class="en">
 * The primary human language or languages
 * used within this learning object to
 * communicate to the intended user.
 * NOTE 1:--An indexation or cataloging tool may
 * provide a useful default.
 * NOTE 2:--If the learning object had no lingual
 * content (as in the case of a picture of the Mona
 * Lisa, for example), then the appropriate value for
 * this data element would be "none".
 * NOTE 3:--This data element concerns the
 * language of the learning object. Data element
 * 3.4:Meta-Metadata.Language concerns the
 * language of the metadata instance.
 *
 * Example:
 * "en","en-GB","de","fr-CA","it", "grc" (ancient greek, until 1453),
 * "en-US-philadelphia", "eng-GB-cockney", "map-PG-buin"
 * (Austronesian –Papua New Guinea – buin), "gem-US-pennsylvania"
 *
 * There is no language consistence verfication.
 * </div>
 *
 * <div class="br">
 * Idioma do objeto de aprendizagem.
 *
 * Não há verificação de consistência.
 *
 * Adaptado de http://www.portalobaa.org/
 * </div>
 *
 * @author LuizRossi
 */
public class Language {

    private String language;

    public Language() {
        language = "";
    }

    public Language(String language) {
        this.language = language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

}

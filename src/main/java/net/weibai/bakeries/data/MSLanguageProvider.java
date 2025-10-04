package net.weibai.bakeries.data;

import net.minecraft.data.PackOutput;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class MSLanguageProvider extends AbstractLanguageProvider {
    private final PackOutput output;
    private final String locale;
    public MSLanguageProvider(PackOutput output, String locale) {
        super(output, locale);
        this.output = output;
        this.locale = locale;
    }
    @Override
    protected void addTranslations() {
        addCreativeModeTabs();
        addItems();
        addBlocks();
        addElements();
        add();
    }

    private void add() {

    }
    private void addCreativeModeTabs() {

    }

    private void addItems() {

    }
    private void addBlocks() {

    }

    private void addElements() {
//        addElements(ElementCollections.H2_NI_O3_SI, "硅酸镍");
//        addElements(ElementCollections.C_H_FE_O4, "碳酸铁");
//        addElements(ElementCollections.CA_C_O3, "CaCO\\U+2083", "CaCO\\U+2083");
    }
}
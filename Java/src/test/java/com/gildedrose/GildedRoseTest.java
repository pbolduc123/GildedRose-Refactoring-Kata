package com.gildedrose;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

public class GildedRoseTest
{
    private static final int INITIAL_AGED_BRIE_QUALITY = 2;
    private static final int INITIAL_APPLE_ITEM_QUALITY = 50;
    public static final int SULFURAS_INITIAL_QUALITY = 50;

    private final Item _agedBrieItem = new Item(GildedRose.AGED_BRIE, 10, INITIAL_AGED_BRIE_QUALITY);
    private final Item _appleItem = new Item("apple", 10, INITIAL_APPLE_ITEM_QUALITY);
    private final Item _sulfurasItem = new Item(GildedRose.SULFURAS_HAND_OF_RAGNAROS, 0, SULFURAS_INITIAL_QUALITY);

    @Test
    public void whenUpdateQuality_thenNameRemainsUnchanged()
    {
        GildedRose app = getApp(_appleItem);
        app.updateQuality();
        assertEquals(_appleItem.name, _appleItem.name);
    }

    @Test
    public void whenUpdateQualityOfGenericItem_thenQualityDecreaseOfOne()
    {
        GildedRose app = getApp(_appleItem);
        app.updateQuality();
        assertEquals(INITIAL_APPLE_ITEM_QUALITY - 1, _appleItem.quality);
    }

    @Test
    public void whenUpdatingQualityOfAgedBrie_thenQualityIncrease()
    {
        GildedRose app = getApp(_agedBrieItem);
        app.updateQuality();
        assertEquals(INITIAL_AGED_BRIE_QUALITY + 1, _agedBrieItem.quality);
    }

    @Test
    public void whenUpdatingQualityOfExpiredApple_thenQualityDeacreaseTwiceAsFast()
    {
        GildedRose app = getApp(_appleItem);

        while (_appleItem.sellIn >= 0)
        {
            app.updateQuality();
        }

        int appleQuality = _appleItem.quality;

        app.updateQuality();

        assertEquals(appleQuality - 2, _appleItem.quality);
    }

    @Test
    public void testQualityIsNeverNegative()
    {
        final GildedRose app = getApp(_appleItem);

        for (int i = 0; i < INITIAL_APPLE_ITEM_QUALITY + 1; i++)
            app.updateQuality();

        assertThat(_appleItem.quality, is(0));
    }

    @Test
    public void testQualityIsNeverGreaterThanFifty()
    {
        final GildedRose app = getApp(_agedBrieItem);

        for (int i = 0; i < 51; i++)
            app.updateQuality();

        assertThat(_agedBrieItem.quality, is(50));
    }

    @Test
    public void testSulfarasHammerNeverDecreaseInQuality()
    {
        final GildedRose app = getApp(_sulfurasItem);

        app.updateQuality();

        assertThat(_sulfurasItem.quality, is(SULFURAS_INITIAL_QUALITY));
    }

    private GildedRose getApp(final Item...items)
    {
        return new GildedRose(items);
    }
}

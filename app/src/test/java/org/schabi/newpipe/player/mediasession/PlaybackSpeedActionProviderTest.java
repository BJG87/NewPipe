package org.schabi.newpipe.player.mediasession;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.schabi.newpipe.R;

public class PlaybackSpeedActionProviderTest {

    private static final float DELTA = 0.001f;

    @Test
    public void testStandardSpeedTransitions() {
        assertEquals(1.25f, PlaybackSpeedActionProvider.getNextSpeedPreset(1.0f), DELTA);
        assertEquals(1.5f, PlaybackSpeedActionProvider.getNextSpeedPreset(1.25f), DELTA);
        assertEquals(2.0f, PlaybackSpeedActionProvider.getNextSpeedPreset(1.5f), DELTA);
        assertEquals(1.0f, PlaybackSpeedActionProvider.getNextSpeedPreset(2.0f), DELTA);
    }

    @Test
    public void testCustomSpeedTransitions() {
        // Below 1.0x advances to 1.0x
        assertEquals(1.0f, PlaybackSpeedActionProvider.getNextSpeedPreset(0.5f), DELTA);
        assertEquals(1.0f, PlaybackSpeedActionProvider.getNextSpeedPreset(0.75f), DELTA);

        // Between 1.0x and 1.25x advances to 1.25x
        assertEquals(1.25f, PlaybackSpeedActionProvider.getNextSpeedPreset(1.1f), DELTA);

        // Between 1.25x and 1.5x advances to 1.5x
        assertEquals(1.5f, PlaybackSpeedActionProvider.getNextSpeedPreset(1.3f), DELTA);

        // Between 1.5x and 2.0x advances to 2.0x
        assertEquals(2.0f, PlaybackSpeedActionProvider.getNextSpeedPreset(1.75f), DELTA);

        // At or above 2.0x wraps back to 1.0x
        assertEquals(1.0f, PlaybackSpeedActionProvider.getNextSpeedPreset(2.5f), DELTA);
        assertEquals(1.0f, PlaybackSpeedActionProvider.getNextSpeedPreset(3.0f), DELTA);
    }

    @Test
    public void testSpeedIcons() {
        assertEquals(R.drawable.ic_speed_1x, PlaybackSpeedActionProvider.getSpeedIcon(1.0f));
        assertEquals(R.drawable.ic_speed_1_25x, PlaybackSpeedActionProvider.getSpeedIcon(1.25f));
        assertEquals(R.drawable.ic_speed_1_5x, PlaybackSpeedActionProvider.getSpeedIcon(1.5f));
        assertEquals(R.drawable.ic_speed_2x, PlaybackSpeedActionProvider.getSpeedIcon(2.0f));

        // Default icon for non-presets is 1x
        assertEquals(R.drawable.ic_speed_1x, PlaybackSpeedActionProvider.getSpeedIcon(0.8f));
        assertEquals(R.drawable.ic_speed_1x, PlaybackSpeedActionProvider.getSpeedIcon(1.75f));
    }
}

package org.schabi.newpipe.player.mediasession;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector;

import org.schabi.newpipe.R;
import org.schabi.newpipe.player.Player;

import java.lang.ref.WeakReference;

public class PlaybackSpeedActionProvider implements MediaSessionConnector.CustomActionProvider {

    static final String ACTION_CYCLE_PLAYBACK_SPEED =
            "org.schabi.newpipe.player.ACTION_CYCLE_PLAYBACK_SPEED";

    private static final float[] SPEED_PRESETS = {1.0f, 1.25f, 1.5f, 2.0f};
    private static final float SPEED_EPSILON = 0.05f;

    @NonNull
    private final Player player;
    @NonNull
    private final WeakReference<Context> context;

    public PlaybackSpeedActionProvider(@NonNull final Player player,
                                       @NonNull final Context context) {
        this.player = player;
        this.context = new WeakReference<>(context);
    }

    @Override
    public void onCustomAction(@NonNull final com.google.android.exoplayer2.Player exoPlayer,
                               @NonNull final String action,
                               @Nullable final Bundle extras) {
        if (ACTION_CYCLE_PLAYBACK_SPEED.equals(action)) {
            final float currentSpeed = player.getPlaybackSpeed();
            final float nextSpeed = getNextSpeedPreset(currentSpeed);
            player.setPlaybackSpeed(nextSpeed);
        }
    }

    @Nullable
    @Override
    public PlaybackStateCompat.CustomAction getCustomAction(
            @NonNull final com.google.android.exoplayer2.Player exoPlayer) {
        final Context actualContext = context.get();
        if (actualContext == null) {
            return null;
        }

        final float currentSpeed = player.getPlaybackSpeed();
        final int icon = getSpeedIcon(currentSpeed);
        final String actionName = actualContext.getString(R.string.playback_speed_control);

        return new PlaybackStateCompat.CustomAction.Builder(
                ACTION_CYCLE_PLAYBACK_SPEED, actionName, icon
        ).build();
    }

    static float getNextSpeedPreset(final float currentSpeed) {
        for (final float preset : SPEED_PRESETS) {
            if (preset - currentSpeed > SPEED_EPSILON) {
                return preset;
            }
        }
        return SPEED_PRESETS[0];
    }

    @DrawableRes
    static int getSpeedIcon(final float speed) {
        if (Math.abs(speed - 1.25f) < SPEED_EPSILON) {
            return R.drawable.ic_speed_1_25x;
        } else if (Math.abs(speed - 1.50f) < SPEED_EPSILON) {
            return R.drawable.ic_speed_1_5x;
        } else if (Math.abs(speed - 2.00f) < SPEED_EPSILON) {
            return R.drawable.ic_speed_2x;
        } else {
            return R.drawable.ic_speed_1x;
        }
    }
}

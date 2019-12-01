package com.climpy.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {

    public static String getDurationBreakdown(long milliseconds) {
        if(milliseconds < 0) {
            throw new IllegalArgumentException("Değer 0'dan düşük!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        milliseconds -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        milliseconds -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        StringBuilder stringBuilder = new StringBuilder(64);

        if (!(days == 0)) {
            stringBuilder.append(days);
            stringBuilder.append(" gün ");
        }

        if (!(hours == 0)) {
            stringBuilder.append(hours);
            stringBuilder.append(" saat ");
        }

        if (!(minutes == 0)) {
            stringBuilder.append(minutes);
            stringBuilder.append(" dakika ");
        }

        if (!(seconds == 0)) {
            stringBuilder.append(seconds);
            stringBuilder.append(" saniye");
        }

        return stringBuilder.toString();
    }

    public static long getTimeParser(String replacement) {
        if (replacement.substring(0, 1).matches("[0-9]")) {

            try {
                char durationEndWith = replacement.charAt(replacement.length() - 1);
                long duration = Long.valueOf(replacement.substring(0, replacement.length() - 1));

                if (duration < 1) {
                    return 0;
                }

                if (durationEndWith == 's' || durationEndWith == 'S') {
                    duration = duration * 1000;
                } else if (durationEndWith == 'm' || durationEndWith == 'M') {
                    duration = duration * 1000 * 60;
                } else if (durationEndWith == 'h' || durationEndWith == 'H') {
                    duration = duration * 1000 * 60 * 60;
                } else if (durationEndWith == 'd' || durationEndWith == 'D') {
                    duration = duration * 1000 * 60 * 60 * 24;
                } else if (durationEndWith == 'w' || durationEndWith == 'W') {
                    duration = duration * 1000 * 60 * 60 * 24 * 7;
                } else if (durationEndWith == 'n' || durationEndWith == 'N') {
                    duration = duration * 1000 * 60 * 60 * 24 * 30;
                } else if (durationEndWith == 'y' || durationEndWith == 'Y') {
                    duration = duration * 1000 * 60 * 60 * 24 * 365;
                } else {
                    return 0;
                }

                return duration;
            } catch (Exception ex) {
                return 0;
            }
        }

        return 0;
    }

    public static String getDurationWordsWithMilliseconds(long durationMillis, boolean suppressLeadingZeroElements, boolean suppressTrailingZeroElements) {
        return getDurationWords(durationMillis, suppressLeadingZeroElements, suppressTrailingZeroElements, "d'g 'H's 'm'd 's'.'S' saniye'");
    }

    public static String getDurationWords(long durationMillis, boolean suppressLeadingZeroElements, boolean suppressTrailingZeroElements) {
        return getDurationWords(durationMillis, suppressLeadingZeroElements, suppressTrailingZeroElements, "d' gün 'H' saat 'm' dakika 's' saniye'");
    }

    public static String getDurationWords(long durationMillis, boolean suppressLeadingZeroElements, boolean suppressTrailingZeroElements, String format) {
        if (durationMillis <= 0) {
            return "Süre değeri sıfırdan büyük olmalıdır!";
        }

        String duration = DurationFormatUtils.formatDuration(durationMillis, format);
        String tmp;

        if (suppressLeadingZeroElements) {
            duration = " " + duration;
            tmp = StringUtils.replaceOnce(duration, " 0g", "").replace(" 0 gün", "");

            if (tmp.length() != duration.length()) {
                duration = tmp;
                tmp = StringUtils.replaceOnce(tmp, " 0s", "").replace(" 0 saat", "");

                if (tmp.length() != duration.length()) {
                    tmp = StringUtils.replaceOnce(tmp, " 0d", "").replace(" 0 dakika", "");
                    duration = tmp;
                }
            }

            if (duration.length() != 0) {
                duration = duration.substring(1);
            }
        }

        if (suppressTrailingZeroElements) {
            tmp = StringUtils.replaceOnce(duration, " 0 saniye", "");
            if (tmp.length() != duration.length()) {
                duration = tmp;

                tmp = StringUtils.replaceOnce(tmp, " 0d", "").replace(" 0 dakika", "");;
                if (tmp.length() != duration.length()) {
                    duration = tmp;

                    tmp = StringUtils.replaceOnce(tmp, " 0s", "").replace(" 0 saat", "");;
                    if (tmp.length() != duration.length()) {
                        duration = StringUtils.replaceOnce(tmp, " 0g", "").replace(" 0 gün", "");;
                    }
                }
            }
        }

        duration = " " + duration;
        duration = StringUtils.replaceOnce(duration, " 1 saniye", " 1 saniye");
        duration = StringUtils.replaceOnce(duration, " 1 d", " 1 d");
        duration = StringUtils.replaceOnce(duration, " 1 s", " 1 s");
        duration = StringUtils.replaceOnce(duration, " 1 g", " 1 g");

        return duration.trim();
    }

}

package com.prind.ctf.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang.time.DurationFormatUtils;

@UtilityClass
public class TimeUtil {

    public String formatDurationLong(long input) {
        return DurationFormatUtils.formatDurationWords(input, true, true);
    }
}

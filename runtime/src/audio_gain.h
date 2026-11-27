#pragma once

#include <algorithm>
#include <cmath>
#include <cstdint>
#include <cstdlib>

namespace nx::plat {

inline int parse_audio_gain_percent(const char* value) {
    if (!value || !*value) return 150;
    char* end = nullptr;
    const long parsed = std::strtol(value, &end, 10);
    if (end == value || *end != '\0') return 150;
    return static_cast<int>(std::clamp(parsed, 50L, 200L));
}

// Stereo-linked soft-knee limiter. Keep unity bit-exact and preserve channel balance.
// Above 80% full scale the output approaches full scale smoothly, without integer overflow.
inline void apply_audio_gain(int16_t* pcm, int32_t frames, int percent) {
    if (percent == 100 || frames <= 0) return;
    const float gain = std::clamp(percent, 50, 200) * 0.01f;
    constexpr float full = 32767.0f;
    constexpr float knee = full * 0.8f;
    constexpr float room = full - knee;
    for (int32_t i = 0; i < frames; ++i, pcm += 2) {
        const float left = pcm[0] * gain;
        const float right = pcm[1] * gain;
        const float peak = std::max(std::abs(left), std::abs(right));
        float scale = 1.0f;
        if (percent > 100 && peak > knee) {
            const float excess = peak - knee;
            const float limited = knee + room * excess / (room + excess);
            scale = limited / peak;
        }
        pcm[0] = static_cast<int16_t>(std::clamp(std::round(left * scale), -32768.0f, full));
        pcm[1] = static_cast<int16_t>(std::clamp(std::round(right * scale), -32768.0f, full));
    }
}

} // namespace nx::plat

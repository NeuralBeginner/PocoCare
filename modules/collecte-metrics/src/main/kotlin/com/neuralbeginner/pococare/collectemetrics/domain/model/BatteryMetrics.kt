package com.neuralbeginner.pococare.collectemetrics.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.Instant

/**
 * Core battery metrics collected from the Android system.
 * 
 * This data class represents all battery-related information that can be
 * collected from the device, following privacy-by-design principles.
 */
@Parcelize
@Serializable
data class BatteryMetrics(
    val timestamp: Long = Instant.now().toEpochMilli(),
    val level: Int, // Percentage 0-100
    val temperature: Float, // Celsius
    val voltage: Float, // Volts
    val current: Float, // Milliamps (positive = charging, negative = discharging)
    val capacity: Int, // mAh
    val cycleCount: Int, // Number of charge cycles
    val health: BatteryHealth,
    val chargingStatus: ChargingStatus,
    val powerSource: PowerSource,
    val isCharging: Boolean,
    val timeToFull: Long? = null, // Milliseconds, null if not charging
    val timeToEmpty: Long? = null, // Milliseconds, null if charging
    val screenOnTime: Long, // Milliseconds since last charge
    val wakeTime: Long // Milliseconds device was awake
) : Parcelable {
    
    /**
     * Anonymizes the metrics by removing/obfuscating identifying information.
     * Used for data export and analytics while preserving privacy.
     */
    fun anonymize(): BatteryMetrics = copy(
        timestamp = (timestamp / 3600000) * 3600000, // Round to nearest hour
        cycleCount = cycleCount / 10 * 10, // Round to nearest 10
        capacity = capacity / 100 * 100 // Round to nearest 100
    )
    
    /**
     * Validates that the metrics are within expected ranges.
     */
    fun isValid(): Boolean {
        return level in 0..100 &&
                temperature > -50 && temperature < 100 &&
                voltage > 0 && voltage < 10 &&
                capacity > 0 &&
                cycleCount >= 0
    }
}

@Serializable
enum class BatteryHealth {
    GOOD,
    FAIR, 
    POOR,
    CRITICAL,
    UNKNOWN;
    
    val displayName: String
        get() = when (this) {
            GOOD -> "Excellent"
            FAIR -> "Bon"
            POOR -> "Dégradé"
            CRITICAL -> "Critique"
            UNKNOWN -> "Inconnu"
        }
}

@Serializable
enum class ChargingStatus {
    CHARGING,
    DISCHARGING,
    NOT_CHARGING,
    FULL;
    
    val displayName: String
        get() = when (this) {
            CHARGING -> "En charge"
            DISCHARGING -> "Décharge"
            NOT_CHARGING -> "Non branché"
            FULL -> "Chargé"
        }
}

@Serializable
enum class PowerSource {
    AC,
    USB,
    WIRELESS,
    BATTERY;
    
    val displayName: String
        get() = when (this) {
            AC -> "Secteur"
            USB -> "USB"
            WIRELESS -> "Sans fil"
            BATTERY -> "Batterie"
        }
}
package com.elecora.app.calculator

import kotlin.math.sqrt

enum class PhaseType {
    SINGLE,
    THREE
}

enum class Conductor {
    COPPER,
    ALUMINIUM
}

data class VoltageDropResult(
    val volts: Double,
    val percent: Double,
    val resistanceOhm: Double
)

data class CableSizingResult(
    val section: Int,
    val ampacity: Double,
    val voltageDropPercent: Double,
    val designCurrent: Double
)

data class BreakerResult(
    val rating: Int,
    val loadCurrent: Double,
    val note: String
)

object ElectricalCalculations {

    private const val RHO_CU = 0.0175
    private const val RHO_AL = 0.0282

    private val standardSections = intArrayOf(
        1, 2, 4, 6, 10, 16, 25, 35,
        50, 70, 95, 120, 150, 185, 240
    )

    fun voltageDrop(
        current: Double?,
        length: Double?,
        section: Double?,
        cosPhi: Double?,
        phase: PhaseType,
        material: Conductor
    ): Pair<VoltageDropResult?, String?> {

        if (
            current == null ||
            length == null ||
            section == null ||
            cosPhi == null
        ) {
            return null to "أدخل جميع القيم."
        }

        if (
            current <= 0 ||
            length <= 0 ||
            section <= 0
        ) {
            return null to "القيم يجب أن تكون أكبر من صفر."
        }

        if (cosPhi <= 0 || cosPhi > 1) {
            return null to "Cos φ يجب أن يكون بين 0 و 1."
        }

        val rho =
            if (material == Conductor.COPPER)
                RHO_CU
            else
                RHO_AL

        val resistance =
            rho * length / section

        val voltageDrop =
            if (phase == PhaseType.SINGLE) {
                2.0 * current * resistance * cosPhi
            } else {
                sqrt(3.0) * current * resistance * cosPhi
            }

        val nominalVoltage =
            if (phase == PhaseType.SINGLE)
                230.0
            else
                400.0

        val percent =
            voltageDrop / nominalVoltage * 100.0

        return VoltageDropResult(
            volts = voltageDrop,
            percent = percent,
            resistanceOhm = resistance
        ) to null
    }

    fun sizeCable(
        current: Double?,
        length: Double?,
        cosPhi: Double?,
        maxDrop: Double?,
        phase: PhaseType,
        material: Conductor
    ): Pair<CableSizingResult?, String?> {

        if (
            current == null ||
            length == null ||
            cosPhi == null ||
            maxDrop == null
        ) {
            return null to "أدخل جميع القيم."
        }

        if (
            current <= 0 ||
            length <= 0 ||
            cosPhi <= 0 ||
            cosPhi > 1 ||
            maxDrop <= 0
        ) {
            return null to "تحقق من القيم المدخلة."
        }

        val ampacityTable =
            if (material == Conductor.COPPER) {

                mapOf(
                    1 to 11.0,
                    2 to 18.0,
                    4 to 25.0,
                    6 to 32.0,
                    10 to 44.0,
                    16 to 59.0,
                    25 to 77.0,
                    35 to 96.0,
                    50 to 117.0,
                    70 to 149.0,
                    95 to 179.0,
                    120 to 206.0,
                    150 to 225.0,
                    185 to 258.0,
                    240 to 300.0
                )

            } else {

                mapOf(
                    1 to 8.0,
                    2 to 14.0,
                    4 to 20.0,
                    6 to 26.0,
                    10 to 36.0,
                    16 to 47.0,
                    25 to 61.0,
                    35 to 75.0,
                    50 to 91.0,
                    70 to 116.0,
                    95 to 139.0,
                    120 to 160.0,
                    150 to 176.0,
                    185 to 201.0,
                    240 to 234.0
                )
            }

        for (section in standardSections) {

            val ampacity =
                ampacityTable[section]
                    ?: continue

            if (ampacity < current)
                continue

            val drop =
                voltageDrop(
                    current,
                    length,
                    section.toDouble(),
                    cosPhi,
                    phase,
                    material
                ).first ?: continue

            if (drop.percent <= maxDrop) {

                return CableSizingResult(
                    section = section,
                    ampacity = ampacity,
                    voltageDropPercent = drop.percent,
                    designCurrent = current
                ) to null
            }
        }

        return null to
                "لا توجد section مناسبة ضمن الجدول المبسط."
    }

    fun breaker(
        current: Double?,
        cableAmpacity: Double?
    ): Pair<BreakerResult?, String?> {

        if (
            current == null ||
            cableAmpacity == null
        ) {
            return null to "أدخل Ib و Iz."
        }

        if (
            current <= 0 ||
            cableAmpacity <= 0
        ) {
            return null to "القيم يجب أن تكون أكبر من صفر."
        }

        val standardRatings = intArrayOf(
            2, 4, 6, 10, 13, 16,
            20, 25, 32, 40, 50,
            63, 80, 100, 125,
            160, 200, 250, 315, 400
        )

        val selected =
            standardRatings.firstOrNull {
                it >= current &&
                it <= cableAmpacity
            }

        if (selected == null) {
            return null to
                    "لا يوجد calibre قياسي مناسب لهذه القيم."
        }

        return BreakerResult(
            rating = selected,
            loadCurrent = current,
            note = "تحقق من منحنى الفصل وقدرة القطع وطبيعة الدارة."
        ) to null
    }
}

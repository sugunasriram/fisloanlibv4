package com.github.sugunasriram.fisloanlibv4.fiscode.network.model.personaLoan

class DurationPeriod {
    var durationPeriodYears: String? = null
    var durationPeriodMonths: String? = null
    fun getdurationPeriodWeeks(): String? {
        return durationPeriodWeeks
    }

    fun setdurationPeriodWeeks(weeks: String?) {
        this.durationPeriodWeeks = weeks
    }

    var durationPeriodWeeks: String? = null
    var durationPeriodDays: String? = null
    var durationPeriodHours: String? = null
    var durationPeriodMinutes: String? = null
    var durationPeriodSeconds: String? = null
    fun setdurationPeriodYears(years: String?) {
        this.durationPeriodYears = years
    }

    fun setdurationPeriodMonths(months: String?) {
        this.durationPeriodMonths = months
    }

    fun setdurationPeriodDays(days: String?) {
        this.durationPeriodDays = days
    }

    fun setHours(hours: String?) {
        this.durationPeriodHours = hours
    }

    fun setdurationPeriodMinutes(minutes: String?) {
        this.durationPeriodMinutes = minutes
    }

    fun setdurationPeriodSeconds(seconds: String?) {
        this.durationPeriodSeconds = seconds
    }

    fun getdurationPeriodYears(): String? {
        return durationPeriodYears
    }

    fun getdurationPeriodMonths(): String? {
        return durationPeriodMonths
    }

    fun getdurationPeriodDays(): String? {
        return durationPeriodDays
    }

    fun getdurationPeriodHours(): String? {
        return durationPeriodHours
    }

    fun getdurationPeriodMinutes(): String? {
        return durationPeriodMinutes
    }

    fun getdurationPeriodSeconds(): String? {
        return durationPeriodSeconds
    }

    companion object {
        fun parse(data: String?): DurationPeriod? {
            var myPeriod: DurationPeriod? = null
            if (data != null && data.length > 0) {
                var i = 0
                val dataChar = data.toCharArray()
                var temp = ""
                var crossedT = false
                myPeriod = DurationPeriod()
                while (i < data.length) {
                    if (dataChar[i] == 'P') {
                        temp = ""
                    } else if (dataChar[i] == 'Y') {
                        myPeriod.durationPeriodYears = temp
                        temp = ""
                    } else if (dataChar[i] == 'M' && crossedT == false) {
                        myPeriod.durationPeriodMonths = temp
                        temp = ""
                    } else if (dataChar[i] == 'W') {
                        myPeriod.durationPeriodWeeks = temp
                        temp = ""
                    } else if (dataChar[i] == 'D') {
                        myPeriod.durationPeriodDays = temp
                        temp = ""
                    } else if (dataChar[i] == 'T') {
                        crossedT = true
                        temp = ""
                    } else if (dataChar[i] == 'H') {
                        myPeriod.durationPeriodHours = temp
                        temp = ""
                    } else if (dataChar[i] == 'M' && crossedT) {
                        myPeriod.durationPeriodMinutes = temp
                        temp = ""
                    } else if (dataChar[i] == 'S') {
                        myPeriod.durationPeriodSeconds = temp
                        temp = ""
                    } else {
                        temp += dataChar[i]
                    }
                    i++
                }
            }
            return myPeriod
        }
    }
}

package com.nikitaend.polproject.time;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Расписание интервалов температур на неделю.
 */
public class WeekSchedule implements Serializable {
    /**
     * Список расписаний на каждый день недели
     */
    ArrayList<DaySchedule> daySchedules;


    public WeekSchedule() {
        initSchedule();
    }

    /**
     * Adds new interval in day schedule
     *
     * @param weekday  weekday
     * @param interval interval
     * @return ID of added users
     * @throws Exception
     */
    public int addInterval(Weekday weekday, TimeInterval interval) throws Exception {
        for (DaySchedule daySchedule : daySchedules) {
            if (daySchedule.getWeekday() == weekday) {
                daySchedule.addInterval(interval);
                return interval.getId();
            }
        }

        throw new Exception("Weekday not found!");
    }

    /**
     * Removes interval by ID
     *
     * @param intervalID Unique interval ID
     */
    public void removeInterval(int intervalID) {
        for (DaySchedule daySchedule : daySchedules) {
            daySchedule.removeInterval(intervalID);
        }
    }

    public void removeIntervalByIndex(int intervalIndex, Weekday weekday) {
        for (DaySchedule daySchedule : daySchedules) {
            if (daySchedule.getWeekday() == weekday) {
                daySchedule.removeIntervalByIndex(intervalIndex);
                return;
            }
        }
    }

    /**
     * Gets intervals of a week
     *
     * @return intervalse of a week
     */
    public ArrayList<DaySchedule> getDaysSchedule() {
        return daySchedules;
    }

    /**
     * Gets next interval
     *
     * @return next interval
     */
    public TimeInterval getNextInterval(TimeInterval interval) {
        for (DaySchedule daySchedule : daySchedules) {
            if (daySchedule.getNextInterval(interval) != null) {
                return daySchedule.getNextInterval(interval);
            }
        }

        for (DaySchedule daySchedule : daySchedules) {
            if (daySchedule.getFirstInterval() != null) {
                if (!daySchedule.getFirstInterval().equals(interval)) {
                    return daySchedule.getFirstInterval();
                }
            }
        }
        return null;
    }

    public TimeInterval getNextInterval(Time time) {
        TimeInterval nextInterval;
        for (DaySchedule daySchedule : daySchedules) {
            if (daySchedule.getWeekday() == time.getWeekday()) {
                nextInterval = daySchedule.getNextInterval(time);
                if (nextInterval != null) {
                    return nextInterval;
                }
                break;
            }
        }

        boolean flag = false;
        for (DaySchedule daySchedule : daySchedules) {
            if (daySchedule.getWeekday() == time.getWeekday()) {
                flag = true;
                continue;
            }
            if (flag) {
                nextInterval = daySchedule.getFirstInterval();
                if (nextInterval != null) {
                    return nextInterval;
                }
            }
        }
        flag = false;
        for (DaySchedule daySchedule : daySchedules) {
            if (daySchedule.getWeekday() == time.getWeekday()) {
                return null;
            }
            nextInterval = daySchedule.getFirstInterval();
            if (nextInterval != null) {
                return nextInterval;
            }
        }
        return null;
    }

    /**
     * Creates and initialize week schedule
     */
    private void initSchedule() {
        daySchedules = new ArrayList<>();

        for (Weekday weekday : Weekday.values()) {
            daySchedules.add(new DaySchedule(weekday));
        }
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();

        for (DaySchedule daySchedule : this.daySchedules) {
            resultString.append(daySchedule.toString());
            resultString.append("\n");
        }

        return resultString.toString();
    }
}

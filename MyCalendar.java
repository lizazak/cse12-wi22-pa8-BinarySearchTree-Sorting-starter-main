/* Name: Liza Zakharova
 * ID: A16907410
 * Email: ezakharova@ucsd.edu
 * Sources used: None
 * 
 * This file contains a class MyCalendar which represents a calendar
 * where you can make bookings.
 */

/**
 * This class contains an instance variable MyTreeMap responsible for
 * keeping track of bookings. This class functions as a calendar where
 * you can make bookings with start and end times.
 */
public class MyCalendar {
    MyTreeMap<Integer, Integer> calendar;
    
    /**
     * Initializes calendar as an empty MyTreeMap
     */
    public MyCalendar() {
        this.calendar = new MyTreeMap<>();
    }
    
    /**
     * Books a new event if it does not cause a double booking. If
     * booking successful, returns true, otherwise false. If start time
     * less than 0 or greater than/equal to end, throws exception.
     * @param start start time of potential booking
     * @param end start time of potential booking
     * @return true if booking successful, false if not
     */
    public boolean book(int start, int end) {
        if (start < 0 || start >= end) {
            throw new IllegalArgumentException();
        }
        boolean canPut = true;
        // checks closest booking start before this start
        Integer beforeStart = calendar.floorKey(start);
        // closest booking start after this start
        Integer afterStart = calendar.ceilingKey(start);

        // checks if booking before exists
        if (beforeStart != null) {
            // if prev end overlaps with current start, cannot put
            if (calendar.get(beforeStart) > start) {
                canPut = false;
            }
        }
        // checks if booking after exists
        if (afterStart != null) {
            // after start time overlaps with current end, cannot put
            if (afterStart < end) {
                canPut = false;
            }
        }
        if (canPut) {
            // puts calendar booking as key = start, value = end
            calendar.put(start, end);
        }
        return canPut;
    }

    /**
     * Returns the calendar tree map
     * @return the calendar MyTreeMap
     */
    public MyTreeMap getCalendar(){
        return this.calendar;
    }
}
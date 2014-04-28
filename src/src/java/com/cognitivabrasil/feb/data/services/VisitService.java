package com.cognitivabrasil.feb.data.services;

import java.util.List;

import com.cognitivabrasil.feb.data.entities.Visita;
import java.util.Date;

/**
 *
 * @author cei
 */
public interface VisitService {

    /**
     *
     * @return All visits' timestamp
     */
    public List<Visita> getAll();

    /**
     * Register a visit on the database
     */
    public void save(Visita v);

    /**
     * Calculate the number of visits in a month
     *
     * @param month number of the month in a year
     * @param year the year in 4 digits in a gregorian calendar
     * @return
     */
    public int visitsInAMonth(int month, int year);

    /**
     * Calculate the number of visits in a year
     *
     * @param year the year in 4 digits in a gregorian calendar
     * @return a list of 12 elements with the visits by month
     */
    public List<Integer> visitsInAYear(int year);
    
    /**
     * Calculate the number of visits in the past 12 months up to the month in parameter
     *
     * @param month number of the month in a year
     * @param year the year in 4 digits in a gregorian calendar
     * @param numMonth Number of months before you want to receive the result
     * @returna list of numMonth elements with the number of visits by month
     */
    public List<Integer> visitsUpToAMonth(int month, int year, int numMonth);
    
    public List<Integer> visitsToBetweenDates(Date i, Date f);
    
    /**
     * Get the Visita of an id.
     * @param id
     * @return
     */
    public Visita get(int id);
}

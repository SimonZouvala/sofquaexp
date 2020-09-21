package fi.muni.sqe.entity;

import fi.muni.sqe.ManipulationMethod;

import java.util.List;
import java.util.Objects;

/**
 * Class, that represents data for save to file as {@link ManipulationMethod},
 * years for the results and list of results.
 *
 * @author Šimon Zouvala {445475@mail.muni.cz}
 */

public class DataToPrint {
    private ManipulationMethod method;
    private List<Integer> years;
    private List<Result> results;

    /**
     * @param method  method that was done
     * @param years   years used for get results
     * @param results results that were created
     */
    public DataToPrint(ManipulationMethod method, List<Integer> years, List<Result> results) {
        this.method = method;
        this.years = years;
        this.results = results;
    }

    public ManipulationMethod getMethod() {
        return method;
    }

    public DataToPrint setMethod(ManipulationMethod method) {
        this.method = method;
        return this;
    }

    public List<Integer> getYears() {
        return years;
    }

    public DataToPrint setYears(List<Integer> year) {
        this.years = year;
        return this;
    }

    public List<Result> getResults() {
        return results;
    }

    public DataToPrint setResults(List<Result> results) {
        this.results = results;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataToPrint dataToPrint = (DataToPrint) o;
        return method == dataToPrint.method &&
                Objects.equals(years, dataToPrint.years) &&
                Objects.equals(results, dataToPrint.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, years, results);
    }
}

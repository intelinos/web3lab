package model;

import db.DAOFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Objects;

@Named
@ApplicationScoped
public class CheckAreaResultsBean implements Serializable {
    @Inject
    private SelectXBean selectXBean;
    @Inject
    private SelectYBean selectYBean;
    @Inject
    private SelectRBean selectRBean;

    private LinkedList<CheckAreaBean> results;

    public CheckAreaResultsBean() {
        super();
        results = new LinkedList<>();
        // fill db with values
        try {
            results = new LinkedList<>(DAOFactory.getInstance().getResultDAO().getAllResults());
        } catch (SQLException ignored) {}
    }

    @Named(value = "resultList")
    public LinkedList<CheckAreaBean> getResults() {
        return results;
    }

    public void setResults(LinkedList<CheckAreaBean> results) {
        this.results = results;
    }

    public void newResult(final double x, final double y, final double r) {
        if (r == 0){
            FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("createErrorMessage(\"Select R before pointing at the graph!\");");
            return;
        }
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("removeErrorMessage()");
        final CheckAreaBean currentResult = new CheckAreaBean();
        final boolean result = AreaResultChecker.getResult(x, y, r);
        currentResult.setX(x);
        currentResult.setY(y);
        currentResult.setR(r);
        currentResult.setResult(result);
        try {
            DAOFactory.getInstance().getResultDAO().addNewResult(currentResult);
        } catch (SQLException ignored) {}
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("drawPointXYRRes(" + x + ", " + y + ", " + r + ", " + result + ");");
        results.addFirst(currentResult);
    }

    public void clearResults() {
        results.clear();
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts().add("doClear()");
        try {
            DAOFactory.getInstance().getResultDAO().clearResults();
        } catch (SQLException ignored) {}
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckAreaResultsBean)) return false;
        CheckAreaResultsBean that = (CheckAreaResultsBean) o;
        return Objects.equals(selectXBean, that.selectXBean) && Objects.equals(selectYBean, that.selectYBean) && Objects.equals(selectRBean, that.selectRBean) && Objects.equals(getResults(), that.getResults());
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectXBean, selectYBean, selectRBean, getResults());
    }

    @Override
    public String toString() {
        return "CheckAreaResultsBean{" +
                "selectXBean=" + selectXBean +
                ", selectYBean=" + selectYBean +
                ", selectRBean=" + selectRBean +
                ", results=" + results +
                '}';
    }
}

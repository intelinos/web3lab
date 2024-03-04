package model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Objects;

@Named
@ApplicationScoped
public class SelectRBean implements Serializable {
    private Double value;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
        FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts()
                .add("removeErrorMessage()");
        if (value!=null) {
            FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts()
                    .add("resize("+value+")");
            FacesContext.getCurrentInstance().getPartialViewContext().getEvalScripts()
                    .add("drawGraphByR(" + value + ");");
        }
    }

    public void validateSelectR(FacesContext facesContext,
                                UIComponent uiComponent, Object o) {
        if (o == null) {
            value = null;
            FacesMessage message = new FacesMessage("Please, input R!");
            throw new ValidatorException(message);
        }
        if ((double) o < 2 || (double) o > 5) {
            value = null;
            FacesMessage message = new FacesMessage("R must be between 2 and 5!");
            throw new ValidatorException(message);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SelectRBean)) return false;
        SelectRBean that = (SelectRBean) o;
        return Double.compare(getValue(), that.getValue()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return "SelectRBean{" +
                "value=" + value +
                '}';
    }
}

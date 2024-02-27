package model;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.Objects;

@Named
@SessionScoped
public class SelectXBean implements Serializable {

    private double value;

    public void setValue(double value) {this.value = value;}
    public double getValue() {
        return value;
    }
    public void sliderValueChanged(){
        System.out.println("Slider value: " + getValue());
    }
    public void validateSelectX(FacesContext context, UIComponent component, Object o) {
        if (o == null) {
            FacesMessage message = new FacesMessage("Please, choose X!");
            throw new ValidatorException(message);
        }
        if ((double) o != -2 && (double) o != -1.5 && (double) o != -1 && (double) o != -0.5 &&
                (double) o != 0 && (double) o != 0.5 && (double) o != 1 && (double) o != 1.5 &&
                (double) o != 2 ) {
            FacesMessage message = new FacesMessage("Y must be between -5 and 3!");
            throw new ValidatorException(message);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SelectXBean)) return false;
        SelectXBean that = (SelectXBean) o;
        return Double.compare(getValue(), that.getValue()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return "SelectXBean{" +
                "value=" + value +
                '}';
    }
}

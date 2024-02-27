package model;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Named
@Entity
@Table(name = "results", schema = "s392251")
@ApplicationScoped
public class CheckAreaBean implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "x")
    private double x;
    @Column(name = "y")
    private double y;
    @Column(name = "r")
    private double r;
    @Column(name = "result")
    private boolean result;

    public CheckAreaBean() {
        super();
    }

    @Column(name = "x")
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Column(name = "y")
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Column(name = "r")
    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Column(name = "result")
    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckAreaBean)) return false;
        CheckAreaBean bean = (CheckAreaBean) o;
        return getId() == bean.getId() && Double.compare(getX(), bean.getX()) == 0 && Double.compare(getY(), bean.getY()) == 0 && Double.compare(getR(), bean.getR()) == 0 && isResult() == bean.isResult();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getX(), getY(), getR(), isResult());
    }

    @Override
    public String toString() {
        return "CheckAreaBean{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", result=" + result +
                '}';
    }
}

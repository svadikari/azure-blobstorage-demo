package com.shyam.azureblobstoragedemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Attribute
 */

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2018-11-14T19:51:20.915Z")

public class Attribute {
    @JsonProperty("attrName")
    @Size(max = 300)
    private String attrName;

    @JsonProperty("oldVal")
    @Size(max = 2000)
    private String oldVal;

    @JsonProperty("newVal")
    @Size(max = 2000)
    private String newVal;

    public Attribute attrName(String attrName) {
        this.attrName = attrName;
        return this;
    }

    /**
     * Get attrName
     *
     * @return attrName
     **/
    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Attribute oldVal(String oldVal) {
        this.oldVal = oldVal;
        return this;
    }

    public String getOldVal() {
        return oldVal;
    }

    public void setOldVal(String oldVal) {
        this.oldVal = oldVal;
    }

    public Attribute newVal(String newVal) {
        this.newVal = newVal;
        return this;
    }

    public String getNewVal() {
        return newVal;
    }

    public void setNewVal(String newVal) {
        this.newVal = newVal;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attribute attribute = (Attribute) o;
        return Objects.equals(this.attrName, attribute.attrName) &&
                Objects.equals(this.oldVal, attribute.oldVal) &&
                Objects.equals(this.newVal, attribute.newVal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attrName, oldVal, newVal);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Attribute {\n");

        sb.append("    attrName: ").append(toIndentedString(attrName)).append("\n");
        sb.append("    oldVal: ").append(toIndentedString(oldVal)).append("\n");
        sb.append("    newVal: ").append(toIndentedString(newVal)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}


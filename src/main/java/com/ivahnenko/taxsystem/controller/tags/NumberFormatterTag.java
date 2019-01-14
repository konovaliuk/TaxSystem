package com.ivahnenko.taxsystem.controller.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * {@code NumberFormatterTag} class responsible for formating money output.
 */
public class NumberFormatterTag extends SimpleTagSupport {

    private String number;

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public void doTag() throws JspException, IOException {
        try {
            String formattedNumber = "0,00";
            if (!number.equals("")) {
                number = number.replaceAll(",", ".");
                formattedNumber = String.format("%.2f", Double.parseDouble(number));
            }

            getJspContext().getOut().write(formattedNumber);
        } catch (Exception e) {
            throw new SkipPageException("Exception in formatting " + number
                    + " with format %.2f");
        }
    }

}
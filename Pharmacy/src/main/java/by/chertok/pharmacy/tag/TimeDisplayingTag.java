package by.chertok.pharmacy.tag;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeDisplayingTag extends TagSupport {
    private LocalDateTime time;
    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public void setTime(LocalDateTime time){
        this.time = time;
    }

    @Override
    public int doStartTag() throws JspTagException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        JspWriter out = pageContext.getOut();

        try{
            out.write((time).format(formatter));
        } catch (IOException e){
            throw new JspTagException(e);
        }

        return SKIP_BODY;
    }
}
